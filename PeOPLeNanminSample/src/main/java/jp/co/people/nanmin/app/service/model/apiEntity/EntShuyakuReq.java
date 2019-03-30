package jp.co.people.nanmin.app.service.model.apiEntity;

import java.util.List;

import lombok.Data;

/**
 * 集約処理APIのリクエスト
 */
@Data
public class EntShuyakuReq {

	String auth_key = "";						// 認証キー
	String from_type = "";						// 集約元リソース種別(ファイルレイアウト)
	String to_type = "";						// 集約先リソース種別
	List<EntShuyakuReqData> data = null;	// 個人一人分のデータのリスト

}
