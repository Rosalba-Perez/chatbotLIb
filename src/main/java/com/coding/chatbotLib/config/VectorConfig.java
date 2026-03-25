package com.coding.chatbotLib.config;

import java.io.File;
import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VectorConfig {

    @Bean
    SimpleVectorStore simpleVectoreStore(EmbeddingModel embeddingModel, PropertiesConfig propertiesConfig) {
        var vectorStore = SimpleVectorStore.builder(embeddingModel).build();

        File vectorStoreFile = new File(propertiesConfig.getVectorPath());

        if(vectorStoreFile.exists()){
            vectorStore.load(vectorStoreFile);
        } else {
            propertiesConfig.getDocumentToLoad().forEach(document -> {
                TikaDocumentReader documentReader = new TikaDocumentReader(document);
                List<Document> docs = documentReader.get();
                TokenTextSplitter textSplitter = new TokenTextSplitter();
                List<Document> splitDocs = textSplitter.apply(docs);
                vectorStore.add(splitDocs);

            });
            vectorStore.save(vectorStoreFile);
        }
        return vectorStore;
    }
    
}
