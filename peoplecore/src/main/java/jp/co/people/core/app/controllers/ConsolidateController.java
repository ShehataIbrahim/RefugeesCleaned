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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jp.co.people.core.app.common.SystemConstants;
import jp.co.people.core.app.exceptions.ApiPermissionException;
import jp.co.people.core.app.exceptions.DataConflictException;
import jp.co.people.core.app.exceptions.InputParametersException;
import jp.co.people.core.app.exceptions.MasterInconsistencyException;
import jp.co.people.core.app.exceptions.NotRegisteredAnchorResourceException;
import jp.co.people.core.app.services.ConsolidateService;
import jp.co.people.core.app.services.model.ConsolidationItemDto;
import jp.co.people.core.app.utilities.DateTimeUtil;
import jp.co.people.core.app.utilities.StringUtils;


/**
 * 集約APIのコントローラー
 **/
@RestController
@RequestMapping("/api/v1")
public class ConsolidateController extends BaseController {
	//! 入力パラメータのキー名：集約元リソース種別
	private static final String REQUEST_KEY_FROM_TYPE = "from_type";
	private static final String REQUEST_TITLE_FROM_TYPE = "集約元リソース種別";
	//! 入力パラメータのキー名：集約先リソース種別
	private static final String REQUEST_KEY_TO_TYPE = "to_type";
	// private static final String REQUEST_TITLE_TO_TYPE = "集約先リソース種別";
	//! 入力パラメータのキー名：データ
	private static final String REQUEST_KEY_DATA = "data";
	private static final String REQUEST_TITLE_DATA = "データ";
	//! 入力パラメータのキー名：処理方法
	private static final String REQUEST_KEY_METHOD = "method";
	private static final String REQUEST_TITLE_METHOD = "処理方法";
	//! 入力パラメータのキー名：名寄せ先個人リソースキー
	private static final String REQUEST_KEY_RESOURCE_KEY = "resource_key";
	private static final String REQUEST_TITLE_RESOURCE_KEY = "名寄せ先個人リソースキー";
	//! 入力パラメータのキー名：バインドパラメータ
	private static final String REQUEST_KEY_BIND_PARAMETERS = "bind_parameters";
	private static final String REQUEST_TITLE_BIND_PARAMETERS = "バインドパラメータ";
	
	//! 出力データのリソースキー群のキー名
	private static final String RESPONSE_KEY_RESOURCE_KEYS = "resource_keys";
	
	//! 処理方法:insert
	private static final String METHOD_INSERT = "insert";
	//! 処理方法:update
	private static final String METHOD_UPDATE = "update";
	
	//! API名
	private static final String API_NAME = "consolidate";
	
	//! メッセージID:MID_E20001:認証キーにリソース種別が紐づけられていません。認証キー発行元に確認してください。
	private static final String MID_E20001 = "E20001";
	//! 指定された認証キーでは、集約元リソース種別に"{0}"を指定できません。大分類が"{1}"のリソース種別を指定してください。
	private static final String MID_E20002 = "E20002";
	//! 集約マスタを取得できませんでした。集約マスタもしくはパラメータに指定した値を確認してください。
	private static final String MID_E20003 = "E20003";
	//! 処理方法が"update"の場合は、名寄せ先個人リソースキーを省略できません。
	private static final String MID_E20004 = "E20004";
	//! [{0}]件目の個人データ処理中にエラーが発生しました。
	private static final String MID_E20005 = "E20005";
	//! 集約マスタに集約元リソース種別{0}の基準日時項目が設定されていません。
	private static final String MID_E20006 = "E20006";
	//! 集約マスタに設定されている集約元項目名{0}が、バインドパラメータに必要です。
	private static final String MID_E20007 = "E20007";
	//! 集約優先順位マスタに不整合があります。
	private static final String MID_E20008 = "E20008";
	//! 基準日時項目:[{0}]に値がセットされていません。
	private static final String MID_E20009 = "E20009";
	
