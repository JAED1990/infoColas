package com.dxtre.www.colas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent();

        intent.setClass(MainActivity.this, MapActivity.class);

        intent.putExtra("lat", "-6.774201");
        intent.putExtra("lng", "-79.849368");

        startActivity(intent);

    }
}
