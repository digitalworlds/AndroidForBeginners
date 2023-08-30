package gl;

import gl.modeltypes.ShadedTexturedModel;

public class Box extends ShadedTexturedModel {

    public Box(){
        super();

        ObjectMaker om=new ObjectMaker();
        om.color(1,1,1);
        om.box(1,1,1);
        om.translate(0,0.5f,0);
        om.color(1,0,0);
        om.cylinder(1,1,1,8);
        om.flush(this);
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
        setNormals(new float[]{0,0,1,0,0,1,0,0,1,0,0,1,0,0,-1,0,0,-1,0,0,-1,0,0,-1,1,0,0,1,0,0,1,0,0,1,0,0,-1,0,0,-1,0,0,-1,0,0,-1,0,0,0,1,0,0,1,0,0,1,0,0,1,0,0,-1,0,0,-1,0,0,-1,0,0,-1,0});

        //For each vertex of the object you need to assign a pixel coordinate of the texture image. The pixel coordinates are in the range [0-1], (0,0) being the lower left pixel of the image, and (1,1) being the upper right pixel of the image. In this example we have 24 vertices and we assign to all of them a pixel location from the texture image:
        setUV(new float[]{0,1,1,1,0,0,1,0,1,1,0,1,1,0,0,0,0,1,0,0,1,1,1,0,1,1,1,0,0,1,0,0,0,0,1,0,0,1,1,1,1,0,0,0,1,1,0,1});



        setTriangles(new short[]{0,2,1,1,2,3,4,5,6,6,5,7,9,11,8,8,11,10,13,12,15,15,12,14,16,17,18,18,17,19,21,20,22,21,22,23});
        */
    }



}
