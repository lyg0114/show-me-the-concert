package com.consert.showmetheconsert.model.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : iyeong-gyo
 * @package : com.consert.showmetheconsert.util
 * @since : 2023/05/02
 */
public class CalendarDayDto {

  private LocalDate date;
  private List<CalendarSlotDto> slots;

  public CalendarDayDto(LocalDate date) {
    this.date = date;
  }

  public String getDay() {
    if (date != null) {
      return String.valueOf(date.getDayOfMonth());
    } else {
      return "";
    }
  }

  public List<CalendarSlotDto> getSlots() {
    if (slots == null || slots.size() == 0) {
      return new ArrayList<>();
    } else {
      return slots;
    }
  }

  public void setSlots(List<CalendarSlotDto> slots) {
    this.slots = slots;
  }
}
