package com.consert.showmetheconsert.schedule;

import com.consert.showmetheconsert.conf.GlobalVar;
import com.consert.showmetheconsert.model.entity.ConcertInfo;
import com.consert.showmetheconsert.repository.ConcertInfoRepository;
import com.consert.showmetheconsert.util.TimeUtil;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
  public static final String CONCERT_TITLE_XPATH = "/html/body/div[2]/div[3]/div/div/div/div[2]/div[2]/div[1]/div[2]/strong";
  public static final String CONCERT_PLACE_XPATH = "/html/body/div[2]/div[3]/div/div/div/div[2]/div[2]/div[1]/div[2]/ul/li[1]/div";
  public static final String CONCERT_DATE_XPATH = "/html/body/div[2]/div[3]/div/div/div/div[2]/div[2]/div[1]/div[2]/ul/li[2]/div";
  public static final String CONCERT_TIME_XPATH = "/html/body/div[2]/div[3]/div/div/div/div[2]/div[2]/div[1]/div[2]/ul/li[3]/div";
  public static final String REG_EXPRESSION_DATE = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}";

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

    ArrayList<String> showIds = new ArrayList<>();
    extractTargestHref(doc, showIds);
    for (String showId : showIds) {
      String host = "https://www.daeguconcerthouse.or.kr/index.do?menu_link=%2Ffront%2Fschedule%2FconcertScheduleDetailView.do&menu_id=00000014&year=2023&con_id=";
      String targetHost = host + showId;
      Document detailDoc = null;
      try {
        detailDoc = Jsoup.connect(targetHost).get();
        extractData(detailDoc, targetHost, showId);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  private void extractData(Document detailDoc, String targetHost, String showId) {
    ConcertInfo info = ConcertInfo.builder()
        .url(targetHost)
        .title(detailDoc.selectXpath(CONCERT_TITLE_XPATH).html())
        .place(detailDoc.selectXpath(CONCERT_PLACE_XPATH).html())
        .concertDateTime(calculateConcertDate(
            detailDoc.selectXpath(CONCERT_TITLE_XPATH).html(),
            detailDoc.selectXpath(CONCERT_DATE_XPATH).html(),
            detailDoc.selectXpath(CONCERT_TIME_XPATH).html()))
        .concertHallTag(GlobalVar.TAG_DAEGUCONCERT_HOUSE)
        .showId(showId)
        .build();
//    saveInfo(info);
    log.info(info.toString());
  }

  public LocalDateTime calculateConcertDate(String title, String dateStr, String timeStr) {
    LocalDateTime localDateTime = null;
    try {
      localDateTime = getLocalDateTime(title, dateStr, timeStr);
    } catch (RuntimeException ex) {
      log.error(title + " : dateTimeStr has null or whitespace");
      ex.printStackTrace();
    }
    return localDateTime;
  }

  private LocalDateTime getLocalDateTime(String title, String dateStr, String timeStr) {
    StringBuilder sb = new StringBuilder();
    sb.append(dateStr);
    sb.append(" ");
    sb.append(timeStr);
    String dateTime = sb.toString();

    Pattern pattern = Pattern.compile(REG_EXPRESSION_DATE);
    Matcher matcher = pattern.matcher(dateTime);
    String datetimeStr = null;
    if (matcher.find()) {
      datetimeStr = matcher.group();
    } else {
      log.warn(title + " : need one more check time info");
    }

    return TimeUtil.convertToLocalDateTime(datetimeStr);
  }

  private void extractTargestHref(Document doc, ArrayList<String> showId) {
    Elements titles = doc.select("a");
    for (Element title : titles) {
      Elements href = title.getElementsByAttribute("href");
      if (href.size() > 0) {
        String link = href.get(0).attributes().get("href");
        if (link.contains(COMPARE_STR)) {
          String[] split = link.split("'");
          showId.add(split[1]);
        }
      }
    }

  }
}
