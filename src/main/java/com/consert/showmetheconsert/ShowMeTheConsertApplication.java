package com.consert.showmetheconsert;

import com.consert.showmetheconsert.conf.GlobalVar;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShowMeTheConsertApplication {

  public static void main(String[] args) {
    System.setProperty(GlobalVar.DRIVER_NAME, GlobalVar.DRIVER_PATH);
    SpringApplication.run(ShowMeTheConsertApplication.class, args);
  }
}
