package com.refugees.portal.db.health.repositories;

import com.refugees.portal.db.health.model.Interview;
import com.refugees.portal.db.health.model.InterviewCategory;
import com.refugees.portal.db.health.model.InterviewCategoryPK;
import com.refugees.portal.db.health.model.InterviewPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface InterviewCategoryRepository extends JpaRepository<InterviewCategory, InterviewCategoryPK> {
    Optional<InterviewCategory> findByInterviewCategoryId(int interviewCategoryId);
}
