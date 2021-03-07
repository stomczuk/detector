package com.example.detector.component;


import com.example.detector.enums.EnumVariant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

import static com.example.detector.constant.FileConstant.PATH_FEMALE_LIST;
import static com.example.detector.constant.FileConstant.PATH_MALE_LIST;
import static com.example.detector.enums.EnumGender.*;
import static com.example.detector.enums.EnumVariant.ALL;
import static com.example.detector.enums.EnumVariant.ONE;


@Component
public class FromFileGenderResolver implements GenderResolver {

    final static String WHITESPACE_REGEX = "\\s+";

    @Override
    public String detectGender(String name, String variant) {
        String gender;
        if (isVariantOne(variant)) {
            gender = detectGenderOneToken(name);
        } else if (isVariantAll(variant)) {
            gender = detectGenderAllToken(name);
        } else gender = INCONCLUSIVE.name();
        return gender;
    }

    private boolean isVariantAll(String variant) {
        return StringUtils.equalsIgnoreCase(variant, ALL.name());
    }

    private boolean isVariantOne(String variant) {
        return StringUtils.equalsIgnoreCase(variant, ONE.name());
    }

    @Override
    public void getAllNameTokensForGender(String gender) {

    }

    private String isolateFirstName(String name) {
        String[] splittedName = splitName(name);
        return splittedName[0];
    }

    private String[] splitName(String name) {
        String[] splitName = name.split(WHITESPACE_REGEX);
        return splitName;
    }

    private String detectGenderBaseOnCounters(int maleCounter, int femaleCounter, int inconclusiveCounter) {
        if (inconclusiveCounter > 0) {
            return INCONCLUSIVE.name();
        } else if (maleCounter > femaleCounter) {
            return MALE.name();
        } else if (femaleCounter > maleCounter) {
            return FEMALE.name();
        }
        return INCONCLUSIVE.name();
    }

    private String detectGenderOneToken(String name) {
        String firstName = isolateFirstName(name);
        if (isMale(firstName, PATH_MALE_LIST)) {
            return MALE.name();
        } else if (isFemale(firstName, PATH_FEMALE_LIST)) {
            return FEMALE.name();
        }
        return INCONCLUSIVE.name();
    }

    private String detectGenderAllToken(String name) {
        int maleCounter = 0;
        int femaleCounter = 0;
        int inconclusiveCounter = 0;
        String[] names = splitName(name);
        for (String n : names) {
            if (isMale(n, PATH_MALE_LIST)) {
                maleCounter++;
            } else if (isFemale(n, PATH_FEMALE_LIST)) {
                femaleCounter++;
            } else inconclusiveCounter++;
        }
        return detectGenderBaseOnCounters(maleCounter, femaleCounter, inconclusiveCounter);
    }

    private boolean isFemale(String name, String listName) {
        return isNameOnList(name, listName);
    }

    private boolean isMale(String name, String listName) {
        return isNameOnList(name, listName);
    }

    private boolean isNameOnList(String name, String listPath) {

        try {
            InputStream file = new FileInputStream(listPath);
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if (StringUtils.equalsIgnoreCase(name, data)) {
                    return true;
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
}
