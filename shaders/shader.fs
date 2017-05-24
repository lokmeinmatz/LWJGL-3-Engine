#version 400 core

in vec2 pass_UVs;
in vec3 surfaceNormal;
in vec3 toLightSource;
in vec3 toCameraVector;

out vec4 out_Color;

uniform sampler2D textureSampler;
uniform vec3 lightColor;
uniform float shineDamper;
uniform float reflectivity;

void main(void){

	vec3 unitNormal = normalize(surfaceNormal);
	vec3 unitLightVector = normalize(toLightSource);

	float nDotl = dot(unitNormal, unitLightVector);
	float brightness = max(nDotl, 0.1);
	
	vec3 diffuse = brightness * lightColor;
	
	
	
	vec3 unitCamVec = normalize(toCameraVector);
	vec3 lightDir = -unitLightVector;
	vec3 refelctedDir = reflect(lightDir, unitNormal);
	
	float ReflDot = dot(refelctedDir, unitCamVec);
	ReflDot = max(ReflDot, 0.0);
	float DampedFac = pow(ReflDot, shineDamper);
	vec3 SpecColor = DampedFac * reflectivity * lightColor;
	
	//out_Color = vec4(ReflDot, ReflDot, ReflDot, 1.0);
	out_Color = vec4(diffuse, 1.0) * texture(textureSampler, pass_UVs) + vec4(SpecColor, 1.0);
	//out_Color = vec4(diffuse, 1.0) * texture(textureSampler, pass_UVs);
}