package com.gitbaby.error.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class DiscordUploadTest {
  @Autowired
  DiscordUploadService service;

  @Test
  @DisplayName("웹훅 전송 테스트")
  public void testWebhookUpload() {
    service.send("ff0000","Exception 발생", "테스트 서비스", "클래스 패스");
  }
}
