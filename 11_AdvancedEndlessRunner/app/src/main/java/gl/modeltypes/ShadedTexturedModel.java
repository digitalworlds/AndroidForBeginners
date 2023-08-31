package gl.modeltypes;

import android.opengl.GLES30;

import gl.Mesh;
import gl.Shader;
import gl.Texture;
import gl.shaders.ShadedTextureShader;


public class ShadedTexturedModel extends Mesh {

    public ShadedTexturedModel(){
        super();

        Shader s=new ShadedTextureShader();
        setShader(s);
        localTransform.updateShader();
        setAmbientColor(new float[]{0.6f,0.6f,0.6f});
        setDiffuseColor(new float[]{0.4f,0.4f,0.4f});
        setSpecularColor(new float[]{0.4f,0.4f,0.4f});
        setSpecularExponent(5);
    }

    float[] uv;
    public float[] getUV(){return uv;}
    public void setUV(float[] uv){
        this.uv=uv;setArrayBuffer2f(uv,1);
    }

    float[] normals;
    public float[] getNormals(){return normals;}
    public void setNormals(float[] normals){
        this.normals=normals;setArrayBuffer3f(normals,2);
    }

    public void setAmbientColor(float[] color){
        int mHandle = GLES30.glGetUniformLocation(shader.shaderProgram, "uAmbientColor");
        GLES30.glUniform3fv(mHandle,1, color,0);
    }

    public void setDiffuseColor(float[] color){
        int mHandle = GLES30.glGetUniformLocation(shader.shaderProgram, "uDiffuseColor");
        GLES30.glUniform3fv(mHandle,1, color,0);
    }

    public void setSpecularColor(float[] color){
        int mHandle = GLES30.glGetUniformLocation(shader.shaderProgram, "uSpecularColor");
        GLES30.glUniform3fv(mHandle,1, color,0);
    }

    public void setSpecularExponent(float exponent){
        int mHandle = GLES30.glGetUniformLocation(shader.shaderProgram, "uSpecularExponent");
        GLES30.glUniform1f(mHandle, exponent);
    }

    protected Texture texture=null;

    public void setTexture(Texture texture){
        this.texture=texture;
        int mTextureHandle = GLES30.glGetUniformLocation(shader.shaderProgram, "uTexture");
        // Tell the texture uniform sampler to use this texture in the shader by binding to texture unit 0.
        GLES30.glUniform1i(mTextureHandle, texture.slot);
    }

    public void computeNormals(){

            float[] XYZ=this.getXYZ();
            float[] NRM=new float[XYZ.length];
            short[] TRI=this.getTriangles();
            int l=TRI.length/3;
            int t=0;
            for(int i=0;i<l;i++)
            {
                int idx1=TRI[t]*3;
                float[] p1=new float[]{XYZ[idx1],XYZ[idx1+1],XYZ[idx1+2]};
                int idx2=TRI[t+1]*3;
                float[] p2=new float[]{XYZ[idx2],XYZ[idx2+1],XYZ[idx2+2]};
                int idx3=TRI[t+2]*3;
                float[] p3=new float[]{XYZ[idx3],XYZ[idx3+1],XYZ[idx3+2]};
                float[] v1=new float[]{p2[0]-p1[0],p2[1]-p1[1],p2[2]-p1[2]};
                float mag1=(float)Math.sqrt(v1[0]*v1[0]+v1[1]*v1[1]+v1[2]*v1[2]);
                float v2[]=new float[]{p3[0]-p1[0],p3[1]-p1[1],p3[2]-p1[2]};
                float mag2=(float)Math.sqrt(v2[0]*v2[0]+v2[1]*v2[1]+v2[2]*v2[2]);
                if(mag1>0&&mag2>0)//&& Math.abs(v1[0]*v2[0]+v1[1]*v2[1]+v1[2]*v2[2])/(mag1*mag2)<0.99)
                {
                    float n[]=new float[]{v1[1]*v2[2]-v1[2]*v2[1],v1[2]*v2[0]-v1[0]*v2[2],v1[0]*v2[1]-v1[1]*v2[0]};
                    float mag=(float)Math.sqrt(n[0]*n[0]+n[1]*n[1]+n[2]*n[2]);
                    if(mag>0){n[0]/=mag;n[1]/=mag;n[2]/=mag;
                        NRM[idx1]+=n[0];NRM[idx1+1]+=n[1];NRM[idx1+2]+=n[2];
                        NRM[idx2]+=n[0];NRM[idx2+1]+=n[1];NRM[idx2+2]+=n[2];
                        NRM[idx3]+=n[0];NRM[idx3+1]+=n[1];NRM[idx3+2]+=n[2];
                    }
                }
                t+=3;
            }
            l=NRM.length/3;
            t=0;
            for(int i=0;i<l;i++)
            {
                float mag=(float)Math.sqrt(NRM[t]*NRM[t]+NRM[t+1]*NRM[t+1]+NRM[t+2]*NRM[t+2]);
                if(mag>0){NRM[t]/=mag;NRM[t+1]/=mag;NRM[t+2]/=mag;}
                t+=3;
            }
            this.setNormals(NRM);
    }

