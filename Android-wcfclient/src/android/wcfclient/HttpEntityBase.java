package android.wcfclient;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.AllClientPNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

/*
*
*	Httpに関わるクラスの基底クラス
*
*/
public abstract class HttpEntityBase {

	// Basic認証のユーザー名
	private String _UserName = null;
	
	// Basic認証のパスワード
	private String _Password = null;
	
	// 接続タイムアウト
	private int _ConnectionTimeout = 30000;

	// ソケットタイムアウト
	private int _SocketTimeout = 50000;

	// Http要求を出して応答を取得、返す
	protected HttpResult AccessHttp(HttpUriRequest request) {

		// 結果
		final HttpResult Result = new HttpResult();

		// タイムアウト設定
		HttpParams httpParms = new BasicHttpParams();
		httpParms.setIntParameter(AllClientPNames.CONNECTION_TIMEOUT, _ConnectionTimeout);
		httpParms.setIntParameter(AllClientPNames.SO_TIMEOUT, _SocketTimeout);

		// 接続生成
		DefaultHttpClient HttpClient = new DefaultHttpClient(httpParms);

		// Basic認証のユーザー名が設定されている場合は認証情報を設定する
		if (_UserName != null) {
			HttpClient.getCredentialsProvider().setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(_UserName,_Password));
		}
		
		String StringContents = "";
		try {
			StringContents = HttpClient.execute(request, new ResponseHandler<String>() {
				public String handleResponse(HttpResponse httpresponse) throws ClientProtocolException, IOException {

					// 実行してステータスコードを取得する
					int StatusCode = httpresponse.getStatusLine().getStatusCode();

					// 戻り値にステータスコードを設定
					Result.set_StatusCode(StatusCode);

					// ステータスコードを確認
					switch (StatusCode) {
			        case HttpStatus.SC_OK:
			        case HttpStatus.SC_CREATED:
			        case HttpStatus.SC_NO_CONTENT:
						// 処理成功
						Result.set_Result(true);
			        	Result.set_ErrorMessage("");
			        	if (httpresponse.getEntity() == null) return "";
			            return EntityUtils.toString(httpresponse.getEntity(), "UTF-8");

			        case HttpStatus.SC_NOT_FOUND:
			        	// 404
						Result.set_Result(false);
			        	Result.set_ErrorMessage("ページが見つかりません。(" + String.valueOf(StatusCode) + ")");
			        	return "";

			        default:
			        	// その他のステータス
						Result.set_Result(false);
			        	Result.set_ErrorMessage("Httpエラーが発生しました。(" + String.valueOf(StatusCode) + ")");
			        	return "";
			        }
				}
			});

			// 内容を設定
			Result.set_Contents(StringContents);

		} catch (ClientProtocolException e) {
			Result.set_Result(false);
			Result.set_Exception(e);
        	Result.set_ErrorMessage("Httpエラーが発生しました。(ClientProtocolException)");
		} catch (IOException e) {
			Result.set_Result(false);
			Result.set_Exception(e);
        	Result.set_ErrorMessage("Httpエラーが発生しました。(IOException)");
		} finally  {
			// 接続シャットダウン
			HttpClient.getConnectionManager().shutdown();
		}

		// 応答を返す
		return Result;

	}

}
