package terrain;

import engine.rednerer.models.RawModel;
import engine.renderer.Loader;
import engine.renderer.textures.ModelTexture;

public class Terrain {
	
	private static final float SIZE = 800;
	private static final int VERTEX_COUNT = 128;

	
	private float x, z;
	
	private RawModel model;
	private ModelTexture texture;
	
	
	public Terrain(int gridX, int gridZ, Loader loader, ModelTexture texture){
		this.texture = texture;
		this.x = gridX * SIZE;
		this.z = gridZ * SIZE;
	}
	
}