    /*public void computeTangents(){
        if(this.buffers['aNormal']&&this.buffers['aUV']&&this.indices['Triangles'])
        {
            var XYZ=this.buffers['aXYZ'].data;
            var NRM=this.buffers['aNormal'].data;
            var TN1=new Float32Array(XYZ.length);
            var TN2=new Float32Array(XYZ.length);
            var UV=this.buffers['aUV'].data;
            var TRI=this.indices['Triangles'].data;
            var l=TRI.length/3;
            var t=0;
            for(var i=0;i<l;i++)
            {
                var idx1=TRI[t]*3;
                var p1=[XYZ[idx1],XYZ[idx1+1],XYZ[idx1+2]];
                var idx2=TRI[t+1]*3;
                var p2=[XYZ[idx2],XYZ[idx2+1],XYZ[idx2+2]];
                var idx3=TRI[t+2]*3;
                var p3=[XYZ[idx3],XYZ[idx3+1],XYZ[idx3+2]];
                var v1=[p2[0]-p1[0],p2[1]-p1[1],p2[2]-p1[2]];
                var v2=[p3[0]-p1[0],p3[1]-p1[1],p3[2]-p1[2]];

                var idx1_=TRI[t]*2;
                var uv1=[UV[idx1_],UV[idx1_+1]];
                var idx2_=TRI[t+1]*2;
                var uv2=[UV[idx2_],UV[idx2_+1]];
                var idx3_=TRI[t+2]*2;
                var uv3=[UV[idx3_],UV[idx3_+1]];
                var u1=[uv2[0]-uv1[0],uv2[1]-uv1[1]];
                var u2=[uv3[0]-uv1[0],uv3[1]-uv1[1]];

                var t1=[(v1[0]*u2[1]-v2[0]*u1[1]),(v1[1]*u2[1]-v2[1]*u1[1]),(v1[2]*u2[1]-v2[2]*u1[1])];
                var r=Math.sqrt(t1[0]*t1[0]+t1[1]*t1[1]+t1[2]*t1[2]);
                if(r>0){t1[0]/=r;t1[1]/=r;t1[2]/=r;}
                r=1.0/(u1[0]*u2[1]-u1[1]*u2[0]);
                var t2=[(v2[0]*u1[0]-v1[0]*u2[0])*r,(v2[1]*u1[0]-v1[1]*u2[0])*r,(v2[2]*u1[0]-v1[2]*u2[0])*r];

                //var n=[NRM[idx1],NRM[idx1+1],NRM[idx1+2]];
                //if(vec3.dot(vec3.cross(n,t1),t2)<0){t1[0]*=-1;t1[1]*=-1;t1[2]*=-1;}

                TN1[idx1]+=t1[0];TN1[idx1+1]+=t1[1];TN1[idx1+2]+=t1[2];
                TN1[idx2]+=t1[0];TN1[idx2+1]+=t1[1];TN1[idx2+2]+=t1[2];
                TN1[idx3]+=t1[0];TN1[idx3+1]+=t1[1];TN1[idx3+2]+=t1[2];
                TN2[idx1]+=t2[0];TN2[idx1+1]+=t2[1];TN2[idx1+2]+=t2[2];
                TN2[idx2]+=t2[0];TN2[idx2+1]+=t2[1];TN2[idx2+2]+=t2[2];
                TN2[idx3]+=t2[0];TN2[idx3+1]+=t2[1];TN2[idx3+2]+=t2[2];
                t+=3;
            }
            l=XYZ.length/3;
            t=0;
            for(var i=0;i<l;i++)
            {
                var mag=Math.sqrt(TN1[t]*TN1[t]+TN1[t+1]*TN1[t+1]+TN1[t+2]*TN1[t+2]);
                if(mag>0){TN1[t]/=mag;TN1[t+1]/=mag;TN1[t+2]/=mag;}
                mag=Math.sqrt(TN2[t]*TN2[t]+TN2[t+1]*TN2[t+1]+TN2[t+2]*TN2[t+2]);
                if(mag>0){TN2[t]/=mag;TN2[t+1]/=mag;TN2[t+2]/=mag;}
                t+=3;
            }
            l=XYZ.length/3;
            t=0;
            for(var i=0;i<l;i++)
            {
                var mag=Math.sqrt(TN1[t]*TN1[t]+TN1[t+1]*TN1[t+1]+TN1[t+2]*TN1[t+2]);
                if(mag>0){TN1[t]/=mag;TN1[t+1]/=mag;TN1[t+2]/=mag;}
                mag=Math.sqrt(TN2[t]*TN2[t]+TN2[t+1]*TN2[t+1]+TN2[t+2]*TN2[t+2]);
                if(mag>0){TN2[t]/=mag;TN2[t+1]/=mag;TN2[t+2]/=mag;}
                t+=3;
            }
            this.setData("aTangent",TN1,3);
            this.setData("aTangent2",TN2,3);
        }
    }*/

    public void draw(){
        GLES30.glUseProgram(shader.shaderProgram);
        GLES30.glBindVertexArray( vertexArrayObject ) ;


        if(texture!=null) {
            //shader.setUniformInteger("uTexture", texture.slot);
            GLES30.glActiveTexture(GLES30.GL_TEXTURE0 + texture.slot);
            // Bind the texture to this unit.
            GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, texture.data);
        }
        GLES30.glDrawElements(GLES30.GL_TRIANGLES, triangleLength, GLES30.GL_UNSIGNED_SHORT,0);
        GLES30.glBindVertexArray( 0 );
        GLES30.glUseProgram( 0 );

    }
}

