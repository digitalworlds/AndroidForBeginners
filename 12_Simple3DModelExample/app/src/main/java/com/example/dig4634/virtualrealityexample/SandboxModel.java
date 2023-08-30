package com.example.dig4634.virtualrealityexample;


import gl.ObjectMaker;
import gl.modeltypes.ShadedColoredModel;

public class SandboxModel extends ShadedColoredModel {

    public SandboxModel(){

        ObjectMaker om=new ObjectMaker();

        //You can use this space as your sandbox to make your own model.
        //In general you have to repeat the following 3 steps:
        // 1. go to a new location (using translate, rotate, scale, identity),
        // 2. pick a color
        // 3. add a primitive shape block to your model (box, pyramid, trapezoid, cylinder, sphere, etc)

        //The following is just an example:

        //white box for house
        om.color(1,1,1);
        om.box(0.5f,0.5f,0.5f);

        //red pyramid for roof
        om.translate(0,0.25f,0);
        om.color(1,0,0);
        om.pyramid(0.5f,0.5f,0.5f);

        //gray chimney
        om.translate(-0.15f,0.25f,0);
        om.color(0.5f,0.5f,0.5f);
        om.cylinderY(0.1f,0.4f,0.1f,8);


        //add around the house several trees
        for(int i=0;i<30;i++) {
            //pick a random spot around the house
            float x=(float)Math.random()*2-1; x=Math.signum(x)+x*1.5f;
            float z=(float)Math.random()*2-1; z=Math.signum(z)+z*1.5f;

            om.identity();//resets the coordinate system
            om.translate(x, 0, z);//go to the randomly selected spot

            om.color(101 / 255f, 67 / 255f, 33 / 255f);//brown color
            om.cylinderY(0.15f, 0.5f, 0.15f, 8);
            om.translate(0, 0.5f, 0);
            om.color(0, 0.5f, 0);//dark green color
            om.sphere(0.5f, 1, 0.5f,8);
        }


        //Helpful tips:
        //1.Use the accelerometer to see your model from various angles as you edit it.

        //2.If you want to see where is your current coordinate system use the following line:
        //axis(om);

        //3.If you want to see a 5x5x5 space box use the following line:
        //area(om);

        //4.When you are done please delete the axis or area box.


        //At the end flush all the vertices (XYZ, triangles, etc) as one solid object.
        om.flush(this);

    }







    private void area(ObjectMaker om){
        float size=5;
        float width=0.1f;
        om.pushMatrix();
        om.color(0.5f,0.5f,0.5f);

        om.identity();
        om.translate(0,size/2,-size/2);
        om.cylinderX(size,width,width,8);
        om.translate(0,-size,0);
        om.cylinderX(size,width,width,8);
        om.translate(0,0,size);
        om.cylinderX(size,width,width,8);
        om.translate(0,size,0);
        om.cylinderX(size,width,width,8);

        om.identity();
        om.rotateY(90);
        om.translate(0,size/2,-size/2);
        om.cylinderX(size,width,width,8);
        om.translate(0,-size,0);
        om.cylinderX(size,width,width,8);
        om.translate(0,0,size);
        om.cylinderX(size,width,width,8);
        om.translate(0,size,0);
        om.cylinderX(size,width,width,8);

        om.identity();
        om.rotateZ(90);
        om.translate(0,size/2,-size/2);
        om.cylinderX(size,width,width,8);
        om.translate(0,-size,0);
        om.cylinderX(size,width,width,8);
        om.translate(0,0,size);
        om.cylinderX(size,width,width,8);
        om.translate(0,size,0);
        om.cylinderX(size,width,width,8);

        om.popMatrix();
    }

    private void axis(ObjectMaker om){
        float width=0.1f;
        float length=2f;
        om.pushMatrix();
        om.color(0,1,0);
        om.cylinderY(width,length,width,8);
        om.rotateX(90);
        om.color(0,0,1);
        om.cylinderY(width,length,width,8);
        om.rotateZ(90);
        om.color(1,0,0);
        om.cylinderY(width,length,width,8);
        om.popMatrix();
    }

}
