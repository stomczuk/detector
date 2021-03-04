package com.example.detector.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;

import static com.example.detector.constant.Enums.Gender.*;
import static com.example.detector.constant.Enums.Variant.ALL;
import static com.example.detector.constant.Enums.Variant.ONE;
import static com.example.detector.constant.ExceptionMessageConstant.*;
import static com.example.detector.constant.FileConstant.NAME_FEMALE_LIST;
import static com.example.detector.constant.FileConstant.NAME_MALE_LIST;


@Service
public class DetectorServiceImpl implements DetectorService {

    private static Logger LOGGER;

    private URI resource(String fileName) throws URISyntaxException {
        URL resource = getClass().getClassLoader().getResource(fileName);
        if (resource == null) {
            LOGGER.info(FILE_NOT_FOUND);
            throw new IllegalArgumentException(GENERAL_ERROR);
        } else {
            return resource.toURI();
        }
    }

    public boolean findNameInList(String name, String listName) {

        try {
            File myObj = new File(resource(listName));
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if (StringUtils.equalsIgnoreCase(name, data)) {
                    return true;
                }
            }
            myReader.close();
        } catch (FileNotFoundException | URISyntaxException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean isFemale(String name, String listName) {
        return findNameInList(name, listName);
    }


    public boolean isMale(String name, String listName) {
         return findNameInList(name, listName);
        }


    @Override
    public String detectGenderOneToken(String name) {
        String firstName = isolateFirstName(name);
        if (isMale(firstName, NAME_MALE_LIST)) {
            return MALE.name();
        } else if (isFemale(firstName, NAME_FEMALE_LIST)) {
            return FEMALE.name();
        }
          return INCONCLUSIVE.name();
    }

    @Override
    public String detectGenderAllToken(String name) {
        int maleCounter = 0;
        int femaleCounter = 0;
        int inconclusiveCounter = 0;
        String[] names = splitName(name);
        for (String n: names) {
            if (isMale(n, NAME_MALE_LIST)) {
                maleCounter++;
            } else if (isFemale(n, NAME_FEMALE_LIST)) {
                femaleCounter++;
            } else inconclusiveCounter++;
        }
        return getGenderBaseOnCounters(maleCounter, femaleCounter, inconclusiveCounter);
    }

    public String getGenderBaseOnCounters(int maleCounter, int femaleCounter, int inconclusiveCounter) {
        if (inconclusiveCounter > 0) {
            return INCONCLUSIVE.name();
        } else if (maleCounter > femaleCounter) {
            return MALE.name();
        } else if (femaleCounter > maleCounter) {
           return FEMALE.name();
        }
        return INCONCLUSIVE.name();
    }

    @Override
    public String detectGender(String name, String variant) {
        String gender;
        validateNameAndVariant(name, variant);
        if (isVariantOne(variant)) {
           gender = detectGenderOneToken(name);
        } else if (isVariantAll(variant)) {
          gender = detectGenderAllToken(name);
        } else gender = INCONCLUSIVE.name();
       return gender;
    }

    public void validateNameAndVariant(String name, String variant) {
        if (StringUtils.isBlank(name) || StringUtils.isBlank(variant)) {
            throw new IllegalArgumentException(NO_PARAMS);
        }
        if (!isVariantOne(variant) && !isVariantAll(variant)) {
            throw new IllegalArgumentException(WRONG_VARIANT);
        }
    }

    public boolean isVariantOne(String variant) {
        return StringUtils.equalsIgnoreCase(variant, ONE.name());
    }

    public boolean isVariantAll(String variant) {
       return StringUtils.equalsIgnoreCase(variant, ALL.name());


    }
    public String isolateFirstName(String name) {
        String[] splittedName = splitName(name);
        return splittedName[0];
    }

    public String[] splitName(String name) {
        String[] splitName = name.split("\\s+");
        return splitName;
    }
}
