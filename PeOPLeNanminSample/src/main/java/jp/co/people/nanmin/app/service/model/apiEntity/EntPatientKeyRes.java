package jp.co.people.nanmin.app.service.model.apiEntity;

import java.util.HashMap;
import java.util.List;

import lombok.Data;

/**
 * Response of personal information resource key acquisition API
 */
@Data
public class EntPatientKeyRes {
    String message_id = ""	;		    			// ID of the message returned when an error occurs
    String message = ""; 			    			// Specific message to be returned when an error occurs
    List<HashMap<String,String>> data  = null;		/* Data acquired with SQL template
													A character string representing an object (associative array) of JSON */
    
}