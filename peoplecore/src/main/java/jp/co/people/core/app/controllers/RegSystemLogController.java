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

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jp.co.people.core.app.common.SystemConstants;
import jp.co.people.core.app.exceptions.ApiPermissionException;
import jp.co.people.core.app.exceptions.InputParametersException;
import jp.co.people.core.app.services.SystemLogService;

/**
 * システムログ関連のインターフェイスを提供するコントローラー
 */
@RestController
@RequestMapping("/api/v1")
public class RegSystemLogController extends BaseController {
	@Autowired
	private SystemLogService systemLogService;

	// ! ログレベル:情報
	private static final String LOG_LEVEL_INFORMATION = "I";
	// ! ログレベル:警告
	private static final String LOG_LEVEL_WARNING = "W";
	// ! ログレベル:エラー
	private static final String LOG_LEVEL_ERROR = "E";

	// 処理番号の名前とキー名
	private static final String REQUEST_TITLE_PROCESS_NUMBER = "処理番号";
	private static final String REQUEST_KEY_PROCESS_NUMBER = "process_number";
	// ログレベルの名前とキー名
	private static final String REQUEST_TITLE_LOG_LEVEL = "ログレベル";
	private static final String REQUEST_KEY_LOG_LEVEL = "log_level";
	// イベントコードの名前とキー名
	private static final String REQUEST_TITLE_EVENT_CODE = "イベントコード";
	private static final String REQUEST_KEY_EVENT_CODE = "event_code";
	private static final int MAX_LENGTH_EVENT_CODE = 16;
	// メッセージの名前とキー名
	private static final String REQUEST_TITLE_MESSAGE = "メッセージ";
	private static final String REQUEST_KEY_MESSAGE = "message";
	// アプリ定義の機能名の名前とキー名
	private static final String REQUEST_TITLE_FUNCTION_NAME = "アプリ定義の機能名";
	private static final String REQUEST_KEY_FUNCTION_NAME = "function_name";
	private static final int MAX_LENGTH_FUNCTION_NAME = 100;

	// ! 出力データのJSONのキー名:処理番号
	private static final String RESPONSE_KEY_PROCESS_NUMBER = "process_number";

	
	/**
	 * システムログ登録API
	 * 
	 * @param jsonString
	 *            JSON形式のパラメータデータ。
	 * @return JSON形式のレスポンスデータ。
	 * @throws Exception
	 * @throws ApiPermissionException
	 * @throws InputParametersException
	 */
	@RequestMapping(value = "/regsystemlog", method = { RequestMethod.POST })
	public Map<String, Object> regsystemlog(HttpServletResponse response, @RequestBody(required=false) String jsonString)
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
		responseBody.put(RESPONSE_KEY_PROCESS_NUMBER, "");

