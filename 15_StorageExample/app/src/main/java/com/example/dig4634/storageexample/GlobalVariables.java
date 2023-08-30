package com.example.dig4634.storageexample;

import android.content.Context;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;

public class GlobalVariables implements Serializable {

    public static int level=1;
    public static int score=0;
    public static float life=0.5f;
    public static boolean tutorial_done=false;
    public static String username="";


    public static void SaveData(Context app){

        try {
            FileOutputStream file=app.openFileOutput("my_data",Context.MODE_PRIVATE);
            ObjectOutputStream os=new ObjectOutputStream(file);
            os.writeObject(new GlobalVariables());
            os.close();
            file.close();

            Log.d("Example","Data Saved!");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void LoadData(Context app)  {


        try {
            FileInputStream file = app.openFileInput("my_data");
            ObjectInputStream os=new ObjectInputStream(file);
            os.readObject();
            os.close();
            file.close();

            Log.d("Example","Data loaded!");


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    public static void SaveDataAsText(Context app){

        try {
            FileOutputStream file=app.openFileOutput("my_text",Context.MODE_PRIVATE);
            BufferedWriter os=new BufferedWriter(new OutputStreamWriter(file));
            os.write(""+level);os.newLine();
            os.write(""+score);os.newLine();
            os.close();
            file.close();

            Log.d("Example","Data Saved as text!");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void LoadDataAsText(Context app)  {


        try {
            FileInputStream file = app.openFileInput("my_text");
            BufferedReader os=new BufferedReader(new InputStreamReader(file));
            level=Integer.parseInt(os.readLine());
            score=Integer.parseInt(os.readLine());

            file.close();

            Log.d("Example","Data loaded as text!");


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e){
            e.printStackTrace();
        }


    }


}
