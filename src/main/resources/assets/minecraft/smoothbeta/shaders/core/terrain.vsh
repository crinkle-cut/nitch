#version 120

#moj_import <fog.glsl>

attribute vec3 Position;
attribute vec2 UV0;
attribute vec4 Color;
attribute vec3 Normal;

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;
uniform vec3 ChunkOffset;

varying float vertexDistance;
varying vec2 texCoord0;
varying vec4 vertexColor;
varying vec4 normal;

void main() {
    vec3 pos = Position + ChunkOffset;
    gl_Position = ProjMat * ModelViewMat * vec4(pos, 1.0);

    vertexDistance = fog_distance(ModelViewMat, pos, 0);
    texCoord0 = UV0;
    vertexColor = Color;
    normal = ProjMat * ModelViewMat * vec4(Normal, 0.0);
}
