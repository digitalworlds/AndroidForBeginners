package com.example.dig4634.virtualrealityexample;

import android.app.Activity;
import android.opengl.GLES30;
import android.view.MotionEvent;
import android.view.View;
import gl.renderers.GyroscopicRenderer;

public class MyRenderer extends GyroscopicRenderer implements View.OnTouchListener {


    SandboxModel my_sandboxModel;

    public MyRenderer(Activity activity){
        super(activity);
    }


    @Override
    public void setup() {

        //In this app we only use 1 model, the SandboxModel.
        //Go to SandboxModel.java to edit its geometry.
        my_sandboxModel =new SandboxModel();
        my_sandboxModel.localTransform.translate(0,0,-5);
        my_sandboxModel.localTransform.updateShader();

        background(1f,1f,1f);//white background
        setLightDir(0,-1,-1);

        setRotationCenter(0,0,-5);
        setFOV(80);

    }


    double lastTime=0;

    @Override
    public void simulate(double elapsedDisplayTime) {

        float perSec=(float)(elapsedDisplayTime-lastTime);
        lastTime=elapsedDisplayTime;

        //modify animation variables here

    }

    @Override
    public void draw() {

        //On every frame this method will be called to draw the scene from the current perspective

        //First we clear the previous frame
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT| GLES30.GL_DEPTH_BUFFER_BIT);

        //And then we draw the model
        my_sandboxModel.draw();

    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return true;
    }
}
