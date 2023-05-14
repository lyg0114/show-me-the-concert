package com.consert.showmetheconsert.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author : iyeong-gyo
 * @package : com.consert.showmetheconsert.controller
 * @since : 2023/05/14
 */
@Controller
@RequestMapping("/")
public class BaseController {

  @GetMapping("")
  public String base() {
    return "redirect:/schedule";
  }
}
