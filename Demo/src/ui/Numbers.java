package ui;

import static org.lwjgl.opengl.GL13.GL_TEXTURE1;
import static org.lwjgl.opengl.GL13.glActiveTexture;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

import graphics.Shader;
import graphics.Texture;
import graphics.VertexArray;
import math.Matrix4f;
import math.Vector3f;
import meshdata.RectMesh;

public class Numbers {
	private static Texture[] numbersTextures = new Texture[10];
	private static VertexArray mesh;
	private static Shader NUMBER_SHADER;
	
	private Vector3f pos = new Vector3f();
	private Matrix4f ml_matrix;
	
	public static void load() {
		try {
			BufferedImage img = ImageIO.read(new FileInputStream("res/numbers.png"));
			
			for(int i = 0; i < 10; ++i) {
			numbersTextures[i] = new Texture(img.getSubimage(i * 20, 0, 20, img.getHeight()));
			}	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		RectMesh rect = new RectMesh(numbersTextures[0].getWidth() * 2, numbersTextures[0].getHeight() * 2, 0.2f);
		mesh = new VertexArray(rect.getVetices(), rect.getIndices(), rect.getTextureCoords());
		
		NUMBER_SHADER = new Shader("shaders/number.vert", "shaders/number.frag");
		glActiveTexture(GL_TEXTURE1);
		NUMBER_SHADER.enable();
		NUMBER_SHADER.setUniformMat4f("pr_matrix", Shader.pr_matrix);
		NUMBER_SHADER.setUniform1i("tex", 1);
		NUMBER_SHADER.disable();
	}
	
	public Numbers(float x, float y) {
		pos.x = x;
		pos.y = y;
		ml_matrix = Matrix4f.translate(pos);
	}
	
	public static int getWidth() {
		return numbersTextures[0].getWidth() * 2;
	}
	
	public void render(int num) {
		numbersTextures[num].bind();
		NUMBER_SHADER.enable();
		NUMBER_SHADER.setUniformMat4f("ml_matrix", ml_matrix);
		mesh.render();
		NUMBER_SHADER.disable();
		numbersTextures[num].unbind();
	}
}
