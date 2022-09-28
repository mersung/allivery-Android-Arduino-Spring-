package com.example.a14c_bluetooth;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ConnectActivity extends AppCompatActivity {
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothSocket bluetoothSocket;
    private ConnectedThread connectedThread;
    TextView textView;
    String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);

        textView = findViewById(R.id.textView);
        Intent intent = getIntent();
        if(intent != null) {
            String sAddress = intent.getStringExtra("address");
            String sName = intent.getStringExtra("name");
            code = intent.getStringExtra("code");

            if(!sAddress.isEmpty()) {
                bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                BluetoothDevice device = bluetoothAdapter.getRemoteDevice(sAddress);
                if(device == null) {
                    Toast.makeText(getApplicationContext(), "해당기기가 없습니다.", Toast.LENGTH_LONG).show();
                    finish();
                    return;
                }
                connectToDevice(device);
            }
        }

    } // onCreate

    private Handler bluetoothHandler = new Handler(Looper.getMainLooper()){
        public void handleMessage(Message msg){
            String readMessage = null;
            readMessage = new String((byte[]) msg.obj, StandardCharsets.UTF_8);
            readMessage = readMessage.trim();
            if(msg.what == ConnectedThread.BT_MESSAGE_READ){

                switch(readMessage) {
                    case "u": case "U": readMessage = "앞으로 이동"; break;
                    case "l": case "L": readMessage = "왼쪽으로 이동"; break;
                    case "r": case "R": readMessage = "오른쪽으로 이동"; break;
                    case "d": case "D": readMessage = "뒤로 이동"; break;
                    case "s": case "S": readMessage = "중지"; break;
                    default: readMessage = "이동 오류";
                }
            }
            textView.setText(readMessage);
        }
    };

    private void connectToDevice(BluetoothDevice device) {
        textView.setText("연결중...");
        if(!bluetoothAdapter.isEnabled()) {
            Toast.makeText(this, "블루투스가 활성화되어 있지 않습니다.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        String sName = device.getName();

        if(bluetoothAdapter.isDiscovering())
            bluetoothAdapter.cancelDiscovery();

        try {

            if (bluetoothSocket != null)
                bluetoothSocket.close();
            bluetoothSocket = device.createRfcommSocketToServiceRecord(ConnectedThread.BLUETOOTH_UUID_SPP);
            bluetoothSocket.connect();
            textView.setText("연결 시도 : " + sName);
            if(connectedThread != null)
                connectedThread.cancel();
            connectedThread = new ConnectedThread(bluetoothSocket, bluetoothHandler);
            connectedThread.start();
            textView.setText("연결 성공 : " + code);
            connectedThread.write("o");
        } catch(Exception e) {
            textView.setText("연결 실패 : " + sName);
            e.printStackTrace();
            try {
                if (bluetoothSocket != null) {
                    bluetoothSocket.close();
                    bluetoothSocket = null;
                }
            } catch(Exception e1) {
                e1.printStackTrace();
            }
            finish();
        }
    } // connectToDevice

    public void onClickMove(View v) {
       if(connectedThread == null) {
           Toast.makeText(this, "연결되지 않은 상태입니다.", Toast.LENGTH_LONG).show();
           return;
       }

       int id = v.getId();
       String sendData = "";
       switch(id) {
           case R.id.imageButtonDown :
               sendData = "D";
               break;
           case R.id.imageButtonUp :
               sendData = "U";
               break;
           case R.id.imageButtonLeft :
               sendData = "L";
               break;
           case R.id.imageButtonRight:
               sendData = "R";
               break;
           default:
               sendData = "b";
               break;
       }
       connectedThread.write(sendData);
        Intent intent = new Intent(ConnectActivity.this, ProductGiveActivity2.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(connectedThread != null)
            connectedThread.cancel();
    }
} // class