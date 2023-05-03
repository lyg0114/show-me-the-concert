package com.consert.showmetheconsert.service;

import com.consert.showmetheconsert.model.entity.ConcertInfo;
import com.consert.showmetheconsert.repository.ConcertInfoRepository;
import com.consert.showmetheconsert.util.CalendarDay;
import com.consert.showmetheconsert.util.CalendarSlot;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author : iyeong-gyo
 * @package : com.consert.showmetheconsert.service
 * @since : 2023/05/02
 */
@RequiredArgsConstructor
@Service
public class MakeCalendarService {

  private final ConcertInfoRepository concertInfoRepo;

  public List<DayOfWeek> getDayOfWeeks() {
    LocalDate startDate = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
    List<DayOfWeek> daysOfWeek = new ArrayList<>();
    for (int i = 0; i < 7; i++) {
      daysOfWeek.add(startDate.plusDays(i).getDayOfWeek());
    }
    return daysOfWeek;
  }

  public List<List<CalendarDay>> makeCalendar() {
    LocalDateTime start = LocalDateTime.of(2023, 5, 1, 0, 0);
    LocalDateTime end = LocalDateTime.of(2023, 6, 1, 0, 0);
    List<ConcertInfo> concertInfos = concertInfoRepo
        .findByConcertDateTimeBetween(start, end);

    LocalDate startDate = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
    LocalDate endDate = startDate.plusMonths(1).minusDays(1);
    List<List<CalendarDay>> calendarRows = new ArrayList<>();
    LocalDate currentDate = startDate;
    while (!currentDate.isAfter(endDate)) {
      List<CalendarDay> row = new ArrayList<>();
      for (int i = 0; i < 7; i++) {
        if (currentDate.isAfter(endDate)) {
          row.add(new CalendarDay(null));
        } else {
          CalendarDay day = new CalendarDay(currentDate);
          List<CalendarSlot> slots = new ArrayList<>();
          for (int j = 0; j <= 23; j++) {
            for (int k = 0; k < 2; k++) {
              LocalTime startTime = LocalTime.of(j, k * 30);
              LocalDateTime currentConcertDateTime = LocalDateTime.of(currentDate, startTime);
              LocalTime endTime = startTime.plusMinutes(30);
              CalendarSlot slot = new CalendarSlot(startTime, endTime);

              for (ConcertInfo concertInfo : concertInfos) {
                if (concertInfo.getConcertDateTime().equals(currentConcertDateTime)) {
                  slot.addInfo(concertInfo);
                  slots.add(slot);
                }
              }

            }
          }
          day.setSlots(slots);
          row.add(day);
        }
        currentDate = currentDate.plusDays(1);
      }
      calendarRows.add(row);
    }

    return calendarRows;
  }
}
