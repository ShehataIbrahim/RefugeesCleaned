package com.refugees.portal.controllers;

import com.refugees.portal.db.health.model.InterviewDisplay;
import com.refugees.portal.db.service.InterviewQuestionsService;
import com.refugees.portal.services.ConsolidateService;
import com.refugees.portal.services.model.InterviewAnswerBaseData;
import com.refugees.portal.services.model.InterviewAnswerData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

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
    @GetMapping(path = "/consolidate")
    public String testConosolidate(@RequestParam(value = "pId", required = false) String patientId,@RequestParam(value = "qId", required = false) String questionId,@RequestParam(value = "answer", required = false) String answer) throws IOException {
        InterviewDisplay questtion = new ArrayList<InterviewDisplay>(questionsService.getAllInterviewQuestionsView()).get(Integer.valueOf(questionId));
        service.consolidateInterviewAnswer(patientId,questtion,answer);
        return service.getResourceKey(patientId);
    }
    @GetMapping(path = "/getanswers")
    public Map<String, InterviewAnswerBaseData> getAnswers(@RequestParam(value = "pId", required = false) String patientId) throws IOException {
        return service.getPatientAnswers(patientId);

    }
    @GetMapping(path = "/questionsList")
    public Collection<InterviewDisplay> getQuestionsList() {
        return questionsService.getAllInterviewQuestionsView();
    }
}
