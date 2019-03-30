package com.refugees.portal.db.health.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class InterviewPK implements Serializable {
    private int interviewCategoryId;
    private int interviewCategoryVersion;
    private int interviewId;

    @Column(name = "interview_category_id")
    @Id
    public int getInterviewCategoryId() {
        return interviewCategoryId;
    }

    public void setInterviewCategoryId(int interviewCategoryId) {
        this.interviewCategoryId = interviewCategoryId;
    }

    @Column(name = "interview_category_version")
    @Id
    public int getInterviewCategoryVersion() {
        return interviewCategoryVersion;
    }

    public void setInterviewCategoryVersion(int interviewCategoryVersion) {
        this.interviewCategoryVersion = interviewCategoryVersion;
    }

    @Column(name = "interview_id")
    @Id
    public int getInterviewId() {
        return interviewId;
    }

    public void setInterviewId(int interviewId) {
        this.interviewId = interviewId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InterviewPK that = (InterviewPK) o;
        return interviewCategoryId == that.interviewCategoryId &&
                interviewCategoryVersion == that.interviewCategoryVersion &&
                interviewId == that.interviewId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(interviewCategoryId, interviewCategoryVersion, interviewId);
    }
}
