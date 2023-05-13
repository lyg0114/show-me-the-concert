package com.consert.showmetheconsert.schedule;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.consert.showmetheconsert.conf.GlobalVar;
import com.consert.showmetheconsert.repository.ConcertInfoRepository;
import com.consert.showmetheconsert.util.DummyWebDriver;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

/**
 * @author : iyeong-gyo
 * @package : com.consert.showmetheconsert.schedule
 * @since : 2023/05/10
 */
@DataJpaTest
class SearchDaeguConcertScheduleBySeleniumTest {

  @Autowired
  private ConcertInfoRepository concertInfoRepository;

  private SearchDaeguConcertScheduleBySelenium getSchedule() {
    return new SearchDaeguConcertScheduleBySelenium(new GlobalVar(),
        new DummyWebDriver(), concertInfoRepository);
  }

  @Test
  public void test_extractShowId_daegu_concert_Url() {
    SearchDaeguConcertScheduleBySelenium schedule = getSchedule();
    String targetUrl = "https://www.daeguconcerthouse.or.kr/index.do?menu_link=%2Ffront%2Fschedule%2FconcertScheduleDetailView.do&con_id=SC_00000000000000001274&menu_id=00000014&year=2023";
    String result = schedule.extractShowId(targetUrl);
    assertEquals("SC_00000000000000001274", result);
  }

  @Test
  public void fail_test_extractShowId_daegu_concert_Url() {
    SearchDaeguConcertScheduleBySelenium schedule = getSchedule();
    String targetUrl = "https://www.daeguconcerthouse.or.kr/index.do";
    assertNull(schedule.extractShowId(targetUrl));
  }
}