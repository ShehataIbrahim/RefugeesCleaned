package com.refugees.portal.db.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="screening")
public class Screening {
	@Id
	@Column(name = "screening_id")
	private int id;
	@Column(name = "nammin_id")
	private int userId;
	@Column(name = "answered_at")
	private Date completinggDate;
	@Column(name = "status")
	private String status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Date getCompletinggDate() {
		return completinggDate;
	}

	public void setCompletinggDate(Date completinggDate) {
		this.completinggDate = completinggDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
