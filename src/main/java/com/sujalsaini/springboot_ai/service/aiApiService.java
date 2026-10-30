package com.sujalsaini.springboot_ai.service;

import org.springframework.stereotype.Service;
import com.sujalsaini.springboot_ai.utils.emailService;
import com.sujalsaini.springboot_ai.utils.weatherService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;

@Service
public class aiApiService {

  private final ChatClient chatClient;

  private final emailService emailService;
  private final weatherService weatherService;

  public aiApiService(weatherService weatherService, emailService emailService, ChatClient chatClient) {
    this.emailService = emailService;
    this.weatherService = weatherService;
    this.chatClient = chatClient;
  }

  public String modelQuery(String conversationId , String question) {
    return chatClient.prompt()
        .user(question)
        .advisors(advisor -> advisor.param(
            ChatMemory.CONVERSATION_ID,
            conversationId))
        .tools(emailService, weatherService)
        .call()
        .content();
  }

}
