package com.refugees.portal.db.service;

import com.refugees.portal.db.health.model.*;
import com.refugees.portal.db.health.repositories.InterviewAnswerRepository;
import com.refugees.portal.db.health.repositories.InterviewCategoryRepository;
import com.refugees.portal.db.health.repositories.InterviewQuestionsViewRepository;
import com.refugees.portal.db.health.repositories.InterviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Service
@EnableJpaRepositories(entityManagerFactoryRef = "secondaryEntityManagerFactory",
        transactionManagerRef = "secondaryTransactionManager",
        basePackages = {"com.refugees.portal.db.health.repositories"})
@EntityScan(basePackages = {"com.refugees.portal.db.health.model"})
public class InterviewQuestionsService {
    @Autowired
    InterviewAnswerRepository answerRepository;
    @Autowired
    InterviewRepository interviewRepository;
    @Autowired
    InterviewCategoryRepository categoryRepository;
    @Autowired
    InterviewQuestionsViewRepository interviewQuestionsViewRepository;
    public Collection<InterviewDisplay> getAllInterviewQuestionsView()
    {
        List<InterviewQuestionsView> questions= interviewQuestionsViewRepository.findAll();
        final HashMap<String, InterviewDisplay> displayItems=new HashMap<>();
        questions.stream().forEach(question->{

            if(displayItems.containsKey(question.objectId()) && !AnswerTypesEnum.FREE.toString().equalsIgnoreCase(question.getAnswerType()))
            {
                InterviewDisplay d=displayItems.get(question.objectId());
                AllowedAnswer answer=new AllowedAnswer();
                answer.setAnswer(question.getAnswerItem());
                answer.setAnswerId(question.getAnswerId());
                d.getAllowedAnswers().add(answer);
            }else
            {
                InterviewDisplay d=new InterviewDisplay();
                if(!AnswerTypesEnum.FREE.toString().equalsIgnoreCase(question.getAnswerType())) {
                    AllowedAnswer answer = new AllowedAnswer();
                    answer.setAnswer(question.getAnswerItem());
                    answer.setAnswerId(question.getAnswerId());
                    d.getAllowedAnswers().add(answer);
                }
                d.setAnswerType(question.getAnswerType());
                d.setCategoryId(question.getInterviewCategoryId());
                d.setCategoryVersion(question.getInterviewCategoryVersion());
                d.setInterviewId(question.getInterviewId());
                d.setInterviewItem(question.getInterviewItem().trim().replace("\n",""));
                displayItems.put(question.objectId(),d);
            }
        });
        return displayItems.values();
    }

}
