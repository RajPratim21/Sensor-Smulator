package com.example.raj.sensorsimulator.activity;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import com.example.raj.sensorsimulator.R;
import com.example.raj.sensorsimulator.business.ChatBusinessLogic;
import com.example.raj.sensorsimulator.util.ToastUtil;

import java.util.Random;

public class ChatActivity extends AppCompatActivity{

    public static int MSG_TOAST = 1;
    public static int MSG_BLUETOOTH = 2;
    public static int BT_TIMER_VISIBLE = 30;

    private final int BT_ACTIVATE = 0;
    private final int BT_VISIBLE = 1;

    private Button buttonService;
    private Button buttonClient;
    private ImageButton buttonSend;
    private EditText editTextMessage;
    private ListView listVewHistoric;
    private ArrayAdapter<String> historic;

    private ToastUtil toastUtil;
    private ChatBusinessLogic chatBusinessLogic;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);

        settingsAttributes();
        settingsView();

        inicializaBluetooth();
        registerFilters();
    }



    //@Override
    public void settingsAttributes() {
        toastUtil = new ToastUtil(this);
        chatBusinessLogic = new ChatBusinessLogic(this, handler);
    }

    //@Override
    public void settingsView() {
      //  editTextMessage = (EditText)findViewById(R.id.editTextMessage);

        historic = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
       // listVewHistoric = (ListView)findViewById(R.id.listVewHistoric);
        ///listVewHistoric.setAdapter(historic);
        final Random rand = new Random();
        buttonSend = (ImageButton)findViewById(R.id.buttonSend);
        buttonSend.setOnClickListener(new View.OnClickListener() {
             @Override
            public void onClick(View v) {
                {
                    for(int i=0;i<1000;i++)
                    {
                        chatBusinessLogic.sendMessage((String.valueOf(Math.random())));
                    }
                }
            }
        });
        ImageButton buttonECG = (ImageButton)findViewById(R.id.BtnECG);
        buttonECG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i=0;i<1000;i++)
                {
                    chatBusinessLogic.sendMessage((String.valueOf(Math.random())));
                }
             }
        });

        ImageButton buttonBP = (ImageButton)findViewById(R.id.BtnBP);
        buttonBP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i=0;i<1000;i++)
                {
                    chatBusinessLogic.sendMessage((String.valueOf(Math.random())));
                }
            }
        });

        ImageButton buttonPuleseOX = (ImageButton)findViewById(R.id.BtnPuseOx);
        buttonPuleseOX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i=0;i<1000;i++)
                {
                    chatBusinessLogic.sendMessage((String.valueOf(Math.random())));
                }


            }
        });

        ImageButton buttonGluco = (ImageButton)findViewById(R.id.BtnGlucometer);
        buttonGluco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i=0;i<1000;i++)
                {
                    chatBusinessLogic.sendMessage((String.valueOf(Math.random())));
                }
            }
        });

        ImageButton buttonThermo = (ImageButton)findViewById(R.id.BtnThermo);
        buttonThermo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i=0;i<1000;i++)
                {
                    chatBusinessLogic.sendMessage((String.valueOf(Math.random())));
                }
            }
        });

        buttonClient = (Button)findViewById(R.id.buttonClient);
        buttonClient.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                chatBusinessLogic.startFoundDevices();
            }
        });
    }

    public void inicializaBluetooth() {
        if (chatBusinessLogic.getBluetoothManager().verifySuportedBluetooth()) {
            if (!chatBusinessLogic.getBluetoothManager().isEnabledBluetooth()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, BT_ACTIVATE);
            }
        } else {
            toastUtil.showToast(getString(R.string.no_support_bluetooth));
            finish();
        }
    }

    public void registerFilters(){
        chatBusinessLogic.registerFilter();
    }

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            synchronized (msg) {
                switch (msg.what) {
                    case 1:
                        toastUtil.showToast((String)(msg.obj));
                        break;
                    case 2:
                        historic.add((String)(msg.obj));
                        historic.notifyDataSetChanged();

                        listVewHistoric.requestFocus();
                        break;
                }
            }
        };
    };

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case BT_ACTIVATE:
                if (RESULT_OK != resultCode) {
                    toastUtil.showToast(getString(R.string.activate_bluetooth_to_continue));
                    finish();
                }
                break;

            case BT_VISIBLE:
                if (resultCode == BT_TIMER_VISIBLE) {

                    chatBusinessLogic.stopCommucanition();
                    chatBusinessLogic.startServer();
                } else {
                    toastUtil.showToast(getString(R.string.device_must_visible));
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        chatBusinessLogic.unregisterFilter();
        chatBusinessLogic.stopCommucanition();
    }

}