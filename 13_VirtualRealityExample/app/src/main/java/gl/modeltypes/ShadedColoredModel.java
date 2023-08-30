package gl.modeltypes;

import android.opengl.GLES30;

import gl.Mesh;
import gl.Shader;
import gl.shaders.ShadedColorShader;


public class ShadedColoredModel extends Mesh {

    public ShadedColoredModel(){
        super();

        Shader s=new ShadedColorShader();
        setShader(s);
        localTransform.updateShader();
        setAmbientColor(new float[]{0.6f,0.6f,0.6f});
        setDiffuseColor(new float[]{0.4f,0.4f,0.4f});
        setSpecularColor(new float[]{0.4f,0.4f,0.4f});
        setSpecularExponent(5);
    }

    public void setColors(float[] colors){
        setArrayBuffer3f(colors,1);
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

    public void setSpecularColor(float[] color){
        int mHandle = GLES30.glGetUniformLocation(shader.shaderProgram, "uSpecularColor");
        GLES30.glUniform3fv(mHandle,1, color,0);
    }

    public void setSpecularExponent(float exponent){
        int mHandle = GLES30.glGetUniformLocation(shader.shaderProgram, "uSpecularExponent");
        GLES30.glUniform1f(mHandle, exponent);
    }

    public void draw(){
        GLES30.glUseProgram(shader.shaderProgram);
        GLES30.glBindVertexArray( vertexArrayObject ) ;

        GLES30.glDrawElements(GLES30.GL_TRIANGLES, triangleLength, GLES30.GL_UNSIGNED_SHORT,0);
        GLES30.glBindVertexArray( 0 );
        GLES30.glUseProgram( 0 );

    }
}

