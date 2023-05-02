package com.consert.showmetheconsert.util;

import java.time.LocalTime;

/**
 * @author : iyeong-gyo
 * @package : com.consert.showmetheconsert.util
 * @since : 2023/05/02
 */
public class CalendarSlot {

  private LocalTime startTime;
  private LocalTime endTime;

  public CalendarSlot(LocalTime startTime, LocalTime endTime) {
    this.startTime = startTime;
    this.endTime = endTime;
  }

  public LocalTime getTimeInfo() {
    return startTime;
  }
}
