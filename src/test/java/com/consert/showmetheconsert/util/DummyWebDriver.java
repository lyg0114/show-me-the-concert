package com.consert.showmetheconsert.util;

import java.util.List;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @author : iyeong-gyo
 * @package : com.consert.showmetheconsert.util
 * @since : 2023/05/07
 */
public class DummyWebDriver implements WebDriver {

  String dateStr;
  String timeStr;

  public DummyWebDriver() {
  }

  public DummyWebDriver(String dateStr, String timeStr) {
    this.dateStr = dateStr;
    this.timeStr = timeStr;
  }

  @Override
  public void get(String url) {
  }

  @Override
  public String getCurrentUrl() {
    return null;
  }

  @Override
  public String getTitle() {
    return null;
  }

  @Override
  public List<WebElement> findElements(By by) {
    return null;
  }

  @Override
  public WebElement findElement(By by) {
    return null;
  }

  @Override
  public String getPageSource() {
    return null;
  }

  @Override
  public void close() {
  }

  @Override
  public void quit() {
  }

  @Override
  public Set<String> getWindowHandles() {
    return null;
  }

  @Override
  public String getWindowHandle() {
    return null;
  }

  @Override
  public TargetLocator switchTo() {
    return null;
  }

  @Override
  public Navigation navigate() {
    return null;
  }

  @Override
  public Options manage() {
    return null;
  }
}
