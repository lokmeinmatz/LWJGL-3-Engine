package engine;

import static org.lwjgl.glfw.GLFW.*;

public class Input {

	private static long window;
	
	private static boolean keys[];
	
	public static void loadInput(long window){
		Input.window = window;
		keys = new boolean[GLFW_KEY_LAST];
	}
	
	public static boolean isKeyDown(int key){
		return glfwGetKey(window, key) == 1;
	}
	
	public static boolean isMouseButtonDown(int button){
		return glfwGetMouseButton(window, button) == 1;
	}
	
	public static boolean isKeyPressed(int key){
		 return (isKeyDown(key) && !keys[key]);
	}
	
	public static boolean isKeyReleased(int key){
		return (!isKeyDown(key) && keys[key]);
	}
	public static void update(){
		for(int i = 0; i < GLFW_KEY_LAST; i++){
			keys[i] = isKeyDown(i);
		}
		
	}
	
}
