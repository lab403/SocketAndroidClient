package com.act.waa;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Scan_IP extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		//-----------------------用於修正4.0.3版無法使用的問題--------------------------
		/*
		 StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()     
         .detectDiskReads()     
         .detectDiskWrites()     
         .detectNetwork()   // or .detectAll() for all detectable problems     
         .penaltyLog()     
         .build());     
  StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()     
         .detectLeakedSqlLiteObjects()     
         .detectLeakedClosableObjects()     
         .penaltyLog()     
         .penaltyDeath()     
         .build());
  		*/
         //----------------------------------------------------------------------------------------
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scan_ip);// IP設定畫面
		EditText serverIP = (EditText) findViewById(R.id.ServerIP);
		//EditText serverPORT = (EditText) findViewById(R.id.ServerPORT);
		serverIP.setText(Select_tab.ServerIp);
		//serverPORT.setText(Select_tab.ServerPORT);
	}

	public void ChkIP(View v) {// IP手動設定
		EditText serverIP = (EditText) findViewById(R.id.ServerIP);
		//EditText serverPORT = (EditText) findViewById(R.id.ServerPORT);
		
		int Error = 0;
		String IP4[] = serverIP.getText().toString().split("\\.");
		
		if(serverIP.getText().toString().isEmpty()){
			//請輸入ip
			Error = 1;
		}else if(IP4.length!=4){
			//錯誤的ip格式
			Error = 2;
		//}else if(Integer.parseInt(serverPORT.getText().toString()) <= 0 || Integer.parseInt(serverPORT.getText().toString()) > 65535){
		//	//錯誤的port
		//	Error = 3;	
		}else
			for (String IP : IP4)
				if(Integer.parseInt(IP) < 0 || Integer.parseInt(IP) > 255)
					Error = 2;

		String MES = null;
		switch(Error){
			case 0:
				MES = getString(R.string.set_ok);
				Select_tab.ServerIp = serverIP.getText().toString();
				//Select_tab.ServerPORT = serverPORT.getText().toString();
				Select_tab.ServerPORT = "3579";
				Intent intent = new Intent();//轉跳選單畫面
				intent.setClass(this, Select_tab.class);
				startActivity(intent);
				this.finish();
				break;
			case 1:
				MES = getString(R.string.input_ip);
				break;
			case 2:
				MES = getString(R.string.ip_error);
				break;
			case 3:
				MES = getString(R.string.port_error);
				break;
		}
			Toast.makeText(getApplication(),MES, Toast.LENGTH_LONG).show(); // 列印異常資訊
	}

	public void AuotScanIP(View v) {// 自動IP設定(未完成)

		Intent intent = new Intent();//轉跳選單畫面
		intent.setClass(this, Select_tab.class);
		startActivity(intent);
		this.finish();
	}
}