	//! 集約APIのサービス
	@Autowired
	private ConsolidateService consolidateService;

	
	/**
	 * 集約API
	 * 
	 * @param response		HTTPレスポンス。
	 * @param jsonString	JSON形式のパラメータデータ。
	 * @return JSON形式のレスポンスデータ。
	 * @param jsonString
	 * @return
	 */
	@RequestMapping(value = "/consolidate", method = { RequestMethod.POST })
	public Map<String, Object> consolidate(HttpServletResponse response, @RequestBody(required=false) String jsonString)
			throws InputParametersException, ApiPermissionException, Exception {
		// Start Log
		this.startLog();
				
		// 1) 出力データ、変数の初期化
		int 				statusCode	  = HttpServletResponse.SC_OK; 		// ステータスコード:200
		String 				systemMessage = null; 							// システムメッセージ
		String 				result 		  = SystemConstants.RESULT_NORMAL;	// 終了結果
		Map<String, Object> responseBody  = new HashMap<String, Object>(); 	// 出力データ
		List<String> 		resourceKeys  = new ArrayList<String>();		// 出力データのリソースキー群
		String				derivedFrom   = null;							// 登録元
		
		responseBody.put(SystemConstants.RESPONSE_KEY_MESSAGE_ID, "");
		responseBody.put(SystemConstants.RESPONSE_KEY_MESSAGE, "");
		responseBody.put(RESPONSE_KEY_RESOURCE_KEYS, resourceKeys);
		
		try {
			// 2) アクセス制御
			JSONObject requestParameters = this.initialize(jsonString);
		
			// 3) リクエストパラメータチェック
			this.validateRequestParameters(requestParameters);
			
			// 4) 集約マスタの取得
			// 4-1) 認証キーに紐づいた登録元となるリソース種別を取得する
			derivedFrom = this.getDerivedFrom(requestParameters);
			
			// 4-3) ～ 集約マスタを取得する
			List<ConsolidationItemDto> consolidationItems = this.getConsolidationItems(requestParameters);
			
			// 5) トランザクション開始～ 10) トランザクション終了まで
			String resourceTypeTo = requestParameters.getString(REQUEST_KEY_TO_TYPE);
			resourceKeys = this.consolidateService.consolidate(
				resourceTypeTo, derivedFrom, consolidationItems, this.getDataFromRequestParameters(requestParameters));
			responseBody.put(RESPONSE_KEY_RESOURCE_KEYS, resourceKeys);
			
			// 11) レスポンスの返却
			// 正常終了
			result = SystemConstants.RESULT_NORMAL;
			statusCode = HttpServletResponse.SC_OK;
			responseBody.put(SystemConstants.RESPONSE_KEY_MESSAGE_ID, "");
			responseBody.put(SystemConstants.RESPONSE_KEY_MESSAGE, "");
			systemMessage = "";
		} catch (InputParametersException e) {	// 入力パラメータエラー
			result = SystemConstants.RESULT_ERROR;
			statusCode = HttpServletResponse.SC_BAD_REQUEST; // 400(Bad Request)
			responseBody.put(SystemConstants.RESPONSE_KEY_MESSAGE_ID, e.getMessageId());
			responseBody.put(SystemConstants.RESPONSE_KEY_MESSAGE, e.getMessage());
			systemMessage = "";
			this.warnLog(e.getMessage());
		} catch (NotRegisteredAnchorResourceException e) {	// インデックス情報未登録エラー
			result = SystemConstants.RESULT_ERROR;
			statusCode = HttpServletResponse.SC_BAD_REQUEST; // 400(Bad Request)
			responseBody.put(SystemConstants.RESPONSE_KEY_MESSAGE_ID, e.getMessageId());
			responseBody.put(SystemConstants.RESPONSE_KEY_MESSAGE, e.getMessage());
			systemMessage = "";
			this.warnLog(e.getMessage());
		} catch (ApiPermissionException e) {	// 権限エラー
			result = SystemConstants.RESULT_ERROR;
			statusCode = HttpServletResponse.SC_FORBIDDEN;	// 403(Forbidden)
			responseBody.put(SystemConstants.RESPONSE_KEY_MESSAGE_ID, e.getMessageId());
			responseBody.put(SystemConstants.RESPONSE_KEY_MESSAGE, e.getMessage());
			systemMessage = "";
			this.warnLog(e.getMessage());
		} catch (DataConflictException e) {		// データ重複エラー
			result = SystemConstants.RESULT_ERROR;
			statusCode = HttpServletResponse.SC_CONFLICT;	// 409(Conflict)
			responseBody.put(SystemConstants.RESPONSE_KEY_MESSAGE_ID, e.getMessageId());
			responseBody.put(SystemConstants.RESPONSE_KEY_MESSAGE, e.getMessage());
			systemMessage = "";
			this.warnLog(e.getMessage());
		} catch (MasterInconsistencyException e) {	// マスタ不整合エラー
			result = SystemConstants.RESULT_ERROR;
			statusCode = HttpServletResponse.SC_PRECONDITION_FAILED;	// 412(Precondition Failed)
			responseBody.put(SystemConstants.RESPONSE_KEY_MESSAGE_ID, e.getMessageId());
			responseBody.put(SystemConstants.RESPONSE_KEY_MESSAGE, e.getMessage());
			systemMessage = "";
			this.warnLog(e.getMessage());
		} catch (UnexpectedRollbackException e) {	// 予期しないロールバック。おそらくタイムアウト
			result = SystemConstants.RESULT_ERROR;
			statusCode = HttpServletResponse.SC_REQUEST_TIMEOUT;
			responseBody.put(SystemConstants.RESPONSE_KEY_MESSAGE_ID, SystemConstants.MID_E00018);
			responseBody.put(SystemConstants.RESPONSE_KEY_MESSAGE, this.messages.get(SystemConstants.MID_E00018));
			systemMessage = e.getMessage();
			this.warnLog(e.getMessage());
		} catch (Exception e) {
			result = SystemConstants.RESULT_ERROR;
			statusCode = HttpServletResponse.SC_INTERNAL_SERVER_ERROR; // 500(Internal Server Error)
			responseBody.put(SystemConstants.RESPONSE_KEY_MESSAGE_ID, SystemConstants.MID_E00006);
			responseBody.put(SystemConstants.RESPONSE_KEY_MESSAGE, messages.get(SystemConstants.MID_E00006));
			systemMessage = e.getMessage();
			this.errorLog(e.getMessage());
		} finally {
			try {
				// ログ出力
				this.apiCallLog(
						result,
						API_NAME,
						jsonString,
						(String)responseBody.get(SystemConstants.RESPONSE_KEY_MESSAGE_ID),
						(String)responseBody.get(SystemConstants.RESPONSE_KEY_MESSAGE),
						systemMessage);
			} catch (Exception e) {
				result = SystemConstants.RESULT_ERROR; // 異常終了
				statusCode = HttpServletResponse.SC_INTERNAL_SERVER_ERROR; // 500(InternalServerError)
				responseBody.put(SystemConstants.RESPONSE_KEY_MESSAGE_ID, SystemConstants.MID_E00019);
				responseBody.put(SystemConstants.RESPONSE_KEY_MESSAGE, messages.get(SystemConstants.MID_E00019));
				this.errorLog(e.getMessage());
			}
			
			// End Log
			this.endLog();
		}
		// [HTTPステータスコード]と[出力データ]をクライアントに返却する。
		response.setStatus(statusCode);
		return responseBody;
	}


