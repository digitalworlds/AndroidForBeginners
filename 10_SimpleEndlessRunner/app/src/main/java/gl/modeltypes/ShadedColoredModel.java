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
        setAmbientColor(new float[]{0.5f,0.5f,0.5f});
        setDiffuseColor(new float[]{0.5f,0.5f,0.5f});
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

    public void draw(){
        GLES30.glUseProgram(shader.shaderProgram);
        GLES30.glBindVertexArray( vertexArrayObject ) ;

        GLES30.glDrawElements(GLES30.GL_TRIANGLES, triangleLength, GLES30.GL_UNSIGNED_SHORT,0);
        GLES30.glBindVertexArray( 0 );
        GLES30.glUseProgram( 0 );

    }
}

