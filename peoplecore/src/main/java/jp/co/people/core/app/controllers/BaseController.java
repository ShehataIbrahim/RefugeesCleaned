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
 *   2018/01/31		00.01								 新規作成
 */

package jp.co.people.core.app.controllers;

import java.util.Iterator;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jp.co.people.core.app.common.SystemConstants;
import jp.co.people.core.app.exceptions.ApiPermissionException;
import jp.co.people.core.app.exceptions.InputParametersException;
import jp.co.people.core.app.services.AccessControllService;
import jp.co.people.core.app.services.ApiCallLogService;
import jp.co.people.core.app.utilities.Messages;
import jp.co.people.core.app.utilities.StringUtils;

/**
 * <PRE>
 * クラス名：
 *   コントローラーの抽象クラス
 *
 * 機能説明：
 *   コントローラーの共通処理を行う
 * </PRE>
 */
public abstract class BaseController {
	/** アクセス制御サービス */
	@Autowired
	protected AccessControllService sccessControllService;
	
	/** メッセージ */
	@Autowired
	protected Messages messages;
	
	/** API呼び出しログサービス */
	@Autowired
	private ApiCallLogService apiCallLogService;
	
	/** コントローラーのlogger*/
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * リクエスト元のIPアドレスを返す。
	 * 
	 * @return リクエスト元のIPアドレス
	 */
	public String getClientIPAddress() {
		HttpServletRequest	httpServletRequest = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		String 				clientIPAddress = httpServletRequest.getHeader(SystemConstants.REQUEST_HEADER_IP_ADRRESS_NAME);
		
		if (clientIPAddress == null) {
			clientIPAddress = httpServletRequest.getRemoteAddr();
		}
		
		return clientIPAddress;
	}
	
	
	/**
	 * リクエストパラメータから取得した認証キーを返す。
	 * 
	 * @return 認証キー。初期化前や認証キーが無い場合は　null を返す。
	 */
	public UUID getAuthKey(JSONObject requestParameters) {
		try{
			// 認証キーのNULLチェック
			String authKeyValue = requestParameters.getString(SystemConstants.AUTH_KEYS_AUTH_KEY);
			return UUID.fromString(authKeyValue);
		}
		catch(Exception e){
			return null;
		}
	}
	
	
	/**
	 * コントローラーの初期化 </br>
	 * ・入力パラメータをJSONオブジェクトに変換 </br>
	 * ・IPアドレスの取得 </br>
	 * ・アクセス制御
	 * 
	 * @param inputParameters
	 *            入力パラメータ（JSON文字列）
	 * 
	 * @exception InputParametersException
	 *                JSONオブジェクトの変換エラー
	 * @exception ApiPermissionException
	 *                権限エラー
	 * @return リクエストパラメータ
	 */
	protected JSONObject initialize(String inputParameters)
			throws InputParametersException, ApiPermissionException, Exception {
		UUID		authKey = null;
		JSONObject	requestParameters = null;
		
		// リクエストパラメータの変換
		try {
			requestParameters = new JSONObject(inputParameters);
			
			// 値から空白を除去
			this.trimWhiteSpaces(requestParameters);
		} catch (Exception e) {
			throw new InputParametersException(SystemConstants.MID_E00008, this.messages.get(SystemConstants.MID_E00008));
		}

		// アクセス制御
		try{
			// 認証キーのNULLチェック
			String authKeyValue = requestParameters.getString(SystemConstants.AUTH_KEYS_AUTH_KEY);
			authKey = UUID.fromString(authKeyValue);
		}
		catch(Exception e){
			throw new ApiPermissionException(SystemConstants.MID_E00001, this.messages.get(SystemConstants.MID_E00001));
		}
		
		// アクセス権限の検証
		sccessControllService.validate(authKey, this.getClientIPAddress());
		
		return requestParameters;
	}
	
	/**
	 * プロセスを開始したログを出力します。
	 */
	protected void startLog(){
		this.logger.info("Start : " + this.getClass().getSimpleName());
	}
	
	/**
	 * 情報ログを登録します。
	 * @param log ログメッセージ
	 */
	protected void infoLog(String log){
		this.logger.info(log);
	}
	
	/**
	 * 警告ログを登録します。
	 * @param log ログメッセージ
	 */
	protected void warnLog(String log){
		this.logger.warn(log);
	}
	
	/**
	 * エラーログを登録します。
	 * @param log ログメッセージ
	 */
	protected void errorLog(String log){
		this.logger.error(log);
	}
	
	/**
	 * プロセスを終了したログを出力します。
	 */
	protected void endLog(){
		this.logger.info("End : " + this.getClass().getSimpleName());
	}
	
	/**
	 * API呼び出しログを登録する。
	 * 
	 * @param logResult APIの結果（'N'：正常、'W'：警告、'E'：エラー）
	 * @param apiName　呼び出したAPIの名前
	 * @param inputParameters 入力パラメータ
	 * @param messageId　警告やエラー時のメッセージID
	 * @param message　警告やエラー時のメッセージ
	 * @param systemMessage　システムから発生したException等の詳細なメッセージ
	 * @throws Exception ログの登録失敗
	 */
	protected void apiCallLog(
			String logResult,
			String apiName,
			String inputParameters,
			String messageId,
			String message,
			String systemMessage) throws Exception{

		// API呼び出しログサービスでAPI呼び出しログを登録する
		apiCallLogService.writeLog(
				logResult,
				apiName,
				inputParameters,
				messageId,
				message,
				systemMessage,
				this.getClientIPAddress());
	}
	
