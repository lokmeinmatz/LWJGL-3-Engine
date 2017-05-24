package engine;


import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

public class Boot {
	
	
	//
	public Window window;
	private int width = 1920, height = 1080;
	
	
	public static void main(String[] args){
		new Boot();
		
		
	}
	
	
	public Boot(){
		
		
		window = new Window(width, height);
		
		GL.createCapabilities();
		
		
		new gameloop(window);
		
		
		
	}

}
