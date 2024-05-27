package com.hat.hereandthere.tourservice.common.config;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Value("${tour-api.key}")
    private String apiKey;

    @Value("${tour-api.url}")
    private String url;

    private String API_LANGUAGE_PATH = "B551011/KorService1";

    @Bean
    public WebClient tourApiWebClient() {
        Map<String, String> defaultVariables = new HashMap<>();
        defaultVariables.put("serviceKey", apiKey);
        defaultVariables.put("MobileOS", "ETC");
        defaultVariables.put("MobileApp", "HAT");
        defaultVariables.put("_type", "json");

        return WebClient
            .builder()
            .baseUrl(url + "/" + API_LANGUAGE_PATH)
            .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(2 * 1024 * 1024))
            .defaultUriVariables(defaultVariables)
            .build();
    }
}