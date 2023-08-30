package com.example.dig4634.developedexample;

public class Animator extends Thread {

    GameActivity surfaceActivity;
    boolean is_running=false;

    public Animator(GameActivity activity){
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
