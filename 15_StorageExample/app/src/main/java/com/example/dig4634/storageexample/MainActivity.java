package com.example.dig4634.storageexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GlobalVariables.LoadDataAsText(this);

        Log.d("Example","Level="+GlobalVariables.level);

        GlobalVariables.level+=1;



    }



    @Override
    protected void onPause(){

        GlobalVariables.SaveDataAsText(this);

        super.onPause();
    }




}
