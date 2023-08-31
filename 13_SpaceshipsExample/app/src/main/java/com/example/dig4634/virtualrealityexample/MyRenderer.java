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

    Texture metal_texture;
    Texture brick_texture;

    Background360 my_background;
    Texture background_texture;
    Texture earth_texture;
    TexturedSphere earth;

    ShadedTexturedModel my_object;
    House my_house;
    Tree my_tree;

    public MyRenderer(Activity activity){
        super(activity);
    }


    @Override
    public void setup() {

        metal_texture=new Texture(getContext(),"planet_3_d.jpg");
        brick_texture=new Texture(getContext(),"bricks.jpg");

        background_texture=new Texture(getContext(),"eso0932a.jpg");
        earth_texture=new Texture(getContext(),"earth_1024.jpg");

        earth=new TexturedSphere(1,1,1);
        earth.setTexture(earth_texture);
        earth.localTransform.translate(0,0,-5);
        earth.localTransform.updateShader();


        ObjectMaker om=new ObjectMaker();

        om.color(0.5f,0.5f,0.5f);//gray
        om.cylinder(0.5f,2,0.5f);
        om.translate(0,1.5f,0);
        om.color(0,1,0);
        om.box(1,1,1);
        om.pushMatrix();
            om.translate(0.5f,0,0);
            om.rotateZ(-90);
            om.color(1,0,0);
            om.pyramid(1,1,1);
         om.popMatrix();
         om.translate(-1,0,0);
         om.color(0,0,1);
         om.sphere(1,1,1);

        my_object=new ShadedTexturedModel();
        om.flush(my_object);
        my_object.localTransform.translate(0,0,-5);
        my_object.localTransform.updateShader();
        my_object.setTexture(metal_texture);


        my_house=new House();
        my_house.localTransform.translate(0,0,-5);
        my_house.localTransform.updateShader();

        my_tree=new Tree();
        my_tree.localTransform.translate(1,0,-5);
        my_tree.localTransform.updateShader();

        my_level=new Level(10,metal_texture,brick_texture);


        my_background=new Background360();
        my_background.setTexture(background_texture);

        //background(0,0,0);
        background(153/255f,	204/255f,	255/255f);
        setLightDir(0,-1,-1);

        setRotationCenter(0,0,-5);
        setFOV(80);

    }


    double lastTime=0;

    @Override
    public void simulate(double elapsedDisplayTime) {

        float perSec=(float)(elapsedDisplayTime-lastTime);
        lastTime=elapsedDisplayTime;

        my_level.simulate(perSec);

        //collision detection logic
        for(int i=0;i<my_level.level_segments.length;i++){
            if(my_level.level_segments[i].collectible!=null){

                //if(Math.abs(my_level.level_segments[i].collectible.positionZ-my_cube.positionZ)<0.5 &&
                 //       Math.abs(my_level.level_segments[i].collectible.positionY-my_cube.positionY)<0.5)
                 //   my_level.level_segments[i].collectible.shown=false;
            }
        }

    }

    @Override
    public void draw() {

        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT| GLES30.GL_DEPTH_BUFFER_BIT);

        my_background.draw();

        //earth.draw();

        //my_object.draw();
        //my_house.draw();
        //my_tree.draw();

        my_level.draw();

    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        if(motionEvent.getAction()==MotionEvent.ACTION_DOWN){
            Log.d("Example","tap");
            //my_cube.speedY=3f;
        }

        return false;
    }
}
