package com.act.waa;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

public class WaaRemoteActivity extends TabActivity {
	int modeTab;
	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setupViewComponent();
	}

	private void setupViewComponent() {// 設定TabHost圖示
		
		final TabHost tabHost = getTabHost();

		tabHost.addTab(tabHost
				.newTabSpec("tab_music")
				.setIndicator("",
						getResources().getDrawable(R.drawable.music_icon))
				.setContent(new Intent(this, Tab_music.class)));

		tabHost.addTab(tabHost
				.newTabSpec("tab_video")
				.setIndicator("",
						getResources().getDrawable(R.drawable.video_icon))
				.setContent(new Intent(this, Tab_video.class)));

		tabHost.addTab(tabHost
				.newTabSpec("tab_powerpoint")
				.setIndicator("",
						getResources().getDrawable(R.drawable.powerpoint_icon))
				.setContent(new Intent(this, Tab_powerpoint.class)));

		tabHost.addTab(tabHost
				.newTabSpec("tab_power")
				.setIndicator("",
						getResources().getDrawable(R.drawable.power_icon))
				.setContent(new Intent(this, Tab_power.class)));

		tabHost.addTab(tabHost
				.newTabSpec("tab_help")
				.setIndicator("",
						getResources().getDrawable(R.drawable.help_icon))
				.setContent(new Intent(this, Tab_help.class)));

        Bundle extras = getIntent().getExtras();
        if(extras!=null)
        	modeTab = extras.getInt("modeTab");
        
		switch (modeTab) {
		case 0:
			tabHost.setCurrentTab(0);
			break;
		case 1:
			tabHost.setCurrentTab(1);
			break;
		case 2:
			tabHost.setCurrentTab(2);
			break;
		case 3:
			tabHost.setCurrentTab(3);
			break;
		case 4:
			tabHost.setCurrentTab(4);
			break;
		}


	}
}