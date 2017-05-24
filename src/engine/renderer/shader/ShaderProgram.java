package engine.renderer.shader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;



public abstract class ShaderProgram {

	private int programID, vertexShaderID, fragmentShaderID;
	
	private FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
	
	
	public ShaderProgram(String vertexShader, String fragmentShader){
		vertexShaderID = loadShader(vertexShader, GL20.GL_VERTEX_SHADER);
		fragmentShaderID = loadShader(fragmentShader, GL20.GL_FRAGMENT_SHADER);
		programID = GL20.glCreateProgram();
		GL20.glAttachShader(programID, vertexShaderID);
		GL20.glAttachShader(programID, fragmentShaderID);
		bindAttributes();
		GL20.glLinkProgram(programID);
		GL20.glValidateProgram(programID);
		getAllUniformLocations();
	}
	
	protected int getUniformLocation(String uniformName){
		return GL20.glGetUniformLocation(programID, uniformName);
	}
	
	protected abstract void getAllUniformLocations();
	
	protected void loadFloat(int location, float value){
		GL20.glUniform1f(location, value);
	}
	
	protected void loadVector(int location, Vector3f vec){
		GL20.glUniform3f(location, vec.x, vec.y, vec.z);
	}
	
	protected void loadBool(int location, boolean value){
		float toLoad = 0;
		if(value)toLoad = 1;
		
		GL20.glUniform1f(location, toLoad);
	}
	
	protected void loadMatrix(int location, Matrix4f mat4){
		GL20.glUniformMatrix4fv(location, false, mat4.get(matrixBuffer));
	}
	
 	public void Start(){
		GL20.glUseProgram(programID);
	}
 	
 	
	
	public void Stop(){
		GL20.glUseProgram(0);
	}
	
	public void cleanUp(){
		Stop();
		GL20.glDetachShader(programID, vertexShaderID);
		GL20.glDetachShader(programID, fragmentShaderID);
		GL20.glDeleteShader(vertexShaderID);
		GL20.glDeleteShader(fragmentShaderID);
		GL20.glDeleteProgram(programID);
	}
	
	protected void bindAttribute(int attribute, String varname){
		GL20.glBindAttribLocation(programID, attribute, varname);
	}
	
	protected abstract void bindAttributes();
	
	private static int loadShader(String file, int type){
		StringBuilder shadersource = new StringBuilder();
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(file)));
			String line;
			while((line = reader.readLine()) != null){
				shadersource.append(line).append("\n");
			}
			reader.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int shaderID = GL20.glCreateShader(type);
		GL20.glShaderSource(shaderID, shadersource);
		
		GL20.glCompileShader(shaderID);
		
		if(GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == 0){
			System.out.println(GL20.glGetShaderInfoLog(shaderID));
			System.err.println("could not read Shader "+file);
			System.exit(-1);
		}
		
		return shaderID;
		
	}
}
