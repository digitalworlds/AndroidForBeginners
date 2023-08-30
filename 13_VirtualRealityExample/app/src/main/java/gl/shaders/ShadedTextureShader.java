package gl.shaders;

import gl.Shader;

public class ShadedTextureShader extends Shader {

    public ShadedTextureShader(){
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
                "in vec2 aUV;" +
                        "in vec3 aNormal;"+
                    "out vec2 vUV;"+
                        "out vec3 vNormal;"+
                        "out vec3 vE;"+
                "void main() {"  +
                "vUV=aUV;"+
                        " vec4 tN=normalize(localTransform *vec4(aNormal,0.0));" +
                        "vNormal=vec3(tN.x,tN.y,tN.z);"+
                        "vec4 p=uMVMatrix *localTransform* vec4(aPosition,1.0);"+
                        "gl_Position = uPMatrix*p;" +
                        "vE=normalize(vec3(p));"+
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
                                "uniform vec3 uSpecularColor;"+
                                "uniform float uSpecularExponent;"+
        "uniform sampler2D uTexture;" +
                    "in vec2 vUV;"+
                                "in vec3 vNormal;"+
                                "in vec3 vE;"+
                                "out vec4 myOutputColor;"+
                "void main() {" +
                                "vec3 E=normalize(vE);"+
                                "vec4 shade=vec4(0.0,0.0,0.0,1.0);"+
                                "shade+=vec4(uAmbientColor,1.0);"+
                                "shade+=vec4(uDiffuseColor,1.0)*max(dot(vNormal,uLightDir),0.0);"+
                                "vec3 reflectionDirection = reflect(uLightDir, vNormal);"+
                                "shade+=vec4(uSpecularColor,1.0)*pow(max(dot(reflectionDirection, E), 0.0), uSpecularExponent);"+

                                "vec4 color=texture(uTexture, vUV);"+
                "  myOutputColor = color*shade;" +
                "}",new String[]{"aPosition","aUV","aNormal"});
    }

}
