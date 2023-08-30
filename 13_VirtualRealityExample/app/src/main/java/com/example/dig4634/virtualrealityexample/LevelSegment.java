package com.example.dig4634.virtualrealityexample;

import gl.ObjectMaker;
import gl.modeltypes.ShadedTexturedModel;

public class LevelSegment extends ShadedTexturedModel {

    public Collectible collectible;

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

    LevelSegment(){
        super();

        ObjectMaker om=new ObjectMaker();
        om.translate((float)(Math.random()*6-3),0,0);
        om.rotateY((float)(Math.random()*360));
        om.sphere(0.5f,0.5f,0.5f,5);
        om.flush(this);


        /*setXYZ(new float[]{-0.5f,0.5f,0.5f,
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


        speedX+=accelarationX*perSec;
        speedY+=accelarationY*perSec;
        speedZ+=accelarationZ*perSec;

        positionX+=speedX*perSec;
        positionY+=speedY*perSec;
        positionZ+=speedZ*perSec;

        if(positionZ>-2){
            positionZ=-2-segments*4;
        }


        localTransform.reset();
        localTransform.translate(positionX,positionY,positionZ);
        localTransform.rotateX(angleX);
        localTransform.rotateZ(angleZ);
        localTransform.scale(2,1,4);
        localTransform.updateShader();
    }
}
