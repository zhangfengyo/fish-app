package com.controller;

import com.alibaba.fastjson.JSONObject;
import com.annotation.IgnoreAuth;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.entity.ChatEntity;
import com.entity.ConfigEntity;
import com.llm.MultiModelAIClient;
import com.service.AssistantChatService;
import com.service.ChatService;
import com.utils.R;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("/assistant")
public class AssistantController {

    private static final String SETTINGS_CONFIG_NAME = "assistantSettings";
    private static final String MODEL_CONFIG_NAME = "assistantModelSettings";
    private static final String DEFAULT_API_URL = "https://ark.cn-beijing.volces.com/api/v3/chat/completions";
    private static final String DEFAULT_MODEL_NAME = "doubao-seed-2-0-code-preview-260215";
    private static final String DEFAULT_TEST_PROMPT = "请用中文回复：模型连接正常。";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ChatService chatService;

    @Autowired
    private AssistantChatService assistantChatService;

    @IgnoreAuth
    @RequestMapping("/config")
    public R getConfig() {
        return R.ok().put("data", readAssistantSettings());
    }

    @RequestMapping("/config/save")
    public R saveConfig(@RequestBody Map<String, Object> payload) {
        Map<String, Object> settings = mergeAssistantSettings(payload);
        saveConfigEntry(SETTINGS_CONFIG_NAME, settings);
        return R.ok().put("data", settings);
    }

    @RequestMapping("/model-config")
    public R getModelConfig() {
        return R.ok().put("data", readModelSettings());
    }

    @RequestMapping("/model-config/save")
    public R saveModelConfig(@RequestBody Map<String, Object> payload) {
        Map<String, Object> settings = mergeModelSettings(payload);
        saveConfigEntry(MODEL_CONFIG_NAME, settings);
        refreshAssistantModelConfig(settings);
        return R.ok().put("data", settings);
    }

    @RequestMapping("/model-config/models")
    public R fetchAvailableModels(@RequestBody Map<String, Object> payload) throws Exception {
        Map<String, Object> settings = mergeModelSettings(payload);
        String apiUrl = readString(settings, "apiUrl");
        String apiKey = readString(settings, "apiKey");
        if (StringUtils.isBlank(apiUrl) || StringUtils.isBlank(apiKey)) {
            return R.error("API URL and API Key are required");
        }
        return R.ok().put("data", MultiModelAIClient.fetchAvailableModels(apiUrl, apiKey));
    }

    @RequestMapping("/model-config/test")
    public R testModelConfig(@RequestBody Map<String, Object> payload) throws Exception {
        Map<String, Object> settings = mergeModelSettings(payload);
        if (StringUtils.isBlank(readString(settings, "apiUrl"))
                || StringUtils.isBlank(readString(settings, "apiKey"))
                || StringUtils.isBlank(readString(settings, "modelName"))) {
            return R.error("Assistant model configuration is incomplete");
        }

        refreshAssistantModelConfig(settings);

        MultiModelAIClient.ChatRequest chatRequest = new MultiModelAIClient.ChatRequest();
        chatRequest.setModel("assistant");
        chatRequest.setMessage(firstNonBlank(readString(settings, "testPrompt"), DEFAULT_TEST_PROMPT));
        chatRequest.setSystemPrompt("Please answer in Simplified Chinese. If the model is available, return a short normal reply.");
        chatRequest.setTemperature(0.1);
        chatRequest.setMaxTokens(200);
        return R.ok().put("data", assistantChatService.chat(chatRequest));
    }

    @RequestMapping("/reply")
    public R reply(@RequestBody Map<String, Object> payload, HttpServletRequest request) throws Exception {
        String ask = payload.get("ask") == null ? "" : String.valueOf(payload.get("ask")).trim();
        if (StringUtils.isBlank(ask)) {
            return R.error("Assistant question cannot be empty");
        }

        Map<String, Object> assistantSettings = readAssistantSettings();
        refreshAssistantModelConfig(readModelSettings());
        MultiModelAIClient.ChatRequest chatRequest = buildChatRequest(ask, request, assistantSettings);

        return R.ok().put("data", assistantChatService.chatWithFallback(
                chatRequest,
                readSessionUserId(request),
                readSessionUsername(request)
        ));
    }

