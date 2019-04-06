/*
 *Copyright (C) Hitachi, Ltd. All rights reserved.
 *
 * プロジェクト名　：
 *   PeOPLe基盤開発
 *
 * 機能仕様　：
 *
 * パラメータのコーリングシーケンス　：
 *
 * 備考　：
 *   なし
 *
 * 履歴　：
 *   日付			バージョン			Ｐ票番号				 内容
 *   2018/02/02		00.01								 新規作成
 */

package jp.co.people.core.app.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import jp.co.people.core.app.exceptions.NotRegisteredAnchorResourceException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jp.co.people.core.app.common.DateFormatEnum;
import jp.co.people.core.app.common.GenericAcquisitionConstants;
import jp.co.people.core.app.common.SystemConstants;
import jp.co.people.core.app.exceptions.ApiPermissionException;
import jp.co.people.core.app.exceptions.InputParametersException;
import jp.co.people.core.app.exceptions.NotExistsApiException;
import jp.co.people.core.app.services.GenericAcquisitionService;
import jp.co.people.core.app.utilities.DateTimeUtil;
import jp.co.people.core.app.utilities.StringUtils;

/**
 * <PRE>
 * クラス名：
 *   汎用データ取得のコントローラークラス
 *
 * 機能説明：
 *   汎用データ取得API
 * </PRE>
 */
@RestController
@RequestMapping("/api/v1")
public class GenericAcquireController extends BaseController {
	/** 汎用データ取得サービス */
	@Autowired
	private GenericAcquisitionService genericAcquisitionService;

	/** 出力データのJSONのキー名:取得結果 */
	private static final String RESPONSE_KEY_DATA = "data";

	
	/**
	 * 汎用データ取得API
	 */
	@RequestMapping(value = "/{sql_template_name}", method = { RequestMethod.POST })
	public Map<String, Object> genericAcquireApi(
			HttpServletResponse response,
			@PathVariable("sql_template_name") String sqlName,
			@RequestBody(required=false) String jsonString)
					throws InputParametersException, ApiPermissionException, Exception {
		// Start Log
		this.startLog();

		// 出力データ、変数の初期化
		int statusCode = HttpServletResponse.SC_OK; // ステータスコード:200
		String systemMessage = null; // システムメッセージ
		String result = SystemConstants.RESULT_NORMAL; // 終了結果
		Map<String, Object> responseBody = new HashMap<String, Object>(); // 出力データ
		responseBody.put(SystemConstants.RESPONSE_KEY_MESSAGE_ID, "");
		responseBody.put(SystemConstants.RESPONSE_KEY_MESSAGE, "");
		responseBody.put(RESPONSE_KEY_DATA, new JSONArray());

		try {
			// アクセス制御等
			JSONObject requestParameters = this.initialize(jsonString);
			
			// リクエストパラメータチェック
			this.validateRequestParameters(requestParameters);
			// 【機能限定版】リクエストパラメータチェック
			this.validateRequestParametersForLimited(requestParameters, sqlName);

			// 汎用データ取得
			JSONObject bindParameter = requestParameters.getJSONObject(SystemConstants.REQUEST_KEY_BIND_PARAMETAR);
			if(sqlName.startsWith(GenericAcquisitionConstants.GET_HEALTH_CATEGORY))
			{
				Object responseKey = genericAcquisitionService.executeQuery("getResourceKey", bindParameter);
				if(responseKey==null)
				{
					throw new NotRegisteredAnchorResourceException(SystemConstants.MID_E10001,
							this.messages.get(SystemConstants.MID_E10001, bindParameter.getString(GenericAcquisitionConstants.HEALTH_RESOURCE_KEY)));
				}
				if(responseKey instanceof List)
				{
					String resourceKey=((Map)((List)responseKey).get(0)).get(GenericAcquisitionConstants.HEALTH_RESOURCE_KEY).toString();
					System.out.println(resourceKey);
					bindParameter.put(GenericAcquisitionConstants.HEALTH_RESOURCE_KEY,resourceKey);
				}
			}

			Object responseJson = genericAcquisitionService.executeQuery(sqlName, bindParameter);
			
			if(responseJson != null){
				// 出力データの設定
				responseBody.put(RESPONSE_KEY_DATA, responseJson);
			}
			else{
				// 取得件数が0件
				responseBody.put(SystemConstants.RESPONSE_KEY_MESSAGE_ID, SystemConstants.MID_I00001);
				responseBody.put(SystemConstants.RESPONSE_KEY_MESSAGE, messages.get(SystemConstants.MID_I00001));
			}
			
		} catch (InputParametersException e) {
			result = SystemConstants.RESULT_ERROR; // 異常終了
			statusCode = HttpServletResponse.SC_BAD_REQUEST; // 400(Bad Request)
			responseBody.put(SystemConstants.RESPONSE_KEY_MESSAGE_ID, e.getMessageId());
			responseBody.put(SystemConstants.RESPONSE_KEY_MESSAGE, e.getMessage());
			this.warnLog(e.getMessage());
		} catch (ApiPermissionException e) {
			result = SystemConstants.RESULT_ERROR; // 異常終了
			statusCode = HttpServletResponse.SC_FORBIDDEN; // 403(Forbidden)
			responseBody.put(SystemConstants.RESPONSE_KEY_MESSAGE_ID, e.getMessageId());
			responseBody.put(SystemConstants.RESPONSE_KEY_MESSAGE, e.getMessage());
			this.warnLog(e.getMessage());
		} catch (NotExistsApiException e) {
			result = SystemConstants.RESULT_ERROR; // 異常終了
			statusCode = HttpServletResponse.SC_NOT_FOUND; // 404(Not Found)
			responseBody.put(SystemConstants.RESPONSE_KEY_MESSAGE_ID, e.getMessageId());
			responseBody.put(SystemConstants.RESPONSE_KEY_MESSAGE, e.getMessage());
			this.warnLog(e.getMessage());
		} catch (Exception e) {
			result = SystemConstants.RESULT_ERROR; // 異常終了
			statusCode = HttpServletResponse.SC_INTERNAL_SERVER_ERROR; // 500(InternalServerError)
			responseBody.put(SystemConstants.RESPONSE_KEY_MESSAGE_ID, SystemConstants.MID_E00006);
			responseBody.put(SystemConstants.RESPONSE_KEY_MESSAGE, messages.get(SystemConstants.MID_E00006));
			systemMessage = e.getMessage();
//			this.errorLog(e.getMessage());
		} finally {
			try {
				// API呼び出しログの登録
				this.apiCallLog(
						result,
						"template/" + sqlName,
						jsonString,
						(String)responseBody.get(SystemConstants.RESPONSE_KEY_MESSAGE_ID),
						(String)responseBody.get(SystemConstants.RESPONSE_KEY_MESSAGE),
						systemMessage);
			} catch (Exception e) {
				result = SystemConstants.RESULT_ERROR; // 異常終了
				statusCode = HttpServletResponse.SC_INTERNAL_SERVER_ERROR; // 500(InternalServerError)
				responseBody.put(SystemConstants.RESPONSE_KEY_MESSAGE_ID, SystemConstants.MID_E00019);
				responseBody.put(SystemConstants.RESPONSE_KEY_MESSAGE, messages.get(SystemConstants.MID_E00019));
//				this.errorLog(e.getMessage());
			}
			
			// End Log
			this.endLog();
		}

		// [HTTPステータスコード]と[出力データ]をクライアントに返却する。
		response.setStatus(statusCode);
		return responseBody;
	}

