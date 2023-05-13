package com.consert.showmetheconsert.schedule;

import com.consert.showmetheconsert.conf.GlobalVar;
import com.consert.showmetheconsert.repository.ConcertInfoRepository;
import java.io.IOException;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

/**
 * @author : iyeong-gyo
 * @package : com.consert.showmetheconsert.schedule
 * @since : 2023/05/13
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class SearchDaeguConcertScheduleByJsoup implements SearchDaeguConcertScheduleInterface {

  public static final String COMPARE_STR = "javascript:fn_view('SC";

  private final GlobalVar global;
  private final ConcertInfoRepository concertInfoRepo;

  @Override
  public void searchData() {
    Document doc = null;
    try {
      doc = Jsoup.connect(global.getDaeguConcertHouseUrl()).get();
    } catch (IOException ex) {
      log.error(ex.getMessage());
      ex.printStackTrace();
      throw new RuntimeException("url connect fail");
    }

    ArrayList<String> targetUrls = new ArrayList<>();
    extractTargestHref(doc, targetUrls);

    for (String targetUrl : targetUrls) {
      System.out.println("targetUrl = " + targetUrl);
    }


  }

  private void extractTargestHref(Document doc, ArrayList<String> targetUrls) {
//    https://www.daeguconcerthouse.or.kr/index.do?menu_link=%2Ffront%2Fschedule%2FconcertScheduleDetailView.do&menu_id=00000014&year=2023&con_id=SC_00000000000000001297
    Elements titles = doc.select("a");
    for (Element title : titles) {
      Elements href = title.getElementsByAttribute("href");
      if (href.size() > 0) {
        String link = href.get(0).attributes().get("href");
        if (link.contains(COMPARE_STR)) {
          targetUrls.add(link);
        }
      }
    }

  }
}
