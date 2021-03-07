package com.example.detector.controller;

import com.example.detector.exception.*;
import com.example.detector.service.DetectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import java.io.*;



@RestController
@RequestMapping("/detector")
public class DetectorController extends ExceptionHandling {

    private final DetectorService detectorService;

    @Autowired
    public DetectorController(DetectorService detectorService) {
        this.detectorService = detectorService;
    }

    @GetMapping("/gender")
    public ResponseEntity detectGender(@RequestParam String name, @RequestParam String variant) throws IOException, BlankNameOrVariantParamException, InvalidNameOrVariantParamException, InvalidCharacterException {
           String gender = detectorService.getGenderByNameToken(name, variant);
            return new ResponseEntity(gender, HttpStatus.OK);
    }

    @GetMapping(value = "/names")
    public ResponseEntity<StreamingResponseBody> getNames(@RequestParam String gender) throws IOException, BlankGenderParamException, InvalidGenderParamException, InvalidCharacterException {
        StreamingResponseBody nameTokens = detectorService.getAllNameTokensForGender(gender);
        return new ResponseEntity(nameTokens, HttpStatus.OK);
    }
}
