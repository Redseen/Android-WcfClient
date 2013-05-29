package com.redseenapp.androidwcfclientsample.dataservice;

import android.wcfclient.WCFDataServiceBase;

public class NorthwindDataService extends WCFDataServiceBase {

	@Override
	protected String getTargetUrl() { return "http://192.168.200.138/WcfSampleProject/Northwind.svc"; }

}
