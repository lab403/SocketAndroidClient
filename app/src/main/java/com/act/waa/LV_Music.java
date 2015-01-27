package com.act.waa;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.widget.ListView;

public class LV_Music extends Activity {
	 ListView lv;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.listview_music);
        
        Bundle extras = getIntent().getExtras();
        if(extras!=null)
        	mStrings = extras.getStringArray("File");
        
        lv = (ListView) findViewById(R.id.listView1);
        
        lv.setChoiceMode( ListView.CHOICE_MODE_MULTIPLE );
        
        lv.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_multiple_choice, mStrings));
        
        lv.setTextFilterEnabled(true);
       
         
    }
    
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1: // 確定檔案的選擇
	        long[] ids=lv.getCheckItemIds();
			//得到選中的itemId
			String str="";
			for(int i=0;i<ids.length;i++){
				str+=mStrings[(int)ids[i]]+"//s";
			}
			if(str.equalsIgnoreCase("")){
				Toast.makeText(getApplication(), getString(R.string.select_file_last), Toast.LENGTH_LONG).show();
			}else{
				//Toast.makeText(getApplication(), str, Toast.LENGTH_LONG).show();
				Intent intent = new Intent();
				intent.putExtra("File", str);
				setResult(RESULT_OK, intent);
				finish();
			}
			break;
		}
	}
    
    public static String[] mStrings;
}