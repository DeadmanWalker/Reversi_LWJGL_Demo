package ui;

import graphics.Shader;
import graphics.Texture;
import graphics.VertexArray;
import main.WindowConstains;
import meshdata.RectMesh;

import static org.lwjgl.opengl.GL13.*;

public class Background {
	private VertexArray background;
	private Texture bgTexture;
	
	public static Shader BG_SHADER;
	
	public static void load() {
		glActiveTexture(GL_TEXTURE1);
		BG_SHADER = new Shader("shaders/bg.vert", "shaders/bg.frag");
		BG_SHADER.enable();
		BG_SHADER.setUniformMat4f("pr_matrix", Shader.pr_matrix);
		BG_SHADER.setUniform1i("tex", 1);
		BG_SHADER.disable();
	}
	
	public Background() {
		RectMesh rect = new RectMesh(WindowConstains.WIDTH / WindowConstains.SIZE_MOD, WindowConstains.HEIGHT / WindowConstains.SIZE_MOD, 0.0f);
		
		background = new VertexArray(rect.getVetices(), rect.getIndices(), rect.getTextureCoords());
		bgTexture = new Texture("res/bg.png");
	}
	
	public void render() {
		bgTexture.bind();
		BG_SHADER.enable();
		background.render();
		BG_SHADER.disable();
		bgTexture.unbind();
	}
}
