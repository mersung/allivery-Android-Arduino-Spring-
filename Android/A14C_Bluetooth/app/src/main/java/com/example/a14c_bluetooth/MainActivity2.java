package com.example.a14c_bluetooth;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.util.Log;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity2 extends AppCompatActivity {

    String token;
    HomeFragment homeFragment;
    QrFragment qrFragment;
    WishListFragment wishlistFragment;

    public void replaceFragment(Fragment fragment) {
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();

//        fragmentTransaction.replace(R.id.container, fragment).commit();      // Fragment로 사용할 MainActivity내의 layout공간을 선택합니다.
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent intent = getIntent();
        token = intent.getStringExtra("token");
        Log.d(TAG, "MainActivity2 토큰 : " + token);

        homeFragment = new HomeFragment();
        qrFragment = new QrFragment();
        wishlistFragment = new WishListFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
        BottomNavigationView bottom_menu = findViewById(R.id.bottom_menu);
//        bottom_menu.setSelectedItemId(R.id.homeButton);
        bottom_menu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.homeButton:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
                        Log.d("LOG", "홈화면");
                        return true;
                    case R.id.qrButton:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, qrFragment).commit();
                        Log.d("LOG", "QR화면");
                        return true;
                    case R.id.wishlistButton:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, wishlistFragment).commit();
                        Log.d("LOG", "장바구니화면");
                        return true;
                    //Intent intent = new Intent(getApplicationContext(), OrderList.class);
                    //startActivity(intent);
                    //return true;
                }
                return false;
            }
        });
    }

    public String getToken() {
        return this.token;
    }
}