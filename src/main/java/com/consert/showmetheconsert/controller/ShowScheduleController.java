package com.consert.showmetheconsert.controller;

import com.consert.showmetheconsert.service.MakeCalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
  public String showSchedule(Model model) {
    model.addAttribute("daysOfWeek", calendarService.getDayOfWeeks());
    model.addAttribute("calendar", calendarService.makeCalendar());
    return VIEWS_CONCERT_SCUEDULE;
  }

}
