package engine.rednerer.models;

import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.joml.Vector2f;
import org.joml.Vector3f;

import engine.renderer.Loader;

public class OBJloader {
	
	public static RawModel loadOBJmodel(String filename, Loader loader){
		FileReader fr = null;
		
		try {
			fr = new FileReader(new File("res/"+filename+".obj"));
		} catch (FileNotFoundException e) {
			System.err.println("Could not read file "+filename);
			e.printStackTrace();
		}
		

		BufferedReader reader = new BufferedReader(fr);
		
		String line;
		ArrayList<Vector3f> vertecies = new ArrayList<>();
		ArrayList<Vector2f> uvcoords = new ArrayList<>();
		ArrayList<Vector3f> normals = new ArrayList<>();
		ArrayList<Integer> indecies = new ArrayList<>();
		
		float[] verteciesArray = null;
		float[] normlasArray = null;
		float[] uvArray = null;
		int[] indeciesArray = null;
		
		
		try {
			
			while(true){
				line = reader.readLine();
				
				String[] currentLine = line.split(" ");
				
				//Vertex
				if(line.startsWith("v ")){
					Vector3f vert = new Vector3f(Float.parseFloat(currentLine[1]), 
							Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
					
					vertecies.add(vert);
				}
				
				//uvCoords
				else if(line.startsWith("vt ")){
					Vector2f uv = new Vector2f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]));
					
					uvcoords.add(uv);
				}
				
				//uv Normals
				else if(line.startsWith("vn")){
					Vector3f Normal = new Vector3f(Float.parseFloat(currentLine[1]), 
							Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
					
					normals.add(Normal);
				}
				
				// indecies
				else if(line.startsWith("f ")){
					uvArray = new float[vertecies.size() * 2];
					normlasArray = new float[vertecies.size() * 3];
					break;
				}
				
			}
			
			while(line != null){
				if(!line.startsWith("f ")){
					line = reader.readLine();
					continue;
				}
				String[] currentline = line.split(" ");
				String[] vertex1 = currentline[1].split("/");
				String[] vertex2 = currentline[2].split("/");
				String[] vertex3 = currentline[3].split("/");
				
				processVertex(vertex1, indecies, uvcoords, normals, uvArray, normlasArray);
				processVertex(vertex2, indecies, uvcoords, normals, uvArray, normlasArray);
				processVertex(vertex3, indecies, uvcoords, normals, uvArray, normlasArray);
				line = reader.readLine();
			}
			reader.close();
			
		} catch (Exception e) {
			System.err.println("Error while reading file "+filename);
		}
		
		verteciesArray = new float[vertecies.size() * 3];
		indeciesArray = new int[indecies.size()];
		
		int vertexPointer = 0;
		
		for(Vector3f vertex:vertecies){
			verteciesArray[vertexPointer++] = vertex.x;
			verteciesArray[vertexPointer++] = vertex.y;
			verteciesArray[vertexPointer++] = vertex.z;
		}
		
		for(int i = 0; i < indecies.size(); i++){
			indeciesArray[i] = indecies.get(i);
		}
		
		return loader.loadToVAO(verteciesArray, uvArray, normlasArray, indeciesArray);
	}
	
	private static void processVertex(String[] vertData, ArrayList<Integer> indecies, ArrayList<Vector2f> textures, ArrayList<Vector3f> normals,
			float[] textureArray, float[] normalsArray ){
		
		int currentVertexPointer = Integer.parseInt(vertData[0] ) - 1;
		indecies.add(currentVertexPointer);
		
		Vector2f currentTex = textures.get(Integer.parseInt(vertData[1]) - 1);
		textureArray[currentVertexPointer * 2] = currentTex.x;
		textureArray[currentVertexPointer * 2 + 1] = currentTex.y;
		
		Vector3f currentNorm = normals.get(Integer.parseInt(vertData[2]) - 1);
		normalsArray[currentVertexPointer * 3] = currentNorm.x;
		normalsArray[currentVertexPointer * 3 + 1] = currentNorm.y;
		normalsArray[currentVertexPointer * 3 + 2] = currentNorm.z;
		
	}

}
