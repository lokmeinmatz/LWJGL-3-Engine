package engine.rednerer.models;

public class RawModel {

	
	private int vaoID;
	private int Vertexcount;
	
	public RawModel(int vaoID, int VertexCount){
		this.vaoID = vaoID;
		this.Vertexcount = VertexCount;
	}

	public int getVaoID() {
		return vaoID;
	}

	public int getVertexcount() {
		return Vertexcount;
	}
	
	
}
