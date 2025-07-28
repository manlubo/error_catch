package com.gitbaby.error.util;

import com.gitbaby.error.domain.Block;
import com.gitbaby.error.domain.en.CodeLanguage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BlockBuilder {
  private final List<Block> blocks = new ArrayList<>();

  public static BlockBuilder create() {
    return new BlockBuilder();
  }

  public BlockBuilder heading2(String content) {
    blocks.add(Block.heading2(content));
    return this;
  }

  public BlockBuilder paragraph(String content) {
    blocks.add(Block.paragraph(content));
    return this;
  }

  public BlockBuilder paragraph(String content, String link) {
    blocks.add(Block.paragraph(content, link));
    return this;
  }

  public BlockBuilder code(CodeLanguage language, String content) {
    blocks.add(Block.code(content, language));
    return this;
  }



  public List<Map<String, Object>> build() {
    return blocks.stream()
      .map(block -> {
        // object, type, 그리고 type에 해당하는 내용 포함한 전체 구조 구성
        Map<String, Object> base = Map.of(
          "object", block.getObject(),
          "type", block.getType()
        );
        return merge(base, block.getContent());
      })
      .collect(Collectors.toList());
  }

  private Map<String, Object> merge(Map<String, Object> base, Map<String, Object> content) {
    // object + type + heading_2/paragraph 내용 merge
    return new java.util.HashMap<>() {{
      putAll(base);
      putAll(content);
    }};
  }
}
