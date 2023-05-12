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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : iyeong-gyo
 * @package : com.consert.showmetheconsert.schedule
 * @since : 2023/05/12
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class SearchSuseongArtSchedule {

  public static final String RETURN_BTN_XPATH = "/html/body/div/section/div/div/div/div/div[3]/a";
  public static final String COMPARE_STR = "&no=";
  public static final String CONCERT_TITLE_XPATH = "/html/body/div/div[3]/div[2]/div/h4";
  public static final String CONCERT_PLACE_XPATH = "/html/body/div/section/div/div/div/div/div[1]/div[2]/table/tbody/tr[4]/td[2]";
  public static final String CONCERT_DATE_XPATH = "/html/body/div/section/div/div/div/div/div[1]/div[2]/table/tbody/tr[1]/td[1]";
  public static final String CONCERT_TIME_XPATH = "/html/body/div/section/div/div/div/div/div[1]/div[2]/table/tbody/tr[1]/td[2]";
  public static final String REG_EXPRESSION_DATE = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}";

  private final GlobalVar global;
  private final WebDriver driver;
  private final ConcertInfoRepository concertInfoRepo;


  public void searchData() {
    driver.get(global.getSuseongArtUrl());
    List<String> targets = new ArrayList<>();
    extractTargestHref(targets);
    extracted(targets);
  }

  private void extractTargestHref(List<String> targets) {
    List<WebElement> links = driver.findElements(By.tagName("a"));
    links.forEach(l -> {
      String href = l.getAttribute("href");
      if (href != null && href.contains(COMPARE_STR)) {
        targets.add(href);
      }
    });
  }

  private void extracted(List<String> URLs) {
    for (String url : URLs) {
      driver.get(url);
      extractData();
      TimeUtil.sleep(1500);
      driver.findElement(By.xpath(RETURN_BTN_XPATH)).click();
      TimeUtil.sleep(1500);
    }
  }

  @Transactional
  public void extractData() {
    ConcertInfo info = ConcertInfo.builder()
        .url(driver.getCurrentUrl())
        .title(driver.findElement(By.xpath(CONCERT_TITLE_XPATH)).getText())
        .place(driver.findElement(By.xpath(CONCERT_PLACE_XPATH)).getText())
        .concertDateTime(calculateConcertDate(
            driver.findElement(By.xpath(CONCERT_TITLE_XPATH)).getText(),
            driver.findElement(By.xpath(CONCERT_DATE_XPATH)).getText(),
            driver.findElement(By.xpath(CONCERT_TIME_XPATH)).getText()))
        .concertHallTag(GlobalVar.TAG_SUSEONGART)
        .showId(extractShowId(driver.getCurrentUrl()))
        .build();
    concertInfoRepo.save(info);
    log.info(info.toString());
  }

  public String extractShowId(String targetUrl) {
    String result = null;
    try {
      result = targetUrl.split("&")[2]
          .split("=")[1];
    } catch (RuntimeException ex) {
      log.error("check the targetUrl : " + targetUrl);
      ex.printStackTrace();
    }
    return result;
  }

  public LocalDateTime calculateConcertDate(String title, String dateStr, String timeStr) {
    LocalDateTime localDateTime = null;
    try {
      StringBuilder sb = new StringBuilder();
      sb.append(dateStr.split("\\.")[0]);
      sb.append("-");
      sb.append(dateStr.split("\\.")[1]);
      sb.append("-");
      sb.append(dateStr.split("\\.")[2]);
      dateStr = sb.toString();
      localDateTime = getLocalDateTime(dateStr, timeStr);
    } catch (RuntimeException ex) {
      log.error(title + " : dateTimeStr has null or whitespace");
      ex.printStackTrace();
    }

    return localDateTime;
  }

  private LocalDateTime getLocalDateTime(String dateStr, String timeStr) {
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

    return TimeUtil.convertToLocalDateTime(datetimeStr);
  }
}
