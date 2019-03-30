package com.refugees.portal.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.refugees.portal.db.model.RefugeeUser;
import com.refugees.portal.db.model.ScreeningQuestions;

@Repository
public interface  ScreeningQuestionRepository extends JpaRepository<ScreeningQuestions, Integer> {

}
