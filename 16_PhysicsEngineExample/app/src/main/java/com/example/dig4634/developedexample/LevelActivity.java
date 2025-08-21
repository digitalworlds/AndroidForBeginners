package com.example.dig4634.developedexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class LevelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
    }

    @Override
    protected void onResume(){
        super.onResume();

        if(GlobalVariables.passed_level[0]){
            Button b=findViewById(R.id.level2_button);
            b.setEnabled(true);
            Log.d("Example","2");

        }
        if(GlobalVariables.passed_level[1]){
            Button b=findViewById(R.id.level3_button);
            b.setEnabled(true);
            Log.d("Example","3");
        }
    }

    public void onLevelClick(View view){

        if(view.getId()==R.id.level1_button) {
            Intent i = new Intent(getBaseContext(), GameActivity.class);
            i.putExtra("Level",1);
            startActivity(i);
        }
        else if(view.getId()==R.id.level2_button) {
            Intent i = new Intent(getBaseContext(), GameActivity.class);
            i.putExtra("Level",2);
            startActivity(i);
        }
        else if(view.getId()==R.id.level3_button) {
            Intent i = new Intent(getBaseContext(), GameActivity.class);
            i.putExtra("Level",3);
            startActivity(i);
        }

    }
}
