package android.wcfclient;

public class HttpResult {

	private boolean _Result = true;
	public boolean get_Result() { return _Result; }
	public void set_Result(boolean value) { this._Result = value; }

	private int _StatusCode = 0;
	public int get_StatusCode() { return _StatusCode; }
	public void set_StatusCode(int value) { this._StatusCode = value; }

	private String _Contents = "";
	public String get_Contents() { return _Contents; }
	public void set_Contents(String value) { this._Contents = value; }

	private String _ErrorMessage = "";
	public String get_ErrorMessage() { return _ErrorMessage; }
	public void set_ErrorMessage(String value) { this._ErrorMessage = value; }

	private Exception _Exception = null;
	public Exception get_Exception() { return _Exception; }
	public void set_Exception(Exception value) { this._Exception = value; }

}
