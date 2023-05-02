package com.consert.showmetheconsert.util;

import java.time.LocalDate;

/**
 * @author : iyeong-gyo
 * @package : com.consert.showmetheconsert.util
 * @since : 2023/05/02
 */
public class CalendarDay {

  private Integer number;
  private boolean currentMonth;

  public CalendarDay(Integer number, boolean currentMonth) {
    this.number = number;
    this.currentMonth = currentMonth;
  }

  public int getNumber() {
    return number;
  }

  public boolean isCurrentMonth() {
    return currentMonth;
  }

  public String getClasses() {
    String classes = "text-center";
    if (!currentMonth) {
      classes += " other-month";
    }
    if (number == LocalDate.now().getDayOfMonth()) {
      classes += " today";
    }
    return classes;
  }
}
