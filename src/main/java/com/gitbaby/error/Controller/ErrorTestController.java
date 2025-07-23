package com.gitbaby.error.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class ErrorTestController {

  @GetMapping("fail")
  public String fail() {
    throw new RuntimeException("테스트 예외 발생!");
  }
}
