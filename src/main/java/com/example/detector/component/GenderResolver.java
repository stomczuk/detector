package com.example.detector.component;

import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;


import java.io.IOException;

public interface GenderResolver {

    String detectGender(String name, String variant) throws IOException;
    StreamingResponseBody getAllNameTokensForGender(String gender) throws IOException;
}
