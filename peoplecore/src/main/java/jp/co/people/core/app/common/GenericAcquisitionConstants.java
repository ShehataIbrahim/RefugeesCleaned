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
 *   汎用データ取得の定数
 *
 * 機能説明：
 *   汎用データ取得の定数を定義する
 * </PRE>
 */
public class GenericAcquisitionConstants {
	/** 個人情報リソースキー取得のSQL名 */
	public static final String GET_PERSONAL_INFO_RESOURCE_KEY_SQL_NAME = "getpersonalresourcekey";
	/** 個人情報取得1のSQL名 */
	public static final String GET_PERSONAL_INFO_01_SQL_NAME = "getlatestpersonalinfo01";
	/** 個人情報取得2のSQL名 */
	public static final String GET_PERSONAL_INFO_02_SQL_NAME = "getlatestpersonalinfo02";
	/** 個人情報取得3のSQL名 */
	public static final String GET_PERSONAL_INFO_03_SQL_NAME = "getlatestpersonalinfo03";
	/** 個人情報取得4のSQL名 */
	public static final String GET_PERSONAL_INFO_04_SQL_NAME = "getlatestpersonalinfo04";
	/** 個人情報取得5のSQL名 */
	public static final String GET_PERSONAL_INFO_05_SQL_NAME = "getlatestpersonalinfo05";
	/** 個人情報取得6のSQL名 */
	public static final String GET_PERSONAL_INFO_06_SQL_NAME = "getlatestpersonalinfo06";
	
	/** 個人情報の患者IDのバインド変数名 */
	public final static String PERSONAL_INFO_PATIENT_ID = "patient_id";
	/** 個人情報の生年月日のバインド変数名 */
	public final static String PERSONAL_INFO_BIRTH_DAY = "birth_day";
	/** 個人情報の性別のバインド変数名 */
	public final static String PERSONAL_INFO_SEX = "sex";
	/** 個人情報の氏名のバインド変数名 */
	public final static String PERSONAL_INFO_FULL_NAME = "full_name";
	/** 個人情報の保険者番号のバインド変数名 */
	public final static String PERSONAL_INFO_INSURER = "insurer";
	/** 個人情報の被保険者記号のバインド変数名 */
	public final static String PERSONAL_INFO_INSURED_MARK = "insured_mark";
	/** 個人情報の被保険者番号のバインド変数名 */
	public final static String PERSONAL_INFO_INSURED_NUMBER = "insured_number";
	
	/** 個人情報の患者ID */
	public final static String PERSONAL_INFO_PATIENT_ID_TITLE = "患者ID";
	/** 個人情報の生年月日 */
	public final static String PERSONAL_INFO_BIRTH_DAY_TITLE = "生年月日";
	/** 個人情報の性別 */
	public final static String PERSONAL_INFO_SEX_TITLE = "性別";
	/** 個人情報の氏名 */
	public final static String PERSONAL_INFO_FULL_NAME_TITLE = "氏名";
	/** 個人情報の保険者番号 */
	public final static String PERSONAL_INFO_INSURER_TITLE = "保険者番号";
	/** 個人情報の被保険者記号 */
	public final static String PERSONAL_INFO_INSURED_MARK_TITLE = "被保険者記号";
	/** 個人情報の被保険者番号 */
	public final static String PERSONAL_INFO_INSURED_NUMBER_TITLE = "被保険者番号";
	
	/** メッセージID：E20010 */
	public static final String MID_E20010 = "E20010";
}