    @RequestMapping(value = "/reply/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public void replyStream(@RequestBody Map<String, Object> payload,
                            HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        prepareStreamResponse(response);

        String ask = payload.get("ask") == null ? "" : String.valueOf(payload.get("ask")).trim();
        if (StringUtils.isBlank(ask)) {
            writeSseMessage(response, "Assistant question cannot be empty");
            writeSseMessage(response, "[DONE]");
            return;
        }

        Map<String, Object> assistantSettings = readAssistantSettings();
        refreshAssistantModelConfig(readModelSettings());
        MultiModelAIClient.ChatRequest chatRequest = buildChatRequest(ask, request, assistantSettings);

        try {
            assistantChatService.streamChatWithFallback(
                    chatRequest,
                    response,
                    readSessionUserId(request),
                    readSessionUsername(request)
            );
        } catch (Exception e) {
            writeSseMessage(response, "错误：" + e.getMessage());
            writeSseMessage(response, "[DONE]");
        }
    }

    private Map<String, Object> readAssistantSettings() {
        Map<String, Object> parsed = readConfigMap(SETTINGS_CONFIG_NAME);
        return mergeAssistantSettings(parsed);
    }

    private Map<String, Object> readModelSettings() {
        Map<String, Object> parsed = readConfigMap(MODEL_CONFIG_NAME);
        return mergeModelSettings(parsed);
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> readConfigMap(String configName) {
        ConfigEntity config = readConfigEntity(configName);
        if (config == null || StringUtils.isBlank(config.getValue())) {
            return null;
        }
        try {
            return JSONObject.parseObject(config.getValue(), Map.class);
        } catch (Exception e) {
            return null;
        }
    }

    private void saveConfigEntry(String configName, Map<String, Object> data) {
        ConfigEntity config = readConfigEntity(configName);
        if (config == null) {
            config = new ConfigEntity();
            config.setName(configName);
            config.setType("1");
        }
        config.setValue(JSONObject.toJSONString(data));
        if (config.getId() == null) {
            jdbcTemplate.update(
                    "INSERT INTO config(name, value, type) VALUES (?, ?, ?)",
                    config.getName(),
                    config.getValue(),
                    config.getType()
            );
        } else {
            jdbcTemplate.update(
                    "UPDATE config SET value = ?, type = ? WHERE id = ?",
                    config.getValue(),
                    config.getType(),
                    config.getId()
            );
        }
    }

    private ConfigEntity readConfigEntity(String configName) {
        List<ConfigEntity> result = jdbcTemplate.query(
                "SELECT id, name, value, type, url FROM config WHERE name = ? LIMIT 1",
                new Object[]{configName},
                (rs, rowNum) -> {
                    ConfigEntity entity = new ConfigEntity();
                    entity.setId(rs.getLong("id"));
                    entity.setName(rs.getString("name"));
                    entity.setValue(rs.getString("value"));
                    entity.setType(rs.getString("type"));
                    entity.setUrl(rs.getString("url"));
                    return entity;
                }
        );
        return result.isEmpty() ? null : result.get(0);
    }

    private Map<String, Object> mergeAssistantSettings(Map<String, Object> source) {
        Map<String, Object> settings = new LinkedHashMap<String, Object>();
        settings.put("assistantName", firstNonBlank(readString(source, "assistantName"), "观赏鱼助手Pro"));
        settings.put("welcomeTitle", firstNonBlank(readString(source, "welcomeTitle"), "你好，我是你的观赏鱼顾问。"));
        settings.put("welcomeDescription", firstNonBlank(readString(source, "welcomeDescription"), "我会用更稳定的中文，回答饲养、鱼种和系统使用问题。"));
        settings.put("quickPromptOne", firstNonBlank(readString(source, "quickPromptOne"), "红龙鱼适合什么水温？"));
        settings.put("quickPromptTwo", firstNonBlank(readString(source, "quickPromptTwo"), "新手养鱼要先准备什么？"));
        settings.put("replyStyle", firstNonBlank(readString(source, "replyStyle"), "专业、温和、偏观赏鱼顾问口吻"));
        settings.put("systemPrompt", firstNonBlank(readString(source, "systemPrompt"), "遇到不确定的问题时，请明确说明仅供参考，并尽量给出分点建议。"));
        return settings;
    }

    private Map<String, Object> mergeModelSettings(Map<String, Object> source) {
        Map<String, Object> settings = new LinkedHashMap<String, Object>();
        settings.put("apiUrl", firstNonBlank(readString(source, "apiUrl"), DEFAULT_API_URL));
        settings.put("apiKey", readString(source, "apiKey"));
        settings.put("modelName", firstNonBlank(readString(source, "modelName"), DEFAULT_MODEL_NAME));
        settings.put("testPrompt", firstNonBlank(readString(source, "testPrompt"), DEFAULT_TEST_PROMPT));
        return settings;
    }

    private String buildSystemPrompt(Map<String, Object> settings) {
        String assistantName = readString(settings, "assistantName");
        String replyStyle = readString(settings, "replyStyle");
        String extraPrompt = readString(settings, "systemPrompt");
        return String.join("",
                "你是", assistantName, "，服务于观赏鱼管理系统。",
                "请默认使用简体中文回答，整体语气保持", replyStyle, "。",
                "你要优先回答观赏鱼饲养、水温、水质、换水、喂食、鱼种差异、疾病预防、日常照护，以及本系统功能使用问题。",
                "如果用户询问系统功能，请结合资讯、教学、提醒、商城、论坛、个人中心和后台数据大屏进行解释。",
                "回答时尽量准确、稳定，优先用分点列表，先给结论，再给建议。",
                "如果结论不确定，要明确说明“仅供参考”，不要编造专业事实。",
                firstNonBlank(extraPrompt, "")
        );
    }

    private MultiModelAIClient.ChatRequest buildChatRequest(String ask,
                                                            HttpServletRequest request,
                                                            Map<String, Object> assistantSettings) {
        MultiModelAIClient.ChatRequest chatRequest = new MultiModelAIClient.ChatRequest();
        chatRequest.setModel("assistant");
        chatRequest.setMessage(ask);
        chatRequest.setSystemPrompt(buildSystemPrompt(assistantSettings));
        chatRequest.setTemperature(0.2);
        chatRequest.setMaxTokens(700);
        appendHistory(chatRequest, request);
        return chatRequest;
    }

    private void appendHistory(MultiModelAIClient.ChatRequest chatRequest, HttpServletRequest request) {
        Long userId = readSessionUserId(request);
        if (userId == null) {
            return;
        }

        List<ChatEntity> chatList = chatService.selectList(
                new EntityWrapper<ChatEntity>().eq("userid", userId).orderBy("addtime", true)
        );
        List<MultiModelAIClient.Message> history = new ArrayList<MultiModelAIClient.Message>();
        int start = Math.max(0, chatList.size() - 12);
        for (int i = start; i < chatList.size(); i++) {
            ChatEntity chat = chatList.get(i);
            if (isUsableHistoryText(chat.getAsk())) {
                history.add(new MultiModelAIClient.Message("user", chat.getAsk()));
            }
            if (isUsableHistoryText(chat.getReply())) {
                history.add(new MultiModelAIClient.Message("assistant", chat.getReply()));
            }
        }
        if (!history.isEmpty()) {
            chatRequest.setHistory(history);
        }
    }

    private void refreshAssistantModelConfig(Map<String, Object> settings) {
        assistantChatService.updateModelConfig(
                "assistant",
                readString(settings, "apiUrl"),
                readString(settings, "apiKey"),
                readString(settings, "modelName")
        );
    }

    private void prepareStreamResponse(HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/event-stream;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Connection", "keep-alive");
    }

    private void writeSseMessage(HttpServletResponse response, String content) throws IOException {
        String normalized = content == null ? "" : content.replace("\r\n", "\n").replace('\r', '\n');
        String[] lines = normalized.split("\n", -1);
        StringBuilder builder = new StringBuilder();
        for (String line : lines) {
            builder.append("data: ").append(line).append("\n");
        }
        builder.append("\n");
        response.getOutputStream().write(builder.toString().getBytes(StandardCharsets.UTF_8));
        response.flushBuffer();
    }

    private boolean isUsableHistoryText(String text) {
        if (StringUtils.isBlank(text)) {
            return false;
        }
        String normalized = text.trim();
        String lower = normalized.toLowerCase(Locale.ROOT);
        if (lower.startsWith("错误：http 错误")
                || lower.startsWith("http 错误")
                || lower.contains("\"type\":\"unauthorized\"")
                || lower.contains("authenticationerror")
                || lower.contains("<html>")
                || lower.contains("assistant model configuration is incomplete")) {
            return false;
        }
        long questionMarks = normalized.chars().filter(ch -> ch == '?').count();
        return questionMarks < Math.max(4, normalized.length() / 2);
    }

    private Long readSessionUserId(HttpServletRequest request) {
        Object userId = request.getSession().getAttribute("userId");
        if (userId instanceof Long) {
            return (Long) userId;
        }
        if (userId != null) {
            try {
                return Long.valueOf(String.valueOf(userId));
            } catch (NumberFormatException ignored) {
                return null;
            }
        }
        return null;
    }

    private String readSessionUsername(HttpServletRequest request) {
        Object username = request.getSession().getAttribute("username");
        return username == null ? null : String.valueOf(username);
    }

    private String readString(Map<String, Object> source, String key) {
        if (source == null || source.get(key) == null) {
            return null;
        }
        String value = String.valueOf(source.get(key)).trim();
        return StringUtils.isBlank(value) ? null : value;
    }

    private String firstNonBlank(String... values) {
        if (values == null) {
            return null;
        }
        for (String value : values) {
            if (StringUtils.isNotBlank(value)) {
                return value.trim();
            }
        }
        return null;
    }
}
