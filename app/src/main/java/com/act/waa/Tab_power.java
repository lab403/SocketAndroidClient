package com.act.waa;

import java.io.DataOutputStream;
import java.net.Socket;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Tab_power extends Activity {
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_power);
	}
	
	public void onClick(View v) {// 依照所案按鍵發送命令碼
		String MCode = "NoCmd";
		switch (v.getId()) {
		case R.id.power_pc_sleep: // 休眠
			MCode = "MRCode_CC_00";
			break;
		case R.id.power_pc_reset: // 重新開機
			MCode = "MRCode_CC_01";
			break;
		case R.id.power_pc_off: // 關機
			MCode = "MRCode_CC_02";
			break;
		}

		Socket s = null;
		DataOutputStream dout = null;
		try {
			s = new Socket(Select_tab.ServerIp, Integer.parseInt(Select_tab.ServerPORT));// 連接伺服器(IP依您電腦位址來修改)
			dout = new DataOutputStream(s.getOutputStream());// 得到輸出串流
			dout.writeUTF(MCode);// 向伺服器發送訊息
		} catch (Exception e) {
			Toast.makeText(getApplication(), e.toString(), Toast.LENGTH_LONG).show(); // 列印異常資訊
		} finally {// 用finally語句塊確保動作執行
			try {
				if (dout != null)  dout.close();// 關閉輸入串流
				if (s != null) 	s.close();// 關閉Socket連接
			} catch (Exception e) {
				Toast.makeText(getApplication(), e.toString(),Toast.LENGTH_LONG).show();
			}
		}
	}
}
