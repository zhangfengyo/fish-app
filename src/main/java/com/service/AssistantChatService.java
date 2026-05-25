package com.service;

import com.llm.MultiModelAIClient;

import javax.servlet.http.HttpServletResponse;

public interface AssistantChatService {

    String chat(MultiModelAIClient.ChatRequest request) throws Exception;

    void streamChat(MultiModelAIClient.ChatRequest request, HttpServletResponse response) throws Exception;

    String chatWithFallback(MultiModelAIClient.ChatRequest request, Long userId, String username) throws Exception;

    void streamChatWithFallback(MultiModelAIClient.ChatRequest request, HttpServletResponse response, Long userId, String username) throws Exception;

    void updateModelConfig(String modelName, String apiUrl, String apiKey, String actualModel);
}