	/**
	 * API毎のリクエストパラメータのチェックを行う。
	 * @throws Exception 
	 * @throws InputParametersException 
	 */
	protected abstract void validateRequestParameters(JSONObject requestParameters) throws InputParametersException, Exception;
	
	/**
	 * リクエストパラメータにバインドパラメータが存在しているか。且つ、NULLではないことをチェックする。
	 * 
	 * @throws InputParametersException
	 * @throws Exception
	 */
	protected void validateBindParameter(JSONObject requestParameters) throws InputParametersException, Exception{
		// バインドパラメータが存在しているかのチェック
		if(!requestParameters.has(SystemConstants.REQUEST_KEY_BIND_PARAMETAR)){
			throw new InputParametersException(SystemConstants.MID_E00004, // パラメータ:{0}は省略できません。
					this.messages.get(SystemConstants.MID_E00004, SystemConstants.REQUEST_KEY_BIND_PARAMETAR_TITLE));
		}
		if(requestParameters.optJSONObject(SystemConstants.REQUEST_KEY_BIND_PARAMETAR) == null){
			throw new InputParametersException(SystemConstants.MID_E00005, // パラメータ:{0}の値:{1}が正しくありません。
					this.messages.get(
							SystemConstants.MID_E00005,
							SystemConstants.REQUEST_KEY_BIND_PARAMETAR_TITLE,
							requestParameters.get(SystemConstants.REQUEST_KEY_BIND_PARAMETAR)));
		}
	}

	/**
	 * リクエストパラメータに指定されたキー項目が存在しているか。且つ、NULLまたは空文字列ではないことをチェックする。
	 * 
	 * @param key 文字列型のキー物理名
	 * @param keyTitle キーの項目名
	 * @return 指定したキー項目の値（文字列）
	 * @throws InputParametersException
	 * @throws Exception
	 */
	protected String validateKeyForString(JSONObject requestParameters, String key, String keyTitle) throws InputParametersException, Exception{
		if(!requestParameters.has(key)){
			throw new InputParametersException(SystemConstants.MID_E00004, // パラメータ:{0}は省略できません。
					this.messages.get(SystemConstants.MID_E00004, keyTitle));
		}
		
		if (!(requestParameters.get(key) instanceof String)) {
			throw new InputParametersException(SystemConstants.MID_E00012, // パラメータ:[{0}]は文字列で指定してください。
					this.messages.get(SystemConstants.MID_E00012, keyTitle));
		}
		
		String keyValue = requestParameters.getString(key);
		
		if(StringUtils.isNullOrEmpty(keyValue)){
			throw new InputParametersException(SystemConstants.MID_E00004, // パラメータ:{0}は省略できません。
					this.messages.get(SystemConstants.MID_E00004, keyTitle));
		}
		
		return StringUtils.trimWhiteSpace(keyValue);
	}
	
	/**
	 * 指定されたバインドパラメータのキーの値を更新する
	 * 
	 * @param key 文字列型のキー物理名
	 * @param keyValue 更新する値
	 * @throws Exception
	 */
	protected void updateBindParameterKey(JSONObject requestParameters, String key, Object keyValue) throws Exception{
		JSONObject bindParameter = requestParameters.getJSONObject(SystemConstants.REQUEST_KEY_BIND_PARAMETAR);
		bindParameter.put(key, keyValue);
	}

	/**
	 * JSONオブジェクトから値の前後の空白を除去する。
     *
	 * @param jsonObject
	 * @throws JSONException 
	 */
	private void trimWhiteSpaces(JSONObject jsonObject) throws JSONException {
		Iterator<?> keys = jsonObject.keys();
		
		while (keys.hasNext()) {
			String key = (String)keys.next();
			if (jsonObject.get(key) instanceof String) {
				jsonObject.put(key, StringUtils.trimWhiteSpace(jsonObject.getString(key)));
			}
			else if (jsonObject.get(key) instanceof JSONObject) {
				this.trimWhiteSpaces(jsonObject.getJSONObject(key));
			}
			else if (jsonObject.get(key) instanceof JSONArray) {
				this.trimWhiteSpaces(jsonObject.getJSONArray(key));
			}
		}
	}
	
	/**
	 * JSON配列から値の前後の空白を除去する。
	 * @param jsonArray
	 * @throws JSONException
	 */
	private void trimWhiteSpaces(JSONArray jsonArray) throws JSONException {
		for (int i = 0; i < jsonArray.length(); i++) {
			if (jsonArray.get(i) instanceof String) {
				jsonArray.put(i, StringUtils.trimWhiteSpace(jsonArray.getString(i)));
			}
			else if (jsonArray.get(i) instanceof JSONObject) {
				this.trimWhiteSpaces(jsonArray.getJSONObject(i));
			}
			else if (jsonArray.get(i) instanceof JSONArray) {
				this.trimWhiteSpaces(jsonArray.getJSONArray(i));
			}
		}
	}
}
