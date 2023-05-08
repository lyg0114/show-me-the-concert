package com.consert.showmetheconsert.util;

import com.consert.showmetheconsert.conf.GlobalVar;
import com.consert.showmetheconsert.repository.ConcertInfoRepository;
import com.consert.showmetheconsert.schedule.SearchDaeguConcertSchedule;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

/**
 * @author : iyeong-gyo
 * @package : com.consert.showmetheconsert.util
 * @since : 2023/05/07
 */
@DataJpaTest
class TimeUtilTest {

  @Autowired
  private ConcertInfoRepository concertInfoRepo;
  private SearchDaeguConcertSchedule schedule;

  private SearchDaeguConcertSchedule getSearchDaeguConcertSchedule() {
    return new SearchDaeguConcertSchedule(
        new GlobalVar(), new DummyWebDriver(), concertInfoRepo);
  }

  @Test
  public void testCalculateConcertDate() {
    SearchDaeguConcertSchedule schedule = getSearchDaeguConcertSchedule();
    LocalDateTime localDateTime = schedule.calculateConcertDate("DUMMY_STRING 2023-05-07", "19:00");
    Assertions.assertEquals(LocalDateTime.of(2023, 5, 7, 19, 0), localDateTime);
  }

}