		try {
			// アクセス制御
			JSONObject requestParameters = this.initialize(jsonString);

			// リクエストパラメータチェック
			this.validateRequestParameters(requestParameters);

			// 処理番号チェック
			String paramProcessNumber = requestParameters.getString(REQUEST_KEY_PROCESS_NUMBER);
			Long outputProcessNumber = 0L;
			if (paramProcessNumber == null || paramProcessNumber.isEmpty()) {
				// 新規採番
				outputProcessNumber = this.systemLogService.getNextProcessNumber();
			} else {
				outputProcessNumber = Long.parseLong(paramProcessNumber);
				// 処理番号の指定がある場合、処理番号が存在するか確認し、存在しない場合は例外を投げる
				if (this.systemLogService.processNumberExists(outputProcessNumber) == false) {
					throw new InputParametersException(SystemConstants.MID_E00005,
							this.messages.get(SystemConstants.MID_E00005, REQUEST_TITLE_PROCESS_NUMBER, paramProcessNumber));
				}
			}

			// システムログの登録元として登録するリソース種別を取得
			String derivedFrom = this.systemLogService.getResourceType(this.getAuthKey(requestParameters));

			// システムログの登録
			this.systemLogService.insert(
					outputProcessNumber,
					requestParameters.getString(REQUEST_KEY_LOG_LEVEL),
					requestParameters.getString(REQUEST_KEY_EVENT_CODE),
					requestParameters.getString(REQUEST_KEY_MESSAGE),
					requestParameters.getString(REQUEST_KEY_FUNCTION_NAME),
					derivedFrom,
					this.getClientIPAddress());
			responseBody.put(RESPONSE_KEY_PROCESS_NUMBER, String.valueOf(outputProcessNumber));

			// レスポンスの返却
			// 正常終了
			statusCode = HttpServletResponse.SC_OK;
			responseBody.put(SystemConstants.RESPONSE_KEY_MESSAGE_ID, "");
			responseBody.put(SystemConstants.RESPONSE_KEY_MESSAGE, "");
			systemMessage = "";
		} catch (InputParametersException e) {
			result = SystemConstants.RESULT_ERROR;
			statusCode = HttpServletResponse.SC_BAD_REQUEST; // 400(Bad Request)
			responseBody.put(SystemConstants.RESPONSE_KEY_MESSAGE_ID, e.getMessageId());
			responseBody.put(SystemConstants.RESPONSE_KEY_MESSAGE, e.getMessage());
			systemMessage = "";
			this.warnLog(e.getMessage());
		} catch (ApiPermissionException e) {
			result = SystemConstants.RESULT_ERROR;
			statusCode = HttpServletResponse.SC_FORBIDDEN; // 403(Forbidden)
			responseBody.put(SystemConstants.RESPONSE_KEY_MESSAGE_ID, e.getMessageId());
			responseBody.put(SystemConstants.RESPONSE_KEY_MESSAGE, e.getMessage());
			systemMessage = "";
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
						"regsystemlog",
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
	 * リクエストパラメータチェック
	 * 
	 * @throws Exception
	 * @throws InputParametersException
	 */
	@Override
	protected void validateRequestParameters(JSONObject requestParameters) throws InputParametersException, Exception {
		// 必須チェック
		String logLevel = this.validateKeyForString(requestParameters, REQUEST_KEY_LOG_LEVEL, REQUEST_TITLE_LOG_LEVEL); // ログレベル
		String eventCode = this.validateKeyForString(requestParameters, REQUEST_KEY_EVENT_CODE, REQUEST_TITLE_EVENT_CODE); // イベントコード
		this.validateKeyForString(requestParameters, REQUEST_KEY_MESSAGE, REQUEST_TITLE_MESSAGE); // メッセージ(キー存在チェックのみで値チェックは無し)
		String functionName = this.validateKeyForString(requestParameters, REQUEST_KEY_FUNCTION_NAME, REQUEST_TITLE_FUNCTION_NAME); // アプリ定義の機能名
		
		// 処理番号の値取得
		String processNumber = null; // 処理番号
		
		if (requestParameters.has(REQUEST_KEY_PROCESS_NUMBER)) {
			if (!(requestParameters.get(REQUEST_KEY_PROCESS_NUMBER) instanceof String)) {
				throw new InputParametersException(SystemConstants.MID_E00012, // パラメータ:[{0}]は文字列で指定してください。
						this.messages.get(SystemConstants.MID_E00012, REQUEST_TITLE_PROCESS_NUMBER));
			}
			
			processNumber = requestParameters.getString(REQUEST_KEY_PROCESS_NUMBER);
		}

		// 値チェック
		// 処理番号が空白でも数値でもない場合
		if (processNumber != null && processNumber.isEmpty() == false) {
			Long longProcessNumber;

			try {
				longProcessNumber = Long.parseLong(processNumber);
			} catch (Exception e) {
				// 数値ではない場合、例外を作成する。
				throw new InputParametersException(SystemConstants.MID_E00005, // パラメータ:{0}の値:{1}が正しくありません。
						this.messages.get(SystemConstants.MID_E00005, REQUEST_TITLE_PROCESS_NUMBER, processNumber));
			}

			// 処理番号が1～9999999999 の範囲に無い場合、例外を作成する。
			if (!(1L <= longProcessNumber && longProcessNumber <= 9999999999L)) {
				// 数値ではない場合、例外を作成する。
				throw new InputParametersException(SystemConstants.MID_E00005, // パラメータ:{0}の値:{1}が正しくありません。
						this.messages.get(SystemConstants.MID_E00005, REQUEST_TITLE_PROCESS_NUMBER, processNumber));
			}
		}

		// ログレベルが"I", "W", "E"のいずれでもない場合、例外を作成する。
		if (logLevel.equals(LOG_LEVEL_INFORMATION) == false && logLevel.equals(LOG_LEVEL_WARNING) == false
				&& logLevel.equals(LOG_LEVEL_ERROR) == false) {
			throw new InputParametersException(SystemConstants.MID_E00005, // パラメータ:{0}の値:{1}が正しくありません。
					this.messages.get(SystemConstants.MID_E00005, REQUEST_TITLE_LOG_LEVEL, logLevel));
		}

		// [入力パラメータ].[イベントコード]が英数字ではない場合、例外を作成する。
		if (SystemLogService.isCode(eventCode) == false) {
			throw new InputParametersException(SystemConstants.MID_E00011, // パラメータ:{0}は英数字、記号で指定してください。
					this.messages.get(SystemConstants.MID_E00011, REQUEST_TITLE_EVENT_CODE));
		}

		// [入力パラメータ].[イベントコード]が16文字を超えている場合、例外を作成する。
		if (eventCode.length() > MAX_LENGTH_EVENT_CODE) {
			throw new InputParametersException(SystemConstants.MID_E00010, // パラメータ:{0}の値が長すぎます。(最大{1}文字に対して{2}文字)
					this.messages.get(SystemConstants.MID_E00010, REQUEST_TITLE_EVENT_CODE, MAX_LENGTH_EVENT_CODE,
							eventCode.length()));
		}

		// [入力パラメータ].[アプリ定義の機能名]が英数字ではない場合、例外を作成する。
		if (SystemLogService.isCode(functionName) == false) {
			throw new InputParametersException(SystemConstants.MID_E00011, // パラメータ:{0}は英数字、記号で指定してください。
					this.messages.get(SystemConstants.MID_E00011, REQUEST_TITLE_FUNCTION_NAME));
		}

		// [入力パラメータ].[アプリ定義の機能名]が16文字を超えている場合、例外を作成する。
		if (functionName.length() > MAX_LENGTH_FUNCTION_NAME) {
			throw new InputParametersException(SystemConstants.MID_E00010, // パラメータ:{0}の値が長すぎます。(最大{1}文字に対して{2}文字)
					this.messages.get(SystemConstants.MID_E00010, REQUEST_TITLE_FUNCTION_NAME, MAX_LENGTH_FUNCTION_NAME,
							functionName.length()));
		}
	}
}
