package com.gitbaby.error.config;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Log4j2
@SpringBootTest
public class NotionConfigTest {
  @Autowired
  private NotionConfig notionConfig;

  @Test
  @DisplayName("notionConfig 객체 취득")
  public void testExist(){
    log.info(notionConfig);
  }

  @Test
  @DisplayName("객체 내 정보 접근 테스트")
  public void testAcsess(){
    log.info(notionConfig.getDatabaseId());
    log.info(notionConfig.getToken());
  }
}
