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
        setAmbientColor(new float[]{0.5f,0.5f,0.5f});
        setDiffuseColor(new float[]{0.5f,0.5f,0.5f});
    }

    public void setUV(float[] uv){
        setArrayBuffer2f(uv,1);
    }

    public void setNormals(float[] normals){
        setArrayBuffer3f(normals,2);
    }

    public void setAmbientColor(float[] color){
        int mHandle = GLES30.glGetUniformLocation(shader.shaderProgram, "uAmbientColor");
        GLES30.glUniform3fv(mHandle,1, color,0);
    }

    public void setDiffuseColor(float[] color){
        int mHandle = GLES30.glGetUniformLocation(shader.shaderProgram, "uDiffuseColor");
        GLES30.glUniform3fv(mHandle,1, color,0);
    }

    protected Texture texture=null;

    public void setTexture(Texture texture){
        this.texture=texture;
        int mTextureHandle = GLES30.glGetUniformLocation(shader.shaderProgram, "uTexture");
        // Tell the texture uniform sampler to use this texture in the shader by binding to texture unit 0.
        GLES30.glUniform1i(mTextureHandle, texture.slot);
    }

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

