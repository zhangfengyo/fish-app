package com.service.impl;

import com.llm.MultiModelAIClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AssistantChatServiceImplTest {

    @Mock
    private LocalAssistantFallbackService localAssistantFallbackService;

    private AssistantChatServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new AssistantChatServiceImpl();
        ReflectionTestUtils.setField(service, "localAssistantFallbackService", localAssistantFallbackService);
    }

    @Test
    void chatWithFallbackReturnsLocalReplyWhenProviderFails() throws Exception {
        MultiModelAIClient.updateModelConfig("assistant", "http://127.0.0.1:1/v1/chat/completions", "test-key", "test-model");

        MultiModelAIClient.ChatRequest request = new MultiModelAIClient.ChatRequest();
        request.setModel("assistant");
        request.setMessage("金龙鱼怎么养");

        when(localAssistantFallbackService.generateReply(request, 7L, "12")).thenReturn("本地答案");

        String reply = service.chatWithFallback(request, 7L, "12");

        assertEquals("本地答案", reply);
        verify(localAssistantFallbackService).generateReply(request, 7L, "12");
    }
}
