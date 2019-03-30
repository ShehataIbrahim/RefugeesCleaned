package com.refugees.portal.db.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.refugees.portal.db.model.Screening;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening, Integer> {
	public List<Screening> findByUserId(int userId);
	public List<Screening> findByUserIdAndStatus(int userId,String status);

}
