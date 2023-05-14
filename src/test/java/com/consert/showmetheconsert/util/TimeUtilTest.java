package com.consert.showmetheconsert.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.consert.showmetheconsert.conf.GlobalVar;
import com.consert.showmetheconsert.repository.ConcertInfoRepository;
import com.consert.showmetheconsert.schedule.selenium.SearchDaeguConcertScheduleBySelenium;
import java.time.LocalDateTime;
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
  private SearchDaeguConcertScheduleBySelenium schedule;

  private SearchDaeguConcertScheduleBySelenium getSearchDaeguConcertSchedule() {
    return new SearchDaeguConcertScheduleBySelenium(
        new GlobalVar(), new DummyWebDriver(), concertInfoRepo);
  }

  @Test
  public void test_calculate_concertDate() {
    SearchDaeguConcertScheduleBySelenium schedule = getSearchDaeguConcertSchedule();
    LocalDateTime resultDateTime = schedule
        .calculateConcertDate("DUMMY_STRING 2023-05-07", "19:00");
    assertEquals(LocalDateTime.of(2023, 5, 7, 19, 0), resultDateTime);
  }

  @Test
  public void test_calculate_concertDate_when_concert_info_has_DummyStr() {
    SearchDaeguConcertScheduleBySelenium schedule = getSearchDaeguConcertSchedule();
    LocalDateTime resultDateTime = schedule
        .calculateConcertDate(" 202DUMMY_STRING3-05-07", "19:00");
    assertNull(resultDateTime);
  }

  @Test
  public void test_calculate_concertDate_when_concert_info_isNull() {
    SearchDaeguConcertScheduleBySelenium schedule = getSearchDaeguConcertSchedule();
    LocalDateTime resultDateTime = schedule.calculateConcertDate(null, null);
    assertNull(resultDateTime);
  }

  @Test
  public void test_calculate_concertDate_when_concert_date_isEmpty() {
    SearchDaeguConcertScheduleBySelenium schedule = getSearchDaeguConcertSchedule();
    LocalDateTime resultDateTime = schedule.calculateConcertDate("2023-05-07", null);
    assertNull(resultDateTime);
  }

  @Test
  public void test_calculate_concertDate_when_concert_time_isEmpty() {
    SearchDaeguConcertScheduleBySelenium schedule = getSearchDaeguConcertSchedule();
    LocalDateTime resultDateTime = schedule.calculateConcertDate(null, "19:00");
    assertNull(resultDateTime);
  }
}