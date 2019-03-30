package com.refugees.portal.demo;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.Translate.TranslateOption;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

public class Translator {
	public static String translate(String sourceLang,String targetLang,String text) {
		if(sourceLang==null || targetLang==null || sourceLang.equalsIgnoreCase(targetLang))
			return text;
		Translate translate = createTranslateService();
		TranslateOption srcLang = TranslateOption.sourceLanguage(sourceLang);
		TranslateOption tgtLang = TranslateOption.targetLanguage(targetLang);
		 Translation translation = translate.translate(text, srcLang, tgtLang);
		 return translation.getTranslatedText();
	}
	public static String translateFromEnglish(String targetLang,String text)
	{
		return translate("en", targetLang, text);
	}
	public static Translate createTranslateService() {
		return TranslateOptions.newBuilder().build().getService();
	}
}
