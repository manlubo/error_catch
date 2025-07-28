package com.gitbaby.error.domain;

import com.gitbaby.error.domain.en.Color;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Property {

  private final String key;
  private final Map<String, Object> value;

  private Property(String key, Map<String, Object> value) {
    this.key = key;
    this.value = value;
  }

  public static Property title(String key, String content) {
    Map<String, Object> title = Map.of(
      "title", List.of(Map.of(
        "text", Map.of("content", content)
      ))
    );
    return new Property(key, title);
  }

  public static Property select(String key, String name, Color color) {
    Map<String, Object> select = Map.of(
      "select", Map.of(
        "name", name,
        "color", color.value()
      )
    );
    return new Property(key, select);
  }

  public static Property multiSelect(String key, String... names) {
    List<Map<String, String>> items = Arrays.stream(names)
      .map(name -> Map.of("name", name))
      .toList();

    return new Property(key, Map.of("multi_select", items));
  }

  public static Property checkbox(String key, boolean checked) {
    return new Property(key, Map.of("checkbox", checked));
  }

  public static Property status(String key, String name) {
    return new Property(key, Map.of("status", Map.of("name", name)));
  }


  public static Property date(String key) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");
    String now = ZonedDateTime.now(ZoneId.of("Asia/Seoul")).format(formatter);
    return new Property(key, Map.of("date", Map.of("start", now)));
  }

  public static Property url(String key, String url) {
    return new Property(key, Map.of("url", url));
  }

  public static Property richText(String key, String content) {
    return new Property(key, Map.of(
      "rich_text", List.of(Map.of(
        "type", "text",
        "text", Map.of("content", content)
      ))
    ));
  }

  public String getKey() {
    return key;
  }

  public Map<String, Object> getValue() {
    return value;
  }
}
