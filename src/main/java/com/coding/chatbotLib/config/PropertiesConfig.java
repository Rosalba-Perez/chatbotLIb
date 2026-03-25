package com.coding.chatbotLib.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class PropertiesConfig {
    
    @Value("${exceptioncoding.vectorStorePath}")
    private String vectorPath;
    @Value("${exceptioncoding.documentToLoad}")
    private List<Resource> documentToLoad;


    public String getVectorPath() {
        return vectorPath;
    }

    public void setVectorPath(String vectorPath) {
        this.vectorPath = vectorPath;
    }

    public List<Resource> getDocumentToLoad() {
        return documentToLoad;
    }

    public void setDocumentToLoad(List<Resource> documentToLoad) {
        this.documentToLoad = documentToLoad;
    }

}

