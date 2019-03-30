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

package jp.co.people.core.app.services;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import jp.co.people.core.app.common.SystemConstants;
import jp.co.people.core.app.exceptions.ApiPermissionException;

/**
 * <PRE>
 * クラス名：
 *   アクセス制御クラス
 *
 * 機能説明：
 *   アクセス制御を行う
 * </PRE>
 */
@Service
public class AccessControllService extends BaseService {
	
	/** 権限チェックSQL */
	private static final String VALIDATE_AUTH_KEY_SQL = "select main.ip_addresses from auth_keys main where main.auth_key = :auth_key;";
	
	/**
	 * 認証キーの権限を検証する 権限がない場合、権限エラーとする。
	 * 
	 * @param authKey
	 *            認証キー
	 * @param ipAddress
	 *            リクエスト元のIPアドレス
	 * 
	 * @throws ApiPermissionException
	 *             APIの実行権限がない
	 * @throws Exception
	 *             予期しない例外
	 */
	public void validate(UUID authKey, String ipAddress)
			throws ApiPermissionException, Exception {


		List<Map<String, Object>> results = this.validateAuthKey(authKey);

		this.validateIpAddress(results, ipAddress);
	}

	/**
	 * 認証キーが認証キーマスタに存在するかを検証し、認証キーの利用可能なIPアドレスリストを返す。
	 * </br>存在しない場合、権限エラーとする。
	 * 
	 * @param authKey
	 *            認証キー
	 * @return 認証キーの利用可能なIPアドレスリスト
	 * @throws ApiPermissionException
	 *             APIの実行権限がない
	 * @throws Exception
	 *             予期しない例外
	 */
	private List<Map<String, Object>> validateAuthKey(UUID authKey)
			throws ApiPermissionException, Exception {
		// 接続情報の取得
		NamedParameterJdbcTemplate template = this.dataSourceManager.getNamedParameterJdbcTemplate(SystemConstants.DATASOURCE_CORE_KEY);

		// バインド変数の設定
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("auth_key", authKey);

		// SQLの実行
		List<Map<String, Object>> result = template.queryForList(VALIDATE_AUTH_KEY_SQL, params);

		if (result.size() == 0) {
			// 取得件数0件で権限エラー
			throw new ApiPermissionException(SystemConstants.MID_E00001, this.messages.get(SystemConstants.MID_E00001));
		}

		return result;
	}

	/**
	 * 認証キーマスタのIPアドレスリストにリクエスト元のIPアドレスが存在するかを検証する。
	 * </br> 存在しない場合、権限エラーとする。
	 * 
	 * @param ipAddressList
	 *            特定の認証キーで利用可能なIPアドレスリスト
	 * @param ipAddress
	 *            リクエスト元のIPアドレス
	 * @throws ApiPermissionException
	 *             APIの実行権限がない
	 * @throws Exception
	 *             予期しない例外
	 */
	private void validateIpAddress(List<Map<String, Object>> ipAddressList, String ipAddress)
			throws ApiPermissionException, Exception {
		for (int index = 0; index < ipAddressList.size(); index++) {
			String ipAddresses = (String) ipAddressList.get(index).get("ip_addresses");
			List<String> ipAddressArrayList = Arrays.asList(ipAddresses.split(";"));

			// リクエスト元のIPアドレスが存在するかを検証
			if (ipAddressArrayList.contains(ipAddress)) {
				// 存在しているので、許可
				return;
			}
		}

		// IPアドレスが許可されていなければ権限エラー
		throw new ApiPermissionException(SystemConstants.MID_E00002, this.messages.get(SystemConstants.MID_E00002, ipAddress));
	}
}
