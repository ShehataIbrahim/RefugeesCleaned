package jp.co.people.nanmin.app.service.nanminSample;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import jp.co.people.nanmin.app.config.ApiConfig;
import jp.co.people.nanmin.app.datasources.DataSourceManager;
import jp.co.people.nanmin.app.service.model.apiEntity.EntShuyakuReq;
import jp.co.people.nanmin.app.service.model.apiEntity.EntShuyakuReqData;
import jp.co.people.nanmin.app.service.model.apiEntity.EntShuyakuReqDataParam;
import jp.co.people.nanmin.app.service.model.apiEntity.EntShuyakuRes;
import jp.co.people.nanmin.app.service.model.form.SampleForm;
import jp.co.people.nanmin.app.utilities.ApiUtils;

/**
 * Aggregation processing API call
 */
@Component
public class Aggregator {

	/** Instance related to API setting */
	@Autowired
	private ApiConfig apiConfig;
	/** Instance related to API call */
	@Autowired
	private ApiUtils apiUtils;
	/** Data instance management instance */
	@Autowired
	protected DataSourceManager dataSourceManager;

	private Logger logger = LogManager.getLogger();

	/* Constant */
	/* Connecting DB */
	private final String DB_PERSONAL = "personal";
	/** SQL used for checking whether it is insert or update */
	private final String CHECK_METHOD_SQL = "select count (*)" + "from personal_informations"
			+ "where resource_key =: resource_key" + "and reference_datetime =: ifa_date"
			+ "and item_class_code = 'patient_id';";
	/* Key string to bind to SQL */
	private final String BIND_KEY = "resource_key"; // resource key
	private final String BIND_DATE = "ifa_date"; // key date
	/** Column name obtained from SQL result */
	private final String CHECK_METHOD_COUNT = "count";

	/* Constants used in request parameters */
	/** Aggregation source resource type */
	private final String REQ_FROM_TYPE = "3001 # 0000 # 0"; // Intention of refugee support accumulation
	/** Aggregation source resource type */
	private final String REQ_TO_TYPE = ""; // All meaning of aggregation
	/* Method */
	private final String REQ_METHOD_INSERT = "insert";
	private final String REQ_METHOD_UPDATE = "update";

	/**
	 * Aggregation process call @ param resourceKey Personal Information Resource
	 * Key @ param form Information from screen form (for one person)
	 * 
	 * @return aggregation API response object @ throws Exception
	 */
	public EntShuyakuRes callConsolidate(String resourceKey, SampleForm form) throws Exception {

		logger.debug("Start aggregation processing");
		EntShuyakuRes res = null;
		String reqParam = ""; // Request parameters
		String resAPI = ""; // JSON string returned by API
		String method = ""; // "insert" or "update" is entered
		int result = 0; // result value
		// Create in order of parameters, data, request
		try {
			// Create a bind parameter (item of request)
			EntShuyakuReqDataParam param = new EntShuyakuReqDataParam();
			// Set each item as a bind parameter from the form
			param.setIfa_date(form.getIfa_date()); // key date
			param.setPatient_id(form.getPatient_id()); // patient ID (refugee ID)
			param.setFull_name(form.getFull_name()); // First Name
			param.setSex(form.getSex()); // Gender
			param.setBirth_day(form.getBirth_day()); // Date of birth
			param.setHeight(form.getHeight()); // Height
			param.setWeight(form.getWeight()); // body weight
			param.setBlood_abo(form.getBlood_abo()); // Blood type
			param.setSystolic(form.getSystolic()); // systolic blood pressure (highest)
			param.setDiastolic(form.getDiastolic()); // diastolic blood pressure (lowest)
			param.setSmoking(form.getSmoking()); // interview smoking
			param.setInsurer("dummy"); // insurer number
			// Set the insurer number to "dummy"
			// Because it is an indispensable item when acquiring Core data.

			// Create data (item of request)
			// In this function, only one item is necessarily in the list
			List<EntShuyakuReqData> data = new ArrayList<EntShuyakuReqData>();
			// one data object to be stored in the list
			EntShuyakuReqData oneData = new EntShuyakuReqData();
			// Create value for method
			// if the resource key is empty
			if (StringUtils.isEmpty(resourceKey)) {
				// Set "insert"
				method = REQ_METHOD_INSERT;
				// if there is a resource key
			} else {
				// Judge whether it is "insert" or "update"
				method = checkReqMethod(resourceKey, form);
			}
			// store the value of method
			oneData.setMethod(method);
			// store personal information resource key
			oneData.setResource_key(resourceKey);
			// store bind parameters
			oneData.setBind_parameters(param);
			// Stored in list
			data.add(oneData);
			EntShuyakuReq req = new EntShuyakuReq();
			req.setAuth_key(apiConfig.getAuthKey());
			// Stores the aggregation source resource type
			req.setFrom_type(REQ_FROM_TYPE);
			// storage of aggregation destination resource type
			req.setTo_type(REQ_TO_TYPE);
			// store the data list for cooperation
			req.setData(data);

			// Convert the created request parameter to JSON string
			ObjectMapper om = new ObjectMapper();
			reqParam = om.writeValueAsString(req);
			logger.info("Request character {}", reqParam);
			// Aggregate API call
			result = apiUtils.callAPI(apiConfig.getUrlResource(), reqParam);
			// Get return string
			resAPI = apiUtils.getResJsonString();
			if (result == 0) {
				// Normal
			} else {
				// fault
				// Output log in ApiUtils
				throw new Exception();
			}
			logger.info("CORE return value: {}", resAPI);
			// store return value of CORE in class
			res = om.readValue(resAPI, EntShuyakuRes.class);
		} catch (Exception e) {
			logger.fatal("error occurred");
			throw e;
		}
		logger.debug("Consolidation processing done");
		return res;
	}

	/**
	 * Return the value of method in the API request parameter
	 * 
	 * @return insert or update @ throws ParseException When conversion of date
	 *         information fails
	 */
	private String checkReqMethod(String resourceKey, SampleForm form) throws ParseException {
		String str = ""; // Return value
		// Set the bind variable
		MapSqlParameterSource params = new MapSqlParameterSource();
		// Fit the type of the value to be bound to the definition of DB
		// Resource key converts type from string to UUID
		UUID uuid = UUID.fromString(resourceKey);
		// Convert type from string to DATE key date
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy - MM - dd");
		Date date = sdf.parse(form.getIfa_date());
		// Store in bind variable
		params.addValue(BIND_KEY, uuid);
		params.addValue(BIND_DATE, date);

		// Connect to DB and execute SQL
		// SQL is one that searches on the basis of personal information resource key
		// and registration date.
		// If there is a match, update processing will be done.
		NamedParameterJdbcTemplate template = this.dataSourceManager.getNamedParameterJdbcTemplate(DB_PERSONAL);
		List<Map<String, Object>> list = null;
		list = template.queryForList(CHECK_METHOD_SQL, params);
		logger.info("count: {}", list.get(0).get(CHECK_METHOD_COUNT));
		int count = Integer.parseInt(list.get(0).get(CHECK_METHOD_COUNT).toString());

		// If the return value is 0 (no match) insert
		if (count == 0) {
			str = REQ_METHOD_INSERT;
			// Otherwise update
		} else {
			str = REQ_METHOD_UPDATE;
		}
		return str;
	}
}
