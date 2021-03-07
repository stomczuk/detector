package com.example.detector.service;

import com.example.detector.exception.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import java.io.IOException;

public interface DetectorService {

    String getGenderByNameToken(String name, String variant) throws IOException, BlankNameOrVariantParamException, InvalidNameOrVariantParamException, InvalidCharacterException;
    StreamingResponseBody getAllNameTokensForGender(String gender) throws IOException, BlankGenderParamException, InvalidGenderParamException, InvalidCharacterException;

}
