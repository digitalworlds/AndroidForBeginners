package com.example.dig4634.virtualrealityexample;

import android.content.Context;

import gl.Texture;
import gl.models.Background360;

public class Textures {
    public static Texture planet_texture;
    public static Texture brick_texture;
    public static Texture metal_texture;
    public static Texture background_texture;

    public Textures(final Context context){

        planet_texture=new Texture(context,"planet_3_d.jpg");
        brick_texture=new Texture(context,"bricks.jpg");
        metal_texture=new Texture(context,"metal.jpg");
        background_texture=new Texture(context,"eso0932a.jpg");
    }
}
