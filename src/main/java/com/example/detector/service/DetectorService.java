package com.example.detector.service;

public interface DetectorService {

    String detectGenderOneToken(String name);
    String detectGenderAllToken(String name);
    String detectGender(String name, String variant);
    boolean findNameInList(String name, String pathList);
    boolean isFemale(String name, String pathList);
    boolean isMale(String name, String pathList);
    String getGenderBaseOnCounters(int maleCounter, int femaleCounter, int inconclusiveCounter);
    void validateNameAndVariant(String name, String variant);
    boolean isVariantOne(String variant);
    boolean isVariantAll(String variant);
    String isolateFirstName(String name);
    String[] splitName(String name);

}
