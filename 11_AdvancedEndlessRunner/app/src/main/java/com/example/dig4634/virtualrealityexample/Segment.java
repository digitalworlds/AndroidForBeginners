package com.example.dig4634.virtualrealityexample;

import android.opengl.Matrix;

import gl.modeltypes.ShadedTexturedModel;

public class Segment {

    int id;
    boolean is_keysegment=false;
    Level level;
    ShadedTexturedModel object;
    ShadedTexturedModel discs;
    ShadedTexturedModel comets;

    float comet_angle=0;

    float[] orientation1;
    float[] orientation2;
    float[] rot;

    public Segment(Level level, int id){

        this.id=id;
        this.level=level;
        this.level.path_maker.appendTriangles(this.level.tri);

        if(this.id%this.level.key_frame_freq==0) {
            this.is_keysegment = true;
        }
    }

    public void flushKeySegment()
    {
        if(!this.is_keysegment)return;



        this.initKeySegment();

        this.level.path_maker.flush(this.object);
        this.object.computeNormals();//because we do not provide them
        //this.object.computeTangents();//because we use normalmap

        this.level.comet_maker.flush(this.comets);

        this.level.disc_maker.flush(this.discs);
        //this.discs.computeTangents();//because we use normalmap
    };

    public void updateGeometry(Segment previous) {
        //compute a random orientation for the new segment
        this.orientation1=new float[]{1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1};
        if (previous!=null)
            System.arraycopy(previous.orientation2,0,this.orientation1,0,16);
        this.orientation2 =new float[]{1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1};
        System.arraycopy(this.orientation1,0,this.orientation2,0,16);
        Matrix.translateM(this.orientation2,0,0,0,-1);
        float[] r =new float[]{(float)(2 * Math.random() - 1), (float)(2 * Math.random() - 1), 0};
        if (previous!=null) {
            r[0] += previous.rot[0];
            r[1] += previous.rot[1];
            r[2] += previous.rot[2];
        }
        this.rot = r;
        float m = (float)Math.sqrt(r[0] * r[0] + r[1] * r[1] + r[2] * r[2]);
        r[0] /= m;
        r[1] /= m;
        r[2] /= m;
        Matrix.rotateM(this.orientation2,0,180/20,r[0],r[1],r[2]);

        //add two circles of xyz points with the new orientation
        for (int j = 0; j < 2; j++) {
            this.level.path_maker.pushMatrix();
            if (j == 0) this.level.path_maker.multiply(this.orientation1);
            else this.level.path_maker.multiply(this.orientation2);
            this.level.path_maker.appendXYZ(this.level.xyz);
            this.level.path_maker.popMatrix();
        }

        //update UV map, (only V based on the order-id of the segment)
        int c = 1;
        int mod = this.id % 5;
        for (int j = 0; j < 2; j++) {
            float v = (mod + j) / 5f;
            for (int i = 0; i < this.level.res; i++) {
                this.level.uv[c] = v;
                c += 2;
            }
        }
        this.level.path_maker.appendUV(this.level.uv);


        //add discs
        this.level.disc_maker.pushMatrix();
        this.level.disc_maker.multiply(this.orientation1);
        this.level.disc_maker.appendObject(this.level.disc);
        if (this.level.circle_freq == 0) {
            if (Math.random() < 0.04) this.level.disc_maker.appendObject(this.level.circle);
        } else if (this.id % this.level.circle_freq == 0)
            this.level.disc_maker.appendObject(this.level.circle);
        this.level.disc_maker.popMatrix();

        float rnd = (float)(2 * 180 * Math.random());
        //add three comets
        for (int i = 0; i < 3; i++) {
            this.level.comet_maker.pushMatrix();
            this.level.comet_maker.identity();//multiply(this.orientation1);
            this.level.comet_maker.rotate((float)(rnd + i * 180 / 3),0, 0, 1);
            this.level.comet_maker.translate(0, (float)(3 + 5 * Math.random()), 0);
            float s = (float)(0.1 + 0.4 * Math.random());
            this.level.comet_maker.scale(s, s, s);
            this.level.comet_maker.rotate((float)(2 * 180 * Math.random()),(float)(2 * Math.random() - 1), (float)(2 * Math.random() - 1), (float)(2 * Math.random() - 1));
            this.level.comet_maker.appendObject(this.level.sphere);
            this.level.comet_maker.popMatrix();
        }
        this.flushKeySegment();
    }

    private void initKeySegment()
    {
        if(this.object!=null)return;

        this.object=new ShadedTexturedModel();
        this.level.path_maker.flush(this.object);
        this.object.setTexture(Textures.brick_texture);
        //this.object.setMaterial(this.level.path_material);

        this.discs=new ShadedTexturedModel();
        this.level.disc_maker.flush(this.discs);
        this.discs.setTexture(Textures.metal_texture);
        //this.discs.setMaterial(this.level.disc.getMaterial());

        this.comets=new ShadedTexturedModel();
        this.level.comet_maker.flush(this.comets);
        this.comets.setTexture(Textures.planet_texture);
        //this.comets.setMaterial(this.level.sphere.getMaterial());
    };

    public void simulate(float perSec){
        if(this.is_keysegment) {
            this.comets.localTransform.identity();
            comet_angle += 5 * perSec;
            this.comets.localTransform.multiply(this.orientation1);
            this.comets.localTransform.rotate(comet_angle, 0, 0, 1);
            this.comets.localTransform.updateShader();
        }
    }

    public void draw()
    {
        if(this.is_keysegment)
        {
            this.object.draw();
            this.discs.draw();
            this.comets.draw();
        }
    }

}
