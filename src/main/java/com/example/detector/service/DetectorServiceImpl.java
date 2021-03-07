package com.example.detector.service;

import com.example.detector.component.GenderResolver;
import com.example.detector.exception.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import java.io.IOException;
import java.util.regex.Pattern;

import static com.example.detector.constant.ExceptionMessageConstant.*;
import static com.example.detector.enums.EnumGender.FEMALE;
import static com.example.detector.enums.EnumGender.MALE;
import static com.example.detector.enums.EnumVariant.ALL;
import static com.example.detector.enums.EnumVariant.ONE;


@Service
public class DetectorServiceImpl implements DetectorService {

    private static final String VALID_CHARACTER_PARAMS = "[\\p{L} ]+";

    private final GenderResolver genderResolver;

    @Autowired
    public DetectorServiceImpl(GenderResolver genderResolver) {
        this.genderResolver = genderResolver;
    }

    @Override
    public String getGenderByNameToken(String name, String variant) throws IOException, BlankNameOrVariantParamException, InvalidNameOrVariantParamException, InvalidCharacterException {
        isNameAndVariantValid(name, variant);
        String gender = genderResolver.detectGender(name, variant);
        return gender;
    }

    private void isNameAndVariantValid(String name, String variant) throws BlankNameOrVariantParamException, InvalidNameOrVariantParamException, InvalidCharacterException {
        if (isNameOrVariantBlank(name, variant)) {
            throw new BlankNameOrVariantParamException(NO_NAME_OR_VARIANT_PARAM);
        }
        if (!Pattern.matches(VALID_CHARACTER_PARAMS, name) || (!Pattern.matches(VALID_CHARACTER_PARAMS, variant))) {
            throw new InvalidCharacterException(INVALID_CHARACTER);
        }
        if (!isVariantValid(variant)) {
            throw new InvalidNameOrVariantParamException(WRONG_NAME_OR_VARIANT_PARAM);
        }
    }
    private boolean isVariantValid(String variant) {
        return StringUtils.equalsIgnoreCase(variant, ONE.name()) || StringUtils.equalsIgnoreCase(variant, ALL.name());
    }

    private boolean isNameOrVariantBlank(String name, String variant) {
        return StringUtils.isBlank(name) || StringUtils.isBlank(variant);
    }

    private void isGenderValid(String gender) throws BlankGenderParamException, InvalidGenderParamException, InvalidCharacterException {
        if (StringUtils.isBlank(gender)) {
            throw new BlankGenderParamException(NO_GENDER);
        }
        if (!Pattern.matches(VALID_CHARACTER_PARAMS, gender)) {
            throw new InvalidCharacterException(INVALID_CHARACTER);
        }
        if (!StringUtils.equalsIgnoreCase(gender, MALE.name()) && !StringUtils.equalsIgnoreCase(gender, FEMALE.name())) {
            throw new InvalidGenderParamException(WRONG_GENDER);
        }
    }

    public StreamingResponseBody getAllNameTokensForGender(String gender) throws IOException, BlankGenderParamException, InvalidGenderParamException, InvalidCharacterException {
        isGenderValid(gender);
        return genderResolver.getAllNameTokensForGender(gender);
    }
}
