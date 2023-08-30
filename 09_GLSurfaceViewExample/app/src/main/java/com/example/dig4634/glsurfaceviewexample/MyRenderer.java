package com.example.dig4634.glsurfaceviewexample;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLES30;

import gl.Texture;
import gl.renderers.GLRenderer;
import gl.renderers.ThirdEyeRenderer;

public class MyRenderer extends ThirdEyeRenderer {

    Triangle my_triangle;
    Cube my_cube;
    Texture wooden_texture;

    public MyRenderer(Activity activity){
        super(activity);
    }




    @Override
    public void setup() {

        wooden_texture=new Texture(getContext(),"box.png");

        my_triangle=new Triangle();
        my_triangle.setTexture(wooden_texture);
        my_triangle.localTransform.translate(0,0,-5);


        my_cube=new Cube();
        my_cube.setTexture(wooden_texture);
        my_cube.localTransform.translate(0,0,-5);

        //background(153/255f,	204/255f,	255/255f);
        setLightDir(0,-1,-1);

    }


    double lastTime=0;

    @Override
    public void simulate(double elapsedDisplayTime) {

        float perSec=(float)(elapsedDisplayTime-lastTime);
        lastTime=elapsedDisplayTime;


        my_cube.localTransform.rotateX(20*perSec);
        my_cube.localTransform.rotateZ(20*perSec);
        my_cube.localTransform.updateShader();

    }

    @Override
    public void draw() {

        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT| GLES30.GL_DEPTH_BUFFER_BIT);


        my_cube.draw();


    }
}
