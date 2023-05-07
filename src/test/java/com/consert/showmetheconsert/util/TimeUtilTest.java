package com.consert.showmetheconsert.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author : iyeong-gyo
 * @package : com.consert.showmetheconsert.util
 * @since : 2023/05/07
 */
class TimeUtilTest {

  @Test
  public void testExpressionWhenDataParse() {
    String timeStr = "DUMMY_STRING 2023-05-07 19:00";
    Pattern pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}");
    Matcher matcher = pattern.matcher(timeStr);
    String datetimeStr = null;
    if (matcher.find()) {
      datetimeStr = matcher.group();
      Assertions.assertEquals("2023-05-07 19:00", datetimeStr);
    }
  }
}