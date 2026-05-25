package com.controller;

import com.alibaba.fastjson.JSONObject;
import com.entity.ConfigEntity;
import com.llm.MultiModelAIClient;
import com.service.AssistantChatService;
import com.service.ChatService;
import com.service.CommonService;
import com.service.ConfigService;
import com.utils.R;
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

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommonControllerAssistantRouteTest {

    @Mock
    private CommonService commonService;

    @Mock
    private ConfigService configService;

    @Mock
    private ChatService chatService;

    @Mock
    private AssistantChatService assistantChatService;

    @Captor
    private ArgumentCaptor<MultiModelAIClient.ChatRequest> chatRequestCaptor;

    private CommonController controller;

    @BeforeEach
    void setUp() {
        controller = new CommonController();
        ReflectionTestUtils.setField(controller, "commonService", commonService);
        ReflectionTestUtils.setField(controller, "configService", configService);
        ReflectionTestUtils.setField(controller, "chatService", chatService);
        ReflectionTestUtils.setField(controller, "assistantChatService", assistantChatService);
    }

    @Test
    void streamChatUsesFallbackServiceForAssistantModel() throws Exception {
        when(configService.selectOne(any())).thenReturn(configEntity());

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setMethod("POST");
        request.setContentType("application/json");
        request.setContent("{\"ask\":\"新手养鱼要先准备什么？\"}".getBytes(StandardCharsets.UTF_8));
        request.getSession().setAttribute("userId", 7L);
        request.getSession().setAttribute("username", "12");
        MockHttpServletResponse response = new MockHttpServletResponse();

        controller.streamChat("assistant", request, response);

        verify(assistantChatService).streamChatWithFallback(chatRequestCaptor.capture(), eq(response), eq(7L), eq("12"));
        assertEquals("assistant", chatRequestCaptor.getValue().getModel());
        assertEquals("新手养鱼要先准备什么？", chatRequestCaptor.getValue().getMessage());
    }

    @Test
    void chatUsesFallbackServiceForAssistantModel() throws Exception {
        when(configService.selectOne(any())).thenReturn(configEntity());
        when(assistantChatService.chatWithFallback(any(), eq(7L), eq("12"))).thenReturn("本地答案");

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setMethod("POST");
        request.setContentType("application/json");
        request.setContent("{\"ask\":\"金龙鱼怎么养\"}".getBytes(StandardCharsets.UTF_8));
        request.getSession().setAttribute("userId", 7L);
        request.getSession().setAttribute("username", "12");

        Object result = controller.chat("assistant", request);

        verify(assistantChatService).chatWithFallback(chatRequestCaptor.capture(), eq(7L), eq("12"));
        assertNotNull(result);
        assertEquals("本地答案", ((R) result).get("data"));
        assertEquals("金龙鱼怎么养", chatRequestCaptor.getValue().getMessage());
    }

    private ConfigEntity configEntity() {
        Map<String, Object> value = new HashMap<String, Object>();
        value.put("apiUrl", "https://example.com/chat");
        value.put("apiKey", "test-key");
        value.put("modelName", "test-model");
        ConfigEntity entity = new ConfigEntity();
        entity.setName("assistantModelSettings");
        entity.setValue(JSONObject.toJSONString(value));
        return entity;
    }
}
