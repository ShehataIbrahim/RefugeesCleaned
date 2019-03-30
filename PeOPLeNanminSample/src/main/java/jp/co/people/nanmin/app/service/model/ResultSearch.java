package jp.co.people.nanmin.app.service.model;

import jp.co.people.nanmin.app.service.model.form.SampleForm;
import lombok.Data;

/**
 * 取得ボタン実行時の結果
 * 取得する項目はフォームと同じであるため、継承する
 */
@Data
public class ResultSearch extends SampleForm {
	
	private String msg = "";
}
