package com.example.dig4634.glsurfaceviewexample;

import gl.modeltypes.ColoredModel;
import gl.modeltypes.TexturedModel;

public class Triangle extends TexturedModel {

    Triangle(){
        super();

        setXYZ(new float[]{
           0,0,0,//lower left corner (id: 0)
           1,0,0,//lower right corner (id: 1)
           0,1,0//upper left corner (id: 2)
        });

        setTriangles(new short[]{
           0,1,2//my first triangle
        });

        setUV(new float[]{
                0,0, //This is the pixel of the texture that will be attached to vertex (id:0)
                1,0, //This is the pixel of the texture that will be attached to vertex (id:1)
                0,1  //This is the pixel of the texture that will be attached to vertex (id:2)
        });

        /*setColors(new float[]{
           1,0,0, //the color of the point id:0
           0,1,0, //the color of the point id:1
           0,0,1  //the color of the point id:2
        });*/


    }


}
