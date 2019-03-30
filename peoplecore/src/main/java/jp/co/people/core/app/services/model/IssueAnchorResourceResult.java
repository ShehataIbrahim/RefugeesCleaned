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

package jp.co.people.core.app.services.model;

import java.util.UUID;

import jp.co.people.core.app.common.IssueResultEnum;

/**
 * <PRE>
 * クラス名：
 *   インデックス情報発行出力データクラス
 *
 * 機能説明：
 *   インデックス情報発行の結果を格納する
 * </PRE>
 */
public class IssueAnchorResourceResult {
	/** インデックス情報発行の結果 */
	private IssueResultEnum result;
	
	/** インデックス情報のアンカーキー */
	private UUID anchorKey;
	
	/** インデックス情報のリソース種別 */
	private String type;
	
	/** インデックス情報のリソースキー */
	private UUID resourceKey;
	
	
	/**
	 * インデックス情報発行出力データのコンストラクタ
	 * 
	 * @param result インデックス情報発行の結果
	 * @param anchorKey インデックス情報のアンカーキー
	 * @param type インデックス情報のリソース種別
	 * @param resourceKey インデックス情報のリソースキー
	 */
	public IssueAnchorResourceResult(IssueResultEnum result, UUID anchorKey, String type, UUID resourceKey){
		this.result = result;
		this.anchorKey = anchorKey;
		this.type = type;
		this.resourceKey = resourceKey;
	}
	
	
	/**
	 * インデックス情報発行の結果を取得する
	 * @return result
	 */
	public IssueResultEnum getResult() {
		return this.result;
	}
	

	/**
	 * インデックス情報のアンカーキーを取得する
	 * @return anchor_key
	 */
	public UUID getAnchorKey() {
		return this.anchorKey;
	}
	

	/**
	 * インデックス情報のリソース種別を取得する
	 * @return type
	 */
	public String getType() {
		return this.type;
	}
	

	/**
	 * インデックス情報のリソースキーを取得する
	 * @return resource_key
	 */
	public UUID getResourceKey() {
		return this.resourceKey;
	}
}
