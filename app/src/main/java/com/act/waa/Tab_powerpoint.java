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
	public static int UPDATE_CUT = 1;//1�N���ƴ�1 0�N����p��
	boolean Run = false;
	TextView EditTime;
	Socket s = null;
	DataOutputStream dout = null;
	DataInputStream din = null;
	ImageView iv1; //���O
	ImageView iv2; //���O
	Thread  Timer = new Thread("timer"){
		public void run(){
			String MS[] = EditTime.getText().toString().split(":");
			int m = Integer.parseInt(MS[0]);
			int s = Integer.parseInt(MS[1]);
			while(m >= 1 || s >= 1){
				if(UPDATE_CUT==1){
					try{
						Thread.sleep(1000);//�ίv�@�q�ɶ� 
					}
					catch(Exception e){	e.printStackTrace();	}
					Message ms = myHandler.obtainMessage();//�s��Message����
					myHandler.sendMessage(ms);//�o�XMessage����
				}
			}
		}
	};

	//Handler����
	Handler myHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {//�мg�B�z�T����k
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
				iv1.setImageDrawable(getResources() .getDrawable(R.drawable.led_y)); //���O�G�_
		        iv2.setImageDrawable(getResources() .getDrawable(R.drawable.led_r_no)); //���O����
			}
			if(m < 2){
				 iv2.setImageDrawable(getResources() .getDrawable(R.drawable.led_r)); //���O�G�_
			        iv1.setImageDrawable(getResources() .getDrawable(R.drawable.led_y_no)); //���O����
			}
			EditTime.setText((m>=10?(m):("0"+m))+":"+(s>=10?(s):("0"+s)));//��sTextView��ܪ����e
		}
	};
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_powerpoint);//²���e��
        Run = false;
        iv1 = (ImageView) findViewById(R.id.imageView1); //���O
    	iv2 = (ImageView) findViewById(R.id.imageView2); //���O
        iv1.setImageDrawable(getResources() .getDrawable(R.drawable.led_y_no)); //�N�O������
        iv2.setImageDrawable(getResources() .getDrawable(R.drawable.led_r_no)); //�N�O������
        EditTime = (TextView) findViewById(R.id.editText1);
        //this.OpenFile();
	}
	
	private void OpenFile(){
		try {
			s = new Socket(Select_tab.ServerIp, Integer.parseInt(Select_tab.ServerPORT));// �s�����A��(IP�̱z�q����}�ӭק�)
			dout = new DataOutputStream(s.getOutputStream());// �o���X��y
			din = new DataInputStream(s.getInputStream());// �o���J��y
			dout.writeUTF("MRCode_Show_Documents");// �V���A���o�e�T��
			//�������ɮײM��e��
			String in = din.readUTF();
			if(in.equalsIgnoreCase("NoFile")){
				Toast.makeText(getApplication(),"getString(R.string.not_file)", Toast.LENGTH_LONG).show(); // �C�L���`��T
			}else{
				Intent intent = new Intent();
				intent.setClass(Tab_powerpoint.this, LV_PowerPoint.class);
				intent.putExtra("File", in.split("//s"));
				startActivityForResult(intent, 9650);
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
		if(requestCode==9650){
			if(resultCode==RESULT_OK){
				try {
					s = new Socket(Select_tab.ServerIp, Integer.parseInt(Select_tab.ServerPORT));// �s�����A��(IP�̱z�q����}�ӭק�)
					dout = new DataOutputStream(s.getOutputStream());// �o���X��y
					din = new DataInputStream(s.getInputStream());// �o���J��y
					dout.writeUTF("MRCode_Run_Documents//s"+data.getCharSequenceExtra("File"));// �V���A���o�e�T��
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
	
	public void onClick(View v) {// �̷өҮ׫���o�e�R�O
		switch (v.getId()) {
			case R.id.powerpoint_power_off: // ����powerpoint
				RunCommend("MRCode_PPT_00");
				Run = false;
				break;
			case R.id.powerpoint_paga_up: // �W�@��
				RunCommend("MRCode_PPT_12");
				break;
			case R.id.powerpoint_run: // �}�l�ε�����v��
				if(!Run){
					//Toast.makeText(getApplication(), "start()",Toast.LENGTH_LONG).show();
			        iv1.setImageDrawable(getResources() .getDrawable(R.drawable.led_y_no)); //�N�O������
			        iv2.setImageDrawable(getResources() .getDrawable(R.drawable.led_r_no)); //�N�O������
					UPDATE_CUT = 1;
					if(Timer.isAlive()==false)
						Timer.start();
					RunCommend("MRCode_PPT_10"); //�}�l��v��
				}else{
					//Toast.makeText(getApplication(), "stop()",Toast.LENGTH_LONG).show();
					UPDATE_CUT = 0;
					RunCommend("MRCode_PPT_11");  //������v��
				}
				Run = !Run;
				break;
			case R.id.powerpoint_paga_down: // �U�@��
				RunCommend("MRCode_PPT_13");
				break;
			case R.id.powerpoint_audio_cut: // ��C���q
				RunCommend("MRCode_PPT_15");
				break;
			case R.id.powerpoint_audio_add: // �W�[���q
				RunCommend("MRCode_PPT_14");
				break;
			case R.id.imageButton7: // �W�[����
			{
				String MS[] = EditTime.getText().toString().split(":");
				int m = Integer.parseInt(MS[0]);
				int s = Integer.parseInt(MS[1]);
				m += m<99?1:0;
				EditTime.setText((m>=10?(m):("0"+m))+":"+(s>=10?(s):("0"+s)));
			}
				break;
			case R.id.imageButton8: // ��֤���
			{
				String MS[] = EditTime.getText().toString().split(":");
				int m = Integer.parseInt(MS[0]);
				int s = Integer.parseInt(MS[1]);
				m -= m<=1?0:1;
				EditTime.setText((m>=10?(m):("0"+m))+":"+(s>=10?(s):("0"+s)));
			}
				break;
				
			case R.id.powerpoint_file: // �n�D²����Ƨ����U���ɮ�
		        this.OpenFile();
				break;
				
		}
	}

}
