package com.gitbaby.error.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gitbaby.error.domain.en.CodeLanguage;
import com.gitbaby.error.util.BlockBuilder;
import com.gitbaby.error.util.NotionPageRequestBuilder;
import com.gitbaby.error.util.PropertiesBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class NotionUploadTest {
  @Autowired
  private NotionUploadService notionUploadService;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  @DisplayName("📄 노션 페이지 업로드 - 카테고리, 문서이름, 생성일시 + 헤딩/코드 블럭 구성")
  void uploadToNotionPage() throws JsonProcessingException {
    String requestBody = NotionPageRequestBuilder.create()
      .database("239720ef233480d4abbdf400bf39ac29") // 실제 DB ID로 교체해줘
      .properties(
        PropertiesBuilder.create()
          .multiSelect("카테고리", "Spring")
          .title("문서 이름", "🧪 테스트 문서입니다")
          .date("생성 일시")
          .build()
      )
      .children(
        BlockBuilder.create()
          .heading2("💥 Stacktrace")
          .code(CodeLanguage.JAVA,
            "java.lang.RuntimeException: 테스트 예외 발생!\n" +
              "\tat com.gitbaby.error.Controller.ErrorTestController.fail(ErrorTestController.java:13)\n" +
              "\tat org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:885)\n" +
              "\t... (생략) ..."
          )
          .build()
      )
      .build();

    String pretty = objectMapper
      .writerWithDefaultPrettyPrinter()
      .writeValueAsString(requestBody);
    log.info(pretty);
    notionUploadService.upload(requestBody);
  }

}
