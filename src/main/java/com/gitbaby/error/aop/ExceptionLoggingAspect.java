package com.gitbaby.error.aop;

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

  @AfterThrowing(pointcut = "execution(* com.gitbaby..*(..))", throwing = "ex")
  public void captureException(JoinPoint joinPoint, Throwable ex) {
    String method = joinPoint.getSignature().toShortString();
    Throwable root = getRootCause(ex);

    log.error("❗ 예외 감지됨: {}", root.toString());
    notionLogger.send(root); // 📬 노션 전송
  }

  private Throwable getRootCause(Throwable ex) {
    Throwable cause = ex;
    while (cause.getCause() != null) {
      cause = cause.getCause();
    }
    return cause;
  }
}
