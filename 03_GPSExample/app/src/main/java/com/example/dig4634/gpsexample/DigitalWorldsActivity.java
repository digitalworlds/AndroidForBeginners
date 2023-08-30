package com.example.dig4634.gpsexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DigitalWorldsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_digital_worlds);

        Bundle extras=getIntent().getExtras();
        if(extras!=null) {

            ImageView frame = findViewById(R.id.photo_frame);
            frame.setImageResource(extras.getInt("pictureID"));

            TextView text = findViewById(R.id.caption);
            text.setText(extras.getString("caption"));
        }
    }
}
