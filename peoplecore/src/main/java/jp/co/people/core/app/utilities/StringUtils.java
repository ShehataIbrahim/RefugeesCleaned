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
 *   2018/02/09		00.01								 新規作成
 */

package jp.co.people.core.app.utilities;

/**
 * <PRE>
 * クラス名：
 *   文字列のユーティリティクラス
 *
 * 機能説明：
 *   文字列の前方・後方の半角全角スペースを除去する
 * </PRE>
 */
public class StringUtils {
	/** 半角スペース */
	//private static final char SPACE_HALF ='\u0020';
	/** 全角スペース */
	private static final char SPACE_WIDE ='\u3000';
	
	/**
	 * 文字列がnullまたは空白文字列のみであるかを判定する。
	 * @param target 判定対象の文字列
	 * @return　文字列がnull、全角スペース・半角スペースのみの場合、trueを返却する。</br>それ以外は、falseを返却する。
	 */
	public static boolean isNullOrEmpty(String target){
		if(target == null || target.isEmpty()){
			return true;
		}
		
		return false;
	}

	/**
	 * 文字列の前方・後方の半角スペース、全角スペースを除去する
	 * @param target 除去対象の文字列
	 * @return 除去した文字列
	 */
	public static String trimWhiteSpace(String target){
		if (target == null || target.length() == 0) {
	        return target;
	    }
		
		String trimedTarget = target.trim();
		int st = 0;
	    int len = trimedTarget.length();
	    char[] val = trimedTarget.toCharArray();
	    while ((st < len) && (val[st] == SPACE_WIDE)) {
	        st++;
	    }
	    while ((st < len) && (val[st] == SPACE_WIDE)) {
	        len--;
	    }
	    return ((st > 0) || (len < trimedTarget.length())) ? trimedTarget.substring(st, len) : trimedTarget;
	}
	
	/**
	 * 指定されたObjectを文字列化し、先頭から length 文字で省略して返す。
	 * @param value
	 * @return 省略した文字列
	 */
	public static String ommit(Object value, int length) {
		String retval = "";
		
		if (value != null) {
			retval = value.toString();
			if (retval.length() > length) {
				retval = retval.substring(0, length) + "...";
			}
		}
		
		return retval;
	}
}
