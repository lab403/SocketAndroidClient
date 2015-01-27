package com.act.waa;

import net.emome.hamiapps.sdk.ForwardActivity;

public class CheckLicense extends ForwardActivity
{
	@SuppressWarnings("unchecked")
	@Override
	public Class getTargetActivity() 
	{
		return Scan_IP.class;
	}
	
	@Override
	public boolean passOnUnavailableDataNetwork()
	{
		return false;
	}
}
