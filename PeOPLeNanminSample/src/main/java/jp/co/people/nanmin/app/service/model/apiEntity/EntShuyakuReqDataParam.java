package jp.co.people.nanmin.app.service.model.apiEntity;

import lombok.Data;

@Data
public class EntShuyakuReqDataParam {
	private String ifa_date; // key date
	private String patient_id; // patient ID (refugee ID)
	private String full_name; // Name
	private String sex; // Gender
	private String birth_day; // Date of birth
	private String height; // Height
	private String weight; // body weight
	private String blood_abo; // blood type
	private String systolic; // systolic blood pressure (highest)
	private String diastolic; // diastolic blood pressure (lowest)
	private String smoking; // interview smoking
	private String insurer; // insurer number
	// There is no entry part in the form.
	// Because it is an indispensable item when acquiring Core data.
}
