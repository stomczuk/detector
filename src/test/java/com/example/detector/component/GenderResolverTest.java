package com.example.detector.component;

import com.example.detector.exception.InvalidGenderParamException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GenderResolverTest {

    @Autowired
    GenderResolver genderResolver;


    @Test
    void detectGender() throws IOException {
        assertEquals("MALE", genderResolver.detectGender("Jan Maria", "ONE"));
        assertEquals("INCONCLUSIVE", genderResolver.detectGender("Jan Maria", "ALL"));
        assertEquals("FEMALE", genderResolver.detectGender("Maria Jan", "ONE"));
        assertEquals("MALE", genderResolver.detectGender("Jan Maria Rokita", "ONE"));
        assertEquals("INCONCLUSIVE", genderResolver.detectGender("Jan Maria Rokita", "ALL"));
        assertEquals("FEMALE", genderResolver.detectGender("Jan Maria Agata", "ALL"));
        assertEquals("INCONCLUSIVE", genderResolver.detectGender("a Maria Agata", "ONE"));
        assertEquals("INCONCLUSIVE", genderResolver.detectGender("a Maria Agata", "ALL"));
    }


}