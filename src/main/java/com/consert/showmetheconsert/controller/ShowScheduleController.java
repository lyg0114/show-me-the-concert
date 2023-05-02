package com.consert.showmetheconsert.controller;

import com.consert.showmetheconsert.util.CalendarDay;
import com.consert.showmetheconsert.util.CalendarSlot;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author : iyeong-gyo
 * @package : com.consert.showmetheconsert.controller
 * @since : 2023/04/30
 */
@Controller
@RequestMapping("/schedule")
public class ShowScheduleController {

  private static final String VIEWS_CONCERT_SCUEDULE = "schedule/showSchedule";

  @GetMapping("")
  public String showSchedule(Model model) {
    LocalDate startDate = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
    LocalDate endDate = startDate.plusMonths(1).minusDays(1);

    List<DayOfWeek> daysOfWeek = new ArrayList<>();
    for (int i = 0; i < 7; i++) {
      daysOfWeek.add(startDate.plusDays(i).getDayOfWeek());
    }

    List<List<CalendarDay>> rows = new ArrayList<>();
    LocalDate currentDate = startDate;
    while (!currentDate.isAfter(endDate)) {
      List<CalendarDay> row = new ArrayList<>();
      for (int i = 0; i < 7; i++) {
        if (currentDate.isAfter(endDate)) {
          row.add(new CalendarDay(null));
        } else {
          CalendarDay day = new CalendarDay(currentDate);
          List<CalendarSlot> slots = new ArrayList<>();
          for (int j = 0; j <= 23; j++) {
            for (int k = 0; k < 2; k++) {
              LocalTime startTime = LocalTime.of(j, k * 30);
              LocalTime endTime = startTime.plusMinutes(30);
              CalendarSlot slot = new CalendarSlot(startTime, endTime);
              slots.add(slot);
            }
          }
          day.setSlots(slots);
          row.add(day);
        }
        currentDate = currentDate.plusDays(1);
      }
      rows.add(row);
    }

    model.addAttribute("daysOfWeek", daysOfWeek);
    model.addAttribute("calendar", rows);

    return VIEWS_CONCERT_SCUEDULE;
  }
}
