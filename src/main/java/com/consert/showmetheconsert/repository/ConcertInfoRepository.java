package com.consert.showmetheconsert.repository;

import com.consert.showmetheconsert.model.entity.ConcertInfo;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author : iyeong-gyo
 * @package : com.consert.showmetheconsert.repository
 * @since : 2023/05/01
 */
public interface ConcertInfoRepository extends JpaRepository<ConcertInfo, Long> {
  List<ConcertInfo> findByConcertDateTimeBetween(LocalDateTime start, LocalDateTime end);
}
