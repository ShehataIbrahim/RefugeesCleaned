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
 *   2018/01/31		00.01								 新規作成
 */
package jp.co.people.core.app.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;


/**
 * メッセージIDからメッセージ文字列を取得するためのユーティリティクラス
 * 
 * <pre>
 * サンプルコード:
 * {@code
 * Messages messages = new Messages();
 * System.out.println(messages.get("I00001", 1, 1));
 * }
 * </pre>
 */
@Component
public class Messages {
	/*! UTF8のプロパティファイルを読み込むためのControl */ 
    private static ResourceBundle.Control UTF8_ENCODING_CONTROL = new ResourceBundle.Control() {
        /**
         * UTF-8 エンコーディングのプロパティファイルから ResourceBundle オブジェクトを生成します。
         *
         * @throws IllegalAccessException
         * @throws InstantiationException
         * @throws IOException
         */
        @Override
        public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader classLoader, boolean reload)
                throws IllegalAccessException, InstantiationException, IOException {
            String bundleName = toBundleName(baseName, locale);
            String resourceName = toResourceName(bundleName, "properties");

            try (InputStream is = classLoader.getResourceAsStream(resourceName);
                 InputStreamReader isr = new InputStreamReader(is, "UTF-8");
                 BufferedReader reader = new BufferedReader(isr)) {
                return new PropertyResourceBundle(reader);
            }
        }
    };
    
    
    /*! メッセージIDからメッセージ文字列を取得する辞書 */
    private Map<String, String> messageFromID = new HashMap<>();
    

    /**
     *  コンストラクタ
     *  
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws IOException
     */
    public Messages() throws IllegalAccessException, InstantiationException, IOException {
    	ResourceBundle bundle = ResourceBundle.getBundle("messages", UTF8_ENCODING_CONTROL);
    	
    	bundle.keySet();
    	for (String key : bundle.keySet()) {
    		messageFromID.put(key, bundle.getString(key));
    	}
    }
    

    /**
     * メッセージIDを元にメッセージ文字列を返します。
     * 
     * @param messageID メッセージID
     * @param args メッセージに含まれる文字書式に対応する変数
     * @return メッセージ
     * @throws Exception 該当するメッセージIDが見つからなかった場合。
     */
    public String get(String messageID, Object... args) throws Exception {
    	if (this.messageFromID.containsKey(messageID) == false) {
    		throw new Exception(MessageFormat.format("メッセージID[{0}]はありません。", messageID));
    	}
    	
    	String message = this.messageFromID.get(messageID);
    	return MessageFormat.format(message, args);
    }
}
