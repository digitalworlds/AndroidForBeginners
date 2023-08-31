package com.example.dig4634.virtualrealityexample;


import android.opengl.Matrix;

import gl.ObjectMaker;
import gl.Texture;
import gl.modeltypes.ShadedTexturedModel;
import gl.renderers.GLRenderer;

public class Level {

    Segment[] segments;

    float user_angle;
    int prev_i;

    int num_of_segments=60;
	int key_frame_freq=5;
	int circle_freq=1;
    int res=31;
    float[] xyz;
    float[] uv;
    short[] tri;
    ShadedTexturedModel sphere;
    ShadedTexturedModel circle;
    ShadedTexturedModel disc;

    ObjectMaker path_maker;
	ObjectMaker disc_maker;
	ObjectMaker comet_maker;



    public Level(){

        this.path_maker=new ObjectMaker();
        this.disc_maker=new ObjectMaker();
        this.comet_maker=new ObjectMaker();

        //calculate the xyz of a circle for the path object
        xyz=new float[3*this.res];
        int c1=0;
        for(int i=0;i<this.res;i++)
        {
            this.xyz[c1]=(float)(0.5*Math.cos(2*3.1416*i/(this.res-1)-3.1416/2));c1+=1;
            this.xyz[c1]=(float)(0.5*Math.sin(2*3.1416*i/(this.res-1)-3.1416/2));c1+=1;
            this.xyz[c1]=0;c1+=1;
        }

        //create a fixed list of triangles and UV map for the path object
        this.uv=new float[2*this.res*2];
        this.tri=new short[6*(this.res-1)];
        int c2=0;
        int c3=0;
        int c4=0;
        for(int j=0;j<2;j++)
            for(int i=0;i<this.res;i++)
            {
                this.uv[c2]=1f-i/(this.res-1f);c2+=1;
                this.uv[c2]=(j*1f/this.key_frame_freq);c2+=1;
                if(j<1&&i<this.res-1)
                {
                    this.tri[c3]=(short)c4;c3+=1;this.tri[c3]=(short)(c4+this.res);c3+=1;this.tri[c3]=(short)(c4+1);c3+=1;
                    this.tri[c3]=(short)(c4+1);c3+=1;this.tri[c3]=(short)(c4+this.res);c3+=1;this.tri[c3]=(short)(c4+this.res+1);c3+=1;
                }
                c4+=1;
            }

        ObjectMaker object_maker=new ObjectMaker();

        //Create the shape of a comet
        object_maker.sphere(1,1,1,7);
        this.sphere=new ShadedTexturedModel();
        object_maker.flush(this.sphere);
        this.sphere.setTexture(Textures.brick_texture);
        this.sphere.setDiffuseColor(new float[]{1,1,1});

        //Create the shape of a circle that is made out or 12 cylinders.
        for(int i=0;i<12;i++)
        {
            object_maker.pushMatrix();
            object_maker.rotate(i*180/6,0,0,1);
            object_maker.translate(0,4,0);
            object_maker.rotate((float)(Math.random()*2*180),1,0,0);
            object_maker.rotateZ(-180/2);
            object_maker.cylinder(2,2,1,21);
            object_maker.popMatrix();
        }
        this.circle=new ShadedTexturedModel();
        object_maker.flush(this.circle);
        this.circle.setTexture(Textures.brick_texture);

        object_maker.translate(0,4,0);
        object_maker.rotateX(180/2);
        object_maker.cylinder(2,2,1,21);
        this.disc=new ShadedTexturedModel();
        object_maker.flush(this.disc);

        //create the array of segments
        this.segments=new Segment[this.num_of_segments];
        for(int i=0;i<this.num_of_segments;i++)
        {
            Segment seg=new Segment(this,i+1);
            this.segments[i]=seg;
            if(i==25)this.circle_freq=5;
            else if(i==40)this.circle_freq=0;
            if(i>0)seg.updateGeometry(this.segments[i-1]);
            else seg.updateGeometry(null);
        }

        this.prev_i=this.key_frame_freq;
        this.user_angle=0;



    }

    public int getPosition(){
        return this.prev_i;
    }

    public void draw()
    {
        //draw the level segments
        for(int i=0;i<this.num_of_segments;i++)
            this.segments[i].draw();
    }

    public void simulate(GLRenderer renderer, float t, float angle, float perSec){
        this.user_angle=angle;
        goTo(renderer,t,angle);
        for(int j=0;j<this.segments.length;j++)this.segments[j].simulate(perSec);
    }

    private void goTo(GLRenderer renderer, float t, float angle)
    {
        int i=(int)Math.floor(t);
        if(this.prev_i<i){
            Segment s=this.segments[0];
            for(int j=0;j<this.segments.length-1;j++)
                this.segments[j]=this.segments[j+1];
            s.updateGeometry(this.segments[this.segments.length-2]);
            s.flushKeySegment();
            this.segments[this.segments.length-1]=s;
        }

        this.prev_i=i;

        float w=t-i;
        float w1=2+w;w1=(3-w1)*(3-w1)/2;
        float w2=1+w;w2=(-2*w2*w2+6*w2-3)/2;
        float w3=w;w3=w3*w3/2;

        //this.segments[this.segments.length-1].fade(w);
        //this.segments[0].fade(1-w);

        float[] o1=this.segments[5].orientation1;
        float[] o2=this.segments[5].orientation2;
        float[] o3=this.segments[6].orientation2;
        float[] m=new float[]{1,0,0,0,0,1,0,0,0,0,1,0,0,0,0,1};
        for(int j=0;j<16;j++)
            m[j]=o1[j]*w1+o2[j]*w2+o3[j]*w3;

        float[] copy=new float[16];
        System.arraycopy(m,0,copy,0,16);
        Matrix.invertM(m,0,copy,0);

        renderer.view.translate(0,-1.5f,0);
        renderer.view.rotate(angle,0,0,1);
        renderer.view.multiply(m);
    };

}
