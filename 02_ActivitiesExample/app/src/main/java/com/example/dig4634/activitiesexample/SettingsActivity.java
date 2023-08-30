package com.example.dig4634.activitiesexample;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Switch dw_switch=findViewById(R.id.dw_switch);
        if(GlobalVariables.AndroidLogoShown)
            dw_switch.setChecked(false);
        else dw_switch.setChecked(true);

    }

    @Override
    protected void onPause(){
        Switch dw_switch=findViewById(R.id.dw_switch);
        if(dw_switch.isChecked())
            GlobalVariables.AndroidLogoShown=false;
        else GlobalVariables.AndroidLogoShown=true;

        super.onPause();

    }

    public void onBackPressed(View view){

        finish();

    }



}