	/**
	 * リクエストパラメータチェック
	 * 
	 * @throws Exception
	 * @throws InputParametersException
	 */
	@Override
	protected void validateRequestParameters(JSONObject requestParameters) throws InputParametersException, NotExistsApiException, Exception {
		// バインドパラメータのチェック
		this.validateBindParameter(requestParameters);
	}
	
	/**
	 * 【機能限定版】リクエストパラメータチェック
	 * 
	 * @param sqlName
	 *            SQL名
	 *            リクエストパラメータ
	 * @throws InputParametersException
	 * @throws NotExistsApiException
	 * @throws Exception
	 */
	public void validateRequestParametersForLimited(JSONObject requestParameters, String sqlName)
			throws InputParametersException, NotExistsApiException, Exception {
		switch (sqlName) {
			case GenericAcquisitionConstants.GET_PERSONAL_INFO_RESOURCE_KEY_SQL_NAME: {
				this.validateBindParameterKeyForString(
						requestParameters,
						GenericAcquisitionConstants.PERSONAL_INFO_PATIENT_ID,
						GenericAcquisitionConstants.PERSONAL_INFO_PATIENT_ID_TITLE);
				break;
			}
			case GenericAcquisitionConstants.GET_PERSONAL_INFO_01_SQL_NAME: {
				this.validateDateFormat(
						requestParameters,
						GenericAcquisitionConstants.PERSONAL_INFO_BIRTH_DAY,
						GenericAcquisitionConstants.PERSONAL_INFO_BIRTH_DAY_TITLE);
				this.validateBindParameterKeyForString(
						requestParameters,
						GenericAcquisitionConstants.PERSONAL_INFO_SEX,
						GenericAcquisitionConstants.PERSONAL_INFO_SEX_TITLE);
				this.validateBindParameterKeyForString(
						requestParameters,
						GenericAcquisitionConstants.PERSONAL_INFO_FULL_NAME,
						GenericAcquisitionConstants.PERSONAL_INFO_FULL_NAME_TITLE);
				this.validateBindParameterKeyForString(
						requestParameters,
						GenericAcquisitionConstants.PERSONAL_INFO_INSURER,
						GenericAcquisitionConstants.PERSONAL_INFO_INSURER_TITLE);
				this.validateBindParameterKeyForString(
						requestParameters,
						GenericAcquisitionConstants.PERSONAL_INFO_INSURED_MARK,
						GenericAcquisitionConstants.PERSONAL_INFO_INSURED_MARK_TITLE);
				this.validateBindParameterKeyForString(
						requestParameters,
						GenericAcquisitionConstants.PERSONAL_INFO_INSURED_NUMBER,
						GenericAcquisitionConstants.PERSONAL_INFO_INSURED_NUMBER_TITLE);
				break;
			}
			case GenericAcquisitionConstants.GET_PERSONAL_INFO_02_SQL_NAME: {
				this.validateDateFormat(
						requestParameters,
						GenericAcquisitionConstants.PERSONAL_INFO_BIRTH_DAY,
						GenericAcquisitionConstants.PERSONAL_INFO_BIRTH_DAY_TITLE);
				this.validateBindParameterKeyForString(
						requestParameters,
						GenericAcquisitionConstants.PERSONAL_INFO_SEX,
						GenericAcquisitionConstants.PERSONAL_INFO_SEX_TITLE);
				this.validateBindParameterKeyForString(
						requestParameters,
						GenericAcquisitionConstants.PERSONAL_INFO_INSURER,
						GenericAcquisitionConstants.PERSONAL_INFO_INSURER_TITLE);
				this.validateBindParameterKeyForString(
						requestParameters,
						GenericAcquisitionConstants.PERSONAL_INFO_INSURED_MARK,
						GenericAcquisitionConstants.PERSONAL_INFO_INSURED_MARK_TITLE);
				this.validateBindParameterKeyForString(
						requestParameters,
						GenericAcquisitionConstants.PERSONAL_INFO_INSURED_NUMBER,
						GenericAcquisitionConstants.PERSONAL_INFO_INSURED_NUMBER_TITLE);
				break;
			}
			case GenericAcquisitionConstants.GET_PERSONAL_INFO_03_SQL_NAME: {
				this.validateDateFormat(
						requestParameters,
						GenericAcquisitionConstants.PERSONAL_INFO_BIRTH_DAY,
						GenericAcquisitionConstants.PERSONAL_INFO_BIRTH_DAY_TITLE);
				this.validateBindParameterKeyForString(
						requestParameters,
						GenericAcquisitionConstants.PERSONAL_INFO_SEX,
						GenericAcquisitionConstants.PERSONAL_INFO_SEX_TITLE);
				this.validateBindParameterKeyForString(
						requestParameters,
						GenericAcquisitionConstants.PERSONAL_INFO_FULL_NAME,
						GenericAcquisitionConstants.PERSONAL_INFO_FULL_NAME_TITLE);
				break;
			}
			case GenericAcquisitionConstants.GET_PERSONAL_INFO_04_SQL_NAME: {
				this.validateDateFormat(
						requestParameters,
						GenericAcquisitionConstants.PERSONAL_INFO_BIRTH_DAY,
						GenericAcquisitionConstants.PERSONAL_INFO_BIRTH_DAY_TITLE);
				this.validateBindParameterKeyForString(
						requestParameters,
						GenericAcquisitionConstants.PERSONAL_INFO_FULL_NAME,
						GenericAcquisitionConstants.PERSONAL_INFO_FULL_NAME_TITLE);
				this.validateBindParameterKeyForString(
						requestParameters,
						GenericAcquisitionConstants.PERSONAL_INFO_INSURER,
						GenericAcquisitionConstants.PERSONAL_INFO_INSURER_TITLE);
				this.validateBindParameterKeyForString(
						requestParameters,
						GenericAcquisitionConstants.PERSONAL_INFO_INSURED_MARK,
						GenericAcquisitionConstants.PERSONAL_INFO_INSURED_MARK_TITLE);
				this.validateBindParameterKeyForString(
						requestParameters,
						GenericAcquisitionConstants.PERSONAL_INFO_INSURED_NUMBER,
						GenericAcquisitionConstants.PERSONAL_INFO_INSURED_NUMBER_TITLE);
				break;
			}
			case GenericAcquisitionConstants.GET_PERSONAL_INFO_05_SQL_NAME: {
				this.validateDateFormat(
						requestParameters,
						GenericAcquisitionConstants.PERSONAL_INFO_BIRTH_DAY,
						GenericAcquisitionConstants.PERSONAL_INFO_BIRTH_DAY_TITLE);
				this.validateBindParameterKeyForString(
						requestParameters,
						GenericAcquisitionConstants.PERSONAL_INFO_INSURER,
						GenericAcquisitionConstants.PERSONAL_INFO_INSURER_TITLE);
				this.validateBindParameterKeyForString(
						requestParameters,
						GenericAcquisitionConstants.PERSONAL_INFO_INSURED_MARK,
						GenericAcquisitionConstants.PERSONAL_INFO_INSURED_MARK_TITLE);
				this.validateBindParameterKeyForString(
						requestParameters,
						GenericAcquisitionConstants.PERSONAL_INFO_INSURED_NUMBER,
						GenericAcquisitionConstants.PERSONAL_INFO_INSURED_NUMBER_TITLE);
				break;
			}
			case GenericAcquisitionConstants.GET_PERSONAL_INFO_06_SQL_NAME: {
				this.validateDateFormat(
						requestParameters,
						GenericAcquisitionConstants.PERSONAL_INFO_BIRTH_DAY,
						GenericAcquisitionConstants.PERSONAL_INFO_BIRTH_DAY_TITLE);
				this.validateBindParameterKeyForString(
						requestParameters,
						GenericAcquisitionConstants.PERSONAL_INFO_FULL_NAME,
						GenericAcquisitionConstants.PERSONAL_INFO_FULL_NAME_TITLE);
				break;
			}
			case GenericAcquisitionConstants.GET_HEALTH_INTERVIEW_SQL_NAME:{
				this.validateBindParameterKeyForString(
						requestParameters,
						GenericAcquisitionConstants.HEALTH_RESOURCE_KEY,
						GenericAcquisitionConstants.HEALTH_RESOURCE_KEY_TITLE);
				break;
			}

			default: {
				// 定義されていないSQL名が指定されている
				throw new NotExistsApiException(SystemConstants.MID_E00007, messages.get(SystemConstants.MID_E00007));
			}
		}
	}
	
