package com.example.detector.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static com.example.detector.constant.FileConstant.*;
import static org.junit.jupiter.api.Assertions.*;


class DetectorServiceTest {

   DetectorService detectorService;

    @BeforeEach
    void setUp() {
        detectorService = new DetectorServiceImpl();
    }

    @Test
    void isFemale() {
        assertEquals(true, detectorService.isFemale("Maria",NAME_FEMALE_LIST));
        assertEquals(false, detectorService.isFemale("Jan",NAME_FEMALE_LIST));
        assertEquals(false, detectorService.isFemale("aa",NAME_FEMALE_LIST));
    }

    @Test
    void isMale() {
        assertEquals(true, detectorService.isMale("Jan",NAME_MALE_LIST));
        assertEquals(true, detectorService.isMale("Jan",NAME_MALE_LIST));
        assertEquals(false, detectorService.isMale("Ada",NAME_MALE_LIST));
    }

    @Test
    void getGenderBaseOnCounters() {
        assertEquals("MALE", detectorService.getGenderBaseOnCounters(2,1,0));
        assertEquals("FEMALE", detectorService.getGenderBaseOnCounters(1,2,0));
        assertEquals("INCONCLUSIVE", detectorService.getGenderBaseOnCounters(1,2,1));
        assertEquals("INCONCLUSIVE", detectorService.getGenderBaseOnCounters(1,1,0));
    }

    @Test
    void detectGender() {
        assertEquals("MALE", detectorService.detectGender("Jan Maria", "ONE"));
        assertEquals("INCONCLUSIVE", detectorService.detectGender("Jan Maria", "ALL"));
        assertEquals("FEMALE", detectorService.detectGender("Maria Jan", "ONE"));
        assertEquals("MALE", detectorService.detectGender("Jan Maria Rokita", "ONE"));
        assertEquals("INCONCLUSIVE", detectorService.detectGender("Jan Maria Rokita", "ALL"));
        assertEquals("FEMALE", detectorService.detectGender("Jan Maria Agata", "ALL"));
        assertEquals("INCONCLUSIVE", detectorService.detectGender("a Maria Agata", "ONE"));
        assertEquals("INCONCLUSIVE", detectorService.detectGender("a Maria Agata", "ALL"));
    }

    @Test
    void isVariantOne() {
        assertEquals(false, detectorService.isVariantOne("All"));
        assertEquals(true, detectorService.isVariantOne("One"));
        assertEquals(false, detectorService.isVariantOne("fghfghfhg"));
        assertEquals(false, detectorService.isVariantOne(null));
    }

    @Test
    void isVariantAll() {
        assertEquals(true, detectorService.isVariantAll("All"));
        assertEquals(false, detectorService.isVariantAll("One"));
        assertEquals(false, detectorService.isVariantAll("asasasasae"));
        assertEquals(false, detectorService.isVariantAll(null));
    }

    @Test
    void isolateFirstName() {
        String twoNames = "Adam Grażyna";
        assertEquals("Adam", detectorService.isolateFirstName(twoNames));
    }

    @Test
    void splitName() {
        String oneName = "Adam";
        String twoNames = "Adam Grażyna";
        String threeNames = "Adam Grażyna Janusz";
        String[] name1 = detectorService.splitName(oneName);
        assertEquals(oneName, name1[0]);
        String[] name2 = detectorService.splitName(twoNames);
        assertEquals(oneName, name2[0]);
        assertEquals("Grażyna", name2[1]);
        String[] name3 = detectorService.splitName(threeNames);
        assertEquals(oneName, name3[0]);
        assertEquals("Grażyna", name3[1]);
        assertEquals("Janusz", name3[2]);
    }
}