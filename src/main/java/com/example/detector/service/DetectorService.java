package com.example.detector.service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URISyntaxException;

public interface DetectorService {


    void validateNameAndVariant(String name, String variant);
    String detectGender(String name, String variant);

}
