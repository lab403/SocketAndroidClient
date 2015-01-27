package com.act.waa;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Select_tab extends Activity {
	int modeTab=0;
    static String ServerIp="192.168.0.102";//ip
    static String ServerPORT="3579";//port
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_select);//選單畫面
	}

	public void gotoTab(View v) {// 轉跳畫面
		if(ServerIp=="0.0.0.0"){//檢查是否設定IP
			Toast.makeText(getApplication(),getString(R.string.first_set), Toast.LENGTH_SHORT).show();
		}
		else{
			switch (v.getId()) {
			case R.id.music_btn:
				modeTab = 0;//music_list
				break;
			case R.id.video_btn:
				modeTab = 1;//video_list
				break;
			case R.id.powerpoint_btn:
				modeTab = 2;//powerpoint_list
				break;
			case R.id.power_btn:
				modeTab = 3;//power
				break;
			case R.id.help_btn:
				modeTab = 4;//help
				break;
			}

			Intent intent = new Intent();//轉跳功能畫面
			intent.setClass(this, WaaRemoteActivity.class);
			intent.putExtra("modeTab", this.modeTab);
			startActivity(intent);
			//this.startActivityForResult(intent, 0);
			//this.moveTaskToBack(true);
			this.finish();
		}
		
	}
	
	public void SetIP(View v){//轉跳IP設定畫面
		Intent intent = new Intent();
		intent.setClass(this, Scan_IP.class);
		startActivity(intent);
		this.finish();
	}
}
