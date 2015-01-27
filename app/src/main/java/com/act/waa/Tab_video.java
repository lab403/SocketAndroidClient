package com.act.waa;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Tab_video extends Activity {
	Socket s = null;
	DataOutputStream dout = null;
	DataInputStream din = null;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_video);//影音畫面
		//this.OpenFile();
	}
	
	private void OpenFile(){
		try {
			s = new Socket(Select_tab.ServerIp, Integer.parseInt(Select_tab.ServerPORT));// 連接伺服器(IP依您電腦位址來修改)
			dout = new DataOutputStream(s.getOutputStream());// 得到輸出串流
			din = new DataInputStream(s.getInputStream());// 得到輸入串流
			dout.writeUTF("MRCode_Show_Videos");// 向伺服器發送訊息
			//切換至檔案清單畫面
			String in = din.readUTF();			
			if(in.equalsIgnoreCase("NoFile")){
				Toast.makeText(getApplication(),getString(R.string.not_file), Toast.LENGTH_LONG).show(); // 列印異常資訊
			}else{
				Intent intent = new Intent();
				intent.setClass(Tab_video.this, LV_Video.class);
				intent.putExtra("File", in.split("//s"));
				startActivityForResult(intent, 9651);
			}
		} catch (Exception e) {
			Toast.makeText(getApplication(), e.toString(), Toast.LENGTH_LONG).show(); // 列印異常資訊
		} finally {// 用finally語句塊確保動作執行
			try { 
				if (dout != null) dout.close();	// 關閉輸入串流
				if (din != null) din.close();	// 關閉輸入串流 
				if (s != null) s.close();		// 關閉Socket連接
			} catch (Exception e) { Toast.makeText(getApplication(), e.toString(),Toast.LENGTH_LONG).show(); }
		}
	}
	
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data ){
		if(requestCode==9651){
			if(resultCode==RESULT_OK){
				try {
					s = new Socket(Select_tab.ServerIp, Integer.parseInt(Select_tab.ServerPORT));// 連接伺服器(IP依您電腦位址來修改)
					dout = new DataOutputStream(s.getOutputStream());// 得到輸出串流
					din = new DataInputStream(s.getInputStream());// 得到輸入串流
					dout.writeUTF("MRCode_Run_Videos//s"+data.getCharSequenceExtra("File"));// 向伺服器發送訊息
					String in = din.readUTF();
					Toast.makeText(getApplication(), in, Toast.LENGTH_LONG).show(); // 列印執行結果
				} catch (Exception e) {
					Toast.makeText(getApplication(), e.toString(), Toast.LENGTH_LONG).show(); // 列印異常資訊
				} finally {// 用finally語句塊確保動作執行
					try { 
						if (dout != null) dout.close();	// 關閉輸入串流
						if (din != null) din.close();	// 關閉輸入串流 
						if (s != null) s.close();		// 關閉Socket連接
					} catch (Exception e) { Toast.makeText(getApplication(), e.toString(),Toast.LENGTH_LONG).show(); }
				}
			}
		}
		
	}
	
	protected void RunCommend(String CmdCode ){
		try {
			s = new Socket(Select_tab.ServerIp, Integer.parseInt(Select_tab.ServerPORT));// 連接伺服器(IP依您電腦位址來修改)
			dout = new DataOutputStream(s.getOutputStream());// 得到輸出串流
			dout.writeUTF(CmdCode);// 向伺服器發送訊息
		} catch (Exception e) {
			Toast.makeText(getApplication(), e.toString(), Toast.LENGTH_LONG).show(); // 列印異常資訊
		} finally {// 用finally語句塊確保動作執行
			try {
				if (dout != null) dout.close();// 關閉輸入串流
				if (s != null) s.close();// 關閉Socket連接
			} catch (Exception e) {
				Toast.makeText(getApplication(), e.toString(),Toast.LENGTH_LONG).show();
			}
		}
	}
	
	public void onClick(View v) {// 依照所案按鍵發送命令碼
		switch (v.getId()) {
		case R.id.video_power_off: // 關閉播放器
			RunCommend("MRCode_WMP_00");
			break;
		case R.id.video_itme_up: // 上一首
			RunCommend("MRCode_WMP_12");
			break;
		case R.id.video_item_play: // 繼續或暫停
			RunCommend("MRCode_WMP_10");
			break;
		case R.id.video_item_down: // 下一首
			RunCommend("MRCode_WMP_13");
			break;
		case R.id.video_itme_beforePlay: // 慢速播放
			RunCommend("MRCode_WMP_18");
			break;
		case R.id.video_item_stopPlay: // 停止播放
			RunCommend("MRCode_WMP_19");
			break;
		case R.id.video_item_afterPlay: // 快速播放
			RunCommend("MRCode_WMP_17");
			break;
		case R.id.video_audio_add: // 音量增加
			RunCommend("MRCode_WMP_15");
			break;
		case R.id.video_audio_cut: // 音量減少
			RunCommend("MRCode_WMP_16");
			break;
		case R.id.video_audio_off: // 靜音
			RunCommend("MRCode_WMP_14");
			break;
		case R.id.video_random: // 隨機播放
			RunCommend("MRCode_WMP_01");
			break;
		case R.id.video_repeat: // 重複播放
			RunCommend("MRCode_WMP_02");
			break;
		case R.id.video_list: // 要求音樂資料夾底下的檔案
			this.OpenFile();
			//切換至檔案清單畫面
			break;
		case R.id.video_full: // 全畫面/關閉全畫面
	        RunCommend("MRCode_WMP_11");
			break;
			
		}

	}
}
