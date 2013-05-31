package com.redseenapp.androidwcfclientsample;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.redseenapp.androidwcfclient_sample.R;
import com.redseenapp.androidwcfclientsample.dataentity.DataCustomer;
import com.redseenapp.androidwcfclientsample.dataservice.NorthwindDataService;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		((Button) findViewById(R.id.btnSelectCustomer)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				ArrayList<DataCustomer> CustomerList = new ArrayList<DataCustomer>();
				
				NorthwindDataService DataService = new NorthwindDataService();

				try {
					DataService.Select(DataCustomer.class, CustomerList);
				} catch (InstantiationException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
				
				if (CustomerList.size() > 0) {
					((TextView) findViewById(R.id.labCustomerName)).setText(CustomerList.get(0).getCompanyName());
				}
				
			}
		});

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
