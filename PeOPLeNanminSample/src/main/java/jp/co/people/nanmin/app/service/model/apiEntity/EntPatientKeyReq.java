package jp.co.people.nanmin.app.service.model.apiEntity;

import java.util.HashMap;

import lombok.Data;

/**
 *Request with personal information resource key acquisition API
 */
@Data
public class EntPatientKeyReq {
    String auth_key = ""; 							// Authentication key
    HashMap<String,String> bind_parameters = null;	// bind parameter

}
