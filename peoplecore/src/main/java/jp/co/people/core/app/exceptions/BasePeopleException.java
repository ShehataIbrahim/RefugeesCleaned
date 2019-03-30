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
 *   PeOPLe例外抽象クラス
 *
 * 機能説明：
 *   PeOPLe例外の共通処理・定義を行う
 * </PRE>
 */
public abstract class BasePeopleException extends Exception {
	/**
	 * シリアルバージョンID
	 */
	private static final long serialVersionUID = 2777845816981968848L;
	
	/**
	 * メッセージID
	 */
	private String messageId;
	
	
	/**
	 * メッセージIDとメッセージで初期化するコンストラクタ
	 * 
	 * @param messageId メッセージID
	 * @param message メッセージ
	 */
	public BasePeopleException(String messageId, String message){
		super(message);
		this.messageId = messageId;
	}
	
	
	/**
	 * メッセージIDを取得する
	 * 
	 * @return メッセージID
	 */
	public String getMessageId() {
		return this.messageId;
	}
	
	
	/**
	 * メッセージIDを設定する
	 * 
	 * @param messageId メッセージID
	 */
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
}
