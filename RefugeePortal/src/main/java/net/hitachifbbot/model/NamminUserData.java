package net.hitachifbbot.model;

import net.hitachifbbot.NamminUser;
import net.hitachifbbot.session.AppSession.UserData;

public class NamminUserData extends UserData {
	private NamminUser dbUserData;
	public NamminUser getDBUserData() {
		return dbUserData;
	}

	public NamminUser getDbUserData() {
		return dbUserData;
	}
	public void setDbUserData(NamminUser dbUserData) {
		this.dbUserData = dbUserData;
	}
}
