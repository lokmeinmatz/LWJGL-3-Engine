package engine;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;

import java.util.ArrayList;
import java.util.Random;

import org.joml.Vector3f;

import engine.rednerer.models.OBJloader;
import engine.rednerer.models.RawModel;
import engine.rednerer.models.TexturedModel;
import engine.renderer.Loader;
import engine.renderer.MasterRenderer;
import engine.renderer.Renderer;
import engine.renderer.shader.StaticShader;
import engine.renderer.textures.ModelTexture;
import entities.Camera;
import entities.Entity;
import entities.Light;

public class gameloop {
	
	private int width, height;
	private Window window;
	
	private int dragons = 500;
	
	public gameloop(Window window){
		this.width = window.getWidth();
		this.height = window.getHeight();
		this.window = window;
		
		
		Loader loader = new Loader();
		MasterRenderer renderer = new MasterRenderer(window);
		Random random = new Random();
		
		//init data
		//________________________________________________________
		
		
		
		RawModel model = OBJloader.loadOBJmodel("models/dragon", loader);
		
		ModelTexture texture = new ModelTexture(loader.loadTexture("texture_kunstwerk"));
		
		TexturedModel texturedModel = new TexturedModel(model, texture);
		
		texturedModel.texture.setReflectivity(4);
		texturedModel.texture.setShineDamper(20);
		
		
		
		ArrayList<Entity> entities = new ArrayList<>();
		
		
		//fill array
		for(int i = 0; i < dragons; i++){
			Entity entity = new Entity(texturedModel, new Vector3f((random.nextFloat()-0.5f) * 500f, (random.nextFloat()-0.5f) * 500f, (random.nextFloat()-0.5f) * 500f), new Vector3f(), 1);
			entities.add(entity);
		}
		
		Light light = new Light(new Vector3f(10, 10, -20), new Vector3f(1f, 0.5f, 0.5f));
		
		Camera camera = new Camera();
		
		double frame_cap = 1d/60d;
		//double frame_cap = 0d;
		double time = Timer.getTime();
		double unproccesed = 0;
		
		//fps counter
		double timer = 0;
		int fps = 0;
		double elapsedTime = 0;
		//actual gameloop
		
		
		while(!window.shouldClose()){
			
			//time
			double now = Timer.getTime();
			double passed = now - time;
			unproccesed += passed;
			
			time = now;
			
			if(unproccesed >= frame_cap){
				
				timer += unproccesed;
				elapsedTime += unproccesed;
				
				
				
				if(window.getInput().isKeyPressed(GLFW_KEY_ESCAPE)){
					glfwSetWindowShouldClose(window.getWindowPointer(), true);
				}
				
				if(window.getInput().isKeyDown(GLFW_KEY_R))camera.getPitchYawRoll().x += unproccesed*60;
				
				
				
				//-------------------
				//update world
				//-------------------
				camera.update((float) unproccesed);
				//entity.getRotation().y += unproccesed * 10;
				
				
				//light.getPosition().z = (float) (Math.sin(elapsedTime) * 20 - 40);
				
				
				//--------------------
				//render
				//--------------------
			
				
				for(Entity e:entities){
					renderer.ProcessEntity(e);
				}
				
				renderer.render(light, camera);
				
				window.swapBuffers();
				fps++;
				if(timer >= 1){
					System.out.println("FPS: "+fps);
					timer = 0;
					fps = 0;
				}
				
				
				window.update();
				unproccesed = 0;
			}
			
			
			
		}
		
		//clean up when game is closed
		renderer.cleanUp();
		loader.cleanUP();
		glfwTerminate();
		
	}

}
