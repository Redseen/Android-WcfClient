package android.wcfclient;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

/*
*
*	WCFデータサービスを使用するクラス
*
*/
public abstract class WCFDataServiceBase extends HttpEntityBase {

	// エンコード
	private static String _EncodeName = "UTF8";

	// サービスURL
	protected abstract String getTargetUrl();

	// データ取得
	public <T extends WCFDataEntityBase> HttpResult Select(Class<?> classInfo, ArrayList<T> RetList) throws InstantiationException, IllegalAccessException, ClassNotFoundException {

		return Select(classInfo, RetList, null);

	}

	// データ取得（条件付き）
	public <T extends WCFDataEntityBase> HttpResult Select(Class<?> classInfo, ArrayList<T> RetList, String condition) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		HttpResult Result = null;

		// 返すリスト
		if (RetList == null) RetList = new ArrayList<T>();

		// インスタンス生成
		T seed = (T) Class.forName(classInfo.getName()).newInstance();
		
		// URL
		String mUrl = getTargetUrl() + "/" + seed.getTableName();

		// 条件がある場合は付加
		if (condition != null && condition != "")
			mUrl = mUrl + "?" + condition;

		// スペースを置き換える
		mUrl = mUrl.replaceAll(" ", "%20");

		// GETメソッド生成
		HttpGet getMethod = new HttpGet(mUrl);

		// ヘッダー追加
		getMethod = (HttpGet) AddHeader(getMethod);

		// 結果を取得する
		Result = AccessHttp(getMethod);

		// エラーがあった場合は処理終了
		if (Result.get_Result() == false) return Result;

		try {

			// Entityを文字列に変換
	        String json = Result.get_Contents();

//			Log.i("SelectJSONObject", json);

	        // 文字列を元にJSONオブジェクト生成
	    	JSONObject rootObject = new JSONObject(json);
	    	// ルート要素からデータ配列を取得
	    	JSONArray DataArray = rootObject.getJSONArray("d");
	    	// 配列数を取得
	        int count = DataArray.length();

	        // 配列を回してリストに追加
	        for (int i=0; i<count; i++){

	        	// アイテムを生成
				T NewItem = (T) Class.forName(classInfo.getName()).newInstance();

	        	// JSONオブジェクトから値を設定
	        	NewItem.setFromJSONObject(DataArray.getJSONObject(i));

	        	// リストに追加
	        	RetList.add(NewItem);

	        }

		} catch (JSONException e) {
			// エラー発生
			RetList.clear();
			Result.set_Result(false);
			Result.set_Exception(e);
        	Result.set_ErrorMessage("サーバーからのデータの変換でエラーが発生しました。(JSONException)");

		} catch (ParseException e) {
			RetList.clear();
			Result.set_Result(false);
			Result.set_Exception(e);
        	Result.set_ErrorMessage("サーバーからのデータの変換でエラーが発生しました。(ParseException)");

		}

		return Result;

	}

	// 追加
	public <T extends WCFDataEntityBase> HttpResult Add(T restData) {
		HttpResult Result = null;

		// POSTメソッド生成
		HttpPost postMethod = new HttpPost(getTargetUrl() + "/" + restData.getTableName());

		// ヘッダー追加
		postMethod = (HttpPost) AddHeader(postMethod);

		Log.i("AddJSONObject", restData.getJSONObject().toString());

		try {
			// 追加データを設定
			postMethod.setEntity(new StringEntity(restData.getJSONObject().toString(), _EncodeName));
		} catch (UnsupportedEncodingException e) {
			// エラー発生
			Result = new HttpResult();
			Result.set_Result(false);
			Result.set_ErrorMessage("送信データの準備に失敗しました。(UnsupportedEncodingException)");
			Result.set_Exception(e);
			return Result;
		}

		// 送信
		Result = AccessHttp(postMethod);

		// 返す
		return Result;

	}

	// 更新
	public <T extends WCFDataEntityBase> HttpResult Update(T restData) {
		HttpResult Result = null;

		// POSTメソッド
		HttpPost postMethod = new HttpPost(getTargetUrl() + "/" + restData.getUniqueUrl());

		// ヘッダー追加
		postMethod = (HttpPost) AddHeader(postMethod);

		// MERGEになるようにヘッダー追加（WCF側でInterceptor使用対応）
		postMethod.setHeader("X-Http-Method", "MERGE");

		Log.i("UpdateJSONObject", restData.getJSONObject().toString());

		try {
			// 更新データを設定
			postMethod.setEntity(new StringEntity(restData.getJSONObject().toString(), _EncodeName));
		} catch (UnsupportedEncodingException e) {
			// エラー発生
			Result = new HttpResult();
			Result.set_Result(false);
			Result.set_ErrorMessage("送信データの準備に失敗しました。(UnsupportedEncodingException)");
			Result.set_Exception(e);
			return Result;
		}

		// 送信
		Result = AccessHttp(postMethod);

		// 返す
		return Result;

	}

	// 削除
	public <T extends WCFDataEntityBase> HttpResult Delete(T restData) {
		HttpResult Result = null;

		// DELETEメソッド
		HttpDelete deleteMethod = new HttpDelete(getTargetUrl() + "/" + restData.getUniqueUrl());

		// ヘッダー追加
		deleteMethod = (HttpDelete) AddHeader(deleteMethod);

		// 送信
		Result = AccessHttp(deleteMethod);

		// 返す
		return Result;

	}

	// ヘッダー追加
	protected HttpUriRequest AddHeader(HttpUriRequest request) { 
	
		// ヘッダーを追加する
    	// Jsonを扱う
    	request.setHeader("Accept", "application/json");
		request.setHeader("Content-type", "application/json");

		return request; 

	}

}
