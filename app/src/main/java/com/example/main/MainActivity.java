package com.example.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tab.BookListTabActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent=new Intent();
//        intent.setClass(MainActivity.this, BookListMainActivity.class);
        intent.setClass(MainActivity.this, BookListTabActivity.class);
        startActivity(intent);
        MainActivity.this.finish();

    }
}