	/**
	 * パラメータチェック
	 */
	@Override
	protected void validateRequestParameters(JSONObject requestParameters) throws InputParametersException, Exception {
		// No.1:集約元リソース種別
		this.validateKeyForString(requestParameters, REQUEST_KEY_FROM_TYPE, REQUEST_TITLE_FROM_TYPE);
		
		// No.2:[入力パラメータ].[データ]がNULLの場合
		if (requestParameters.has(REQUEST_KEY_DATA) == false) {
			// パラメータ:{0}は省略できません。
			throw new InputParametersException(SystemConstants.MID_E00004, this.messages.get(SystemConstants.MID_E00004, REQUEST_TITLE_DATA));
		}
		
		// No.3:[入力パラメータ].[データ]がハッシュでもリストでもない場合、例外を投げる 
		if (requestParameters.optJSONArray(REQUEST_KEY_DATA) == null
		&&  requestParameters.optJSONObject(REQUEST_KEY_DATA) == null) {
			// パラメータ:{0}の値:{1}が正しくありません。
			String message = this.messages.get(
				SystemConstants.MID_E00005,
				REQUEST_TITLE_DATA,
				requestParameters.getString(REQUEST_KEY_DATA));
			throw new InputParametersException(SystemConstants.MID_E00005, message);
		}
		
		JSONArray data = this.getDataFromRequestParameters(requestParameters);
		for (int i = 0; i < data.length(); i++) {
			JSONObject personData = data.optJSONObject(i);
			
			// No.4:[入力パラメータ].[データ]の要素がハッシュではない。
			if (personData == null) {
				// [{0}]件目の個人データ処理中にエラーが発生しました。
				// パラメータ:{0}の値:{1}が正しくありません。
				String message = this.messages.get(MID_E20005, i + 1)
							   + this.messages.get(SystemConstants.MID_E00005, REQUEST_TITLE_DATA, data.getString(i));
				throw new InputParametersException(SystemConstants.MID_E00005, message);
			}
			
			// No.5:[i].[処理方法]が NULL の場合
			if (personData.has(REQUEST_KEY_METHOD) == false) {
				// [{0}]件目の個人データ処理中にエラーが発生しました。
				// パラメータ:{0}は省略できません。
				String message = this.messages.get(MID_E20005, i + 1)
							   + this.messages.get(SystemConstants.MID_E00004, REQUEST_TITLE_METHOD);
				throw new InputParametersException(SystemConstants.MID_E00004, message);
			}

			// No.6:[i].[処理方法]が"insert" でも "update" でもない。
			String method = personData.getString(REQUEST_KEY_METHOD); 
			if (method.equals(METHOD_INSERT) == false && method.equals(METHOD_UPDATE) == false) {
				// [{0}]件目の個人データ処理中にエラーが発生しました。
				// パラメータ:{0}の値:{1}が正しくありません。
				String message = this.messages.get(MID_E20005, i + 1)
						       + this.messages.get(SystemConstants.MID_E00005, REQUEST_TITLE_METHOD, method);
				throw new InputParametersException(SystemConstants.MID_E00005, message);
			}
			
			// No.7:[i].[処理方法]が"update"の場合、且つ、[i].[名寄せ先個人リソースキー]がNULLの場合
			String resourceKey = personData.optString(REQUEST_KEY_RESOURCE_KEY);
			if (method.equals(METHOD_UPDATE)) {
				if (resourceKey == null || resourceKey.isEmpty()) {
					// [{0}]件目の個人データ処理中にエラーが発生しました。
					// 処理方法が"update"の場合は、名寄せ先個人リソースキーを省略できません。
					String message = this.messages.get(MID_E20005, i + 1)
						       	   + this.messages.get(MID_E20004);
					throw new InputParametersException(MID_E20004, message);
				}
			}
			
			// No.8:[i].[名寄せ先個人リソースキー]がNULLではない、且つUUID文字列ではなかった場合
			if (resourceKey != null && resourceKey.isEmpty() == false) {
				try {
					UUID.fromString(resourceKey);
				} catch (Exception e) {
					// [{0}]件目の個人データ処理中にエラーが発生しました。
					// パラメータ:{0}の値:{1}が正しくありません。
					String message = this.messages.get(MID_E20005, i + 1)
							       + this.messages.get(SystemConstants.MID_E00005,
							    		   REQUEST_TITLE_RESOURCE_KEY,
							    		   personData.getString(REQUEST_KEY_RESOURCE_KEY));
					throw new InputParametersException(SystemConstants.MID_E00005, message);
				}
			}
			
			// No.9:[i].[バインドパラメータ]が NULL の場合
			if (personData.has(REQUEST_KEY_BIND_PARAMETERS) == false) {
				// [{0}]件目の個人データ処理中にエラーが発生しました。
				// パラメータ:{0}は省略できません。
				String message = this.messages.get(MID_E20005, i + 1)
							   + this.messages.get(SystemConstants.MID_E00004, REQUEST_TITLE_BIND_PARAMETERS);
				throw new InputParametersException(SystemConstants.MID_E00004, message);
			}
			
			// No.10:[i].[バインドパラメータ]が JSONObject ではない場合。
			if (personData.optJSONObject(REQUEST_KEY_BIND_PARAMETERS) == null) {
				// [{0}]件目の個人データ処理中にエラーが発生しました。
				// パラメータ:{0}の値:{1}が正しくありません。
				String message = this.messages.get(MID_E20005, i + 1)
						       + this.messages.get(SystemConstants.MID_E00005,
						    		   REQUEST_TITLE_BIND_PARAMETERS,
						    		   StringUtils.ommit(personData.get(REQUEST_KEY_BIND_PARAMETERS), 30));
				throw new InputParametersException(SystemConstants.MID_E00005, message);
			}
		}
	}
	
	
	/**
	 * 認証キーに紐づいた登録元となるリソース種別を取得と登録元の確認を行う。
	 * 
	 * @return 
	 * @throws MasterInconsistencyException, InputParametersException 
	 * @throws Exception 
	 */
	private String getDerivedFrom(JSONObject requestParameters) throws MasterInconsistencyException, InputParametersException, Exception {
		// 4-1) 認証キーに紐づいた登録元となるリソース種別を取得する
		String derivedFrom = this.consolidateService.getResourceType(this.getAuthKey(requestParameters));
		
		// 4-2) 登録元の確認
		// 4-2-1) 登録元が null の場合、例外を投げる
		if (derivedFrom == null || derivedFrom.isEmpty()) {
			// E20001:認証キーにリソース種別が紐づけられていません。認証キー発行元に確認してください。 
			throw new MasterInconsistencyException(MID_E20001, this.messages.get(MID_E20001));
		}
		
		// 4-2-2) [入力パラメータ].[集約元リソース種別]の大分類と[登録元]の大分類が一致しない場合、例外を投げる
		String resourceTypeFrom 	 = requestParameters.getString(REQUEST_KEY_FROM_TYPE);	// 集約元リソース種別
		String largeClassTypeFrom    = ConsolidateController.getLargeClass(resourceTypeFrom);	// 集約元の大分類
		String largeClassDerivedFrom = ConsolidateController.getLargeClass(derivedFrom);		// 登録元の大分類
		if (largeClassTypeFrom.equals(largeClassDerivedFrom) == false) {
			// 指定された認証キーでは、集約元リソース種別に"{0}"を指定できません。大分類が"{1}"のリソース種別を指定してください。
			throw new InputParametersException(MID_E20002, this.messages.get(MID_E20002, largeClassTypeFrom, largeClassDerivedFrom));
		}
		
		return derivedFrom;
	}
	
	
	/**
	 * 集約マスタを取得し、整合性の確認を行う。整合性が取れていない場合や、データが無かった場合は例外を投げる
	 *
	 * @return 集約マスタのレコードセット
	 * @throws Exception 
	 */
	private List<ConsolidationItemDto> getConsolidationItems(JSONObject requestParameters)
			throws InputParametersException, MasterInconsistencyException, Exception {
		String resourceTypeFrom = requestParameters.getString(REQUEST_KEY_FROM_TYPE);
		String resourceTypeTo   = requestParameters.getString(REQUEST_KEY_TO_TYPE);
		
		// 4-3) 集約マスタを取得する。
		List<ConsolidationItemDto> consolidationItems = this.consolidateService.getConsolidationItems(resourceTypeFrom, resourceTypeTo);
		
		// 4-4) 集約マスタの整合性確認
		// 4-4-1) 取得結果が0件の場合、例外を投げる
		if (consolidationItems == null || consolidationItems.size() == 0) {
			// 集約マスタを取得できませんでした。集約マスタもしくはパラメータに指定した値を確認してください。
			throw new InputParametersException(MID_E20003, this.messages.get(MID_E20003));
		}
		
		// 4-4-2) 優先順位が null のデータがある場合、例外を作投げる
		for (ConsolidationItemDto row : consolidationItems) {
			if (StringUtils.isNullOrEmpty(row.getToTableName()) == false && row.getPriority() == null) {
				// 集約優先順位マスタに不整合があります。
				throw new MasterInconsistencyException(MID_E20008, this.messages.get(MID_E20008));
			}
		}
		
		// 4-4-3) 基準日時項目が”Y"のレコードが無い場合、例外を投げる。
		String referenceDateTimeItemName = ConsolidateService.getReferenceDateTimeItemName(consolidationItems);
		if (referenceDateTimeItemName == null) {
			// 集約マスタに集約元リソース種別{0}の基準日時項目が設定されていません。
			throw new MasterInconsistencyException(MID_E20006, this.messages.get(MID_E20006, resourceTypeFrom));
		}
		
		// 4-4-4) 集約マスタの集約元項目名が、[入力パラメータ].[データ].[バインドパラメータ]に含まれていない場合、例外を投げる。
		JSONArray data = this.getDataFromRequestParameters(requestParameters);
		for (ConsolidationItemDto row : consolidationItems) {
			String fromItemName = row.getFromItemName();
			
			for (int i = 0; i < data.length(); i++) {
				JSONObject personData = (JSONObject)data.get(i);
				JSONObject bindParameters = personData.optJSONObject(REQUEST_KEY_BIND_PARAMETERS);

				// 集約元項目名がパラメータに含まれていない場合、例外を投げる。
				if (bindParameters == null || bindParameters.has(fromItemName) == false) {
					// [{0}]件目の個人データ処理中にエラーが発生しました。
					// 集約マスタに設定されている集約元項目名{0}が、バインドパラメータに必要です。
					String message = this.messages.get(MID_E20005, i + 1)
								   + this.messages.get(MID_E20007, fromItemName);
					throw new InputParametersException(MID_E20007, message);
				}
			}
		}
		
		// 基準日時項目に値がセットされていない場合、例外を投げる
		for (int i = 0; i < data.length(); i++) {
			JSONObject bindParameters = data.getJSONObject(i).optJSONObject(REQUEST_KEY_BIND_PARAMETERS);
			String referenceDateTime = bindParameters.optString(referenceDateTimeItemName);
			if (referenceDateTime == null || referenceDateTime.isEmpty()) {
				// [{0}]件目の個人データ処理中にエラーが発生しました。
				// 基準日時項目:[{0}]に値がセットされていません。
				String message = this.messages.get(MID_E20005, i + 1)
							   + this.messages.get(MID_E20009, referenceDateTimeItemName);
				throw new InputParametersException(MID_E20009, message);
			}
			else if (DateTimeUtil.toDate(referenceDateTime) == null) {
				// パラメータ:{0}の値:{1}が正しくありません。
				String message = this.messages.get(MID_E20005, i + 1)
						       + this.messages.get(SystemConstants.MID_E00005, referenceDateTimeItemName, referenceDateTime);
				throw new InputParametersException(SystemConstants.MID_E00005, message);
			}
		}
		
		return consolidationItems;
	}
	
	
	/**
	 * 入力パラメータの[データ]を JSONArray として取得する。
	 * 
	 * @return 入力パラメータの[データ]
	 * @throws InputParametersException 入力パラメータの[データ]の形式が正しくない場合。
	 * @throws Exception 予期しない例外。
	 */
	private JSONArray getDataFromRequestParameters(JSONObject requestParameters) throws InputParametersException, Exception {
		JSONArray array = requestParameters.optJSONArray(REQUEST_KEY_DATA);
		
		if (array == null) {
			array = new JSONArray();
			// 入力チェックを通っている時点で、JSONArray か JSONObject の筈。どちらかに変換できないとおかしい。
			array.put(requestParameters.getJSONObject(REQUEST_KEY_DATA));
		}
		
		return array;
	}
	
	
	/**
	 * リソース種別の大分類を返す。取得できなかった場合は空文字列を返す。
	 */
	private static String getLargeClass(String resourceType) {
		String largeClass = "";

		if (resourceType != null && resourceType.isEmpty() == false) {
			String[] classes = resourceType.split("#");
			if (classes.length == 3) {
				largeClass = classes[0];
			}
		}

		return largeClass;
	}
}
