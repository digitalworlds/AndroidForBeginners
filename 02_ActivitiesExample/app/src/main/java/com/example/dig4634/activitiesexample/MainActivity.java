package com.example.dig4634.activitiesexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    @Override
    protected void onResume(){
        super.onResume();

        if(GlobalVariables.AndroidLogoShown){
            ImageView myFrame=findViewById(R.id.logoFrame);
            myFrame.setImageResource(R.drawable.ic_launcher_foreground);
        }
        else {
            ImageView myFrame=findViewById(R.id.logoFrame);
            myFrame.setImageResource(R.drawable.dw_logo);
        }

    }


    public void onSettingsPressed(View view){

        Intent myIntent=new Intent(getBaseContext(),SettingsActivity.class);
        startActivity(myIntent);

    }
}
