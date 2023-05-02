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

  public String getNumber() {
    if (date != null) {
      return String.valueOf(date.getDayOfMonth());
    }else {
      return "";
    }
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public List<CalendarSlot> getSlots() {
    return slots;
  }

  public void setSlots(List<CalendarSlot> slots) {
    this.slots = slots;
  }
}
