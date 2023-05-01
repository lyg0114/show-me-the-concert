package com.consert.showmetheconsert;

import com.consert.showmetheconsert.conf.GlobalVar;
import com.consert.showmetheconsert.schedule.SearchDaeguConcertSchedule;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ShowMeTheConsertApplication {
  public static void main(String[] args) {
    System.setProperty(GlobalVar.DRIVER_NAME, GlobalVar.DRIVER_PATH);
    SpringApplication.run(ShowMeTheConsertApplication.class, args)
        .getBean(SearchDaeguConcertSchedule.class)
        .searchData()
    ;
  }

  @Bean
  public WebDriver webDriver() {
    return new ChromeDriver(chromeOptions());
  }

  @Bean
  public ChromeOptions chromeOptions() {
    return new ChromeOptions()
        .addArguments("--remote-allow-origins=*")
        .setHeadless(false)
        ;
  }
}
