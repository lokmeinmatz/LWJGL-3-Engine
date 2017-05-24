package engine.renderer;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glEnable;

import java.util.List;
import java.util.Map;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import Utils.Maths;
import engine.Window;
import engine.rednerer.models.RawModel;
import engine.rednerer.models.TexturedModel;
import engine.renderer.shader.StaticShader;
import entities.Entity;

public class Renderer {
	
	private static final float FOV = 70;
	private static final float NEAR_PLANE = 0.1f;
	private static final float FAR_PLANE = 1000;
	private Window window;
	private Matrix4f projectionMatix;
	private StaticShader shader;
	
	public Renderer(Window window, StaticShader shader){
		this.window = window;
		this.shader = shader;
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
		
		createProjectionMatrix();
		shader.Start();
		shader.loadProjectionMatrix(projectionMatix);
		shader.Stop();
	}
	
	
	public void prepare(){
		GL11.glClearColor(0, 0, 0.1f, 1);
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_DEPTH_TEST);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}

	public void render(Map<TexturedModel, List<Entity>> entities){
		for(TexturedModel model : entities.keySet()){
			prepareTexturedModel(model);
			
			List<Entity> batch = entities.get(model);
			
			for(Entity e:batch){
				prepareInstance(e);
				GL11.glDrawElements(GL11.GL_TRIANGLES, model.getModel().getVertexcount(), GL11.GL_UNSIGNED_INT, 0);
			}
			
			unbindTexturedModel();
			
		}
	}
	
	private void prepareTexturedModel(TexturedModel texmodel){
		RawModel model = texmodel.getModel();
		GL30.glBindVertexArray(model.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		

		shader.loadShineVariables(texmodel.texture.getShineDamper(), texmodel.texture.getReflectivity());
		//enable tex
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texmodel.getTexture().getID());
	}
	
	
	private void unbindTexturedModel(){
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}
	
	private void prepareInstance(Entity e){
		Matrix4f transformMat4 = Maths.createTransformMatrix(e.getPosition(), e.getRotation(), e.getScale());
		shader.loadTranformationMatrix(transformMat4);
	}
	

	
	private void createProjectionMatrix(){
		float aspectRatio = (float) window.getWidth() / (float) window.getHeight();
		float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV/2f))) * aspectRatio);
		float x_scale = y_scale/aspectRatio;
		float frustumlength = FAR_PLANE - NEAR_PLANE;
		
		projectionMatix = new Matrix4f();
		projectionMatix.m00(x_scale);
		projectionMatix.m11(y_scale);
		projectionMatix.m22(-((FAR_PLANE + NEAR_PLANE)/ frustumlength));
		projectionMatix.m23(-1);
		projectionMatix.m32(-((2 * FAR_PLANE * NEAR_PLANE)/ frustumlength));
		projectionMatix.m33(0);
		
		
		
		
	}
}
