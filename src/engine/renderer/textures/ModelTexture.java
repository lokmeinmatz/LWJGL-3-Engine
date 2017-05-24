package engine.renderer.textures;

public class ModelTexture {

	private int textureID;
	
	private float shineDamper = 1, reflectivity = 0;
	
	public ModelTexture(int id){
		textureID = id;
	}
	
	public int getID(){return textureID;}

	public float getShineDamper() {
		return shineDamper;
	}

	public void setShineDamper(float shineDamper) {
		this.shineDamper = shineDamper;
	}

	public float getReflectivity() {
		return reflectivity;
	}

	public void setReflectivity(float reflectivity) {
		this.reflectivity = reflectivity;
	}
	
	
}
