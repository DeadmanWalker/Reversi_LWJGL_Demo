package play;

import graphics.Shader;
import graphics.Texture;
import graphics.VertexArray;
import main.WindowConstains;
import math.Matrix4f;
import math.Vector3f;
import meshdata.RectMesh;

import static org.lwjgl.opengl.GL13.*;

public class Piece {
	private Vector3f pos = new Vector3f();
	private Matrix4f ml_matrix;
	private int playerID;
	
	private static Texture[] texture = new Texture[] {
			new Texture("res/piece/white_piece.png"),
			new Texture("res/piece/black_piece.png")
	};
	private static final int SIZE = texture[0].getWidth();
	private static VertexArray mesh;
	
	public static Shader PIECE_SHADER;
	
	public static void create() {
		RectMesh rect = new RectMesh((SIZE) * WindowConstains.SIZE_MOD, (SIZE) * WindowConstains.SIZE_MOD, 0.2f);
		
		mesh = new VertexArray(rect.getVetices(), rect.getIndices(), rect.getTextureCoords());
	}
	
	public static void load() {
		PIECE_SHADER = new Shader("shaders/cell.vert", "shaders/cell.frag");
		glActiveTexture(GL_TEXTURE1);
		PIECE_SHADER.enable();
		PIECE_SHADER.setUniformMat4f("pr_matrix", Shader.pr_matrix);
		PIECE_SHADER.setUniform1i("tex", 1);
		PIECE_SHADER.disable();
	}
	
	public Piece(float x, float y, int player_ID) {
		pos.x = x;
		pos.y = y;
		ml_matrix = Matrix4f.translate(pos);
		this.playerID = player_ID;
	}
	
	public static VertexArray getMesh() {
		return mesh;
	}
	
	public Texture getTexture() {
		return texture[playerID];
	}
	
	public int getPlayerID() {
		return playerID;
	}
	
	public Matrix4f getMl_matrix() {
		return ml_matrix;
	}
	
	public void render() {
		texture[playerID].bind();
		PIECE_SHADER.enable();
		PIECE_SHADER.setUniformMat4f("ml_matrix", ml_matrix);
		mesh.render();
		PIECE_SHADER.disable();
		texture[playerID].unbind();
	}
}
