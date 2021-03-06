package com.example.detector.controller;

import com.example.detector.exception.ExceptionHandling;
import com.example.detector.service.DetectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
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
    public ResponseEntity detectGender(@RequestParam String name, @RequestParam String variant) {
           String gender = detectorService.detectGender(name, variant);
            return new ResponseEntity(gender, HttpStatus.OK);
    }


}
