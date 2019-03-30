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
package jp.co.people.core.app.exceptions;

/**
 * <PRE>
 * クラス名：
 *   マスタ不整合例外クラス
 *
 * 機能説明：
 *   マスタ不整合の例外を作成する
 * </PRE>
 */
public class MasterInconsistencyException extends BasePeopleException {
	/**
	 * シリアルバージョンID
	 */
	private static final long serialVersionUID = -427163511095608000L;

	
	/**
	 * メッセージIDとメッセージで初期化するコンストラクタ
	 * 
	 * @param messageId メッセージID
	 * @param message メッセージ
	 */
	public MasterInconsistencyException(String messageId, String message) {
		super(messageId, message);
	}
}
