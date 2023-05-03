package com.consert.showmetheconsert.util;

import com.consert.showmetheconsert.model.entity.ConcertInfo;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : iyeong-gyo
 * @package : com.consert.showmetheconsert.util
 * @since : 2023/05/02
 */
public class CalendarSlot {

  private LocalDateTime currentConcertDateTime;
  private LocalTime startTime;
  private LocalTime endTime;
  private List<ConcertInfo> infos;

  public CalendarSlot(LocalDateTime currentConcertDateTime,
      LocalTime startTime, LocalTime endTime) {
    this.currentConcertDateTime = currentConcertDateTime;
    this.startTime = startTime;
    this.endTime = endTime;
    infos = new ArrayList<>();
  }

  public LocalTime getStartTimeInfo() {
    return startTime;
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