	/**
	 * 日付文字列項目がキーの存在チェックする。且つ、NULLまたは空文字列ではないことをチェックする。
	 * </br>また、日付文字列の書式チェックする。
	 * @param key 文字列型のキー物理名
	 * @param keyTitle キーの項目名
	 * @throws InputParametersException
	 * @throws Exception
	 */
	private void validateDateFormat(JSONObject requestParameters, String key, String keyTitle) throws InputParametersException, Exception{
		String dateString = this.validateBindParameterKeyForString(requestParameters, key, keyTitle);
		String convDateString = this.convertDateTimeFormat(dateString);
		if(convDateString == null){
			throw new InputParametersException(
					SystemConstants.MID_E00005,
					this.messages.get(SystemConstants.MID_E00005, keyTitle, dateString));
		}
		this.updateBindParameterKey(requestParameters, key, convDateString);
	}
	
	/**
	 * 入力パラメータの日付の書式を変換する
	 * @param dateString 日付・日時の文字列
	 * @return yyyy-MM-dd書式の文字列（変換できない場合はnullを返却）
	 */
	private String convertDateTimeFormat(String dateString){
		Date date = DateTimeUtil.toDate(dateString);
		if(date == null){
			return null;
		}
		return DateTimeUtil.toString(date, DateFormatEnum.DATE);
	}

	/**
	 * バインドパラメータに指定されたキー項目が存在しているか。且つ、NULLまたは空文字列ではないことをチェックする。
	 * 
	 * @param key 文字列型のキー物理名
	 * @param keyTitle キーの項目名
	 * @return キーに設定されている文字列の値
	 * @throws InputParametersException
	 * @throws Exception
	 */
	private String validateBindParameterKeyForString(JSONObject requestParameters, String key, String keyTitle)
			throws InputParametersException, Exception{
		JSONObject bindParameter = requestParameters.getJSONObject(SystemConstants.REQUEST_KEY_BIND_PARAMETAR);
		if(!bindParameter.has(key)){
			throw new InputParametersException(SystemConstants.MID_E00004, // パラメータ:{0}は省略できません。
					this.messages.get(SystemConstants.MID_E00004, keyTitle));
		}
		String keyValue = bindParameter.getString(key);
		if(StringUtils.isNullOrEmpty(keyValue)){
			throw new InputParametersException(SystemConstants.MID_E00004, // パラメータ:{0}は省略できません。
					this.messages.get(SystemConstants.MID_E00004, keyTitle));
		}
		return keyValue;
	}
}
