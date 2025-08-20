package com.example.dig4634.wearosexample;

import android.content.Intent;
import android.os.Bundle;
import androidx.activity.ComponentActivity;
import android.view.View;
import android.widget.TextView;



public class MainActivity extends ComponentActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.text);
    }

    public void onOKClick(View view){

        Intent i=new Intent(getBaseContext(),SecondActivity.class);
        startActivity(i);

    }
}
