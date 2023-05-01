package com.consert.showmetheconsert.conf;

import java.time.Duration;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author : iyeong-gyo
 * @package : com.consert.showmetheconsert.conf
 * @since : 2023/04/30
 */
@Getter
@Component
public class GlobalVar {

  public static Duration COMMON_DELAY = Duration.ofMillis(10);
  public static String DRIVER_NAME = "webdriver.chrome.driver";
  public static String RESOURCE_PATH = "/Users/iyeong-gyo/Desktop/study/cicd-study/show-me-the-consert/src/main/resources";
  public static String DRIVER_PATH = RESOURCE_PATH + "/chromedriver";

  public static String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";

  @Value("${daeguConcertHouse.url}")
  private String daeguConcertHouseUrl;

}
