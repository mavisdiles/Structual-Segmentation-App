package com.example.mobile;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class LoadActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);

        TextView show_text;
        show_text = (TextView) findViewById(R.id.textView);
        connect cnt = new connect();
        cnt.connect();

        try {
            while (cnt.connected == false) {
            }
            System.out.println("데이터 로딩중 ...");
            while (cnt.return_data == null) {
            }
            Log.w("received data", cnt.return_data);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(LoadActivity.this, MainActivity.class);
                    intent.putExtra("section", cnt.return_data);
                    startActivity(intent);
                }
            }, 2000);
        } catch (Exception ex) {
        }

    }
}
