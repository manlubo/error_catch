package com.gitbaby.error.domain.en;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Color {
  DEFAULT("default"),
  GRAY("gray"),
  BROWN("brown"),
  ORANGE("orange"),
  YELLOW("yellow"),
  GREEN("green"),
  BLUE("blue"),
  PURPLE("purple"),
  PINK("pink"),
  RED("red");

  private final String value;

  Color(String value) {
    this.value = value;
  }

  @JsonValue
  public String value() {
    return value;
  }
}
