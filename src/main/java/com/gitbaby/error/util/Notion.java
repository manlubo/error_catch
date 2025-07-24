package com.gitbaby.error.util;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public interface Notion {
  // 제목
  Notion title(String categoryName, String title);

  //
  Notion select(String field, String name, String id, String color);
  Notion checkbox(String field, boolean checked);
  Notion url(String field, String url);
  Notion richText(String field, String content);
  Notion multiSelect(String field, List<String> values);
  Notion heading(String content);
  Notion date(String field, String start, String end);
  Notion paragraph(String content, String link);
  Map<String, Object> build();



  default Notion date(String field) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");
    String now = ZonedDateTime.now(ZoneId.of("Asia/Seoul")).format(formatter);
    return date(field, now, null);
  }


}
