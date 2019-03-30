package com.refugees.portal.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.refugees.portal.db.model.RefugeeUser;

@Repository
public interface RefugeeUserRepository extends JpaRepository<RefugeeUser, Integer> {
	public RefugeeUser findByName(String name);

	public RefugeeUser findByMailAddress(String mailAddress);

	public RefugeeUser findByFacebookUserId(String facebookUserId);
}
