package com.example.raj.sensorsimulator.activity;

import android.app.Activity;

public abstract class GenericActivity extends Activity{
	
	public static final String TAG = "Chat Bluetooth";
	
	public abstract void settingsAttributes();
	
	public abstract void settingsView();

}