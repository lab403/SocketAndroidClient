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
		setContentView(R.layout.tab_video);//�v���e��
		//this.OpenFile();
	}
	
	private void OpenFile(){
		try {
			s = new Socket(Select_tab.ServerIp, Integer.parseInt(Select_tab.ServerPORT));// �s�����A��(IP�̱z�q����}�ӭק�)
			dout = new DataOutputStream(s.getOutputStream());// �o���X��y
			din = new DataInputStream(s.getInputStream());// �o���J��y
			dout.writeUTF("MRCode_Show_Videos");// �V���A���o�e�T��
			//�������ɮײM��e��
			String in = din.readUTF();			
			if(in.equalsIgnoreCase("NoFile")){
				Toast.makeText(getApplication(),getString(R.string.not_file), Toast.LENGTH_LONG).show(); // �C�L���`��T
			}else{
				Intent intent = new Intent();
				intent.setClass(Tab_video.this, LV_Video.class);
				intent.putExtra("File", in.split("//s"));
				startActivityForResult(intent, 9651);
			}
		} catch (Exception e) {
			Toast.makeText(getApplication(), e.toString(), Toast.LENGTH_LONG).show(); // �C�L���`��T
		} finally {// ��finally�y�y���T�O�ʧ@����
			try { 
				if (dout != null) dout.close();	// ������J��y
				if (din != null) din.close();	// ������J��y 
				if (s != null) s.close();		// ����Socket�s��
			} catch (Exception e) { Toast.makeText(getApplication(), e.toString(),Toast.LENGTH_LONG).show(); }
		}
	}
	
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data ){
		if(requestCode==9651){
			if(resultCode==RESULT_OK){
				try {
					s = new Socket(Select_tab.ServerIp, Integer.parseInt(Select_tab.ServerPORT));// �s�����A��(IP�̱z�q����}�ӭק�)
					dout = new DataOutputStream(s.getOutputStream());// �o���X��y
					din = new DataInputStream(s.getInputStream());// �o���J��y
					dout.writeUTF("MRCode_Run_Videos//s"+data.getCharSequenceExtra("File"));// �V���A���o�e�T��
					String in = din.readUTF();
					Toast.makeText(getApplication(), in, Toast.LENGTH_LONG).show(); // �C�L���浲�G
				} catch (Exception e) {
					Toast.makeText(getApplication(), e.toString(), Toast.LENGTH_LONG).show(); // �C�L���`��T
				} finally {// ��finally�y�y���T�O�ʧ@����
					try { 
						if (dout != null) dout.close();	// ������J��y
						if (din != null) din.close();	// ������J��y 
						if (s != null) s.close();		// ����Socket�s��
					} catch (Exception e) { Toast.makeText(getApplication(), e.toString(),Toast.LENGTH_LONG).show(); }
				}
			}
		}
		
	}
	
	protected void RunCommend(String CmdCode ){
		try {
			s = new Socket(Select_tab.ServerIp, Integer.parseInt(Select_tab.ServerPORT));// �s�����A��(IP�̱z�q����}�ӭק�)
			dout = new DataOutputStream(s.getOutputStream());// �o���X��y
			dout.writeUTF(CmdCode);// �V���A���o�e�T��
		} catch (Exception e) {
			Toast.makeText(getApplication(), e.toString(), Toast.LENGTH_LONG).show(); // �C�L���`��T
		} finally {// ��finally�y�y���T�O�ʧ@����
			try {
				if (dout != null) dout.close();// ������J��y
				if (s != null) s.close();// ����Socket�s��
			} catch (Exception e) {
				Toast.makeText(getApplication(), e.toString(),Toast.LENGTH_LONG).show();
			}
		}
	}
	
	public void onClick(View v) {// �̷өҮ׫���o�e�R�O�X
		switch (v.getId()) {
		case R.id.video_power_off: // ��������
			RunCommend("MRCode_WMP_00");
			break;
		case R.id.video_itme_up: // �W�@��
			RunCommend("MRCode_WMP_12");
			break;
		case R.id.video_item_play: // �~��μȰ�
			RunCommend("MRCode_WMP_10");
			break;
		case R.id.video_item_down: // �U�@��
			RunCommend("MRCode_WMP_13");
			break;
		case R.id.video_itme_beforePlay: // �C�t����
			RunCommend("MRCode_WMP_18");
			break;
		case R.id.video_item_stopPlay: // �����
			RunCommend("MRCode_WMP_19");
			break;
		case R.id.video_item_afterPlay: // �ֳt����
			RunCommend("MRCode_WMP_17");
			break;
		case R.id.video_audio_add: // ���q�W�[
			RunCommend("MRCode_WMP_15");
			break;
		case R.id.video_audio_cut: // ���q���
			RunCommend("MRCode_WMP_16");
			break;
		case R.id.video_audio_off: // �R��
			RunCommend("MRCode_WMP_14");
			break;
		case R.id.video_random: // �H������
			RunCommend("MRCode_WMP_01");
			break;
		case R.id.video_repeat: // ���Ƽ���
			RunCommend("MRCode_WMP_02");
			break;
		case R.id.video_list: // �n�D���ָ�Ƨ����U���ɮ�
			this.OpenFile();
			//�������ɮײM��e��
			break;
		case R.id.video_full: // ���e��/�������e��
	        RunCommend("MRCode_WMP_11");
			break;
			
		}

	}
}
