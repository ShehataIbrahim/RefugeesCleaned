package com.refugees.portal.controllers;

import com.refugees.portal.db.health.model.InterviewDisplay;
import com.refugees.portal.db.service.InterviewQuestionsService;
import com.refugees.portal.services.ConsolidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Collection;

@RestController
@RequestMapping("/consolodiate")
public class ConsolidateTestController {
    @Autowired
    InterviewQuestionsService questionsService;
    @Autowired
    ConsolidateService service;

    @GetMapping(path = "/resourceKey")
    public String getData(@RequestParam(value = "pId", required = false) String patientId) throws IOException {
        return service.getResourceKey(patientId);
    }
    @GetMapping(path = "/questionsList")
    public Collection<InterviewDisplay> getQuestionsList() {
        return questionsService.getAllInterviewQuestionsView();
    }
}
