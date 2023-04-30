package com.consert.showmetheconsert.util;

/**
 * @author : iyeong-gyo
 * @package : com.consert.showmetheconsert.util
 * @since : 2023/04/30
 */
public class TimeUtil {

  public static void sleep(long sec) {
    try {
      Thread.sleep(sec);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
