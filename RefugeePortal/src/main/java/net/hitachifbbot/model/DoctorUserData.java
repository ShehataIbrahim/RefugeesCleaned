package net.hitachifbbot.model;

import net.hitachifbbot.DB;
import net.hitachifbbot.session.AppSession.UserData;

public class DoctorUserData extends UserData{
	private DoctorUser dbUserData;

	public DoctorUser getDbUserData() {
		return dbUserData;
	}

	public void setDbUserData(DoctorUser dbUserData) {
		this.dbUserData = dbUserData;
	}
	
}
