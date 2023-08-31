package gl.shaders;


import gl.Shader;

public class MatCapShader extends Shader {

    public MatCapShader(){
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
                        "in vec3 aNormal;" +
                        "out vec3 vNormal;"+
                        "out vec3 vE;"+
                "void main() {"  +
                        " vec4 tN=normalize(localTransform *vec4(aNormal,0.0));" +
                        "vNormal=vec3(tN.x,tN.y,tN.z);"+

                        "vec4 p=uMVMatrix *localTransform* vec4(aPosition,1.0);"+
                        "gl_Position = uPMatrix*p;" +
                        "vE=normalize(vec3(p));"+
                "}",




                        "#version 300 es\n"+
                                "precision mediump float;" +
        "uniform sampler2D uMatCap;" +
                                "in vec3 vNormal;"+
                                "in vec3 vE;"+
                                "out vec4 myOutputColor;"+
                "void main() {" +
                                "vec3 N=normalize(vNormal);"+
                                "vec3 E=normalize(vE);"+
                                "vec3 r = reflect(E,N);"+
        "float m=2.0*sqrt(pow(r.x,2.)+pow(r.y,2.)+pow(r.z+1.,2.));"+
                "vec4 matcapColor=texture(uMatCap,r.xy / m + .5);"+
                "  myOutputColor = matcapColor;" +
                "}",new String[]{"aPosition","aNormal"});
    }

}
