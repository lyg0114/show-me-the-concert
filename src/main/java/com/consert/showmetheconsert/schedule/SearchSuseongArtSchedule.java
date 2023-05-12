package com.consert.showmetheconsert.schedule;

import com.consert.showmetheconsert.conf.GlobalVar;
import com.consert.showmetheconsert.repository.ConcertInfoRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Service;

/**
 * @author : iyeong-gyo
 * @package : com.consert.showmetheconsert.schedule
 * @since : 2023/05/12
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class SearchSuseongArtSchedule {

  private final GlobalVar global;
  private final WebDriver driver;
  private final ConcertInfoRepository concertInfoRepo;


  public void searchData() {
    driver.get(global.getDaeguConcertHouseUrl());
    List<String> targets = new ArrayList<>();
    extractTargestHref(targets);
    extracted(targets);
  }

  private void extractTargestHref(List<String> targets) {

  }

  private void extracted(List<String> targets) {

  }
}
