package gl.shaders;

import gl.Shader;

public class ShadedColorShader extends Shader {

    public ShadedColorShader(){
        super(

                "#version 300 es\n"+
                        "uniform SceneMatrices\n"+
                        "{\n"+
                        "uniform mat4 uMVMatrix;\n" +
                        "uniform mat4 uPMatrix;\n" +
                        "uniform vec3 uLightDir;\n"+
                        "};\n"+

                "uniform mat4 localTransform;\n"+
                "in vec3 aPosition;" +
                "in vec3 aColor;" +
                        "in vec3 aNormal;"+
                        "out vec3 vColor;"+
                        "out vec3 vNormal;"+
                "void main() {"  +
                        " vec4 tN=normalize(localTransform *vec4(aNormal,0.0));" +
                        "vNormal=vec3(tN.x,tN.y,tN.z);"+
                "vColor=aColor;"+
                "gl_Position = uPMatrix*uMVMatrix *localTransform* vec4(aPosition,1.0);" +
                "}",




                        "#version 300 es\n"+
                                "precision mediump float;" +
                                "uniform SceneMatrices\n"+
                                "{\n"+
                                "uniform mat4 uMVMatrix;\n" +
                                "uniform mat4 uPMatrix;\n" +
                                "uniform vec3 uLightDir;\n"+
                                "};\n"+
                                "uniform vec3 uAmbientColor;"+
                                "uniform vec3 uDiffuseColor;"+
                    "in vec3 vColor;"+
                                "in vec3 vNormal;"+
                                "out vec4 myOutputColor;"+
                "void main() {" +
                                "vec4 shade=vec4(0.0,0.0,0.0,1.0);"+
                                "shade+=vec4(uAmbientColor,1.0);"+
                                "shade+=vec4(uDiffuseColor,1.0)*max(dot(vNormal,uLightDir),0.0);"+
                "  myOutputColor = vec4(vColor,1.0)*shade;" +
                "}",new String[]{"aPosition","aColor","aNormal"});
    }

}
