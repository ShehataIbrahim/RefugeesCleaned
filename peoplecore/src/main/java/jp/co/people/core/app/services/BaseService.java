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

package jp.co.people.core.app.services;

import org.springframework.beans.factory.annotation.Autowired;

import jp.co.people.core.app.datasources.DataSourceManager;
import jp.co.people.core.app.utilities.Messages;

/**
 * <PRE>
 * クラス名：
 *   サービスの抽象クラス
 *
 * 機能説明：
 *   サービス共通の定義
 * </PRE>
 */
public abstract class BaseService {
	/**　データソースの管理インスタンス　*/
	@Autowired
	protected DataSourceManager dataSourceManager;
	
	/** メッセージ */
	@Autowired
	protected Messages messages;
}
