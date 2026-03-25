package com.coding.chatbotLib.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coding.chatbotLib.service.ChatAIService;

@RestController
@RequestMapping("/AI")
public class ChatAIController {

    @Autowired
    private ChatAIService service;

    @PostMapping()
    public Map<String,String> generateResponse(@RequestBody String question){
        return this.service.getPrompt(question);
    }

    @PostMapping("/movies")
    public String generatePromptByRAG(@RequestBody String message){
        return this.service.getPromptByRAG(message);
    }
}
