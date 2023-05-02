package com.consert.showmetheconsert.controller;

import com.consert.showmetheconsert.util.CalendarDay;
import java.time.LocalDate;
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

    LocalDate now = LocalDate.now();

    int year = now.getYear();
    int month = now.getMonthValue();
    int day = now.getDayOfMonth();

    LocalDate firstDayOfMonth = LocalDate.of(year, month, 1);
    LocalDate lastDayOfMonth = firstDayOfMonth.with(TemporalAdjusters.lastDayOfMonth());

    List<List<CalendarDay>> calendar = new ArrayList<>();
    List<CalendarDay> week = new ArrayList<>();
    int dayOfWeek = firstDayOfMonth.getDayOfWeek().getValue() % 7;
    for (int i = 0; i < dayOfWeek; i++) {
      week.add(null);
    }
    for (LocalDate date = firstDayOfMonth; !date.isAfter(lastDayOfMonth); date = date.plusDays(1)) {
      if (week.size() == 7) {
        calendar.add(week);
        week = new ArrayList<>();
      }
      week.add(new CalendarDay(date.getDayOfMonth(), date.getMonthValue() == month));
    }
    if (week.size() < 7) {
      for (int i = week.size(); i < 7; i++) {
        week.add(null);
      }
      calendar.add(week);
    }

    model.addAttribute("calendar", calendar);

    return VIEWS_CONCERT_SCUEDULE;
  }
}
