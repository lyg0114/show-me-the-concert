package com.consert.showmetheconsert.service;

import com.consert.showmetheconsert.repository.ConcertInfoRepository;
import com.consert.showmetheconsert.service.sample.MakeSampleData;
import com.consert.showmetheconsert.util.CalendarDay;
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
    List<List<CalendarDay>> lists = service.makeCalendar();
    for (List<CalendarDay> list : lists) {
      for (CalendarDay day : list) {
        System.out.println("day.getDay() = " + day.getDay());
      }
    }
  }
}

