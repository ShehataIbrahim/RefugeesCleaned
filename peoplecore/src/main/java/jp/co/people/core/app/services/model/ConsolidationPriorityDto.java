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
 *   2018/02/05		00.01								 新規作成
 */
package jp.co.people.core.app.services.model;

/**
 * 集約優先順位マスタのDTO
 */
public class ConsolidationPriorityDto {
	private String dbName;
	private String tableName;
	private String resourceType;
	/**
	 * @return DB名
	 */
	public String getDbName() {
		return dbName;
	}
	
	
	/**
	 * @param dbName DB名
	 */
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	
	
	/**
	 * @return テーブル物理名
	 */
	public String getTableName() {
		return tableName;
	}
	
	
	/**
	 * @param tableName テーブル物理名
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	
	/**
	 * @return リソース種別
	 */
	public String getResourceType() {
		return resourceType;
	}
	
	
	/**
	 * @param resourceType リソース種別
	 */
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
}
