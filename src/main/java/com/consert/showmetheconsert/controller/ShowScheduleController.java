package com.consert.showmetheconsert.controller;

import com.consert.showmetheconsert.service.MakeCalendarService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author : iyeong-gyo
 * @package : com.consert.showmetheconsert.controller
 * @since : 2023/04/30
 */
@RequiredArgsConstructor
@Controller
@RequestMapping("/schedule")
public class ShowScheduleController {

  private static final String VIEWS_CONCERT_SCUEDULE = "schedule/showSchedule";

  private final MakeCalendarService calendarService;

  @GetMapping("")
  public String showSchedule() {
    LocalDateTime now = LocalDateTime.now();
    int year = now.getYear();
    int month = now.getMonthValue();
    return "redirect:/schedule/" + year + "/" + month;
  }

  @GetMapping("/{year}/{month}")
  public String showSchedule(
      @PathVariable("year") int year,
      @PathVariable("month") int month, Model model) {
    model.addAttribute("daysOfWeek", calendarService.getDayOfWeeks(year, month));
    model.addAttribute("calendar", calendarService.makeCalendar(year, month));
    setBeforeNextInfo(model, year, month);

    return VIEWS_CONCERT_SCUEDULE;
  }

  private void setBeforeNextInfo(Model model, int year, int month) {
    model.addAttribute("currentYear", LocalDate.of(year, month, 1).getYear());
    model.addAttribute("currentMonth", LocalDate.of(year, month, 1).getMonthValue());
    model.addAttribute("beforeYear", LocalDate.of(year, month, 1).minusMonths(1).getYear());
    model.addAttribute("beforeMonth", LocalDate.of(year, month, 1).minusMonths(1).getMonthValue());
    model.addAttribute("afterYear", LocalDate.of(year, month, 1).plusMonths(1).getYear());
    model.addAttribute("afterMonth", LocalDate.of(year, month, 1).plusMonths(1).getMonthValue());
  }
}
