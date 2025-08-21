package com.example.dig4634.developedexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onStartClick(View view){

        Intent i=new Intent(getBaseContext(),LevelActivity.class);
        startActivity(i);

    }

    public void onSettingsClick(View view){

        Intent i=new Intent(getBaseContext(),SettingsActivity.class);
        startActivity(i);

    }
}
