package jp.co.people.nanmin.app.service.nanminSample;

import java.text.SimpleDateFormat;
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

import jp.co.people.nanmin.app.datasources.DataSourceManager;
import jp.co.people.nanmin.app.service.model.ResultSearch;

/**
 * Personal Information Resource KeyからRefugeeの情報を取得する(1人分)
 */
@Component
public class FindNanminInfo {
	
	/** データソースの管理インスタンス */
	@Autowired
	private DataSourceManager dataSourceManager;

	private Logger logger = LogManager.getLogger();

	/*定数*/
	/*接続するDB*/
	private final String DB_CORE = "core";
	private final String DB_PERSONAL = "personal";
	private final String DB_HEALTH = "health";
	
	/*リソース種別*/
	private final String RESOURCE_PROFILE = "0200#0100#1";
	private final String RESOURCE_KENSA = "0200#0200#1";
	/**キー取得SQL*/
	private final String GET_KEY = "select resource_key as result" + 
			" from anchor_resources " + 
			" where resource_type = :resource_type "
			+ "and anchor_key = ( select anchor_key from anchor_resources "
			+ "where resource_key = :resource_key);";
	/* SQLにバインドするキー文字列*/
	private final String BIND_DATE = "reference_datetime";		// キー日付
	private final String BIND_KEY = "resource_key";				// リソースキー
	private final String BIND_TYPE = "resource_type";			// リソース種別

	/** SQL実行結果列*/
	private final String RESULT_COLUMN = "result";
	
