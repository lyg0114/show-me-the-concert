package com.consert.showmetheconsert.util;

import java.time.LocalDate;
import java.util.List;

/**
 * @author : iyeong-gyo
 * @package : com.consert.showmetheconsert.util
 * @since : 2023/05/02
 */
public class CalendarDay {

  private LocalDate date;
  private List<CalendarSlot> slots;

  public CalendarDay(LocalDate date) {
    this.date = date;
  }

  public String getDay() {
    if (date != null) {
      return String.valueOf(date.getDayOfMonth());
    } else {
      return "";
    }
  }

  public List<CalendarSlot> getSlots() {
    return slots;
  }

  public void setSlots(List<CalendarSlot> slots) {
    this.slots = slots;
  }
}
