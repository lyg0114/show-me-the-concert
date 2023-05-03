package com.consert.showmetheconsert;

import com.consert.showmetheconsert.conf.GlobalVar;
import com.consert.showmetheconsert.model.entity.ConcertInfo;
import com.consert.showmetheconsert.repository.ConcertInfoRepository;
import com.consert.showmetheconsert.schedule.SearchDaeguConcertSchedule;
import java.time.LocalDateTime;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ShowMeTheConsertApplication {

  public static void main(String[] args) {
    System.setProperty(GlobalVar.DRIVER_NAME, GlobalVar.DRIVER_PATH);
    SpringApplication.run(ShowMeTheConsertApplication.class, args)
        .getBean(SearchDaeguConcertSchedule.class)
//        .searchData()
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
        .setHeadless(true)
        ;
  }

  @Bean
  public CommandLineRunner sampleData(ConcertInfoRepository repository) {
    return (args) -> {
      repository.save(ConcertInfo.builder().showId("show-1").place("place-1").title("title-1")
          .url("https://example1.com").concertHallTag("Tag-1")
          .concertDateTime(LocalDateTime.of(2023, 5, 3, 13, 0))
          .build()
      );
      repository.save(ConcertInfo.builder().showId("show-2").place("place-2").title("title-2")
          .url("https://example2.com").concertHallTag("Tag-1")
          .concertDateTime(LocalDateTime.of(2023, 5, 17, 17, 0))
          .build());
      repository.save(ConcertInfo.builder().showId("show-3").place("place-3").title("title-3")
          .url("https://example3.com").concertHallTag("Tag-1")
          .concertDateTime(LocalDateTime.of(2023, 5, 24, 18, 30))
          .build());
    };
  }
}
