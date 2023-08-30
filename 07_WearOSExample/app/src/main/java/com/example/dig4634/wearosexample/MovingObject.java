package com.example.dig4634.wearosexample;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class MovingObject {

    //has

    int x_position=0;
    int y_position=0;
    float angle=0;
    Bitmap image;

    //does

    MovingObject(Bitmap i, int x, int y){
        image=i;
        x_position=x;
        y_position=y;
    }

    void draw(Canvas c){

        c.save();
        c.translate(x_position+100,y_position+100);
        c.rotate(angle);
        c.drawBitmap(image,-100,-100,null);
        c.restore();

    }

    void updateWithAccelerometer(int width, int height, float acc_x, float acc_y){
        angle+=10;
        x_position-=acc_x*2;
        y_position+=acc_y*2;

        if(x_position<0)x_position=0;
        else if(x_position>width-200)x_position=width-200;

        if(y_position<0)y_position=0;
        else if(y_position>height-200)y_position=height-200;
    }

    boolean collidesWith(MovingObject sun){
        if(Math.abs(x_position-sun.x_position)<200 && Math.abs(y_position-sun.y_position)<200 )return true;
        else return false;
    }

}
