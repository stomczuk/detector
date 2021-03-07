package com.example.detector.controller;

import com.example.detector.service.DetectorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = DetectorController.class)
class DetectorControllerTest {

    @MockBean
    DetectorService detectorService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldReturnMaleONEVariant() throws Exception {
        given(detectorService.getGenderByNameToken("Jan", "ONE")).willReturn("MALE");
        MvcResult mvcResult = mockMvc
                .perform(get("/detector/gender").param("name", "Jan").param("variant", "ONE"))
                .andExpect(status().isOk())
                .andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals("MALE", content);
    }

    @Test
    void shouldReturnFemaleONEVariant() throws Exception {
        given(detectorService.getGenderByNameToken("Maria", "ONE")).willReturn("FEMALE");
        MvcResult mvcResult = mockMvc
                .perform(get("/detector/gender").param("name", "Maria").param("variant", "ONE"))
                .andExpect(status().isOk())
                .andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals("FEMALE", content);

    }

    @Test
    void shouldReturnMaleALLVariant() throws Exception {
        given(detectorService.getGenderByNameToken("Maria Jan Janusz", "ALL")).willReturn("MALE");
        MvcResult mvcResult = mockMvc
                .perform(get("/detector/gender").param("name", "Maria Jan Janusz").param("variant", "ALL"))
                .andExpect(status().isOk())
                .andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals("MALE", content);

    }

    @Test
    void shouldReturnFemaleALLVariant() throws Exception {
        given(detectorService.getGenderByNameToken("Maria Jan Wioletta", "ALL")).willReturn("FEMALE");
        MvcResult mvcResult = mockMvc
                .perform(get("/detector/gender").param("name", "Maria Jan Wioletta").param("variant", "ALL"))
                .andExpect(status().isOk())
                .andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals("FEMALE", content);

    }

    @Test
    void shouldReturnInconclusiveOneVariant() throws Exception {
        given(detectorService.getGenderByNameToken("aa Maria Jan", "ONE")).willReturn("INCONCLUSIVE");
        MvcResult mvcResult = mockMvc
                .perform(get("/detector/gender").param("name", "aa Maria Jan").param("variant", "ONE"))
                .andExpect(status().isOk())
                .andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals("INCONCLUSIVE", content);

    }

    @Test
    void shouldReturnInconclusiveALLVariant() throws Exception {
        given(detectorService.getGenderByNameToken("Maria Jan aa", "ALL")).willReturn("INCONCLUSIVE");
        MvcResult mvcResult = mockMvc
                .perform(get("/detector/gender").param("name", "Maria Jan aa").param("variant", "ALL"))
                .andExpect(status().isOk())
                .andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals("INCONCLUSIVE", content);

    }
}