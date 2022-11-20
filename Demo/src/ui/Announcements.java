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

public class Announcements {
	private static Matrix4f ml_matrix;
	
	private static Shader ANNOUNCE_SHADER = new Shader("shaders/announce.vert", "shaders/announce.frag");
	private static Texture[] player = new Texture[] {
			new Texture("res/player/white.png"),
			new Texture("res/player/black.png")
	};
	
	private static Texture draw_tex = new Texture("res/results/draw.png");
	private static Texture wins_tex = new Texture("res/results/wins.png");
	
	private static VertexArray[] player_mesh = new VertexArray[2];
	private static VertexArray draw_mesh;
	private static VertexArray wins_mesh;
	
	public static void create() {
		for(int i = 0; i < 2; ++i) {
			RectMesh rect = new RectMesh(player[i].getWidth() * (WindowConstains.SIZE_MOD + 1), player[i].getHeight() * (WindowConstains.SIZE_MOD + 1), 0.4f);
			player_mesh[i] = new VertexArray(rect.getVetices(), rect.getIndices(), rect.getTextureCoords());
		}
		
		RectMesh drawRect = new RectMesh(draw_tex.getWidth() * (WindowConstains.SIZE_MOD + 1), draw_tex.getWidth() * (WindowConstains.SIZE_MOD + 1), 0.4f);
		draw_mesh = new VertexArray(drawRect.getVetices(), drawRect.getIndices(), drawRect.getTextureCoords());
		
		RectMesh winsRect = new RectMesh(wins_tex.getWidth() * (WindowConstains.SIZE_MOD + 1), wins_tex.getHeight() * (WindowConstains.SIZE_MOD + 1), 0.4f);
		wins_mesh = new VertexArray(winsRect.getVetices(), winsRect.getIndices(), winsRect.getTextureCoords());
	}
	
	public static void load() {
		glActiveTexture(GL_TEXTURE1);
		ANNOUNCE_SHADER.enable();
		ANNOUNCE_SHADER.setUniformMat4f("pr_matrix", Shader.pr_matrix);
		ANNOUNCE_SHADER.setUniform1i("tex", 1);
		ANNOUNCE_SHADER.disable();
	}
	
	public static void renderGameOver(int winner) {
		
		if(winner != -1) {
			ANNOUNCE_SHADER.enable();
			player[winner].bind();
			ml_matrix = Matrix4f.translate(new Vector3f((WindowConstains.WIDTH - player[winner].getWidth() * (WindowConstains.SIZE_MOD + 1)) / 2, WindowConstains.HEIGHT * 1 / 3, 0.0f));
			ANNOUNCE_SHADER.setUniformMat4f("ml_matrix", ml_matrix);
			player_mesh[winner].render();
			player[winner].unbind();
			wins_tex.bind();
			ml_matrix = Matrix4f.translate(new Vector3f((WindowConstains.WIDTH - wins_tex.getWidth() * (WindowConstains.SIZE_MOD + 1)) / 2, WindowConstains.HEIGHT * 1 / 3 + (player[winner].getHeight() + 2) * (WindowConstains.SIZE_MOD + 1), 0.0f));
			ANNOUNCE_SHADER.setUniformMat4f("ml_matrix", ml_matrix);
			wins_mesh.render();
			wins_tex.unbind();
			ANNOUNCE_SHADER.disable();
		}
		else {
			ANNOUNCE_SHADER.enable();
			draw_tex.bind();
			ml_matrix = Matrix4f.translate(new Vector3f((WindowConstains.WIDTH - draw_tex.getWidth() * (WindowConstains.SIZE_MOD + 1)) / 2, WindowConstains.HEIGHT * 1 / 3, 0.0f));
			ANNOUNCE_SHADER.setUniformMat4f("ml_matrix", ml_matrix);
			draw_mesh.render();
			draw_tex.unbind();
			ANNOUNCE_SHADER.disable();
		}
		
	}
}