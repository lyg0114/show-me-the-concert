package com.consert.showmetheconsert.model.dto;

import java.time.LocalTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author : iyeong-gyo
 * @package : com.consert.showmetheconsert.model.dto
 * @since : 2023/05/14
 */
class CalendarSlotDtoTest {

  @Test
  public void test_LocalTime() {
    LocalTime now = LocalTime.of(00, 00);
    LocalTime now2 = LocalTime.of(00, 00);
    Assertions.assertEquals(now, now2);
  }
}