package com.example.dig4634.examplelayout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RatingBar rating=findViewById(R.id.ratingBar);
        rating.setNumStars(5);
        rating.setRating(5);
    }



    public void onClearButtonPressed(View view){

        EditText nameField=findViewById(R.id.FirstNameField);
        nameField.setText("");

        EditText lastnameField=findViewById(R.id.LastNameField);
        lastnameField.setText("");

        RatingBar rating=findViewById(R.id.ratingBar);
        rating.setRating(0);

        TextView message=findViewById(R.id.messageField);
        message.setText("");

    }

    public void onSubmitButtonClicked(View view){

        RatingBar rating=findViewById(R.id.ratingBar);


        TextView message=findViewById(R.id.messageField);
        message.setText("Your rating was: "+rating.getRating()+". Thank you for your feedback!");


    }
}
