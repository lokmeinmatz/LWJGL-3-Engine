package engine.rednerer.models;

import engine.renderer.textures.ModelTexture;

public class TexturedModel {
	
	public RawModel model;
	public ModelTexture texture;
	
	public TexturedModel(RawModel model, ModelTexture texture){
		this.model = model;
		this.texture = texture;
		
	}

	public RawModel getModel() {
		return model;
	}

	public ModelTexture getTexture() {
		return texture;
	}
	
	
}
