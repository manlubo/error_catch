package com.gitbaby.error.aop;

import com.gitbaby.error.service.DiscordUploadService;
import com.gitbaby.error.util.ExceptionFormater;
import com.gitbaby.error.util.NotionLogger;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
@Log4j2
@EnableAspectJAutoProxy
public class ExceptionLoggingAspect {
  private final NotionLogger notionLogger;
  private final DiscordUploadService discordUploadService;

  @AfterThrowing(pointcut = "execution(* com.gitbaby..*(..))", throwing = "ex")
  public void captureException(JoinPoint joinPoint, Throwable ex) {
    log.error("❗ 예외 감지됨: {}", ex.toString(), ex);

    notionLogger.send(ex);
    discordUploadService.send("ff0000", "오류 발생", ExceptionFormater.getRootExceptionClassPath(ex), ExceptionFormater.getRootExceptionType(ex));
  }
}
