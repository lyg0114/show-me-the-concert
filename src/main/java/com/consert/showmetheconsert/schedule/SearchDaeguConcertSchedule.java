package com.consert.showmetheconsert.schedule;

import com.consert.showmetheconsert.conf.GlobalVar;
import com.consert.showmetheconsert.model.entity.ConcertInfo;
import com.consert.showmetheconsert.repository.ConcertInfoRepository;
import com.consert.showmetheconsert.util.TimeUtil;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : iyeong-gyo
 * @package : com.consert.showmetheconsert.service
 * @since : 2023/04/30
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class SearchDaeguConcertSchedule {

  public static final String RETURN_BTN_XPATH = "/html/body/div[2]/div[3]/div/div/div/div[2]/div[1]/ul/li[3]/a";
  public static final String COMPARE_STR = "javascript:fn_view('SC";
  public static final String CONCERT_TITLE_XPATH = "/html/body/div[2]/div[3]/div/div/div/div[2]/div[2]/div[1]/div[2]/strong";
  public static final String CONCERT_PLACE_XPATH = "/html/body/div[2]/div[3]/div/div/div/div[2]/div[2]/div[1]/div[2]/ul/li[1]/div";
  public static final String CONCERT_DATE_XPATH = "/html/body/div[2]/div[3]/div/div/div/div[2]/div[2]/div[1]/div[2]/ul/li[2]/div";
  public static final String CONCERT_TIME_XPATH = "/html/body/div[2]/div[3]/div/div/div/div[2]/div[2]/div[1]/div[2]/ul/li[3]/div";
  public static final String REG_EXPRESSION_DATE = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}";

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
    List<WebElement> links = driver.findElements(By.tagName("a"));
    links.forEach(l -> {
      String href = l.getAttribute("href");
      if (href.contains(COMPARE_STR)) {
        targets.add(href);
      }
    });
  }

  private void extracted(List<String> targets) {
    JavascriptExecutor jsDriver = (JavascriptExecutor) driver;
    for (int i = 0; i < targets.size(); i++) {
      jsDriver.executeScript(targets.get(i));
      extractData();
      TimeUtil.sleep(1000);
      driver.findElement(By.xpath(RETURN_BTN_XPATH)).click();
      TimeUtil.sleep(1000);
    }
  }

  @Transactional
  protected void extractData() {
    ConcertInfo info = ConcertInfo.builder()
        .url(driver.getCurrentUrl())
        .title(driver.findElement(By.xpath(CONCERT_TITLE_XPATH)).getText())
        .place(driver.findElement(By.xpath(CONCERT_PLACE_XPATH)).getText())
        .concertDateTime(calculateConcertDate(
            driver.findElement(By.xpath(CONCERT_DATE_XPATH)).getText(),
            driver.findElement(By.xpath(CONCERT_TIME_XPATH)).getText()))
        .concertHallTag("TAG-1")
        .build();
    concertInfoRepo.save(info);
    log.info(info.toString());
  }

  public LocalDateTime calculateConcertDate(String dateStr, String timeStr) {
    StringBuilder sb = new StringBuilder();
    sb.append(dateStr);
    sb.append(" ");
    sb.append(timeStr);
    String dateTime = sb.toString();

    Pattern pattern = Pattern.compile(REG_EXPRESSION_DATE);
    Matcher matcher = pattern.matcher(dateTime);
    String datetimeStr = null;
    if (matcher.find()) {
      datetimeStr = matcher.group();
    }

    LocalDateTime resultDateTime = null;
    try {
      resultDateTime = TimeUtil.convertToLocalDateTime(datetimeStr);
    } catch (NullPointerException ex) {
      log.error(ex.getMessage());
      throw ex;
    }

    return resultDateTime;
  }
}

