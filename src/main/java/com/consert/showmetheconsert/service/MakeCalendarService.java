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
    List<ConcertInfo> concertInfos = concertInfoRepo.findByConcertDateTimeBetween(start, end);

    List<List<CalendarDay>> calendarRows = new ArrayList<>();
    makeBasicCalendar(calendarRows);
    asignConcertInfos(concertInfos, calendarRows);

    return calendarRows;
  }

  private void makeBasicCalendar(List<List<CalendarDay>> calendarRows) {
    LocalDate startDate = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
    LocalDate endDate = startDate.plusMonths(1).minusDays(1);
    LocalDate currentDate = startDate;
    while (!currentDate.isAfter(endDate)) {
      List<CalendarDay> row = new ArrayList<>();
      for (int i = 0; i < 7 && !currentDate.isAfter(endDate); i++) {
        CalendarDay day = new CalendarDay(currentDate);
        List<CalendarSlot> slots = new ArrayList<>();
        for (int j = 0; j <= 23; j++) {
          for (int k = 0; k < 2; k++) {
            LocalTime startTime = LocalTime.of(j, k * 30);
            LocalDateTime currentConcertDateTime = LocalDateTime.of(currentDate, startTime);
            LocalTime endTime = startTime.plusMinutes(30);
            CalendarSlot slot = new CalendarSlot(currentConcertDateTime, startTime, endTime);
            slots.add(slot);
          }
        }
        day.setSlots(slots);
        row.add(day);
        currentDate = currentDate.plusDays(1);
      }
      calendarRows.add(row);
    }
  }

  private void asignConcertInfos(List<ConcertInfo> concertInfos,
      List<List<CalendarDay>> calendarRows) {
    for (List<CalendarDay> calendarRow : calendarRows) {
      for (CalendarDay calendarDay : calendarRow) {
        for (CalendarSlot slot : calendarDay.getSlots()) {
          LocalDateTime currentConcertDateTime = slot.getCurrentConcertDateTime();
          concertInfos.stream()
              .filter(
                  concertInfo -> concertInfo.getConcertDateTime().equals(currentConcertDateTime))
              .forEach(slot::addInfo);
        }
      }
    }
  }
}
