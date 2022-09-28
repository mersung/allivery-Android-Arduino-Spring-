package com.example.a14c_bluetooth;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    ListView listViewPaired;
    ListView listViewSearched;
    Button btnSearch;
    Switch switchOn;
    String code;

    private BluetoothAdapter bluetoothAdapter;
    private ArrayList<BluetoothDevice> arrayListPaired;
    private ArrayList<String> arrayListPairedName;
    private ArrayList<BluetoothDevice> arrayListSearched;
    private ArrayList<String> arrayListSearchedName;
    private ArrayAdapter<String> adapterPaired;
    private ArrayAdapter<String> adapterSearched;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN,
                    Manifest.permission.BLUETOOTH_CONNECT, Manifest.permission.BLUETOOTH_SCAN}, 100);
            return;
        }

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        initList();
        initOnOff();
        searchPairedDevice();
        searchDevice();

        Intent get = getIntent();
        code = get.getStringExtra("code");
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode != 100)
            return;
        if(grantResults.length >= 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            initList();
            initOnOff();
            searchPairedDevice();
            searchDevice();
        }
    }
    private void initList() {
        listViewPaired = findViewById(R.id.listPaired);
        listViewSearched = findViewById(R.id.listDevices);
        btnSearch = findViewById(R.id.buttonSearch);

        arrayListPaired = new ArrayList<BluetoothDevice>();
        arrayListSearched = new ArrayList<BluetoothDevice>();
        arrayListPairedName = new ArrayList<String>();
        arrayListSearchedName = new ArrayList<String>();
        adapterPaired = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayListPairedName);
        adapterSearched = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayListSearchedName);
        listViewPaired.setAdapter(adapterPaired);
        listViewSearched.setAdapter(adapterSearched);

        listViewPaired.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, ConnectActivity.class);
                BluetoothDevice device = arrayListPaired.get(i);
                intent.putExtra("name", device.getName());
                intent.putExtra("address", device.getAddress());
                intent.putExtra("code", code);
                startActivity(intent);
                return true;
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchPairedDevice();
                searchDevice();
            }
        });

        listViewSearched.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, ConnectActivity.class);
                BluetoothDevice device = arrayListSearched.get(i);
                intent.putExtra("name", device.getName());
                intent.putExtra("address", device.getAddress());
                intent.putExtra("code", code);
                startActivity(intent);
                return true;
            }
        });
    } // initList
    private ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    int resultCode = result.getResultCode();
                    if (resultCode == RESULT_OK) {
                        searchPairedDevice();
                        searchDevice();
                        Toast.makeText(getApplicationContext(), "블루투스 활성화", Toast.LENGTH_LONG).show();
                    } else if (resultCode == RESULT_CANCELED) {
                        Toast.makeText(getApplicationContext(), "취소", Toast.LENGTH_LONG).show();
                        switchOn.setChecked(false);
                    }
                }
            });
    private void initOnOff() {
        switchOn = findViewById(R.id.switchOn);
        switchOn.setChecked(bluetoothAdapter.isEnabled());
        switchOn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    launcher.launch(intent);
                } else
                    bluetoothAdapter.disable();

            }
        });
    }
    private  void searchPairedDevice() {
        if(!bluetoothAdapter.isEnabled())
            return;
        arrayListPaired.clear();
        arrayListPairedName.clear();

        Set<BluetoothDevice> devices = bluetoothAdapter.getBondedDevices();
        for(BluetoothDevice device : devices) {
            arrayListPaired.add(device);
            String sName = device.getName();
            if(sName == null || sName.isEmpty())
                sName = device.getAddress();
            arrayListPairedName.add(sName);
        }

        adapterPaired.notifyDataSetChanged();

    }
    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String sAction = intent.getAction();
            if(sAction.equals(BluetoothDevice.ACTION_FOUND)){
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if(device != null){
                    arrayListSearched.add(device);
                    String sName = device.getName();
                    if(sName == null || sName.isEmpty())
                        sName = device.getAddress();
                    arrayListSearchedName.add(sName);
                    adapterSearched.notifyDataSetChanged();
                }
            }
        }
    };
    private  void searchDevice() {
        if(!bluetoothAdapter.isEnabled())
            return;
        arrayListSearched.clear();
        arrayListSearchedName.clear();

        if(bluetoothAdapter.isDiscovering())
            bluetoothAdapter.cancelDiscovery();

        bluetoothAdapter.startDiscovery();
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(receiver,filter);

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

}

