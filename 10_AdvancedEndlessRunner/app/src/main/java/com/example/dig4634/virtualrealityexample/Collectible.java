package com.example.dig4634.virtualrealityexample;

import gl.models.Spaceship;
import gl.modeltypes.ShadedColoredModel;

public class Collectible extends ShadedColoredModel {

    public boolean shown=true;

    public float segments=0;

    public float positionX=0;
    public float positionY=0;
    public float positionZ=0;

    public float speedX=0;
    public float speedY=0;
    public float speedZ=0;

    public float accelarationX=0;
    public float accelarationY=0;
    public float accelarationZ=0;

    public float angleX=0;
    public float angleZ=0;

    Collectible(){
        super();

        new Spaceship((int)Math.floor(Math.random()*Spaceship.getNumOfModels()),this);
        /*
        setXYZ(new float[]{-0.5f,0.5f,0.5f,
                0.5f,0.5f,0.5f,
                -0.5f,-0.5f,0.5f,
                0.5f,-0.5f,0.5f,
                -0.5f,0.5f,-0.5f,
                0.5f,0.5f,-0.5f,
                -0.5f,-0.5f,-0.5f,
                0.5f,-0.5f,-0.5f,
                0.5f,0.5f,0.5f,
                0.5f,-0.5f,0.5f,
                0.5f,0.5f,-0.5f,
                0.5f,-0.5f,-0.5f,
                -0.5f,0.5f,0.5f,
                -0.5f,-0.5f,0.5f,
                -0.5f,0.5f,-0.5f,
                -0.5f,-0.5f,-0.5f,
                -0.5f,0.5f,0.5f,
                0.5f,0.5f,0.5f,
                -0.5f,0.5f,-0.5f,
                0.5f,0.5f,-0.5f,
                -0.5f,-0.5f,0.5f,
                0.5f,-0.5f,0.5f,
                -0.5f,-0.5f,-0.5f,
                0.5f,-0.5f,-0.5f});


        setTriangles(new short[]{0,2,1,1,2,3,4,5,6,6,5,7,9,11,8,8,11,10,13,12,15,15,12,14,16,17,18,18,17,19,21,20,22,21,22,23});

        setUV(new float[]{0,1,1,1,0,0,1,0,1,1,0,1,1,0,0,0,0,1,0,0,1,1,1,0,1,1,1,0,0,1,0,0,0,0,1,0,0,1,1,1,1,0,0,0,1,1,0,1});

        setNormals(new float[]{0,0,1,0,0,1,0,0,1,0,0,1,0,0,-1,0,0,-1,0,0,-1,0,0,-1,1,0,0,1,0,0,1,0,0,1,0,0,-1,0,0,-1,0,0,-1,0,0,-1,0,0,0,1,0,0,1,0,0,1,0,0,1,0,0,-1,0,0,-1,0,0,-1,0,0,-1,0});

        */

    }

    public void simulate(float perSec){

        angleX+=20*perSec;
        angleZ+=20*perSec;

        speedX+=accelarationX*perSec;
        speedY+=accelarationY*perSec;
        speedZ+=accelarationZ*perSec;

        positionX+=speedX*perSec;
        positionY+=speedY*perSec;
        positionZ+=speedZ*perSec;

        if(positionZ>-2){
            positionZ=-2-segments*4;
            shown=true;
        }


        localTransform.reset();
        localTransform.translate(positionX,positionY,positionZ);
        localTransform.rotateX(angleX);
        localTransform.rotateZ(angleZ);
        localTransform.scale(0.5f,0.5f,0.5f);
        localTransform.updateShader();
    }
}
