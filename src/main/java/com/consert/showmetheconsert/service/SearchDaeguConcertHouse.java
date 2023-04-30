package com.consert.showmetheconsert.service;

import com.consert.showmetheconsert.conf.GlobalVar;
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
public class SearchDaeguConcertHouse {

  private static final String RETURN_BTN = "/html/body/div[2]/div[3]/div/div/div/div[2]/div[1]/ul/li[3]/a";
  private static final String COMPARE_STR = "javascript:fn_view('SC";
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
      sleep(1000);
      driver.findElement(By.xpath(RETURN_BTN)).click();
      sleep(1000);
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
  }

  private void sleep(long sec) {
    try {
      Thread.sleep(sec);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