	/**
	 * Personal Information Resource KeyからDBを検索し、Refugeeの情報を取得する
	 * 取得した情報を処理結果オブジェクトに格納して返す
	 * @param ifa_date キー日付
	 * @param personalKey Personal Information Resource Key
	 * @param result 処理結果オブジェクト
	 * @return 処理結果オブジェクト
	 * @throws Exception 
	 */
	public ResultSearch find(String ifa_date,String personalKey,ResultSearch result) throws Exception {
		logger.debug("Refugee情報取得処理開始");
		String sql = "";						//実行するSQL
		List<Map<String, Object>> list = null;	// 結果格納用リスト
		String full_name = "";	// 氏名
		String sex = "";		// 性別
		String birth_day = "";	// 生年月日
		String height = "";		// 身長
		String weight = "";		// 体重
		String blood_abo = "";	// 血液型
		String systolic = "";	// 収縮期血圧(最高)
		String diastolic = "";	// 拡張期血圧(最低)
		String smoking = "";	// 問診喫煙
		
		// キー日付を型変換する(String → Date)
		Date keyDate = null; 	// SQL実行時に使うキー日付
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		keyDate =  sdf.parse(ifa_date);
		//日付が正しくない場合、エクセプションを発生させる
		if(ifa_date.equals(sdf.format(keyDate))) {
			// 正常
		}else {
			logger.error("日付が正しくありません。");
			logger.error("キー日付：{}",ifa_date);
			throw new Exception();
		}
		
		// personalDBと接続
		NamedParameterJdbcTemplate template = this.dataSourceManager.
				getNamedParameterJdbcTemplate(DB_PERSONAL);
		// リソース種別が「個人情報」で氏名、性別、生年月日を検索する
		// バインド変数：reference_datetime(キー日付) resource_key(Personal Information Resource Key)
		sql = "SELECT nameTable.json_data ->> 'full_name' AS full_name, "+
				"birthTable.json_data ->> 'birth_day' AS birth_day, "+
				"sexTable.json_data ->> 'sex' AS sex "+
				"FROM personal_informations birthTable, "+
				" personal_informations nameTable, "+
				" personal_informations sexTable "+
				"WHERE birthTable.item_class_code = 'birth_day' "+
				"and nameTable.item_class_code = 'full_name' "+
				"and sexTable.item_class_code = 'sex' "+
				"and birthTable.resource_key = nameTable.resource_key "+
				"and nameTable.resource_key = sexTable.resource_key "+
				"and sexTable.resource_key = :resource_key "+
				"and birthTable.reference_datetime = :reference_datetime "+
				"and nameTable.reference_datetime = :reference_datetime "+
				"and sexTable.reference_datetime = :reference_datetime ";
		// バインド変数の設定
		MapSqlParameterSource params = new MapSqlParameterSource();
		// Personal Information Resource Keyの型を文字列からUUIDに変換する
		UUID uuidPersonal = UUID.fromString(personalKey);
		// キー日付、Personal Information Resource Keyをバインド変数に格納する
		params.addValue(BIND_DATE, keyDate);
		params.addValue(BIND_KEY, uuidPersonal);
		// SQLの実行
		list = template.queryForList(sql, params);
		// 氏名		
		full_name = list.get(0).get("full_name").toString();
		logger.info("氏名：{}",full_name);
		// 性別
		sex = list.get(0).get("sex").toString();		
		logger.info("性別：{}",sex);
		// 生年月日
		birth_day = list.get(0).get("birth_day").toString();	
		logger.info("生年月日：{}",birth_day);

		// healthDBと接続
		template = dataSourceManager.getNamedParameterJdbcTemplate(DB_HEALTH);
		// Personal Information Resource Keyからプロフィールキーと検査結果キーを取得する
		UUID uuidProfile = getKey(uuidPersonal, RESOURCE_PROFILE);
		UUID uuidKensa = getKey(uuidPersonal, RESOURCE_KENSA);
		
		// リソース種別が「プロフィール」で「physical_informations」表から
	    // 身長、体重、問診喫煙を検索する
		// バインド変数：reference_datetime(キー日付) resource_key(Personal Information Resource Key)
		sql = "SELECT heightTable.json_data ->> 'height' AS height, "+
				"weightTable.json_data ->> 'weight' AS weight, "+
				"smokingTable.json_data ->> 'smoking' AS smoking "+
				"FROM physical_informations heightTable, "+
				"physical_informations weightTable ,"+ 
				"medical_checkup_interviews smokingTable "+
				"WHERE heightTable.item_class_code = 'standard_info' "+
				"and weightTable.item_class_code = 'standard_info'  "+
				"and smokingTable.item_class_code = 'smoking' "+
				"and heightTable.resource_key = weightTable.resource_key "+
				"and weightTable.resource_key = smokingTable.resource_key "+
				"and smokingTable.resource_key = :resource_key "+
				"and heightTable.reference_datetime = :reference_datetime "+
				"and weightTable.reference_datetime = :reference_datetime "+
				"and smokingTable.reference_datetime = :reference_datetime ";
		
		// バインド変数の設定
		params = new MapSqlParameterSource();
		// キー日付、プロフィール情報リソースキーをバインド変数に格納する
		params.addValue(BIND_DATE, keyDate);
		params.addValue(BIND_KEY, uuidProfile);
		// SQLの実行
		list = template.queryForList(sql, params);
		// 身長
		height = list.get(0).get("height").toString();		
		logger.info("身長：{}",height);
		// 体重
		weight = list.get(0).get("weight").toString();		
		logger.info("体重：{}",weight);
		// 問診喫煙
		smoking = list.get(0).get("smoking").toString();	
		logger.info("問診喫煙：{}",smoking);

		// リソース種別が「検査結果」で「inspection_results」表から
		// 血液型、収縮期血圧(最高)、拡張期血圧(最低)を検索する
		// バインド変数：reference_datetime(キー日付) resource_key(Personal Information Resource Key)
		sql = "SELECT blood_aboTable.json_data ->> 'blood_abo' AS blood_abo, "+
				"systolicTable.json_data ->> 'systolic' AS systolic, "+
				"diastolicTable.json_data ->> 'diastolic' AS diastolic "+
				"FROM inspection_results blood_aboTable, "+
				" inspection_results systolicTable, "+
				" inspection_results diastolicTable "+
				"WHERE blood_aboTable.item_class_code = 'blood_abo' "+
				"and systolicTable.item_class_code = 'blood_pressure' "+
				"and diastolicTable.item_class_code = 'blood_pressure' "+
				"and systolicTable.resource_key = blood_aboTable.resource_key "+
				"and blood_aboTable.resource_key = diastolicTable.resource_key "+
				"and diastolicTable.resource_key = :resource_key "+
				"and systolicTable.reference_datetime = :reference_datetime "+
				"and blood_aboTable.reference_datetime = :reference_datetime "+
				"and diastolicTable.reference_datetime = :reference_datetime ";
		
		// バインド変数の設定
		params = new MapSqlParameterSource();
		// キー日付、検査結果情報リソースキーをバインド変数に格納する
		params.addValue(BIND_DATE, keyDate);
		params.addValue(BIND_KEY, uuidKensa);
		// SQLの実行
		list = template.queryForList(sql, params);
		// 血液型
		blood_abo = list.get(0).get("blood_abo").toString();	
		logger.info("血液型：{}",blood_abo);
		// 収縮期血圧(最高)
		systolic = list.get(0).get("systolic").toString();	
		logger.info("収縮期血圧(最高)：{}",systolic);
		// 拡張期血圧(最低)
		diastolic = list.get(0).get("diastolic").toString();	
		logger.info("拡張期血圧(最低)：{}",diastolic);
		
		// 各項目をセットする
		result.setFull_name(full_name);
		result.setSex(sex);
		result.setBirth_day(birth_day);
		result.setHeight(height);
		result.setWeight(weight);
		result.setBlood_abo(blood_abo);
		result.setSystolic(systolic);
		result.setDiastolic(diastolic);
		result.setSmoking(smoking);
		
		result.setMsg("取得に成功しました");
		logger.debug("Refugee情報取得処理終了");

		return result;
	}
	/**
	 * あるリソースキーからそれに紐づく別のリソース種別のキーを取得する
	 * @param from 入力となるリソースキー
	 * @param type リソース種別
	 * @return 引数のリソース種別に対応するリソースキー
	 */
	private UUID getKey(UUID from, String type) {
		UUID to = null;
		List<Map<String, Object>> list = null;	// 結果格納用リスト

		// バインド変数の初期化
		MapSqlParameterSource params = new MapSqlParameterSource();
		// coreDBと接続
		NamedParameterJdbcTemplate template = this.dataSourceManager.
				getNamedParameterJdbcTemplate(DB_CORE);
		// バインド変数にリソースキーとリソース種別を格納する
		params.addValue(BIND_KEY, from);
		params.addValue(BIND_TYPE, type);

		// SQLを実行
		list = template.queryForList(GET_KEY, params);
		// 取り出した結果をUUIDにキャストする
		to =  UUID.fromString(list.get(0).get(RESULT_COLUMN).toString());
		
		return to;
	}
}
