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
 *   インデックス情報発行結果の列挙クラス
 *
 * 機能説明：
 *   ANCHOR インデックス情報を新規発行
 *   RESOURCE 新規にリソースキーを発行して、既存のアンカーキーに紐づけてインデックス情報を発行
 *   ALREADY インデックス情報が発行済み
 * </PRE>
 */
public enum IssueResultEnum {
	/** インデックス情報を新規発行 */
	ANCHOR,
	/** 新規にリソースキーを発行して、</br>既存のアンカーキーに紐づけてインデックス情報を発行 */
	RESOURCE,
	/** インデックス情報が発行済み */
	ALREADY
}
