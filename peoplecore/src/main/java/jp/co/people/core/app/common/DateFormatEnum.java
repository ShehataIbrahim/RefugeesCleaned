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
 *   2018/02/08		00.01								 新規作成
 */

package jp.co.people.core.app.common;

/**
 * <PRE>
 * クラス名：
 *   日付・日時フォーマットの列挙クラス
 *
 * 機能説明：
 *   YEARMONTH	年月
 *   DATE		年月日
 *   DATETIME	日時
 * </PRE>
 */
public enum DateFormatEnum {
	/** 年月（yyyy-MM） */
	YEARMONTH("yyyy-MM"),
	/** 年月日（yyyy-MM-dd） */
	DATE("yyyy-MM-dd"),
	/** 日時（yyyy-MM-ddTHH:mm:ss.SSS） */
	DATETIME("yyyy-MM-dd'T'HH:mm:ss'.'SSS");
	
	/** 書式文字列 */
	private final String format;
	
	/**
	 * コンストラクタ
	 * @param format 列挙型と対応する書式文字列
	 */
	private DateFormatEnum(final String format){
		this.format = format;
	}
	
	/**
	 * 列挙型と対応する書式文字列を取得する
	 * @return 列挙型と対応する書式文字列
	 */
	public String getFormat(){
		return this.format;
	}
}
