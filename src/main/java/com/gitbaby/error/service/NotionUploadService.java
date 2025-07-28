package com.gitbaby.error.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotionUploadService {

  private static final String NOTION_API_URL = "https://api.notion.com/v1/pages";
  private static final String NOTION_VERSION = "2022-06-28";

  private final RestTemplate restTemplate = new RestTemplate();

  @Value("${notion.token}")
  private String notionToken;

  public void upload(String jsonBody) {
    try {
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);
      headers.setBearerAuth(notionToken);
      headers.set("Notion-Version", NOTION_VERSION);

      HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);
      ResponseEntity<String> response = restTemplate.postForEntity(NOTION_API_URL, entity, String.class);

      if (response.getStatusCode().is2xxSuccessful()) {
        log.info("✅ 노션 업로드 성공: {}", response.getBody());
      } else {
        log.warn("❌ 노션 응답 코드: {}", response.getStatusCode());
        log.warn("❌ 응답 내용: {}", response.getBody());
      }

    } catch (Exception e) {
      log.error("🚨 노션 업로드 중 오류 발생", e);
    }
  }
}
