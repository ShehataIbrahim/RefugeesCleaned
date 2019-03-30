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

package jp.co.people.core.app.utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * <PRE>
 * クラス名：
 *   JSONのユーティリティクラス
 *
 * 機能説明：
 *   JSONオブジェクトをマップデータへ変換する
 * </PRE>
 */
public class JsonUtils {
	
	/**
	 * JSONObject型をMap<String, Object>型に変換する
	 * 
	 * @param inputJson　JSONObject型のデータ
	 * @return　Map<String, Object>型のデータ
	 * @throws JSONException　JSONObject型のデータの不整合等
	 */
	public static Map<String, Object> convertToMap(JSONObject inputJson) throws JSONException{
		Map<String, Object> map = new HashMap<String, Object>();

		// イテレータでキーと値を取得
		@SuppressWarnings("unchecked")
		Iterator<String> keys = inputJson.keys();
		while (keys.hasNext()) {
			String key = keys.next();
			if (inputJson.get(key).getClass() == JSONArray.class) {
				List<Map<String, Object>> map_array = new ArrayList<Map<String, Object>>();
				JSONArray json_array = inputJson.getJSONArray(key);
				for(int index = 0; index < json_array.length(); index++){
					JSONObject json_object = json_array.getJSONObject(index);
					Map<String, Object> map_object = convertToMap(json_object);
					map_array.add(map_object);
				}
				map.put(key, map_array);
			} else if (inputJson.get(key).getClass() == JSONObject.class) {
				Map<String, Object> map_object = convertToMap(inputJson.getJSONObject(key));
				map.put(key, map_object);
			} else {
				map.put(key, inputJson.get(key));
			}
		}

		return map;
	}
}
