package com.consert.showmetheconsert.model.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @author : iyeong-gyo
 * @package : com.consert.showmetheconsert.model.entity
 * @since : 2023/05/01
 */
@MappedSuperclass
public class BaseEntity implements Serializable {

  @Id
  @Column(name = "ID", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
}
