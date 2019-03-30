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
 *   2018/02/01		00.01								 新規作成
 */

package jp.co.people.core.app.common;

/**
 * <PRE>
 * クラス名：
 *   PeOPLe基盤の定数
 *
 * 機能説明：
 *   PeOPLe基盤の定数を定義する
 * </PRE>
 */
public class SystemConstants {
	
	/** COREデータベースの接続情報のキー情報 */
	public static final String DATASOURCE_CORE_KEY = "core";
	
	/** 健康記録データベースの接続情報のキー情報 */
	public static final String DATASOURCE_HEALTH_KEY = "health";
	
	/** 個人情報データベースの接続情報のキー情報 */
	public static final String DATASOURCE_PERSONAL_KEY = "personal";
	
	/** アンカーキー発行のキー情報 */
	public static final String MD5_ANCHOR_KEY = "ANCHOR_KEY";
	
	/** リソースキー発行のキー情報 */
	public static final String MD5_RESOURCE_KEY = "RESOURCE_KEY";
	
	/** APIリクエストのバインド変数情報の格納項目の項目名 */
	public static final String REQUEST_KEY_BIND_PARAMETAR = "bind_parameters";
	/** APIリクエストのバインド変数情報の格納項目の項目名 */
	public static final String REQUEST_KEY_BIND_PARAMETAR_TITLE = "バインドパラメータ";
	
	/** HTTPリクエストヘッダーのIPアドレスを格納するカスタムプロパティ */
	public static final String REQUEST_HEADER_IP_ADRRESS_NAME = "X-CALLED-IF-ADDR";
	
	/** 認証キーマスタの認証キー物理名 */
	public static final String AUTH_KEYS_AUTH_KEY = "auth_key";
	
	/** インデックス情報のアンカーキー物理名 */
	public static final String ANCHOR_RESOURCES_ANCHOR_KEY = "anchor_key";
	/** インデックス情報のリソース種別物理名 */
	public static final String ANCHOR_RESOURCES_TYPE = "resource_type";
	/** インデックス情報のリソースキー物理名 */
	public static final String ANCHOR_RESOURCES_RESOURCE_KEY = "resource_key";
	
	/** 出力データのJSONのキー名：メッセージID */
	public static final String RESPONSE_KEY_MESSAGE_ID = "message_id";
	/** 出力データのJSONのキー名：メッセージ */
	public static final String RESPONSE_KEY_MESSAGE = "message";
	
	/** 終了結果:正常終了 */
	public static final String RESULT_NORMAL = "N";
	/** 終了結果:警告終了 */
	public static final String RESULT_WARN = "W";
	/** 終了結果:エラー終了 */
	public static final String RESULT_ERROR = "E";
	
	/** メッセージID：I00001 */
	public static final String MID_I00001 = "I00001";
	/** メッセージID：I00001 */
	public static final String MID_I00002 = "I00002";
	
	/** メッセージID：E00001 */
	public static final String MID_E00001 = "E00001";
	/** メッセージID：E00002 */
	public static final String MID_E00002 = "E00002";
	/** メッセージID：E00003 */
	public static final String MID_E00003 = "E00003";
	/** メッセージID：E00004 */
	public static final String MID_E00004 = "E00004";
	/** メッセージID：E00005 */
	public static final String MID_E00005 = "E00005";
	/** メッセージID:E00006 */
	public static final String MID_E00006 = "E00006";
	/** メッセージID:E00007 */
	public static final String MID_E00007 = "E00007";
	/** メッセージID：E00008 */
	public static final String MID_E00008 = "E00008";
	/** メッセージID：E00009 */
	public static final String MID_E00009 = "E00009";
	/** メッセージID：E00010 */
	public static final String MID_E00010 = "E00010";
	/** メッセージID：E00011 */
	public static final String MID_E00011 = "E00011";
	/** メッセージID：E00012 */
	public static final String MID_E00012 = "E00012";
	/** メッセージID：E00013 */
	public static final String MID_E00013 = "E00013";
	/** メッセージID：E00014 */
	public static final String MID_E00014 = "E00014";
	/** メッセージID：E00015 */
	public static final String MID_E00015 = "E00015";
	/** メッセージID：E00016 */
	public static final String MID_E00016 = "E00016";
	/** メッセージID：E00017 */
	public static final String MID_E00017 = "E00017";
	/** メッセージID：E00018 */
	public static final String MID_E00018 = "E00018";
	/** メッセージID：E00019 */
	public static final String MID_E00019 = "E00019";
	
	/** メッセージID：E10001 */
	public static final String MID_E10001 = "E10001";
}