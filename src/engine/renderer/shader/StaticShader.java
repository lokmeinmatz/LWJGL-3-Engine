package engine.renderer.shader;

import org.joml.Matrix4f;

import Utils.Maths;
import entities.Camera;
import entities.Light;

public class StaticShader extends ShaderProgram{

	
	private static final String VERTEXSHADER = "./shaders/shader.vs";
	private static final String FRAGMENTSHADER = "./shaders/shader.fs";
	
	private int loc_transformationMatrix, loc_projectionMatrix, loc_viewMatrix, loc_lightPsoition,
	loc_lightColor, loc_shineDamper, loc_reflectivity;
	
	public StaticShader() {
		super(VERTEXSHADER, FRAGMENTSHADER);
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
		super.bindAttribute(2, "normal");
	}

	@Override
	protected void getAllUniformLocations() {
		loc_transformationMatrix = super.getUniformLocation("transformationMatrix");
		loc_projectionMatrix = super.getUniformLocation("projectionMatrix");
		loc_viewMatrix = super.getUniformLocation("viewMatrix");
		loc_lightPsoition = super.getUniformLocation("lightPos");
		loc_lightColor = super.getUniformLocation("lightColor");
		loc_shineDamper = super.getUniformLocation("shineDamper");
		loc_reflectivity = super.getUniformLocation("reflectivity");
	}
	
	public void loadTranformationMatrix(Matrix4f matrix){
		super.loadMatrix(loc_transformationMatrix, matrix);
	}

	public void loadLight(Light light){
		super.loadVector(loc_lightPsoition, light.getPosition());
		super.loadVector(loc_lightColor, light.getColor());
	}
	
	public void loadProjectionMatrix(Matrix4f matrix){
		super.loadMatrix(loc_projectionMatrix, matrix);
	}
	
	public void loadViewMatrix(Camera camera){
		super.loadMatrix(loc_viewMatrix, Maths.createViewMatrix(camera));
	}
	
	public void loadShineVariables(float damper, float reflect){
		super.loadFloat(loc_shineDamper, damper);
		super.loadFloat(loc_reflectivity, reflect);
	}
}
