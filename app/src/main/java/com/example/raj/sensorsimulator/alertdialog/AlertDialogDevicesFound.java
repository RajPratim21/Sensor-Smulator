package com.example.raj.sensorsimulator.alertdialog;

import java.util.List;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

import com.example.raj.sensorsimulator.R;
import com.example.raj.sensorsimulator.business.IBusinessLogic;

public class AlertDialogDevicesFound extends AlertDialogGeneric implements OnClickListener{

	private List<BluetoothDevice> devicesFound;
	private IBusinessLogic.OnBluetoothDeviceSelectedListener onBluetoothDeviceSelectedListener;
	
	public AlertDialogDevicesFound(Context context, IBusinessLogic.OnBluetoothDeviceSelectedListener onBluetoothDeviceSelectedListener) {
		super(context);
		
		this.onBluetoothDeviceSelectedListener = onBluetoothDeviceSelectedListener;
	}

	public void settingsAlertDialog(List<BluetoothDevice> devicesFound) {
		
		this.devicesFound = devicesFound;
		
		String[] devices = new String[devicesFound.size()]; 

		for (int i = 0; i < devicesFound.size(); i++){
			devices[i] = devicesFound.get(i).getName();
		}
		
		alert.setTitle(context.getString(R.string.devices_found));
		alert.setItems(devices, this);
		
		showAlertDialog();
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		onBluetoothDeviceSelectedListener.onBluetoothDeviceSelected(devicesFound.get(which));
	}
	
}