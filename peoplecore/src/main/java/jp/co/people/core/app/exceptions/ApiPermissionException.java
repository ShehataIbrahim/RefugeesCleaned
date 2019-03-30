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

package jp.co.people.core.app.exceptions;

/**
 * <PRE>
 * クラス名：
 *   API権限例外クラス
 *
 * 機能説明：
 *   APIの権限の例外を作成する
 * </PRE>
 */
public class ApiPermissionException extends BasePeopleException {

	/**
	 * シリアルバージョンID
	 */
	private static final long serialVersionUID = -6719239763724190800L;

	/**
	 * メッセージIDとメッセージで初期化するコンストラクタ
	 * 
	 * @param messageId メッセージID
	 * @param message メッセージ
	 */
	public ApiPermissionException(String messageId, String message) {
		super(messageId, message);
	}

}
