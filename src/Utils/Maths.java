package Utils;


import org.joml.Matrix4f;
import org.joml.Vector3f;

import entities.Camera;

public class Maths {

	public static Matrix4f createTransformMatrix(Vector3f position, Vector3f rotation, float scale){
		Matrix4f mat4 = new Matrix4f().identity();
		
		mat4.translate(position);
		
		mat4.rotate((float) Math.toRadians(rotation.x), 1, 0, 0);
		mat4.rotate((float) Math.toRadians(rotation.y), 0, 1, 0);
		mat4.rotate((float) Math.toRadians(rotation.z), 0, 0, 1);
		mat4.scale(scale);
		
		return mat4;
		
	}
	
	public static Matrix4f createViewMatrix(Camera camera){
		Matrix4f viewmat = new Matrix4f().identity();
		viewmat.rotate((float) Math.toRadians(camera.getPitchYawRoll().x), 1, 0, 0);
		viewmat.rotate((float) Math.toRadians(camera.getPitchYawRoll().y), 0, 1, 0);
		viewmat.rotate((float) Math.toRadians(camera.getPitchYawRoll().z), 0, 0, 1);
		viewmat.translate(camera.getPosition().mul(-1, new Vector3f()));
		return viewmat;
	}
	
}
