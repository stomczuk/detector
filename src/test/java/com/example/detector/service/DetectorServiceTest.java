package com.example.detector.service;

import com.example.detector.exception.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.io.IOException;
import java.util.regex.Pattern;

import static com.example.detector.constant.ExceptionMessageConstant.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DetectorServiceTest {

    @Autowired
    DetectorService detectorService;


    @Test
    void detectGender() throws BlankNameOrVariantParamException, InvalidNameOrVariantParamException, IOException, InvalidCharacterException {
        assertEquals("MALE", detectorService.getGenderByNameToken("Jan Maria", "ONE"));
        assertEquals("INCONCLUSIVE", detectorService.getGenderByNameToken("Jan Maria", "ALL"));
        assertEquals("FEMALE", detectorService.getGenderByNameToken("Maria Jan", "ONE"));
        assertEquals("MALE", detectorService.getGenderByNameToken("Jan Maria Rokita", "ONE"));
        assertEquals("INCONCLUSIVE", detectorService.getGenderByNameToken("Jan Maria Rokita", "ALL"));
        assertEquals("FEMALE", detectorService.getGenderByNameToken("Jan Maria Agata", "ALL"));
        assertEquals("INCONCLUSIVE", detectorService.getGenderByNameToken("a Maria Agata", "ONE"));
        assertEquals("INCONCLUSIVE", detectorService.getGenderByNameToken("a Maria Agata", "ALL"));
        assertEquals("INCONCLUSIVE", detectorService.getGenderByNameToken("a Maria Agata", "ALL"));
    }

    @Test
    void invalidCharactersInGenderParams() {
        Exception exception = assertThrows(InvalidCharacterException.class, () -> {
            detectorService.getAllNameTokensForGender("!@#$%^&*()!@#123");
        });
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(INVALID_CHARACTER));
    }

    @Test
    void wrongGenderParams() {
        Exception exception = assertThrows(InvalidGenderParamException.class, () -> {
            detectorService.getAllNameTokensForGender("Mal");
        });
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(WRONG_GENDER));
    }



    @Test
    void blankGenderParam() {
        Exception exception = assertThrows(BlankGenderParamException.class, () -> {
            detectorService.getAllNameTokensForGender("");
        });
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(NO_GENDER));
    }

    @Test
    void blankNameOrVariantParam() {
        Exception exception = assertThrows(BlankNameOrVariantParamException.class, () -> {
            detectorService.getGenderByNameToken("", "");
        });
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(NO_NAME_OR_VARIANT_PARAM));
    }

    @Test
    void wrongVariantParam() {
        Exception exception = assertThrows(InvalidNameOrVariantParamException.class, () -> {
            detectorService.getGenderByNameToken("Jan", "dgdgdg");
        });
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(WRONG_NAME_OR_VARIANT_PARAM));
    }

    @Test
    void invalidCharacterNameParam() {
        Exception exception = assertThrows(InvalidCharacterException.class, () -> {
            detectorService.getGenderByNameToken("!@#$%^&*()", "ONE");
        });
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(INVALID_CHARACTER));
    }

    @Test
    void invalidCharacterVariantParam() {
        Exception exception = assertThrows(InvalidCharacterException.class, () -> {
            detectorService.getGenderByNameToken("Jan ", "!@$%^&*()");
        });
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(INVALID_CHARACTER));
    }

    @Test
    void isValidCharacter() {
        String VALID_CHARACTER_PARAMS = "[\\p{L}]+";
        assertFalse(Pattern.matches(VALID_CHARACTER_PARAMS, "%"));
        assertFalse(Pattern.matches(VALID_CHARACTER_PARAMS, "^"));
        assertFalse(Pattern.matches(VALID_CHARACTER_PARAMS, "!@#$%^&*()-_=+{[\\]:;\\'<>,.?/"));
        assertTrue(Pattern.matches(VALID_CHARACTER_PARAMS, "male"));
        assertTrue(Pattern.matches(VALID_CHARACTER_PARAMS, "female"));
        assertTrue(Pattern.matches(VALID_CHARACTER_PARAMS, "one"));
        assertTrue(Pattern.matches(VALID_CHARACTER_PARAMS, "all"));
    }
}