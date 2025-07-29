package com.gitbaby.error.domain;

import com.gitbaby.error.domain.en.CodeLanguage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Block {
  private final String object = "block";
  private final String type;
  private final Map<String, Object> content;

  private Block(String type, Map<String, Object> content) {
    this.type = type;
    this.content = content;
  }

  public static Block heading1(String content) {
    return new Block("heading_1", Map.of(
      "heading_1", Map.of(
        "rich_text", splitRichText(content)
      )
    ));
  }

  public static Block heading2(String content) {
    return new Block("heading_2", Map.of(
      "heading_2", Map.of(
        "rich_text", splitRichText(content)
      )
    ));
  }


  public static Block heading3(String content) {
    return new Block("heading_3", Map.of(
      "heading_3", Map.of(
        "rich_text", splitRichText(content)
      )
    ));
  }

  public static Block bulletedListItem(String content) {
    return new Block("bulleted_list_item", Map.of(
      "bulleted_list_item", Map.of(
        "rich_text", splitRichText(content)
      )
    ));
  }

  public static Block numberedListItem(String content) {
    return new Block("numbered_list_item", Map.of(
      "numbered_list_item", Map.of(
        "rich_text", splitRichText(content)
      )
    ));
  }

  public static Block paragraph(String content) {
    return new Block("paragraph", Map.of(
      "paragraph", Map.of(
        "rich_text", splitRichText(content)
      )
    ));
  }

  public static Block paragraph(String content, String link) {
    return new Block("paragraph", Map.of(
      "paragraph", Map.of(
        "rich_text", splitRichTextWithLink(content, link)
      )
    ));
  }

  public static Block quote(String content) {
    return new Block("quote", Map.of(
      "quote", Map.of(
        "rich_text", splitRichText(content)
      )
    ));
  }

  public static Block divider() {
    return new Block("divider", Map.of(
      "divider", Map.of()
    ));
  }


  public static Block code(String content, CodeLanguage language) {
    return new Block("code", Map.of(
      "code", Map.of(
        "language", language,
        "rich_text", splitRichText(content)
      )
    ));
  }

  private static List<Map<String, Object>> splitRichText(String content) {
    final int LIMIT = 2000;
    List<Map<String, Object>> richTexts = new ArrayList<>();

    for (int i = 0; i < content.length(); i += LIMIT) {
      String part = content.substring(i, Math.min(i + LIMIT, content.length()));
      richTexts.add(Map.of(
        "type", "text",
        "text", Map.of("content", part)
      ));
    }

    return richTexts;
  }

  private static List<Map<String, Object>> splitRichTextWithLink(String content, String link) {
    final int LIMIT = 2000;
    List<Map<String, Object>> richTexts = new ArrayList<>();

    for (int i = 0; i < content.length(); i += LIMIT) {
      String part = content.substring(i, Math.min(i + LIMIT, content.length()));
      richTexts.add(Map.of(
        "type", "text",
        "text", Map.of(
          "content", part,
          "link", Map.of("url", link)
        )
      ));
    }

    return richTexts;
  }

  public String getObject() {
    return object;
  }

  public String getType() {
    return type;
  }

  public Map<String, Object> getContent() {
    return content;
  }
}
