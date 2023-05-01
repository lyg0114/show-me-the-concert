package com.consert.showmetheconsert.util;

import com.consert.showmetheconsert.conf.GlobalVar;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

  public static LocalDateTime convertToLocalDateTime(String dateTimeStr) {
    return LocalDateTime
        .parse(dateTimeStr, DateTimeFormatter.ofPattern(GlobalVar.DATE_TIME_FORMAT));
  }
}
