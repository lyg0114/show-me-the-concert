package com.consert.showmetheconsert.util;

import com.consert.showmetheconsert.model.entity.ConcertInfo;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : iyeong-gyo
 * @package : com.consert.showmetheconsert.util
 * @since : 2023/05/02
 */
public class CalendarSlot {

  private LocalTime startTime;
  private LocalTime endTime;
  private List<ConcertInfo> infos;

  public CalendarSlot(LocalTime startTime, LocalTime endTime) {
    this.startTime = startTime;
    this.endTime = endTime;
    infos = new ArrayList<>();
  }

  public LocalTime getStartTimeInfo() {
    return startTime;
  }

  public void addInfo(ConcertInfo info) {
    infos.add(info);
  }

  public List<ConcertInfo> getConcertInfos() {
    if(infos == null)
      return new ArrayList<>();

    return infos;
  }
}
