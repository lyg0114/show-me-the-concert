package com.consert.showmetheconsert.util;

import static org.mockito.Mockito.when;

import com.consert.showmetheconsert.conf.GlobalVar;
import com.consert.showmetheconsert.repository.ConcertInfoRepository;
import com.consert.showmetheconsert.schedule.SearchDaeguConcertSchedule;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
  public void testExpressionWhenDataParse() {
    String timeStr = "DUMMY_STRING 2023-05-07 19:00";
    Pattern pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}");
    Matcher matcher = pattern.matcher(timeStr);
    String datetimeStr = null;
    if (matcher.find()) {
      datetimeStr = matcher.group();
      Assertions.assertEquals("2023-05-07 19:00", datetimeStr);
    }
  }

  @Test
  public void testCalculateConcertDate() {
    SearchDaeguConcertSchedule schedule = getSearchDaeguConcertSchedule();
    when(null).thenReturn(null);
  }

}