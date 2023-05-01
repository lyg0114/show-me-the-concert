package com.consert.showmetheconsert.repository;

import com.consert.showmetheconsert.model.entity.ConcertInfo;
import org.springframework.data.repository.CrudRepository;

/**
 * @author : iyeong-gyo
 * @package : com.consert.showmetheconsert.repository
 * @since : 2023/05/01
 */
public interface ConcertInfoRepository extends CrudRepository<ConcertInfo, Long> {
}
