package com.gitbaby.error.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DiscordUploadService {
  @Value("${discord.webhook-url}")
  private String webhookUrl;

  public void send(String colorHex, String title, String classPath, String errorType) {
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    Map<String, Object> embed = Map.of(
      "title", title,
      "color", hexToDiscordColor(colorHex), // 빨강
      "fields", List.of(
        Map.of("name", "경로", "value", classPath),
        Map.of("name", "오류 정보", "value", errorType)
      ),
      "footer", Map.of("text", "Error-Catch"),
      "timestamp", OffsetDateTime.now().toString()
    );

    Map<String, Object> body = Map.of(
      "username", "ErrorBot",
      "avatar_url", "https://i.imgur.com/AfFp7pu.png",
      "embeds", List.of(embed)
    );

    HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
    restTemplate.postForEntity(webhookUrl, request, String.class);
    System.out.println("디스코드 전송 완료");
  }


  // 컬러값 변환
  private static int hexToDiscordColor(String hex) {
    if (!hex.startsWith("#")) {
      hex = "#" + hex;
    }
    return Integer.parseInt(hex.substring(1), 16);
  }


}
