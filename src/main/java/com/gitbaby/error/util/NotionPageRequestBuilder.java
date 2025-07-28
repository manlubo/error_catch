package com.gitbaby.error.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gitbaby.error.domain.Property;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class NotionPageRequestBuilder {
  private static final ObjectMapper MAPPER = new ObjectMapper();

  private String databaseId;
  private Map<String, Object> properties;
  private List<Map<String, Object>> children;

  public static NotionPageRequestBuilder create() {
    return new NotionPageRequestBuilder();
  }

  public NotionPageRequestBuilder database(String dbId) {
    this.databaseId = dbId;
    return this;
  }

  public NotionPageRequestBuilder properties(Map<String, Object> props) {
    this.properties = props;
    return this;
  }

  public NotionPageRequestBuilder children(List<Map<String, Object>> blocks) {
    this.children = blocks;
    return this;
  }

  public String build() {
    try {
      Map<String, Object> json = new LinkedHashMap<>();
      json.put("parent", Map.of("database_id", databaseId));
      json.put("properties", properties);
      json.put("children", children);

      return MAPPER.writeValueAsString(json);

    } catch (JsonProcessingException e) {
      throw new RuntimeException("JSON 직렬화 실패", e);
    }
  }
}
