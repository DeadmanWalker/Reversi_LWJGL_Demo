package play;

import static org.lwjgl.opengl.GL11.GL_NO_ERROR;
import static org.lwjgl.opengl.GL11.glGetError;
import static org.lwjgl.opengl.GL13.GL_TEXTURE1;
import static org.lwjgl.opengl.GL13.glActiveTexture;

import graphics.Shader;
import graphics.Texture;
import graphics.VertexArray;
import main.WindowConstains;
import math.Matrix4f;
import math.Vector3f;
import meshdata.RectMesh;

public class MoveVisualizer {
	private Vector3f pos = new Vector3f();
	private Matrix4f ml_matrix;
	
	private static Texture texture = new Texture("res/move.png");
	private static int size = texture.getWidth();
	private static int size_mod = WindowConstains.SIZE_MOD;
	private static VertexArray mesh;
	
	public static Shader MOVE_SHADER;
	
	public static void create() {
		RectMesh rect = new RectMesh((size) * size_mod, (size) * size_mod, 0.2f);
		
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
	
	public static VertexArray getMesh() {
		return mesh;
	}
	
	public Texture getTexture() {
		return texture;
	}
	
	public Matrix4f getMl_matrix() {
		return ml_matrix;
	}
	
	public void render() {
		texture.bind();
		MOVE_SHADER.enable();
		MOVE_SHADER.setUniformMat4f("ml_matrix", ml_matrix);
		mesh.render();
		MOVE_SHADER.disable();
		texture.unbind();
		
		int error = glGetError();
		if(error != GL_NO_ERROR) {
			System.out.println(error);
		}
	}
}
