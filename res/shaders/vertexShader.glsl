#version 400 core

 layout (location = 0) in vec3 aPos; // the position variable has attribute position 0
 layout (location = 1) in vec3 aColor; // the position variable has attribute position 1
 layout (location = 2) in vec2 aTexturePos; // the position variable has attribute position 2

out vec3 outColor;
out vec2 outTexture;

uniform mat4 objectMatrix;

 void main() {
    //gl_PointSize = 10.0;
    gl_Position = objectMatrix * vec4(aPos, 1.0);

    outColor = aColor;
    outTexture = aTexturePos;
 }