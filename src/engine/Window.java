package engine;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.GLFWVidMode;

public class Window {
	
	private long window;
	
	private GLFWVidMode vidmode;
	
	private int width, height;
	
	private Input input;
	
	private boolean fullscreen = false;

	public Window(int width, int height) {
		
		this.width = width;
		this.height = height;
		if(!glfwInit()){
			throw new IllegalArgumentException("Failed ti initialize GLFW");
		}
		vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		
		if(fullscreen){
			this.width = vidmode.width();
			this.height = vidmode.height();
		}
		glfwSwapInterval(10);
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		window = glfwCreateWindow(width, height, "2D", fullscreen ? glfwGetPrimaryMonitor() : 0, 0);
		if(window == 0)throw new IllegalArgumentException("Error on creating window.");
		
		
		if(!fullscreen)glfwSetWindowPos(window, (vidmode.width() - width)/2, (vidmode.height() - height)/2);
		
		glfwShowWindow(window);
		
		glfwMakeContextCurrent(window);
		
		Input.loadInput(window);
	}
	
	public void update(){
		input.update();
		glfwPollEvents();
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public void setSize(int width, int height){
		this.width = width;
		this.height = height;
	}
	
	public boolean shouldClose(){
		return glfwWindowShouldClose(window);
	}
	
	public void swapBuffers(){
		glfwSwapBuffers(window);
	}

	public boolean isFullscreen() {
		return fullscreen;
	}

	public void setFullscreen(boolean fullscreen) {
		this.fullscreen = fullscreen;
		if(fullscreen){
			this.width = vidmode.width();
			this.height = vidmode.height();
			glfwSetWindowSize(window, width, height);
			glfwSetWindowMonitor(window, glfwGetPrimaryMonitor(), 0, 0, width, height, 60);
		}
	}
	
	public Input getInput() {return input;}
	
	public long getWindowPointer(){return window;}

}
