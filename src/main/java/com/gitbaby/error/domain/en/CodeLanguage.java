package com.gitbaby.error.domain.en;

import com.fasterxml.jackson.annotation.JsonValue;

public enum CodeLanguage {
  JAVA("java"),
  KOTLIN("kotlin"),
  GROOVY("groovy"),
  XML("xml"),
  YAML("yaml"),
  PROPERTIES("properties"),
  SQL("sql"),
  BASH("bash"),
  JSON("json");

  private final String value;

  CodeLanguage(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }
}
