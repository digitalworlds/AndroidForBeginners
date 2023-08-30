package com.example.dig4634.wearosexample;

public class Animator extends Thread {

    SecondActivity surfaceActivity;
    boolean is_running=false;

    public Animator(SecondActivity activity){
        surfaceActivity=activity;
    }

    public void run(){
        is_running=true;

        while(is_running){

            surfaceActivity.draw();

            try {
                sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }

    public void finish(){
        is_running=false;
    }

}
