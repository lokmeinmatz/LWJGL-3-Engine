package engine.renderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import engine.Window;
import engine.rednerer.models.TexturedModel;
import engine.renderer.shader.StaticShader;
import entities.Camera;
import entities.Entity;
import entities.Light;

public class MasterRenderer {
	
	private StaticShader shader = new StaticShader();
	private Renderer renderer;
	private Map<TexturedModel, List<Entity>> entities = new HashMap<>();
	
	

	public MasterRenderer(Window w){
		
		renderer = new Renderer(w, shader);
		
	}
	
	public void ProcessEntity(Entity e){
		TexturedModel tmod = e.getModel();
		List<Entity> batch = entities.get(tmod);
		
		if(batch != null){
			batch.add(e);
		}
		else{
			List<Entity> newBatch = new ArrayList<>();
			newBatch.add(e);
			entities.put(tmod, newBatch);
		}
		
	}
	
	public void render(Light sun, Camera cam){
		renderer.prepare();
		
		shader.Start();
		shader.loadLight(sun);
		shader.loadViewMatrix(cam);
		renderer.render(entities);
		shader.Stop();
		entities.clear();
		
	}
	
	
	public void cleanUp(){
		shader.cleanUp();
	}
			
}
