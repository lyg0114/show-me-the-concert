package com.consert.showmetheconsert.model.dto;

import com.consert.showmetheconsert.model.entity.ConcertInfo;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : iyeong-gyo
 * @package : com.consert.showmetheconsert.util
 * @since : 2023/05/02
 */
public class CalendarSlotDto {

  private LocalDateTime currentConcertDateTime;
  private LocalTime startTime;
  private LocalTime endTime;
  private List<ConcertInfo> infos;

  public CalendarSlotDto(LocalDateTime currentConcertDateTime, LocalTime start, LocalTime end) {
    this.currentConcertDateTime = currentConcertDateTime;
    this.startTime = start;
    this.endTime = end;
    infos = new ArrayList<>();
  }

  public String getStartTimeInfo() {
    String startTimeStr = startTime.format(DateTimeFormatter.ofPattern("HH:mm"));
    return startTimeStr.equals("00:00") ? "-" : startTimeStr;
  }

  public LocalDateTime getCurrentConcertDateTime() {
    return currentConcertDateTime;
  }

  public void addInfo(ConcertInfo info) {
    infos.add(info);
  }

  public List<ConcertInfo> getConcertInfos() {
    if (infos == null) {
      return new ArrayList<>();
    }

    return infos;
  }
}
