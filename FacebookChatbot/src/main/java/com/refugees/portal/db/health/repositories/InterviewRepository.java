package com.refugees.portal.db.health.repositories;

import com.refugees.portal.db.health.model.Interview;
import com.refugees.portal.db.health.model.InterviewPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface InterviewRepository extends JpaRepository<Interview, InterviewPK> {
    Optional<Interview> findByInterviewCategoryIdAndInterviewCategoryVersion(int interviewCategoryId,int interviewCategoryVersion);
    Optional<Interview> findByInterviewCategoryId(int interviewCategoryId);
}
