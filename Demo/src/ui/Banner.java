package ui;

import static org.lwjgl.opengl.GL13.GL_TEXTURE1;
import static org.lwjgl.opengl.GL13.glActiveTexture;

import graphics.Shader;
import graphics.Texture;
import graphics.VertexArray;
import main.WindowConstains;
import math.Matrix4f;
import math.Vector3f;
import meshdata.RectMesh;

public class Banner {
	private static Shader BANNER_SHADER = new Shader("shaders/board.vert", "shaders/board.frag");
	private static Texture texture = new Texture("res/banner.png");
	private static Vector3f pos = new Vector3f((WindowConstains.WIDTH / WindowConstains.SIZE_MOD - texture.getWidth()) / 2, 100, 0);
	private static VertexArray mesh;
	
	public static void load() {
		glActiveTexture(GL_TEXTURE1);
		BANNER_SHADER.enable();
		BANNER_SHADER.setUniformMat4f("pr_matrix", Shader.pr_matrix);
		BANNER_SHADER.setUniform1i("tex", 1);
		BANNER_SHADER.disable();
		
		RectMesh rect = new RectMesh(texture.getWidth(), texture.getHeight(), 0.1f);
		mesh = new VertexArray(rect.getVetices(), rect.getIndices(), rect.getTextureCoords());
	}
	
	public static void render() {
		texture.bind();
		BANNER_SHADER.enable();
		BANNER_SHADER.setUniformMat4f("ml_matrix", Matrix4f.translate(pos));
		mesh.render();
		BANNER_SHADER.disable();
		texture.unbind();
	}
}
