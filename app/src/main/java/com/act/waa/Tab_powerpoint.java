package com.act.waa;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Tab_powerpoint extends Activity{
	public static int UPDATE_CUT = 1;//1代表秒數減1 0代表停止計時
	boolean Run = false;
	TextView EditTime;
	Socket s = null;
	DataOutputStream dout = null;
	DataInputStream din = null;
	ImageView iv1; //黃燈
	ImageView iv2; //紅燈
	Thread  Timer = new Thread("timer"){
		public void run(){
			String MS[] = EditTime.getText().toString().split(":");
			int m = Integer.parseInt(MS[0]);
			int s = Integer.parseInt(MS[1]);
			while(m >= 1 || s >= 1){
				if(UPDATE_CUT==1){
					try{
						Thread.sleep(1000);//睡眠一段時間 
					}
					catch(Exception e){	e.printStackTrace();	}
					Message ms = myHandler.obtainMessage();//新建Message物件
					myHandler.sendMessage(ms);//發出Message物件
				}
			}
		}
	};

	//Handler物件
	Handler myHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {//覆寫處理訊息方法
			String MS[] = EditTime.getText().toString().split(":");
			int m = Integer.parseInt(MS[0]);
			int s = Integer.parseInt(MS[1]);
			if(s==0 && m!=0){
				m--;
				s = 59;
			}else if(s==0 && m==0)
				;
			else
				s--;
			if(m < 3){
				iv1.setImageDrawable(getResources() .getDrawable(R.drawable.led_y)); //黃燈亮起
		        iv2.setImageDrawable(getResources() .getDrawable(R.drawable.led_r_no)); //紅燈隱藏
			}
			if(m < 2){
				 iv2.setImageDrawable(getResources() .getDrawable(R.drawable.led_r)); //紅燈亮起
			        iv1.setImageDrawable(getResources() .getDrawable(R.drawable.led_y_no)); //黃燈隱藏
			}
			EditTime.setText((m>=10?(m):("0"+m))+":"+(s>=10?(s):("0"+s)));//更新TextView顯示的內容
		}
	};
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_powerpoint);//簡報畫面
        Run = false;
        iv1 = (ImageView) findViewById(R.id.imageView1); //黃燈
    	iv2 = (ImageView) findViewById(R.id.imageView2); //紅燈
        iv1.setImageDrawable(getResources() .getDrawable(R.drawable.led_y_no)); //將燈號隱藏
        iv2.setImageDrawable(getResources() .getDrawable(R.drawable.led_r_no)); //將燈號隱藏
        EditTime = (TextView) findViewById(R.id.editText1);
        //this.OpenFile();
	}
	
	private void OpenFile(){
		try {
			s = new Socket(Select_tab.ServerIp, Integer.parseInt(Select_tab.ServerPORT));// 連接伺服器(IP依您電腦位址來修改)
			dout = new DataOutputStream(s.getOutputStream());// 得到輸出串流
			din = new DataInputStream(s.getInputStream());// 得到輸入串流
			dout.writeUTF("MRCode_Show_Documents");// 向伺服器發送訊息
			//切換至檔案清單畫面
			String in = din.readUTF();
			if(in.equalsIgnoreCase("NoFile")){
				Toast.makeText(getApplication(),"getString(R.string.not_file)", Toast.LENGTH_LONG).show(); // 列印異常資訊
			}else{
				Intent intent = new Intent();
				intent.setClass(Tab_powerpoint.this, LV_PowerPoint.class);
				intent.putExtra("File", in.split("//s"));
				startActivityForResult(intent, 9650);
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
		if(requestCode==9650){
			if(resultCode==RESULT_OK){
				try {
					s = new Socket(Select_tab.ServerIp, Integer.parseInt(Select_tab.ServerPORT));// 連接伺服器(IP依您電腦位址來修改)
					dout = new DataOutputStream(s.getOutputStream());// 得到輸出串流
					din = new DataInputStream(s.getInputStream());// 得到輸入串流
					dout.writeUTF("MRCode_Run_Documents//s"+data.getCharSequenceExtra("File"));// 向伺服器發送訊息
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
	
	public void onClick(View v) {// 依照所案按鍵發送命令
		switch (v.getId()) {
			case R.id.powerpoint_power_off: // 關閉powerpoint
				RunCommend("MRCode_PPT_00");
				Run = false;
				break;
			case R.id.powerpoint_paga_up: // 上一頁
				RunCommend("MRCode_PPT_12");
				break;
			case R.id.powerpoint_run: // 開始或結束投影片
				if(!Run){
					//Toast.makeText(getApplication(), "start()",Toast.LENGTH_LONG).show();
			        iv1.setImageDrawable(getResources() .getDrawable(R.drawable.led_y_no)); //將燈號隱藏
			        iv2.setImageDrawable(getResources() .getDrawable(R.drawable.led_r_no)); //將燈號隱藏
					UPDATE_CUT = 1;
					if(Timer.isAlive()==false)
						Timer.start();
					RunCommend("MRCode_PPT_10"); //開始投影片
				}else{
					//Toast.makeText(getApplication(), "stop()",Toast.LENGTH_LONG).show();
					UPDATE_CUT = 0;
					RunCommend("MRCode_PPT_11");  //結束投影片
				}
				Run = !Run;
				break;
			case R.id.powerpoint_paga_down: // 下一頁
				RunCommend("MRCode_PPT_13");
				break;
			case R.id.powerpoint_audio_cut: // 減低音量
				RunCommend("MRCode_PPT_15");
				break;
			case R.id.powerpoint_audio_add: // 增加音量
				RunCommend("MRCode_PPT_14");
				break;
			case R.id.imageButton7: // 增加分鐘
			{
				String MS[] = EditTime.getText().toString().split(":");
				int m = Integer.parseInt(MS[0]);
				int s = Integer.parseInt(MS[1]);
				m += m<99?1:0;
				EditTime.setText((m>=10?(m):("0"+m))+":"+(s>=10?(s):("0"+s)));
			}
				break;
			case R.id.imageButton8: // 減少分鐘
			{
				String MS[] = EditTime.getText().toString().split(":");
				int m = Integer.parseInt(MS[0]);
				int s = Integer.parseInt(MS[1]);
				m -= m<=1?0:1;
				EditTime.setText((m>=10?(m):("0"+m))+":"+(s>=10?(s):("0"+s)));
			}
				break;
				
			case R.id.powerpoint_file: // 要求簡報資料夾底下的檔案
		        this.OpenFile();
				break;
				
		}
	}

}
