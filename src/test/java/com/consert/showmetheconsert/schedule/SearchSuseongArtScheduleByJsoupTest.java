package com.consert.showmetheconsert.schedule;

import com.consert.showmetheconsert.conf.GlobalVar;
import com.consert.showmetheconsert.repository.ConcertInfoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author : iyeong-gyo
 * @package : com.consert.showmetheconsert.schedule
 * @since : 2023/05/13
 */
@SpringBootTest
class SearchSuseongArtScheduleByJsoupTest {

  @Autowired
  private GlobalVar global;
  @Autowired
  private ConcertInfoRepository concertInfoRepo;

  private SearchSuseongArtScheduleInterface getSchedule() {
    return new SearchSuseongArtScheduleByJsoup(
        global, concertInfoRepo);
  }

  @Test
  public void test_searchData() {
    SearchSuseongArtScheduleInterface schedule = getSchedule();
    schedule.searchData();
  }

}