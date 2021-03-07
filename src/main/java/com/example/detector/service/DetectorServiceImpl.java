package com.example.detector.service;

import com.example.detector.component.GenderResolver;
import com.example.detector.enums.EnumVariant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.detector.constant.ExceptionMessageConstant.NO_PARAMS;
import static com.example.detector.constant.ExceptionMessageConstant.WRONG_VARIANT;
import static com.example.detector.enums.EnumVariant.ALL;
import static com.example.detector.enums.EnumVariant.ONE;


@Service
public class DetectorServiceImpl implements DetectorService {

    private final GenderResolver genderResolver;

    @Autowired
    public DetectorServiceImpl(GenderResolver genderResolver) {
        this.genderResolver = genderResolver;
    }

    @Override
    public String detectGender(String name, String variant) {
        validateNameAndVariant(name, variant);
        String gender = genderResolver.detectGender(name, variant);
        return gender;
    }

    public void validateNameAndVariant(String name, String variant) {
        if (isNameOrVariantBlank(name, variant)) {
            throw new IllegalArgumentException(NO_PARAMS);
        }
        if (!isVariantValid(variant)) {
            throw new IllegalArgumentException(WRONG_VARIANT);
        }
    }
    private boolean isVariantValid(String variant) {
        return StringUtils.equalsIgnoreCase(variant, ONE.name()) || StringUtils.equalsIgnoreCase(variant, ALL.name());
    }

    private boolean isNameOrVariantBlank(String name, String variant) {
        return StringUtils.isBlank(name) || StringUtils.isBlank(variant);
    }
}
