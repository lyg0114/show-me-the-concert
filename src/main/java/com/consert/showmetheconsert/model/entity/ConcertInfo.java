package com.consert.showmetheconsert.model.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author : iyeong-gyo
 * @package : com.consert.showmetheconsert.model.entity
 * @since : 2023/05/01
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "CONCERT_INFO")
public class ConcertInfo extends BaseEntity {

  @Column(name = "SHOW_ID")
  private String showId;

  @Column(name = "PLACE")
  private String place;

  @Column(name = "TITLE")
  private String title;

  @Column(name = "URL")
  private String url;

  @Column(name = "CONCERT_DATE")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
  private LocalDateTime concertDateTime;

  @Column(name = "CONCERT_HALL_TAG")
  private String concertHallTag;

  @Override
  public String toString() {
    return "ConcertInfo{" +
        "showId='" + showId + '\'' +
        ", place='" + place + '\'' +
        ", title='" + title + '\'' +
        ", url='" + url + '\'' +
        ", concertDateTime=" + concertDateTime +
        ", concertHallTag='" + concertHallTag + '\'' +
        '}';
  }

  public String getClassStr() {
    return concertHallTag + " concert-title-div";
  }

  public void updateConcertInfo(ConcertInfo newInfo) {
    if (newInfo.getPlace() != null) { this.place = newInfo.getPlace(); }
    if (newInfo.getTitle() != null) { this.title = newInfo.getTitle(); }
    if (newInfo.getUrl() != null) { this.url = newInfo.getUrl(); }
    if (newInfo.getConcertDateTime() != null) { this.concertDateTime = newInfo.getConcertDateTime(); }
    if (newInfo.getConcertHallTag() != null) { this.concertHallTag = newInfo.getConcertHallTag(); }
  }
}
