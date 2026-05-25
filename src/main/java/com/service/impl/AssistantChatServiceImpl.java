package com.service.impl;

import com.llm.MultiModelAIClient;
import com.service.AssistantChatService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AssistantChatServiceImpl implements AssistantChatService {

    @Autowired
    private LocalAssistantFallbackService localAssistantFallbackService;

    @Override
    public String chat(MultiModelAIClient.ChatRequest request) throws Exception {
        return MultiModelAIClient.chat(request);
    }

    @Override
    public void streamChat(MultiModelAIClient.ChatRequest request, HttpServletResponse response) throws Exception {
        MultiModelAIClient.streamChatDirect(request, response);
    }

    @Override
    public String chatWithFallback(MultiModelAIClient.ChatRequest request, Long userId, String username) throws Exception {
        try {
            String reply = MultiModelAIClient.chat(request);
            if (StringUtils.isNotBlank(reply)) {
                return reply.trim();
            }
        } catch (Exception e) {
            log.warn("Assistant provider unavailable, switch to local fallback: {}", e.getMessage());
        }
        return localAssistantFallbackService.generateReply(request, userId, username);
    }

    @Override
    public void streamChatWithFallback(MultiModelAIClient.ChatRequest request,
                                       HttpServletResponse response,
                                       Long userId,
                                       String username) throws Exception {
        String reply = chatWithFallback(request, userId, username);
        writeStreamResponse(response, reply);
    }

    @Override
    public void updateModelConfig(String modelName, String apiUrl, String apiKey, String actualModel) {
        MultiModelAIClient.updateModelConfig(modelName, apiUrl, apiKey, actualModel);
    }

    private void writeStreamResponse(HttpServletResponse response, String reply) throws IOException {
        List<String> chunks = splitChunks(StringUtils.defaultString(reply));
        for (String chunk : chunks) {
            writeSseMessage(response, chunk);
        }
        writeSseMessage(response, "[DONE]");
        response.flushBuffer();
    }

    private List<String> splitChunks(String text) {
        List<String> chunks = new ArrayList<String>();
        if (StringUtils.isBlank(text)) {
            chunks.add("助手暂时没有返回内容，请稍后再试。");
            return chunks;
        }
        StringBuilder current = new StringBuilder();
        char[] chars = text.toCharArray();
        for (char currentChar : chars) {
            current.append(currentChar);
            boolean hitBoundary = "，。！？；\n".indexOf(currentChar) >= 0;
            if ((hitBoundary && current.length() >= 18) || current.length() >= 36) {
                chunks.add(current.toString());
                current.setLength(0);
            }
        }
        if (current.length() > 0) {
            chunks.add(current.toString());
        }
        return chunks;
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
}
