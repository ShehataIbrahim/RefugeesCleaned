package jp.co.people.nanmin.app.service.model.apiEntity;

import lombok.Data;

/**
 * Data for one individual in request of aggregation processing API
 */
@Data
public class EntShuyakuReqData {
	String method = ""; // insert or update
	String resource_key = ""; // personal information resource key
	EntShuyakuReqDataParam bind_parameters = null; // bind parameter
}
