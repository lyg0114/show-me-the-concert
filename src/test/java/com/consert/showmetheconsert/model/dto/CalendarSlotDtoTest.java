package com.consert.showmetheconsert.model.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
    assertEquals(now, now2);
  }

  @Test
  public void test_LocalTime_to_String() {
    LocalTime now = LocalTime.of(00, 00);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
    String timeStr = now.format(formatter);
    assertEquals("00:00", timeStr);
  }
}