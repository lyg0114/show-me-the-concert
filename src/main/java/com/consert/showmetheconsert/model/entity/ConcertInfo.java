package com.consert.showmetheconsert.model.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author : iyeong-gyo
 * @package : com.consert.showmetheconsert.model.entity
 * @since : 2023/05/01
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "CONCERT_INFO")
public class ConcertInfo extends BaseEntity {

  @Column(name = "PLACE")
  private String place;

  @Column(name = "TITLE")
  private String title;

  @Column(name = "URL")
  private String url;

  @Column(name = "CONCERT_DATE")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
  private LocalDateTime concertDateTime;

  @Override
  public String toString() {
    return "ConcertInfo{" +
        "place='" + place + '\'' +
        ", title='" + title + '\'' +
        ", url='" + url + '\'' +
        ", concertDateTime=" + concertDateTime +
        '}';
  }
}