package com.refugees.portal.db.service;

import com.refugees.portal.db.health.model.InterviewDisplay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class BeanConfigurations {
    @Autowired
    InterviewQuestionsService questionsService;

    @Singleton
    @Bean
    public List<InterviewDisplay> getQuestions() {
        return new ArrayList<>(questionsService.getAllInterviewQuestionsView());
    }
}
