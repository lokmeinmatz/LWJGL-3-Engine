package engine.renderer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import engine.rednerer.models.RawModel;

public class Loader {
	// vertex array object, contains multiple vbos
	private List<Integer> vaos = new ArrayList<>();
	
	//vertex buffer object
	private List<Integer> vbos = new ArrayList<>();
	
	//textures in memory
	private List<Integer> texs = new ArrayList<>();
	
	public RawModel loadToVAO(float[] positions, float[] uv, float[] normals, int [] indecies){
		int vaoID = createVAO();
		bindIndeciesBuffer(indecies);
		storeInAttributeList(0, 3, positions);
		storeInAttributeList(1, 2, uv);
		storeInAttributeList(2, 3, normals);
		unbindVAO();
		return new RawModel(vaoID, indecies.length);
	}

	public int loadTexture(String filename){
		filename = "./res/textures/"+filename+".png";
		System.out.println("loading texture "+filename);
		MemoryStack stack = MemoryStack.stackPush();
		int textureID = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		
		IntBuffer w = stack.mallocInt(1);
		IntBuffer h = stack.mallocInt(1);
		IntBuffer comp = stack.mallocInt(1);
		
		ByteBuffer image = STBImage.stbi_load(filename, w, h, comp, 4);
		
		if (image == null) {
		    throw new RuntimeException("Failed to load a texture file!"
		            + System.lineSeparator() + STBImage.stbi_failure_reason());
		}

		int width = w.get();
		int height = h.get();
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, image);
		MemoryUtil.memFree(image);

		texs.add(textureID);
		System.out.println("created texture texture "+textureID+" with size "+width+" x "+height);
		return textureID;
	}
	
	private int createVAO(){
		int vaoID = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vaoID);
		vaos.add(vaoID);
		return vaoID;
	}
	
	private void storeInAttributeList(int attributelist, int coordinateSize,  float[] data){
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		FloatBuffer buffer = storeDatainFloatBuffer(data);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(attributelist, coordinateSize, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		
		
	}
	
	private void bindIndeciesBuffer(int[] indecies){
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
		IntBuffer buffer = storeDatainIntBuffer(indecies);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		
	}
	
	public void cleanUP(){
		for(int vao:vaos){
			GL30.glDeleteVertexArrays(vao);
		}
		
		for(int vbo:vbos){
			GL15.glDeleteBuffers(vbo);
		}
		for(int textureID:texs){
			GL11.glDeleteTextures(textureID);
		}
		
	}
	
	public void unbindVAO(){
		GL30.glBindVertexArray(0);
	}
	
	public IntBuffer storeDatainIntBuffer(int[] data){
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
		
	}
	
	public FloatBuffer storeDatainFloatBuffer(float[] data){
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
		
	}
	
}
