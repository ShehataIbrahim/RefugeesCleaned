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
 * 集約マスタのDTO
 */
public class ConsolidationItemDto {
    private String fromResourceType;
    private String fromItemName;
    private String fromIsReferenceDatetime;
    private String toDbName;
    private String toResourceType;
    private String toTableName;
    private String toItemClassCode;
    private String toItemName;
    private Integer priority;
    
    
    /**
     * @return 集約元リソース種別を返す
     */
    public String getFromResourceType() {
        return this.fromResourceType;
    }
    
    
    /**
     * @param fromResourceType 集約元リソース種別
     */
    public void setFromResourceType(String fromResourceType) {
        this.fromResourceType = fromResourceType;
    }
    
    
    /**
     * @return 集約元項目名を返す。
     */
    public String getFromItemName() {
        return this.fromItemName;
    }
    
    
    /**
     * @param fromItemName 集約元項目名
     */
    public void setFromItemName(String fromItemName) {
        this.fromItemName = fromItemName;
    }
    
    
    /**
     * @return 基準日時項目の場合にtrueを返す。
     */
    public String getFromIsReferenceDatetime() {
        return this.fromIsReferenceDatetime;
    }
    
    
/**
     * @return 基準日時項目の場合にtrueを返す。
     */
    public Boolean isReferenceDatetime() {
        return this.fromIsReferenceDatetime != null && this.fromIsReferenceDatetime.equals("Y");
    }
    
    
    /**
     * @param fromIsReferenceDatetime 基準日時項目
     */
    public void setFromIsReferenceDatetime(String fromIsReferenceDatetime) {
        this.fromIsReferenceDatetime = fromIsReferenceDatetime;
    }
    
    
    /**
     * @return 集約先DB名
     */
    public String getToDbName() {
        return this.toDbName;
    }
    
    
    /**
     * @param toDbName  集約先DB名
     */
    public void setToDbName(String toDbName) {
        this.toDbName = toDbName;
    }
    
    
    /**
     * @return 集約先リソース種別
     */
    public String getToResourceType() {
        return this.toResourceType;
    }
    
    
    /**
     * @param toResourceType  集約先リソース種別
     */
    public void setToResourceType(String toResourceType) {
        this.toResourceType = toResourceType;
    }
    
    
    /**
     * @return 集約先テーブル名
     */
    public String getToTableName() {
        return this.toTableName;
    }
    
    
    /**
     * @param toTableName  集約先テーブル名
     */
    public void setToTableName(String toTableName) {
        this.toTableName = toTableName;
    }
    
    
    /**
     * @return 集約先項目分類コード
     */
    public String getToItemClassCode() {
        return this.toItemClassCode;
    }
    
    
    /**
     * @param toItemClassCode  集約先項目分類コード
     */
    public void setToItemClassCode(String toItemClassCode) {
        this.toItemClassCode = toItemClassCode;
    }
    
    
    /**
     * @return 集約先項目名
     */
    public String getToItemName() {
        return this.toItemName;
    }
    
    
    /**
     * @param toItemName  集約先項目名
     */
    public void setToItemName(String toItemName) {
        this.toItemName = toItemName;
    }
    
    
    /**
     * @return 優先順位
     */
    public Integer getPriority() {
        return this.priority;
    }
    
    
    /**
     * @param priority 優先順位
     */
    public void setPriority(Integer priority) {
        this.priority = priority;
    }
}
