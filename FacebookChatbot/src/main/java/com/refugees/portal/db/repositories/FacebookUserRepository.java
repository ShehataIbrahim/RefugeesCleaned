package com.refugees.portal.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.refugees.portal.db.model.FacebookUser;
import com.refugees.portal.db.model.RefugeeUser;

@Repository
public interface  FacebookUserRepository extends JpaRepository<FacebookUser, String> {

}
