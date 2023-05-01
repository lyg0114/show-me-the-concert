package com.consert.showmetheconsert.schedule;

import com.consert.showmetheconsert.conf.GlobalVar;
import com.consert.showmetheconsert.util.TimeUtil;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

/**
 * @author : iyeong-gyo
 * @package : com.consert.showmetheconsert.service
 * @since : 2023/04/30
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class SearchDaeguConcertSchedule {

  private static final String RETURN_BTN_XPATH = "/html/body/div[2]/div[3]/div/div/div/div[2]/div[1]/ul/li[3]/a";
  private static final String COMPARE_STR = "javascript:fn_view('SC";
  private static final String CONCERT_TITLE_XPATH = "/html/body/div[2]/div[3]/div/div/div/div[2]/div[2]/div[1]/div[2]/strong";
  private static final String CONCERT_PLACE_XPATH = "/html/body/div[2]/div[3]/div/div/div/div[2]/div[2]/div[1]/div[2]/ul/li[1]/div";
  private static final String CONCERT_DATE_XPATH = "/html/body/div[2]/div[3]/div/div/div/div[2]/div[2]/div[1]/div[2]/ul/li[2]/div";
  private static final String CONCERT_TIME_XPATH = "/html/body/div[2]/div[3]/div/div/div/div[2]/div[2]/div[1]/div[2]/ul/li[3]/div";

  private final GlobalVar global;
  private final WebDriver driver;

  public void searchData() {
    driver.get(global.getDaeguConcertHouseUrl());
    List<String> targets = new ArrayList<>();
    extractTargestHref(targets);
    extracted(targets);
  }

  private void extracted(List<String> targets) {
    JavascriptExecutor jsDriver = (JavascriptExecutor) driver;
    for (String target : targets) {
      jsDriver.executeScript(target);
      extractData();
      TimeUtil.sleep(1000);
      driver.findElement(By.xpath(RETURN_BTN_XPATH)).click();
      TimeUtil.sleep(1000);
    }
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

  private void extractData() {
    String currentUrl = driver.getCurrentUrl();
    System.out.println("currentUrl = " + currentUrl);

    WebElement elTitle = driver.findElement(By.xpath(CONCERT_TITLE_XPATH));
    String titleText = elTitle.getText();
    System.out.println("titleText = " + titleText);

    WebElement elPlace = driver.findElement(By.xpath(CONCERT_PLACE_XPATH));
    String place = elPlace.getText();
    System.out.println("place = " + place);

    WebElement elDate = driver.findElement(By.xpath(CONCERT_DATE_XPATH));
    String dateStr = elDate.getText();
    System.out.println("dateStr = " + dateStr);
    WebElement elTime = driver.findElement(By.xpath(CONCERT_TIME_XPATH));
    String timeStr = elTime.getText();
    System.out.println("timeStr = " + timeStr);

    LocalDateTime localDateTime = TimeUtil.convertToLocalDateTime(concatDateInfo(dateStr, timeStr));
    System.out.println("localDateTime = " + localDateTime);

  }

  private String concatDateInfo(String dateStr, String timeStr) {
    StringBuilder sb = new StringBuilder();
    sb.append(dateStr);
    sb.append(" ");
    sb.append(timeStr);
    return sb.toString();
  }
}


















