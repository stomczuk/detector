package com.example.detector.component;

public interface GenderResolver {

    String detectGender(String name, String variant);
    void getAllNameTokensForGender(String genderL);
}
