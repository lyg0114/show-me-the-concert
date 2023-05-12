package com.consert.showmetheconsert.controller;

import com.consert.showmetheconsert.schedule.SearchDaeguConcertSchedule;
import com.consert.showmetheconsert.schedule.SearchSuseongArtScheduleInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : iyeong-gyo
 * @package : com.consert.showmetheconsert.controller
 * @since : 2023/05/05
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/batch")
public class BatchController {

  private final SearchDaeguConcertSchedule daeguConcertSchedule;
  private final SearchSuseongArtScheduleInterface suseongArtSchedule;

  @GetMapping("/daegu-concert-house")
  public String getDataFromDaeguConcertHouse() {
    daeguConcertSchedule.searchData();
    return "daegu-concert-house BATCH SUCCESS";
  }

  @GetMapping("/suseong-art")
  public String getDataFromSuseongArt() {
    suseongArtSchedule.searchData();
    return "suseong-art BATCH SUCCESS";
  }
}
