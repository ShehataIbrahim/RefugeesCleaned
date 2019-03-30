package jp.co.people.nanmin.app.service.model.apiEntity;

import java.util.List;

import lombok.Data;

/**
 * Response of aggregation processing API
 */
@Data
public class EntShuyakuRes {

	String message_id = ""; // ID of the message to be returned when an error occurs
	String message = ""; // Specific message to be returned when an error occurs
	List <String> resource_keys = null; // Array of resource keys that were dispatched for the requesting storage server
}
