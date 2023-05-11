package com.consert.showmetheconsert;

import com.consert.showmetheconsert.conf.GlobalVar;
import com.consert.showmetheconsert.model.entity.ConcertInfo;
import com.consert.showmetheconsert.repository.ConcertInfoRepository;
import java.time.LocalDateTime;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShowMeTheConsertApplication {

  public static void main(String[] args) {
    System.setProperty(GlobalVar.DRIVER_NAME, GlobalVar.DRIVER_PATH);
    SpringApplication.run(ShowMeTheConsertApplication.class, args);
  }


}
