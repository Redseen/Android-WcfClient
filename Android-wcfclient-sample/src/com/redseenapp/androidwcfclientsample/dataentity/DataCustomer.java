package com.redseenapp.androidwcfclientsample.dataentity;

import org.json.JSONException;
import org.json.JSONObject;

import android.wcfclient.WCFDataEntityBase;

public class DataCustomer extends WCFDataEntityBase {

	@Override
	protected String getTableName() { return "Customers"; }

	@Override
	protected String getTypeName() { return "WcfSampleProject.Customers"; }

	@Override
	protected String getUniqueUrl() { return getTableName() + "(" + _CustomerID + ")"; }

	@Override
	public JSONObject getJSONObject() {

		// 基底メソッドをからオブジェクトを受け取る
		JSONObject RetObj = super.getJSONObject();

		try {

			// メンバを設定
			RetObj.put("CustomerID", _CustomerID);
			RetObj.put("CompanyName", _CompanyName);
			RetObj.put("ContactName", _ContactName);
			RetObj.put("ContactTitle", _ContactTitle);
			RetObj.put("Address", _Address);
			RetObj.put("City", _City);
			RetObj.put("Region", _Region);
			RetObj.put("PostalCode", _PostalCode);
			RetObj.put("Country", _Country);
			RetObj.put("Phone", _Phone);
			RetObj.put("Fax", _Fax);

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return RetObj;

	}

	@Override
	protected void setFromJSONObject(JSONObject source) {

		try {

			_CustomerID = source.isNull("CustomerID") ? "" : source.getString("CustomerID");
			_CompanyName = source.isNull("CompanyName") ? "" : source.getString("CompanyName");
			_ContactName = source.isNull("ContactName") ? "" : source.getString("ContactName");
			_ContactTitle = source.isNull("ContactTitle") ? "" : source.getString("ContactTitle");
			_Address = source.isNull("Address") ? "" : source.getString("Address");
			_City = source.isNull("City") ? "" : source.getString("City");
			_Region = source.isNull("Region") ? "" : source.getString("Region");
			_PostalCode = source.isNull("PostalCode") ? "" : source.getString("PostalCode");
			_Country = source.isNull("Country") ? "" : source.getString("Country");
			_Phone = source.isNull("Phone") ? "" : source.getString("Phone");
			_Fax = source.isNull("Fax") ? "" : source.getString("Fax");

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	private String _CustomerID;
	public String getCustomerID() { return this._CustomerID; }
	public void setCustomerID(String value) { this._CustomerID = value; }

	private String _CompanyName;
	public String getCompanyName() { return this._CompanyName; }
	public void setCompanyName(String value) { this._CompanyName = value; }
	
	private String _ContactName;
	public String getContactName() { return this._ContactName; }
	public void setContactName(String value) { this._ContactName = value; }
	
	private String _ContactTitle;
	public String getContactTitle() { return this._ContactTitle; }
	public void setContactTitle(String value) { this._ContactTitle = value; }

	private String _Address;
	public String getAddress() { return this._Address; }
	public void setAddress(String value) { this._Address = value; }

	private String _City;
	public String getCity() { return this._City; }
	public void setCity(String value) { this._City = value; }

	private String _Region;
	public String getRegion() { return this._Region; }
	public void setRegion(String value) { this._Region = value; }

	private String _PostalCode;
	public String getPostalCode() { return this._PostalCode; }
	public void setPostalCode(String value) { this._PostalCode = value; }

	private String _Country;
	public String getCountry() { return this._Country; }
	public void setCountry(String value) { this._Country = value; }
	
	private String _Phone;
	public String getPhone() { return this._Phone; }
	public void setPhone(String value) { this._Phone = value; }
	
	private String _Fax;
	public String getFax() { return this._Fax; }
	public void setFax(String value) { this._Fax = value; }

}
