package com.consert.showmetheconsert.service;

import com.consert.showmetheconsert.model.dto.CalendarDayDto;
import com.consert.showmetheconsert.model.dto.CalendarSlotDto;
import com.consert.showmetheconsert.model.entity.ConcertInfo;
import com.consert.showmetheconsert.repository.ConcertInfoRepository;
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

  public List<DayOfWeek> getDayOfWeeks(int year, int month) {
    LocalDate startDate = LocalDate.of(year, month, 1);
    startDate.with(TemporalAdjusters.firstDayOfMonth());

    List<DayOfWeek> daysOfWeek = new ArrayList<>();
    for (int i = 0; i < 7; i++) {
      daysOfWeek.add(startDate.plusDays(i).getDayOfWeek());
    }
    return daysOfWeek;
  }

  public List<List<CalendarDayDto>> makeCalendar(int year, int month) {
    LocalDateTime start = LocalDateTime.of(year, month, 1, 0, 0);
    LocalDateTime end = LocalDateTime.of(year, getNextMonth(month), 1, 0, 0);
    List<ConcertInfo> concertInfos = concertInfoRepo.findByConcertDateTimeBetween(start, end);
    List<List<CalendarDayDto>> calendarRows = new ArrayList<>();
    makeBasicCalendar(calendarRows, year, month);
    asignConcertInfos(concertInfos, calendarRows);
    cleanTable(calendarRows);
    return calendarRows;
  }

  private int getNextMonth(int month) {
    if (month == 12) {
      return 1;
    } else {
      return month + 1;
    }
  }

  private void makeBasicCalendar(List<List<CalendarDayDto>> calendarRows, int year, int month) {
    LocalDate startDate = LocalDate.of(year, month, 1)
        .with(TemporalAdjusters.firstDayOfMonth());
    LocalDate endDate = startDate.plusMonths(1).minusDays(1);
    LocalDate currentDate = startDate;
    while (!currentDate.isAfter(endDate)) {
      List<CalendarDayDto> row = new ArrayList<>();
      for (int i = 0; i < 7 && !currentDate.isAfter(endDate); i++) {
        CalendarDayDto day = new CalendarDayDto(currentDate);
        List<CalendarSlotDto> slots = new ArrayList<>();
        for (int j = 0; j <= 23; j++) {
          for (int k = 0; k < 2; k++) {
            LocalTime startTime = LocalTime.of(j, k * 30);
            LocalDateTime currentConcertDateTime = LocalDateTime.of(currentDate, startTime);
            LocalTime endTime = startTime.plusMinutes(30);
            CalendarSlotDto slot = new CalendarSlotDto(currentConcertDateTime, startTime, endTime);
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
      List<List<CalendarDayDto>> calendarRows) {
    for (List<CalendarDayDto> calendarRow : calendarRows) {
      for (CalendarDayDto calendarDay : calendarRow) {
        for (CalendarSlotDto slot : calendarDay.getSlots()) {
          LocalDateTime currentConcertDateTime = slot.getCurrentConcertDateTime();
          concertInfos.stream()
              .filter(
                  concertInfo -> concertInfo.getConcertDateTime().equals(currentConcertDateTime))
              .forEach(slot::addInfo);
        }
      }
    }
  }

  private void cleanTable(List<List<CalendarDayDto>> calendarRows) {
    for (List<CalendarDayDto> calendarRow : calendarRows) {
      for (CalendarDayDto calendarDay : calendarRow) {
        calendarDay.getSlots().removeIf(slot -> slot.getConcertInfos().isEmpty());
      }
    }
  }
}
