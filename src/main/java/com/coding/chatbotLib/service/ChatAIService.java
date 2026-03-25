package com.coding.chatbotLib.service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.jspecify.annotations.Nullable;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class ChatAIService {
    
    @Autowired
    ChatClient chatClient;

    
    @Autowired
    SimpleVectorStore simpleVectorStore;

    @Value("classpath:/templates/rag-template.st")
    private Resource ragTemplate;
    

    public Map<String,String> getPrompt(String message){
        return Map.of("answer", Objects.requireNonNull(
            this.chatClient.prompt().user(message).call().content())
        );
    }

    public @Nullable String getPromptByRAG(String message){
        List<Document> documents = simpleVectorStore.similaritySearch(SearchRequest.builder().query(message).topK(5).build());
        List<String> contentList = documents.stream().map(Document::getFormattedContent).toList();
        String unidos = String.join("\n", contentList);

        PromptTemplate promptTemplate = new PromptTemplate(ragTemplate);
        Prompt prompt = promptTemplate.create(Map.of("input", message, "documents", unidos));
        return chatClient.prompt(prompt).call().content();
    }
}
