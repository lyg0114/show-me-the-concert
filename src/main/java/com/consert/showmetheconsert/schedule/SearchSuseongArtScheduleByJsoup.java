package com.consert.showmetheconsert.schedule;

import com.consert.showmetheconsert.conf.GlobalVar;
import com.consert.showmetheconsert.model.entity.ConcertInfo;
import com.consert.showmetheconsert.repository.ConcertInfoRepository;
import com.consert.showmetheconsert.util.TimeUtil;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : iyeong-gyo
 * @package : com.consert.showmetheconsert.schedule
 * @since : 2023/05/12
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class SearchSuseongArtScheduleByJsoup implements SearchSuseongArtScheduleInterface {

  public static final String CONCERT_TITLE_XPATH = "/html/body/div/div[3]/div[2]/div/h4";
  public static final String CONCERT_PLACE_XPATH = "/html/body/div/section/div/div/div/div/div[1]/div[2]/table/tbody/tr[4]/td[2]";
  public static final String CONCERT_DATE_XPATH = "/html/body/div/section/div/div/div/div/div[1]/div[2]/table/tbody/tr[1]/td[1]";
  public static final String CONCERT_TIME_XPATH = "/html/body/div/section/div/div/div/div/div[1]/div[2]/table/tbody/tr[1]/td[2]";
  public static final String REG_EXPRESSION_DATE = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}";

  private final GlobalVar global;
  private final ConcertInfoRepository concertInfoRepo;

  @Override
  public void searchData() {
    Document doc = null;
    try {
      doc = Jsoup.connect(global.getSuseongArtUrl()).get();
    } catch (IOException ex) {
      log.error(ex.getMessage());
      ex.printStackTrace();
      throw new RuntimeException("url connect fail");
    }

    ArrayList<String> targetUrls = new ArrayList<>();
    extractTargestHref(doc, targetUrls);

    for (String targetURL : targetUrls) {
      String host = "https://www.ssartpia.kr/";
      String targetHost = host + targetURL;
      Document detailDoc = null;
      try {
        detailDoc = Jsoup.connect(targetHost).get();
        extractData(detailDoc, targetHost);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  @Transactional
  public void extractData(Document detailDoc, String targetHost) {
    ConcertInfo info = ConcertInfo.builder()
        .url(targetHost)
        .title(detailDoc.selectXpath(CONCERT_TITLE_XPATH).html())
        .place(detailDoc.selectXpath(CONCERT_PLACE_XPATH).html())
        .concertDateTime(calculateConcertDate(
            detailDoc.selectXpath(CONCERT_TITLE_XPATH).html(),
            detailDoc.selectXpath(CONCERT_DATE_XPATH).html(),
            detailDoc.selectXpath(CONCERT_TIME_XPATH).html()))
        .concertHallTag(GlobalVar.TAG_SUSEONGART)
        .showId(extractShowId(targetHost))
        .build();
    saveInfo(info);
    log.info(info.toString());
  }

  private void saveInfo(ConcertInfo info) {
    if (info.getShowId() == null) {
      return;
    }

    Optional<ConcertInfo> savedInfo = concertInfoRepo
        .findConcertInfoByShowId(info.getShowId());
    if (savedInfo.isPresent()) {
      savedInfo.ifPresent(i -> i.updateConcertInfo(info));
    } else {
      concertInfoRepo.save(info);
    }
  }

  public String extractShowId(String targetUrl) {
    String result = null;
    try {
      result = targetUrl.split("&")[2]
          .split("=")[1];
    } catch (RuntimeException ex) {
      log.error("check the targetUrl : " + targetUrl);
      ex.printStackTrace();
    }
    return result;
  }

  public LocalDateTime calculateConcertDate(String title, String dateStr, String timeStr) {
    LocalDateTime localDateTime = null;
    try {
      StringBuilder sb = new StringBuilder();
      sb.append(dateStr.split("\\.")[0]);
      sb.append("-");
      sb.append(dateStr.split("\\.")[1]);
      sb.append("-");
      sb.append(dateStr.split("\\.")[2]);
      dateStr = sb.toString();

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
    String dateTimeStr = sb.toString();

    Pattern pattern = Pattern.compile(REG_EXPRESSION_DATE);
    Matcher matcher = pattern.matcher(dateTimeStr);
    String datetimeStrResult = null;
    if (matcher.find()) {
      datetimeStrResult = matcher.group();
    } else {
      datetimeStrResult = dateStr + " 00:00";
      log.warn(title + " : need one more check time info");
    }

    return TimeUtil.convertToLocalDateTime(datetimeStrResult);
  }

  private void extractTargestHref(Document doc, ArrayList<String> targetUrls) {
    Elements titles = doc.select("a");
    for (Element title : titles) {
      Elements href = title.getElementsByAttribute("href");
      if (href.size() > 0) {
        String link = href.get(0).attributes().get("href");
        if (link.contains("&no=")) {
          targetUrls.add(link);
        }
      }
    }
  }
}
