package com.gitbaby.error.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HelloController {
  @GetMapping("/")
  public String hello() {
    return "서버 정상 작동 중";
  }
}
