package com.example.a14c_bluetooth;

import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.SystemClock;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

class ConnectedThread extends Thread {
    final static int BT_MESSAGE_READ  = 1;
    final static int BT_MESSAGE_ERROR = 2;

    final static UUID BLUETOOTH_UUID_SPP = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

    private BluetoothSocket mmSocket;
    private InputStream mmInStream;
    private OutputStream mmOutStream;
    private Handler mmBluetoothHandler;

    public ConnectedThread(BluetoothSocket socket, Handler handler) {
        mmSocket = socket;
        mmBluetoothHandler = handler;

        try {
            mmInStream = socket.getInputStream();
            mmOutStream = socket.getOutputStream();
        } catch (IOException e) {
            String sMsg = "소켓 연결 중 오류가 발생했습니다.";
            byte[] buf = sMsg.getBytes();
            mmBluetoothHandler.obtainMessage(BT_MESSAGE_ERROR, buf.length, -1, buf).sendToTarget();
        }
    }

    public void run() {
        byte[] buffer = new byte[1024];
        int bytes;

        while (true) {
            try {
                bytes = mmInStream.available();
                if (bytes != 0) {
                    SystemClock.sleep(100);
                    bytes = mmInStream.available();
                    bytes = mmInStream.read(buffer, 0, bytes);
                    mmBluetoothHandler.obtainMessage(BT_MESSAGE_READ, bytes, -1, buffer).sendToTarget();
                }
            } catch (IOException e) {
                String sMsg = "데이터 읽기 중 오류가 발생했습니다.";
                byte[] buf = sMsg.getBytes();
                mmBluetoothHandler.obtainMessage(BT_MESSAGE_ERROR, buf.length, -1, buf).sendToTarget();
                break;
            }
        }
    }

    public void write(String str) {
        byte[] bytes = str.getBytes();
        try {
            mmOutStream.write(bytes);
        } catch (IOException e) {
            String sMsg = "데이터 전송 중 오류가 발생했습니다.";
            byte[] buffer = sMsg.getBytes();
            mmBluetoothHandler.obtainMessage(BT_MESSAGE_ERROR, buffer.length, -1, buffer).sendToTarget();
        }
    }

    public void cancel() {
        try {
            mmOutStream.close();
            mmInStream.close();
            mmSocket.close();
        } catch (IOException e) {
            String sMsg = "소켓 해제 중 오류가 발생했습니다.";
            byte[] buffer = sMsg.getBytes();
            mmBluetoothHandler.obtainMessage(BT_MESSAGE_ERROR, buffer.length, -1, buffer).sendToTarget();
        }
    }
}
