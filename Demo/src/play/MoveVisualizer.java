package play;

import static org.lwjgl.opengl.GL13.GL_TEXTURE1;
import static org.lwjgl.opengl.GL13.glActiveTexture;

import graphics.Shader;
import graphics.Texture;
import graphics.VertexArray;
import math.Matrix4f;
import math.Vector3f;
import meshdata.RectMesh;

public class MoveVisualizer {
	private Vector3f pos = new Vector3f();
	private Matrix4f ml_matrix;
	
	private static Texture texture = new Texture("res/move.png");
	private static int size = texture.getWidth();
	private static VertexArray mesh;
	
	public static Shader MOVE_SHADER;
	
	public static void create() {
		RectMesh rect = new RectMesh((size), (size), 0.2f);
		
		mesh = new VertexArray(rect.getVetices(), rect.getIndices(), rect.getTextureCoords());
	}
	
	public static void load() {
		MOVE_SHADER = new Shader("shaders/cell.vert", "shaders/cell.frag");
		glActiveTexture(GL_TEXTURE1);
		MOVE_SHADER.enable();
		MOVE_SHADER.setUniformMat4f("pr_matrix", Shader.pr_matrix);
		MOVE_SHADER.setUniform1i("tex", 1);
		MOVE_SHADER.disable();
	}
	
	public MoveVisualizer(float x, float y) {
		pos.x = x;
		pos.y = y;
		ml_matrix = Matrix4f.translate(pos);
	}
	
	public void render() {
		texture.bind();
		MOVE_SHADER.enable();
		MOVE_SHADER.setUniformMat4f("ml_matrix", ml_matrix);
		mesh.render();
		MOVE_SHADER.disable();
		texture.unbind();
	}
}
