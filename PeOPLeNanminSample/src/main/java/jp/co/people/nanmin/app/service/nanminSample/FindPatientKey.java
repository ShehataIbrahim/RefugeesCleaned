package jp.co.people.nanmin.app.service.nanminSample;

import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jp.co.people.nanmin.app.config.ApiConfig;
import jp.co.people.nanmin.app.service.model.apiEntity.EntPatientKeyReq;
import jp.co.people.nanmin.app.service.model.apiEntity.EntPatientKeyRes;
import jp.co.people.nanmin.app.utilities.ApiUtils;

/**
 * Personal Information Resource Key Get API Call
 */
@Component
public class FindPatientKey {

	/** Instance related to API setting */
	@Autowired
	private ApiConfig apiConfig;
	/** Instance related to API call */
	@Autowired
	private ApiUtils apiUtils;

	private Logger logger = LogManager.getLogger();

	/**
	 * Personal Information Resource Key Get API call
	 * 
	 * @param patient_id
	 *            patientID(RefugeeID)
	 * @return Personal Information Resource Key
	 * @throws Exception
	 */
	public String callGetPatientKey(String patient_id) throws Exception {

		logger.debug("Personal Information Resource Key Start acquisition process");
		String authKey = ""; // Authentication key
		String reqParam = ""; // Request parameters
		String resAPI = ""; // Core return JSON string
		int result = 0; // Result (0: Normal Other than: abnormal)
		EntPatientKeyRes res = null;
		String personalKey = "";// Return value

		// Create Request Parameters
		authKey = apiConfig.getAuthKey();
		// Create bind parameters
		HashMap<String, String> bindMap = new HashMap<String, String>();
		bindMap.put("patient_id", patient_id);
		// Create instances of request parameters and set each item
		EntPatientKeyReq req = new EntPatientKeyReq();
		req.setAuth_key(authKey);
		req.setBind_parameters(bindMap);
		// Create JSON string from instance
		ObjectMapper om = new ObjectMapper();
		reqParam = om.writeValueAsString(req);
		logger.info("Request parameters{}", reqParam);

		// API call with JSON string of Request parameters created
		result = apiUtils.callAPI(apiConfig.getUrlPersonal(), reqParam);
		// Retrieve return string
		resAPI = apiUtils.getResJsonString();
		logger.info("CORE Return value: {}", resAPI);
		// Generate an exception when the execution result of the API call is abnormal
		if (result == 0) {
			// normal
		} else {
			// fault
			// Output log in ApiUtils
			throw new Exception();
		}
		// Convert JSON string obtained by API call to class
		res = om.readValue(resAPI, EntPatientKeyRes.class);

		// Get the Personal Information Resource Key from the returned object
		if (res.getData().size() == 0) {
			// Not applicable
			personalKey = "";
		} else if (res.getData().size() == 1) {
			// applicable case
			personalKey = res.getData().get(0).get("resource_key");
		} else {
			// corresponding multiple
			// Use the first retrieved resource key
			personalKey = res.getData().get(0).get("resource_key");
			// Please output to the log because there are plural inappropriate originally
			logger.info("patientID(RefugeeID)Multiple matches");
		}
		logger.debug("Personal Information Resource Key End of acquisition processing");
		return personalKey;
	}
}
