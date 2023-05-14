package com.consert.showmetheconsert.schedule;

import com.consert.showmetheconsert.conf.GlobalVar;
import com.consert.showmetheconsert.model.dto.daeguconcert.DaeguConcertDto;
import com.consert.showmetheconsert.repository.ConcertInfoRepository;
import com.consert.showmetheconsert.schedule.jsoup.SearchDaeguConcertScheduleByJsoup;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author : iyeong-gyo
 * @package : com.consert.showmetheconsert.schedule
 * @since : 2023/05/13
 */
@SpringBootTest
class SearchDaeguConcertScheduleByJsoupTest {

  @Autowired
  private GlobalVar global;
  @Autowired
  private ConcertInfoRepository concertInfoRepo;

  private SearchDaeguConcertScheduleByJsoup getSchedule() {
    return new SearchDaeguConcertScheduleByJsoup(global, concertInfoRepo);
  }

  @Test
  public void test_searchData() {
    SearchDaeguConcertScheduleByJsoup schedule = getSchedule();
    schedule.searchData();
  }

  @Test
  public void test_extractTargestHref() {
    SearchDaeguConcertScheduleByJsoup schedule = getSchedule();
    ArrayList<DaeguConcertDto> daeguConcertDtos = new ArrayList<>();
  }

}