package com.consert.showmetheconsert.schedule.jsoup;

import com.consert.showmetheconsert.conf.GlobalVar;
import com.consert.showmetheconsert.model.dto.daeguconcert.DaeguConcertDto;
import com.consert.showmetheconsert.model.entity.ConcertInfo;
import com.consert.showmetheconsert.repository.ConcertInfoRepository;
import com.consert.showmetheconsert.schedule.SearchDaeguConcertScheduleInterface;
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
  public static final String HOST_URL = "https://www.daeguconcerthouse.or.kr/index.do?menu_link=%2Ffront%2Fschedule%2FconcertScheduleDetailView.do&menu_id=00000014&year=2023&con_id=";
  public static final String REG_EXPRESSION_DATE = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}";

  private final GlobalVar global;
  private final ConcertInfoRepository concertInfoRepo;

  @Override
  public void searchData() {
    ArrayList<DaeguConcertDto> daeguConcertDtos = new ArrayList<>();
    extractTargestHref(daeguConcertDtos);
    extractInfos(daeguConcertDtos);
  }

  public void extractTargestHref(ArrayList<DaeguConcertDto> daeguConcertDtos) {
    Document doc = getDaeguConcertDocument();
    Elements titles = doc.select("a");
    for (Element title : titles) {
      Elements href = title.getElementsByAttribute("href");
      if (href.size() > 0) {
        String link = href.get(0).attributes().get("href");
        if (link.contains(COMPARE_STR)) {
          String[] showIds = link.split("'");
          String titleStr = title.html();
          daeguConcertDtos.add(new DaeguConcertDto(showIds[1], titleStr));
        }
      }
    }
  }

  private Document getDaeguConcertDocument() {
    Document doc = null;
    try {
      doc = Jsoup.connect(global.getDaeguConcertHouseUrl()).get();
    } catch (IOException ex) {
      log.error(ex.getMessage());
      ex.printStackTrace();
      throw new RuntimeException("url connect fail");
    }
    return doc;
  }

  public void extractInfos(ArrayList<DaeguConcertDto> daeguConcertDtos) {
    for (DaeguConcertDto daeguConcertDto : daeguConcertDtos) {
      String targetHost = HOST_URL + daeguConcertDto.getShowId();
      Document detailDoc = null;
      try {
        detailDoc = Jsoup.connect(targetHost).get();
        extractInfo(detailDoc, targetHost, daeguConcertDto);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  private void extractInfo(Document detailDoc, String targetHost, DaeguConcertDto daeguConcertDto) {
    ConcertInfo info = ConcertInfo.builder()
        .url(targetHost)
        .title(daeguConcertDto.getTitle())
        .place(detailDoc.selectXpath(CONCERT_PLACE_XPATH).html())
        .concertDateTime(calculateConcertDate(
            detailDoc.selectXpath(CONCERT_TITLE_XPATH).html(),
            detailDoc.selectXpath(CONCERT_DATE_XPATH).html(),
            detailDoc.selectXpath(CONCERT_TIME_XPATH).html()))
        .concertHallTag(GlobalVar.TAG_DAEGUCONCERT_HOUSE)
        .showId(daeguConcertDto.getShowId())
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

  public LocalDateTime calculateConcertDate(String title, String dateStr, String timeStr) {
    LocalDateTime localDateTime = null;
    try {
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
    } catch (RuntimeException ex) {
      log.error(title + " : dateTimeStr has null or whitespace");
      ex.printStackTrace();
      return localDateTime;
    }
  }
}
