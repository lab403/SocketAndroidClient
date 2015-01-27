package com.act.waa;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class Tab_help extends Activity {
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_help);//helpµe­±
        
WebView wv;
        
        wv = (WebView) findViewById(R.id.webView1);
        wv.loadUrl("file:///android_asset/help.htm");
	}
}
