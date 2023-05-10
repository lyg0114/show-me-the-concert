package com.consert.showmetheconsert.service;

import com.consert.showmetheconsert.model.entity.ConcertInfo;
import com.consert.showmetheconsert.repository.ConcertInfoRepository;
import com.consert.showmetheconsert.service.sample.MakeSampleData;
import com.consert.showmetheconsert.model.dto.CalendarDayDto;
import com.consert.showmetheconsert.model.dto.CalendarSlotDto;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

/**
 * @author : iyeong-gyo
 * @package : com.consert.showmetheconsert.service
 * @since : 2023/05/03
 */
@DataJpaTest
class MakeCalendarServiceTest {

  @Autowired
  private ConcertInfoRepository concertInfoRepository;
  private MakeCalendarService service;

  @BeforeEach
  public void setUp() {
    MakeSampleData.makeconcertInfoData(concertInfoRepository);
    service = new MakeCalendarService(concertInfoRepository);
  }

  @Test
  public void testMakeCalendar() {
    int year = 2023;
    int month = 5;
    List<List<CalendarDayDto>> lists = service.makeCalendar(year, month);
    for (List<CalendarDayDto> list : lists) {
      for (CalendarDayDto day : list) {
        List<CalendarSlotDto> slots = day.getSlots();
        if (slots.size() > 0) {
          for (CalendarSlotDto slot : slots) {
            System.out
                .println("slot.getCurrentConcertDateTime() = " + slot.getCurrentConcertDateTime());
            List<ConcertInfo> concertInfos = slot.getConcertInfos();
            for (ConcertInfo concertInfo : concertInfos) {
              System.out.println("concertInfo = " + concertInfo);
            }
          }
        }
      }
    }
  }

}

