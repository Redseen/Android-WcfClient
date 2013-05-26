package android.wcfclient;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

/*
*
*	WCFデータサービスとのやり取りで使用するデータの基底クラス
*
*/
public abstract class WCFDataEntityBase {

	// テーブル名
	protected abstract String getTableName();
	// タイプ名
	protected abstract String getTypeName();
	// このデータがユニークになるRESTURL
	protected abstract String getUniqueUrl();
	// 新しいインスタンスを生成する
	public abstract WCFDataEntityBase CreateInstance();

	// RESTに送信するJSONデータの取得
	public JSONObject getJSONObject() {

		// 新しいオブジェクトを生成
		JSONObject RetJsonObj = new JSONObject();

		try {

			// Uri、Typeを生成
			JSONObject jsonMeta = new JSONObject();
			jsonMeta.put("Uri", "/" + getTableName() + "/");
			jsonMeta.put("Type", getTypeName());

			// Uri、Typeを設定
			RetJsonObj.put("__metadata", jsonMeta);

		} catch (JSONException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		// 返す
		return RetJsonObj;

	}

	// JSONObjectからの値の設定
	protected abstract void setFromJSONObject(JSONObject source);

	// JSON日付をDate型に変換する
	public static Date JsonDateToDate(String jsonDate) {

		// 日付が無ければnullを返す
		if (jsonDate == null) return null;

		// カッコの位置を取得
		int idx1 = jsonDate.indexOf("(");
		int idx2 = jsonDate.indexOf(")");

		// どちらかのカッコが見つからない場合はnullを返す
		if (idx1 == -1 || idx2 == -1) return null;

		// カッコの間を抜き出す
		String s = jsonDate.substring(idx1+1, idx2);

		// longに変換
		long l = Long.valueOf(s);

//		return new Date(l);

		Date d = new Date(l);

		// 日付を返す（時差を考慮）
		return new Date(d.getTime() + (d.getTimezoneOffset() * 60000));

	}

	// 日付のフォーマッター
	private static SimpleDateFormat _DateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");

	// Date型をJSON日付に変換する
	public static String DateToJsonDate(Date sourceDate) {

		// 日付が無ければnullを返す
		if (sourceDate == null) return null;

		// 日付を返す
		return _DateFormat.format(sourceDate.getTime());

	}

}
