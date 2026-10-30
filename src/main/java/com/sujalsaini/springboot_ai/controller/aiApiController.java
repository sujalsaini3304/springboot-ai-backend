package com.sujalsaini.springboot_ai.controller;

import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sujalsaini.springboot_ai.DTO.AskRequest;
import com.sujalsaini.springboot_ai.service.aiApiService;

// import org.springframework.web.bind.annotation.CrossOrigin;
// import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/ai")
public class aiApiController {

    private final aiApiService aiApiService;

    public aiApiController(aiApiService aiApiService) {
        this.aiApiService = aiApiService;
    }

    @PostMapping("/ask")
    public String askToAi(@RequestBody AskRequest request) {
        return aiApiService.modelQuery( request.getConversationId() ,  request.getQuestion());
    }

}
