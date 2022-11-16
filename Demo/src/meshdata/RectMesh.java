package meshdata;

public class RectMesh extends MeshData {
	public RectMesh(int width_px, int height_px, float layer) {
		super();
		
		vertices = new float[] {
			0.0f, height_px, layer,
			0.0f, 0.0f, layer,
			width_px, 0.0f, layer,
			width_px, height_px, layer
		};
		
		indices = new byte[] {
			0, 1, 2,
			2, 3, 0
		};
		
		tcs = new float[] {
			0, 1,
			0, 0,
			1, 0,
			1, 1
		};
	}
	
}
