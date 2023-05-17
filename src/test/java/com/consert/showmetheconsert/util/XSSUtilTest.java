package com.consert.showmetheconsert.util;

import org.apache.commons.text.StringEscapeUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author : iyeong-gyo
 * @package : com.consert.showmetheconsert.util
 * @since : 2023/05/17
 */
public class XSSUtilTest {

  @Test
  public void test_xss() {
    String input = "<>";
    String output = StringEscapeUtils.escapeHtml4(input);
    String results = StringEscapeUtils.unescapeHtml4(output);
    Assertions.assertEquals(input, results);
  }
}
