package com.gitbaby.error.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;

@Builder
public class Notion {
  private String title = "";
  private List<MultiSelect> multiSelects;
  private List<Time> times;
  private Content content;

  @Builder
  @AllArgsConstructor
  public class MultiSelect {
    private final String CategoryName;
    private List<String> FieldNames;
  }

  @Builder
  public class Time {
    private final String CategoryName;
  }

  @Builder
  public class Content {
    private final String language;
    private final String content;
  }






}
