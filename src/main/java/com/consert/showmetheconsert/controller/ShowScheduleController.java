package com.consert.showmetheconsert.controller;

import org.springframework.stereotype.Controller;
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
  public String initCreationForm() {
    return VIEWS_CONCERT_SCUEDULE;
  }
}
