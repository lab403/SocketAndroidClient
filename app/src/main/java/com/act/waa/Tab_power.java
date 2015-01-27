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
	
	public void onClick(View v) {// �̷өҮ׫���o�e�R�O�X
		String MCode = "NoCmd";
		switch (v.getId()) {
		case R.id.power_pc_sleep: // ��v
			MCode = "MRCode_CC_00";
			break;
		case R.id.power_pc_reset: // ���s�}��
			MCode = "MRCode_CC_01";
			break;
		case R.id.power_pc_off: // ����
			MCode = "MRCode_CC_02";
			break;
		}

		Socket s = null;
		DataOutputStream dout = null;
		try {
			s = new Socket(Select_tab.ServerIp, Integer.parseInt(Select_tab.ServerPORT));// �s�����A��(IP�̱z�q����}�ӭק�)
			dout = new DataOutputStream(s.getOutputStream());// �o���X��y
			dout.writeUTF(MCode);// �V���A���o�e�T��
		} catch (Exception e) {
			Toast.makeText(getApplication(), e.toString(), Toast.LENGTH_LONG).show(); // �C�L���`��T
		} finally {// ��finally�y�y���T�O�ʧ@����
			try {
				if (dout != null)  dout.close();// ������J��y
				if (s != null) 	s.close();// ����Socket�s��
			} catch (Exception e) {
				Toast.makeText(getApplication(), e.toString(),Toast.LENGTH_LONG).show();
			}
		}
	}
}
