#version 400 core

in vec3 position;
in vec2 textureCoords;
in vec3 normal;

out vec2 pass_UVs;
out vec3 surfaceNormal;
out vec3 toLightSource;
out vec3 toCameraVector;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform vec3 lightPos;

void main(void){

	vec4 worldpos = transformationMatrix * vec4(position, 1.0);
	gl_Position = projectionMatrix * viewMatrix * worldpos;
	pass_UVs = textureCoords;
	
	
	surfaceNormal = (transformationMatrix * vec4(normal, 0.0)).xyz;
	toLightSource = lightPos - worldpos.xyz;
	toCameraVector = (inverse(viewMatrix) * vec4(0.0, 0.0, 0.0, 1.0)).xyz - worldpos.xyz;
}