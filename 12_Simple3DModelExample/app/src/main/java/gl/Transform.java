package gl;

import android.opengl.Matrix;

import java.util.ArrayList;
import java.util.List;

public class Transform {
    public float[] matrix=new float[16];
    private final List<float[]> matrixList=new ArrayList<float[]>();

    private boolean modified=true;

    public Transform(){
        identity();
    }

    private Mesh mesh;

    public Transform(Mesh mesh){
        this.mesh=mesh;
        identity();
    }

    public void updateShader(){
        if(!modified)return;
        modified=false;
        Shader shader=mesh.shader;
        shader.use();
        shader.setUniformMat4("localTransform",matrix);
    }

    public Transform rotate(float degrees, float x, float y, float z){
        Matrix.rotateM(matrix, 0, degrees, x, y, z);
        modified=true;
        return this;
    }

    public Transform rotateX(float degrees){
        Matrix.rotateM(matrix, 0, degrees, 1, 0, 0.0f);
        modified=true;
        return this;
    }

    public Transform rotateY(float degrees){
        Matrix.rotateM(matrix, 0, degrees, 0, 1, 0.0f);
        modified=true;
        return this;
    }

    public Transform rotateZ(float degrees){
        Matrix.rotateM(matrix, 0, degrees, 0, 0, 1.0f);
        modified=true;
        return this;
    }

    public Transform translate(float x, float y, float z){
        Matrix.translateM(matrix,0,x,y,z);
        modified=true;
        return this;
    }

    public Transform scale(float x, float y , float z){
        Matrix.scaleM(matrix,0,x,y,z);
        modified=true;
        return this;
    }

    public Transform identity(){
        Matrix.setIdentityM(matrix,0);
        modified=true;
        return this;
    }

    public Transform reset(){
        return identity();
    }

    public Transform multiply(float[] mat){
        float[] copy=new float[16];
        System.arraycopy(matrix,0,copy,0,16);
        Matrix.multiplyMM(matrix,0,copy,0,mat,0);
        modified=true;
        return this;
    }

    public Transform pushMatrix(){
        matrixList.add(matrix) ;
        float[] m=new float[16];
        for(int i=0;i<16;i++)m[i]=matrix[i];
        matrix=m;
        return this;
    }

    public Transform popMatrix(){
        if(matrixList.size()>0)
            matrix=matrixList.remove(matrixList.size()-1);
        return this;
    }
}
