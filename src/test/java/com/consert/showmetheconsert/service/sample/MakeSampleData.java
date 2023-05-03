package com.consert.showmetheconsert.service.sample;

import com.consert.showmetheconsert.model.entity.ConcertInfo;
import com.consert.showmetheconsert.repository.ConcertInfoRepository;
import java.time.LocalDateTime;

/**
 * @author : iyeong-gyo
 * @package : com.consert.showmetheconsert.service.sample
 * @since : 2023/05/03
 */
public class MakeSampleData {

  public static void makeconcertInfoData(ConcertInfoRepository concertInfoRepository) {
    concertInfoRepository
        .save(ConcertInfo.builder().showId("show-1").place("place-1").title("title-1")
            .url("https://example1.com").concertHallTag("Tag-1")
            .concertDateTime(LocalDateTime.of(2023, 5, 3, 13, 0))
            .build()
        );
    concertInfoRepository
        .save(ConcertInfo.builder().showId("concert-A").place("place-A").title("title-A")
            .url("https://exampleA.com").concertHallTag("Tag-2")
            .concertDateTime(LocalDateTime.of(2023, 5, 3, 13, 0))
            .build()
        );

    concertInfoRepository
        .save(ConcertInfo.builder().showId("show-2").place("place-2").title("title-2")
            .url("https://example2.com").concertHallTag("Tag-1")
            .concertDateTime(LocalDateTime.of(2023, 5, 17, 17, 0))
            .build());
    concertInfoRepository
        .save(ConcertInfo.builder().showId("show-3").place("place-3").title("title-3")
            .url("https://example3.com").concertHallTag("Tag-1")
            .concertDateTime(LocalDateTime.of(2023, 5, 24, 18, 30))
            .build());
  }

}
