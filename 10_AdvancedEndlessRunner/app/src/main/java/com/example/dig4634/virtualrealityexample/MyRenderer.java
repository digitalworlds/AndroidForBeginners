package com.example.dig4634.virtualrealityexample;

import android.app.Activity;
import android.opengl.GLES30;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import gl.ObjectMaker;
import gl.Texture;
import gl.models.Background360;
import gl.models.House;
import gl.models.TexturedSphere;
import gl.models.Tree;
import gl.modeltypes.ShadedColoredModel;
import gl.modeltypes.ShadedTexturedModel;
import gl.renderers.GyroscopicRenderer;

public class MyRenderer extends GyroscopicRenderer implements View.OnTouchListener {

    Level my_level;
    Textures textures;
    Background360 my_background;

    public MyRenderer(Activity activity){
        super(activity);
    }


    @Override
    public void setup() {

        textures=new Textures(getContext());

        my_level=new Level();


        my_background=new Background360();
        my_background.setTexture(Textures.background_texture);

        //background(0,0,0);
        //background(153/255f,	204/255f,	255/255f);
        setLightDir(0,-1,-1);

        setRotationCenter(0,0,-5);
        setFOV(80);

    }


    double lastTime=0;

    @Override
    public void simulate(double elapsedDisplayTime) {

        float perSec=(float)(elapsedDisplayTime-lastTime);
        lastTime=elapsedDisplayTime;
        //my_level.simulate(perSec);
        t+=1*perSec;

        if(left_pressed)angle-=45*perSec;
        if(right_pressed)angle+=45*perSec;

        my_level.simulate(this,t,angle,perSec);
    }

    float t=0;
    float angle=0;
    boolean left_pressed=false;
    boolean right_pressed=false;

    @Override
    public void draw() {
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT| GLES30.GL_DEPTH_BUFFER_BIT);
        my_background.draw();
        my_level.draw();
    }


    @Override
    public boolean onTouch(View view, MotionEvent event) {

        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(event.getX()<width/2)left_pressed=true;
                else right_pressed=true;
                break;
            case MotionEvent.ACTION_UP:
                if(event.getX()<width/2)left_pressed=false;
                else right_pressed=false;
                break;
        }

        return true;
    }
}
