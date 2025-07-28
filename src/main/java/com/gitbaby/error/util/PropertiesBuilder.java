package com.gitbaby.error.util;

import com.gitbaby.error.domain.Property;
import com.gitbaby.error.domain.en.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PropertiesBuilder {

  private final List<Property> props = new ArrayList<>();
  private boolean titleCalled = false;

  public static PropertiesBuilder create() {
    return new PropertiesBuilder();
  }

  public PropertiesBuilder title(String key, String content) {
    if (titleCalled) {
      throw new IllegalStateException("title()은 한 번만 호출할 수 있습니다.");
    }
    props.add(Property.title(key, content));
    titleCalled = true;
    return this;
  }

  public PropertiesBuilder select(String key, String name, Color color) {
    props.add(Property.select(key, name, color));
    return this;
  }

  public PropertiesBuilder multiSelect(String key, String... names) {
    props.add(Property.multiSelect(key, names));
    return this;
  }

  public PropertiesBuilder checkbox(String key, boolean checked) {
    props.add(Property.checkbox(key, checked));
    return this;
  }

  public PropertiesBuilder status(String key, String name) {
    props.add(Property.status(key, name));
    return this;
  }

  public PropertiesBuilder url(String key, String url) {
    props.add(Property.url(key, url));
    return this;
  }

  public PropertiesBuilder richText(String key, String content) {
    props.add(Property.richText(key, content));
    return this;
  }

  public PropertiesBuilder date(String key) {
    props.add(Property.date(key));
    return this;
  }

  public Map<String, Object> build() {
    if (!titleCalled) {
      // 자동으로 빈 title 추가 (기본 필드명 "Name")
      props.add(Property.title("Name", ""));
    }

    return props.stream()
      .collect(Collectors.toMap(Property::getKey, Property::getValue));
  }
}
