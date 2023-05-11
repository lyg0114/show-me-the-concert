package com.consert.showmetheconsert.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

/**
 * @author : iyeong-gyo
 * @package : com.consert.showmetheconsert.controller
 * @since : 2023/04/30
 */
@SpringBootTest
@AutoConfigureMockMvc
class ShowScheduleControllerTest {

  @Autowired
  private MockMvc mockMvc;

  private static final int TEST_YEAR = 2023;
  private static final int TEST_MONTH = 5;

  @Test
  void testShowSchedule() throws Exception {
    mockMvc.perform(get("/schedule/{year}/{month}", TEST_YEAR, TEST_MONTH))
        .andExpect(status().isOk())
        .andExpect(model().attributeExists("daysOfWeek"))
        .andExpect(model().attributeExists("calendar"))
        .andExpect(model().attributeExists("currentYear"))
        .andExpect(model().attributeExists("currentMonth"))
        .andExpect(model().attributeExists("beforeYear"))
        .andExpect(model().attributeExists("beforeMonth"))
        .andExpect(model().attributeExists("afterYear"))
        .andExpect(model().attributeExists("afterMonth"))
        .andExpect(view().name("schedule/showSchedule"));
  }
}