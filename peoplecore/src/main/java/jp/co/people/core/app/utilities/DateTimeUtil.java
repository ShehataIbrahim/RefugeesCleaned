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
package jp.co.people.core.app.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jp.co.people.core.app.common.DateFormatEnum;

/**
 * 日付に関するユーティリティ
 */
public class DateTimeUtil {
	private static final Pattern DATETIME_PATTERN = Pattern.compile("^\\d\\d\\d\\d-\\d?\\d(-\\d?\\d(T\\d?\\d:\\d?\\d(:\\d?\\d((,|\\.)\\d?\\d?\\d)?)?)?)?$");
	
	/**
	 * 文字列が日付を表す場合にtrue、そうでない場合にfalseを返す。
	 * @param dateString 検証したい文字列
	 * @return true:文字列は日付である。false:文字列は日付ではない。
	 */
	public static Boolean isDate(String dateString) {
		return DATETIME_PATTERN.matcher(dateString).find();
	}
	
	/**
	 * ISO 8601の書式の日付文字列をDate型に変換する。
	 * @param dateString ISO 8601書式の文字列
	 * @return 変換できた場合はDate型の値、できなかった場合はnullを返す。
	 */
	public static Date toDate(String dateString) {
		Date date = null;
		
		try {
			Matcher matcher = DATETIME_PATTERN.matcher(dateString);
			
			if (matcher.find()) {
				// ミリ秒まで指定したケース。 
				if (matcher.group(4) != null) {
					if (matcher.group(5).equals(".")) {
						dateString = dateString.replace('.', ',');
					}
					
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss,SSS");
					format.setLenient(false);
					date = format.parse(dateString);
				}
				// 秒まで指定したケース
				else if (matcher.group(3) != null) {
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
					format.setLenient(false);
					date = format.parse(dateString);
				}
				// 分まで指定したケース
				else if (matcher.group(2) != null) {
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
					format.setLenient(false);
					date = format.parse(dateString);
				}
				// 日付まで指定したケース
				else if (matcher.group(1) != null) {
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					format.setLenient(false);
					date = format.parse(dateString);
				}
				// 月まで指定したケース
				else {
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
					format.setLenient(false);
					date = format.parse(dateString);
				}
			}
		} catch (Exception e) {
			// System.out.println("Error:" + e.getMessage());
		}
		
		return date;
	}
	
	/**
	 * 日時型データを指定書式の文字列に変換する。
	 * @param date 日時
	 * @param format 書式（jp.co.people.core.app.common.DateFormatEnum）
	 * @return 指定書式の文字列
	 */
	public static String toString(Date date, DateFormatEnum format){
		SimpleDateFormat targetFormat = new SimpleDateFormat(format.getFormat());
		return targetFormat.format(date);
	}
	
	/**
	 * PostgreSQLでの日付書式の文字列に変換する。
	 * </br>0時0分0秒、0ミリ秒などの場合、時刻部分を省略して文字列を返却する。
	 * @param date 日付
	 * @return 変換した文字列
	 */
	public static String toPgFormat(Date date) {
		SimpleDateFormat targetFormat = new SimpleDateFormat(DateFormatEnum.DATETIME.getFormat());
		return targetFormat.format(date).replaceAll("\\.000$", "").replaceAll("T00:00:00$", "");
	}
}
