package entities;

import org.joml.Vector3f;

import engine.rednerer.models.TexturedModel;

public class Entity {

	private TexturedModel model;
	private Vector3f position;
	private Vector3f rotation;
	private float scale;
	
	public Entity(TexturedModel model, Vector3f position, Vector3f rotation, float scale) {
		super();
		this.model = model;
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
	}

	public TexturedModel getModel() {
		return model;
	}

	public Vector3f getPosition() {
		return position;
	}

	public Vector3f getRotation() {
		return rotation;
	}

	public float getScale() {
		return scale;
	}
	
	
	
}
