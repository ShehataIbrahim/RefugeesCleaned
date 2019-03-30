package com.refugees.portal.db.health.repositories;

import com.refugees.portal.db.health.model.Interview;
import com.refugees.portal.db.health.model.InterviewAnswer;
import com.refugees.portal.db.health.model.InterviewAnswerPK;
import com.refugees.portal.db.health.model.InterviewPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface InterviewAnswerRepository extends JpaRepository<InterviewAnswer, InterviewAnswerPK> {
    Optional<InterviewAnswer> findByInterviewCategoryIdAndInterviewCategoryVersion(int interviewCategoryId, int interviewCategoryVersion);
    Optional<InterviewAnswer> findByInterviewCategoryIdAndInterviewCategoryVersionAndInterviewId(int interviewCategoryId, int interviewCategoryVersion,int interviewId);
    Optional<InterviewAnswer> findByInterviewCategoryId(int interviewCategoryId);
}
