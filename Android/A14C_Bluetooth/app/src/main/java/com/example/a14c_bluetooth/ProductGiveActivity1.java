package com.example.a14c_bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.charset.StandardCharsets;

public class ProductGiveActivity1 extends AppCompatActivity {

    private BluetoothAdapter bluetoothAdapter;
    private BluetoothSocket bluetoothSocket;
    private ConnectedThread connectedThread;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_give1);

        ImageView receive_btn = (ImageView) findViewById(R.id.receive_btn);
        textView = findViewById(R.id.textView);
        Intent intent = getIntent();

        receive_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductGiveActivity1.this, ProductGiveActivity2.class);
                startActivity(intent);
            }
        });

        if(intent != null) {
            String sAddress = intent.getStringExtra("address");
            String sName = intent.getStringExtra("name");
            String code = intent.getStringExtra("code");

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

        if(connectedThread == null) {
            Toast.makeText(this, "연결되지 않은 상태입니다.", Toast.LENGTH_LONG).show();
            return;
        }
        String sendData = "o";
        connectedThread.write(sendData);
    } // onCreate


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
            connectedThread = new ConnectedThread(bluetoothSocket,bluetoothHandler);
            connectedThread.start();
            textView.setText("연결 성공 : " + sName);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(connectedThread != null)
            connectedThread.cancel();
    }
}