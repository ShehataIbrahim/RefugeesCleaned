package net.hitachifbbot;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class NamminUser{
    /*
nammin_id                   SERIAL PRIMARY KEY,
nammin_name                 VARCHAR(256),
facebook_info               TEXT,
mail_address                VARCHAR(256),
pass_hash                   VARCHAR(128),
pass_salt                   VARCHAR(128),
translate_lang_code         VARCHAR(10),
last_screening_time         DATE,
last_screening_request_time DATE
    */
    public int namminID;
    public String namminName;
    public String facebookInfo;
    public String mailAddress;
    public String passHash;
    public String passSalt;
    public String translateLangCode;
    public Date lastScreeningTime;
    public Date lastScreeningRequestTime;

    public NamminUser(int namminID, String namminName, String facebookInfo, String mailAddress, String passHash, String passSalt, String translateLangCode, Date lastScreeningTime, Date lastScreeningRequestTime) {
        this.namminID = namminID;
        this.namminName = namminName;
        this.facebookInfo = facebookInfo;
        this.mailAddress = mailAddress;
        this.passHash = passHash;
        this.passSalt = passSalt;
        this.translateLangCode = translateLangCode;
        this.lastScreeningTime = lastScreeningTime;
        this.lastScreeningRequestTime = lastScreeningRequestTime;
    }

    public static NamminUser getFromResultSet(ResultSet r) throws SQLException {
        return new NamminUser(
                r.getInt("nammin_id"),
                r.getString("nammin_name"),
                r.getString("facebook_info"),
                r.getString("mail_address"),
                r.getString("pass_hash"),
                r.getString("pass_salt"),
                r.getString("translate_lang_code"),
                r.getTimestamp("last_screening_time"),
                r.getTimestamp("last_screening_request_time")
        );
    }
}