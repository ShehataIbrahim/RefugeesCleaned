package com.refugees.consolidate.model;

import java.util.ArrayList;
import java.util.List;

public class InterviewDisplay {
    private int interviewId;
    private String interviewItem;
    private String answerType;
    private Integer categoryId;
    private List<AllowedAnswer> allowedAnswers;
    private AnswerTypesEnum type;

    public InterviewDisplay() {
        allowedAnswers=new ArrayList<>();
    }
    public void addAllowedAnswer(AllowedAnswer answer)
    {
        allowedAnswers.add(answer);
    }
    public AnswerTypesEnum getType() {
        return type;
    }
    public List<AllowedAnswer> getAllowedAnswers() {
        return allowedAnswers;
    }
    public void setAllowedAnswers(List<AllowedAnswer> allowedAnswers) {
        this.allowedAnswers = allowedAnswers;
    }
    public Integer getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
    public int getCategoryVersion() {
        return categoryVersion;
    }
    public void setCategoryVersion(int categoryVersion) {
        this.categoryVersion = categoryVersion;
    }
    private int categoryVersion;
    public int getInterviewId() {
        return interviewId;
    }
    public void setInterviewId(int interviewId) {
        this.interviewId = interviewId;
    }
    public String getInterviewItem() {
        return interviewItem;
    }
    public void setInterviewItem(String interviewItem) {
        this.interviewItem = interviewItem;
    }
    public String getAnswerType() {
        return answerType;
    }
    public void setAnswerType(String answerType) {
        this.answerType = answerType;
        try {
            if(answerType!=null && !answerType.trim().isEmpty())
                type=AnswerTypesEnum.valueOf(answerType.trim());
        }catch(Exception e)
        {

        }
    }
    @Override
    public int hashCode() {

        return (String.valueOf(categoryId)+"_"+String.valueOf(categoryVersion)+"_"+String.valueOf(interviewId)).hashCode();
    }
    public String objectId()
    {
        return String.valueOf(categoryId)+"_"+String.valueOf(categoryVersion)+"_"+String.valueOf(interviewId);
    }
    public boolean isMultiSelection()
    {
        return !allowedAnswers.isEmpty() && type==AnswerTypesEnum.LIST;
    }
}
