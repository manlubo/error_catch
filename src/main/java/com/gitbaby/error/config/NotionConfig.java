package com.gitbaby.error.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@Getter
public class NotionConfig {
  @Value("${notion.token}")
  private String token;
  @Value("${notion.database-id}")
  private String databaseId;
}
