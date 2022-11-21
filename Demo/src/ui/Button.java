package ui;

import static org.lwjgl.opengl.GL13.GL_TEXTURE1;
import static org.lwjgl.opengl.GL13.glActiveTexture;

import org.lwjgl.glfw.GLFW;

import graphics.Shader;
import graphics.Texture;
import graphics.VertexArray;
import input.InputCursor;
import input.InputMouse;
import math.Matrix4f;
import math.Vector3f;
import meshdata.RectMesh;

public class Button {
	private static Shader BUTTON_SHADER = new Shader("shaders/button.vert", "shaders/button.frag");
	
	private Vector3f pos;
	private Texture texture;
	private VertexArray mesh;
	
	public Button(Vector3f pos, String path) {
		texture = new Texture(path);
		this.pos = pos;
		RectMesh rect = new RectMesh(texture.getWidth(), texture.getHeight(), 0.4f);
		mesh = new VertexArray(rect.getVetices(), rect.getIndices(), rect.getTextureCoords());
	}
	
	public static void load() {
		glActiveTexture(GL_TEXTURE1);
		BUTTON_SHADER.enable();
		BUTTON_SHADER.setUniformMat4f("pr_matrix", Shader.pr_matrix);
		BUTTON_SHADER.setUniform1i("tex", 1);
		BUTTON_SHADER.disable();
	}
	
	public boolean isHover() {
		return InputCursor.POS_X >= pos.x && InputCursor.POS_X < pos.x + texture.getWidth()
			&& InputCursor.POS_Y >= pos.y && InputCursor.POS_Y < pos.y + texture.getHeight();
	}
	
	public boolean isClick() {
		return InputMouse.click[GLFW.GLFW_MOUSE_BUTTON_LEFT] && isHover();
	}
	
	public void render() {
		texture.bind();
		BUTTON_SHADER.enable();
		BUTTON_SHADER.setUniformMat4f("ml_matrix", Matrix4f.translate(pos));
		mesh.render();
		BUTTON_SHADER.disable();
		texture.unbind();
	}
}
