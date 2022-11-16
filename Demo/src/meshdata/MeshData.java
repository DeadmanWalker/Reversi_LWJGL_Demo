package meshdata;

class MeshData {
	protected float[] vertices;
	protected byte[] indices;
	protected float[] tcs;
	
	protected MeshData() {
		
	}
	
	public float[] getVetices() {
		return vertices;
	}
	
	public byte[] getIndices() {
		return indices;
	}
	
	public float[] getTextureCoords() {
		return tcs;
	}
}
