package jp.co.people.nanmin.app.service.model.form;

import lombok.Data;

/**
 * 画面フォームのオブジェクト
 */
@Data
public class SampleForm {

	private String ifa_date;		// キー日付
	private String patient_id;		// patientID(RefugeeID)
	private String full_name;		// 氏名
	private String sex;				// 性別
	private String birth_day;		// 生年月日
	private String height;			// 身長
	private String weight;			// 体重
	private String blood_abo;		// 血液型
	private String systolic;		// 収縮期血圧(最高)
	private String diastolic;		// 拡張期血圧(最低)
	private String smoking;			// 問診喫煙
	

	
}
