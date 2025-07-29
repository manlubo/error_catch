package com.gitbaby.error.util;

import com.gitbaby.error.config.NotionConfig;

import com.gitbaby.error.domain.en.CodeLanguage;
import com.gitbaby.error.service.NotionUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Log4j2
public class NotionLogger {

  private final NotionConfig config;
  private final NotionUploadService notionUploadService;

  public void send(Throwable ex) {
    String rootError = ExceptionFormater.formatRootStackTrace(ex);


    Map<String, Object> props = PropertiesBuilder.create()
      .title("문서 이름", "")
      .multiSelect("카테고리", "Spring")
      .date("생성 일시")
      .build();

    List<Map<String, Object>> blocks = BlockBuilder.create()
      .heading2("Stacktrace")
      .code(CodeLanguage.JAVA, rootError)
      .build();

    String json = NotionPageRequestBuilder.create()
      .database(config.getDatabaseId())
      .properties(props)
      .children(blocks)
      .build();

    notionUploadService.upload(json);
  }
}
