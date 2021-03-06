package com.example.detector.component;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GenderResolverTest {

    GenderResolver genderResolver;

    @BeforeEach
    void setUp() {
        genderResolver = new FromFileGenderResolver();

    }

    @Test
    void detectGender() {
        assertEquals("MALE", genderResolver.detectGender("Jan Maria", "ONE"));
        assertEquals("INCONCLUSIVE", genderResolver.detectGender("Jan Maria", "ALL"));
        assertEquals("FEMALE", genderResolver.detectGender("Maria Jan", "ONE"));
        assertEquals("MALE", genderResolver.detectGender("Jan Maria Rokita", "ONE"));
        assertEquals("INCONCLUSIVE", genderResolver.detectGender("Jan Maria Rokita", "ALL"));
        assertEquals("FEMALE", genderResolver.detectGender("Jan Maria Agata", "ALL"));
        assertEquals("INCONCLUSIVE", genderResolver.detectGender("a Maria Agata", "ONE"));
        assertEquals("INCONCLUSIVE", genderResolver.detectGender("a Maria Agata", "ALL"));
    }
    @Test
    void getAllNameTokensForGender() {
    }
}