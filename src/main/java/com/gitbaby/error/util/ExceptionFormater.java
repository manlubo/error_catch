package com.gitbaby.error.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionFormater {
  public static String formatRootStackTrace(Throwable ex) {
    Throwable root = ex;
    while (root.getCause() != null && root.getCause() != root) {
      root = root.getCause();
    }

    StringWriter sw = new StringWriter();
    root.printStackTrace(new PrintWriter(sw));
    return sw.toString();
  }

  public static String getRootExceptionClassPath(Throwable ex) {
    Throwable root = ex;
    while (root.getCause() != null && root.getCause() != root) {
      root = root.getCause();
    }

    StackTraceElement[] stackTrace = root.getStackTrace();
    return stackTrace.length > 0 ? stackTrace[0].getClassName() : "Unknown";
  }

  public static String getRootExceptionType(Throwable ex) {
    Throwable root = ex;
    while (root.getCause() != null && root.getCause() != root) {
      root = root.getCause();
    }

    return root.getClass().getSimpleName();
  }


}
