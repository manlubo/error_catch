package com.gitbaby.error.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gitbaby.error.config.NotionConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Log4j2
public class NotionLogger {
  private final NotionConfig config;
  private final ObjectMapper objectMapper;
  private final NotionConfig notionConfig;

  public void send(Throwable ex) {
    try {
      String json = buildJson(ex); // 예외 내용 + 메서드명 → JSON 만들기

      HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create("https://api.notion.com/v1/pages"))
        .header("Authorization", "Bearer " + config.getToken())
        .header("Notion-Version", "2022-06-28")
        .header("Content-Type", "application/json")
        .POST(HttpRequest.BodyPublishers.ofString(json))
        .build();

      HttpResponse<String> response = HttpClient.newHttpClient()
        .send(request, HttpResponse.BodyHandlers.ofString());

      log.info("✅ Notion 전송 성공: {}", response.statusCode());

    } catch (Exception e) {
      log.warn("❌ Notion 전송 실패: {}", e.getMessage());
    }
  }

  private String buildJson(Throwable ex) throws JsonProcessingException {
    Throwable rootCause = getRootCause(ex);
    String rootStack = getStackTrace(rootCause);
    DateTimeFormatter formatter = DateTimeFormatter
      .ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX"); // or use .withZone(ZoneOffset.ofHours(9))
    String now = ZonedDateTime.now(ZoneId.of("Asia/Seoul")).format(formatter);

    Map<String, Object> title = new LinkedHashMap<>();
    title.put("type", "text");
    title.put("text", Map.of("content", rootCause.toString()));

    Map<String, Object> headingText = new LinkedHashMap<>();
    headingText.put("type", "text");
    headingText.put("text", Map.of("content", "💥 주요 오류 로그"));

    Map<String, Object> headingBlock = new LinkedHashMap<>();
    headingBlock.put("object", "block");
    headingBlock.put("type", "heading_2");
    Map<String, Object> headingContent = new LinkedHashMap<>();
    headingContent.put("rich_text", List.of(headingText));
    headingBlock.put("heading_2", headingContent);


    Map<String, Object> codeText = new LinkedHashMap<>();
    codeText.put("type", "text");
    codeText.put("text", Map.of("content", rootStack.replace("\r\n", "\n")));

    Map<String, Object> codeBlock = new LinkedHashMap<>();
    codeBlock.put("object", "block");
    codeBlock.put("type", "code");
    Map<String, Object> codeContent = new LinkedHashMap<>();
    codeContent.put("language", "java");
    codeContent.put("rich_text", List.of(codeText));
    codeBlock.put("code", codeContent);

    Map<String, Object> body = new LinkedHashMap<>();
    body.put("parent", Map.of("database_id", notionConfig.getDatabaseId()));
    Map<String, Object> dateMap = new LinkedHashMap<>();
    dateMap.put("start", now);

    Map<String, Object> properties = new LinkedHashMap<>();
    properties.put("문서 이름", Map.of("title", List.of(title)));
    properties.put("카테고리", Map.of("multi_select", List.of(Map.of("name", "Spring"))));
    properties.put("생성 일시", Map.of("date", dateMap));

    body.put("properties", properties);

    body.put("children", List.of(headingBlock, codeBlock));

    String pretty = objectMapper
      .writerWithDefaultPrettyPrinter()
      .writeValueAsString(body); // Map or Object

    log.info(pretty);
    return objectMapper.writeValueAsString(body);
  }

  // 루트 원인 찾아주는 함수
  private Throwable getRootCause(Throwable ex) {
    Throwable root = ex;
    while (root.getCause() != null && root.getCause() != root) {
      root = root.getCause();
    }
    return root;
  }

  // 루트 스택트레이스 문자열 추출
  private String getStackTrace(Throwable ex) {
    StringWriter sw = new StringWriter();
    ex.printStackTrace(new PrintWriter(sw));
    String[] lines = sw.toString().split("\n");

    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < Math.min(lines.length, 30); i++) {
      if (sb.length() + lines[i].length() + 1 > 1900) break;
      sb.append(lines[i]).append("\n");
    }

    return sb.toString();
  }

}
