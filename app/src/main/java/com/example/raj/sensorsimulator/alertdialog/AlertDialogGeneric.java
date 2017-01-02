package com.example.raj.sensorsimulator.alertdialog;

import android.app.AlertDialog;
import android.content.Context;

import com.example.raj.sensorsimulator.R;

public abstract class AlertDialogGeneric {

	protected Context context;
	protected AlertDialog.Builder alert;
	
	public AlertDialogGeneric(Context context) {
		this.context = context;
		
		alert = new AlertDialog.Builder(context);
	 }
	
	public void showAlertDialog(){
		alert.create().show();
	}
	
	public void cancelAlertDialog(){
		alert.create().cancel();
	}

}