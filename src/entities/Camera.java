package entities;

import org.joml.Vector3f;
import static org.lwjgl.glfw.GLFW.*;

import engine.Input;

public class Camera {
	
	private Vector3f position = new Vector3f();
	private Vector3f PitchYawRoll = new Vector3f();
	private float fastSpeed = 20;
	private float slowSpeed = 5;
	public Camera(){
		
	}
	
	
	public Vector3f getPosition() {
		return position;
	}
	public Vector3f getPitchYawRoll() {
		return PitchYawRoll;
	}
	
	
	
	public void update(float dt){
		
		boolean faster = false;
		
		if(Input.isKeyDown(GLFW_KEY_LEFT_SHIFT)){
			faster = true;
		}
		
		if(Input.isKeyDown(GLFW_KEY_W)){
			position.z -= dt * (faster? fastSpeed:slowSpeed);
		}
		if(Input.isKeyDown(GLFW_KEY_S)){
			position.z += dt * (faster? fastSpeed:slowSpeed);
		}
		if(Input.isKeyDown(GLFW_KEY_A)){
			position.x -= dt * (faster? fastSpeed:slowSpeed);
		}
		if(Input.isKeyDown(GLFW_KEY_D)){
			position.x += dt * (faster? fastSpeed:slowSpeed);
		}
		
		
	}

}
