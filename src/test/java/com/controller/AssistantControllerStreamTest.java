package com.controller;

import com.alibaba.fastjson.JSONObject;
import com.entity.ChatEntity;
import com.entity.ConfigEntity;
import com.llm.MultiModelAIClient;
import com.service.AssistantChatService;
import com.service.ChatService;
import com.service.ConfigService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AssistantControllerStreamTest {

    @Mock
    private ConfigService configService;

    @Mock
    private ChatService chatService;

    @Mock
    private AssistantChatService assistantChatService;

    @Captor
    private ArgumentCaptor<MultiModelAIClient.ChatRequest> chatRequestCaptor;

    private AssistantController controller;

    @BeforeEach
    void setUp() {
        controller = new AssistantController();
        ReflectionTestUtils.setField(controller, "configService", configService);
        ReflectionTestUtils.setField(controller, "chatService", chatService);
        ReflectionTestUtils.setField(controller, "assistantChatService", assistantChatService);
    }

    @Test
    void replyStreamWritesSseErrorWhenAskIsBlank() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        controller.replyStream(Collections.singletonMap("ask", "   "), request, response);

        assertEquals("text/event-stream;charset=UTF-8", response.getContentType());
        assertTrue(response.getContentAsString().contains("Assistant question cannot be empty"));
        assertTrue(response.getContentAsString().contains("[DONE]"));
        verify(assistantChatService, never()).streamChatWithFallback(any(), eq(response), any(), any());
    }

    @Test
    void replyStreamBuildsHistoryAndDelegatesToAssistantChatService() throws Exception {
        when(configService.selectOne(any()))
                .thenReturn(configEntity("assistantSettings", new HashMap<String, Object>() {{
                    put("assistantName", "观赏鱼助手Pro");
                    put("replyStyle", "专业、温和");
                    put("systemPrompt", "不确定时明确说明仅供参考。");
                }}))
                .thenReturn(configEntity("assistantModelSettings", new HashMap<String, Object>() {{
                    put("apiUrl", "https://example.com/chat");
                    put("apiKey", "test-key");
                    put("modelName", "test-model");
                }}));

        ChatEntity first = new ChatEntity();
        first.setAsk("红龙鱼需要多大鱼缸？");
        first.setReply("建议先从 120cm 鱼缸起步。");
        when(chatService.selectList(any())).thenReturn(Arrays.asList(first));

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.getSession().setAttribute("userId", 7L);
        request.getSession().setAttribute("username", "12");
        MockHttpServletResponse response = new MockHttpServletResponse();

        controller.replyStream(Collections.singletonMap("ask", "锦鲤鱼适合什么水温？"), request, response);

        assertEquals("text/event-stream;charset=UTF-8", response.getContentType());
        assertEquals("no-cache", response.getHeader("Cache-Control"));
        assertEquals("keep-alive", response.getHeader("Connection"));

        verify(assistantChatService).updateModelConfig("assistant", "https://example.com/chat", "test-key", "test-model");
        verify(assistantChatService).streamChatWithFallback(chatRequestCaptor.capture(), eq(response), eq(7L), eq("12"));

        MultiModelAIClient.ChatRequest chatRequest = chatRequestCaptor.getValue();
        assertNotNull(chatRequest);
        assertEquals("assistant", chatRequest.getModel());
        assertEquals("锦鲤鱼适合什么水温？", chatRequest.getMessage());
        assertTrue(chatRequest.hasHistory());
        assertEquals(2, chatRequest.getHistory().size());
        assertTrue(chatRequest.getSystemPrompt().contains("观赏鱼助手Pro"));
    }

    @Test
    void replyStreamSkipsBrokenHttpErrorsFromHistory() throws Exception {
        when(configService.selectOne(any()))
                .thenReturn(configEntity("assistantSettings", new HashMap<String, Object>()))
                .thenReturn(configEntity("assistantModelSettings", new HashMap<String, Object>() {{
                    put("apiUrl", "https://example.com/chat");
                    put("apiKey", "test-key");
                    put("modelName", "test-model");
                }}));

        ChatEntity broken = new ChatEntity();
        broken.setAsk("旧问题");
        broken.setReply("错误：HTTP 错误：401 - invalid api key");
        when(chatService.selectList(any())).thenReturn(Arrays.asList(broken));

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.getSession().setAttribute("userId", 7L);
        request.getSession().setAttribute("username", "12");
        MockHttpServletResponse response = new MockHttpServletResponse();

        controller.replyStream(Collections.singletonMap("ask", "再问一次"), request, response);

        verify(assistantChatService).streamChatWithFallback(chatRequestCaptor.capture(), eq(response), eq(7L), eq("12"));
        MultiModelAIClient.ChatRequest chatRequest = chatRequestCaptor.getValue();
        assertTrue(chatRequest.hasHistory());
        assertEquals(1, chatRequest.getHistory().size());
        assertEquals("user", chatRequest.getHistory().get(0).getRole());
    }

    private ConfigEntity configEntity(String name, Map<String, Object> value) {
        ConfigEntity entity = new ConfigEntity();
        entity.setName(name);
        entity.setValue(JSONObject.toJSONString(value));
        return entity;
    }
}
