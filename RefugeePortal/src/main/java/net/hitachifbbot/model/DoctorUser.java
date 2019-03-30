package net.hitachifbbot.model;

import java.sql.ResultSet;
import java.sql.SQLException;


public class DoctorUser{
    /*
doctor_id VARCHAR(256) PRIMARY KEY,
pass_hash VARCHAR(128),
pass_salt VARCHAR(128),
translate_lang_code VARCHAR(10)
     */
    public String doctorID;
    public String passHash;
    public String passSalt;
    public String translateLangCode;

    public DoctorUser(String doctorID, String passHash, String passSalt, String translateLangCode) {
        this.doctorID = doctorID;
        this.translateLangCode = translateLangCode;
        this.passHash = passHash;
        this.passSalt = passSalt;
    }

    public static DoctorUser getFromResultSet(ResultSet r) throws SQLException {
        return new DoctorUser(
                r.getString("doctor_id"),
                r.getString("pass_hash"),
                r.getString("pass_salt"),
                r.getString("translate_lang_code")
        );
    }
}