package com.consert.showmetheconsert.model.dto.daeguconcert;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : iyeong-gyo
 * @package : com.consert.showmetheconsert.model.dto.daeguconcert
 * @since : 2023/05/13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DaeguConcertDto {
  private String showId;
  private String title;
}
