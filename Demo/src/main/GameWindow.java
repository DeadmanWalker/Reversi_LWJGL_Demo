package main;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;



public class GameWindow {
	private int width = 1280;
	private int height = 720;
	
	private long window;
	
	public GameWindow() {
		if(!glfwInit()) {
			System.out.println("OH NO!!!");
			return;
		}
		
		glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);
		glfwWindowHint(GLFW_VISIBLE, GL_FALSE);
		window = glfwCreateWindow(width, height, "Reversi", NULL, NULL);
		
		GLFWVidMode vidmodeBuffer = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window, (vidmodeBuffer.width() - width) / 2, (vidmodeBuffer.height() - height) / 2);
		
		glfwMakeContextCurrent(window);
		GL.createCapabilities();
		
		glfwShowWindow(window);
		
		glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		glEnable(GL_DEPTH_TEST);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_BLEND);
		
		glfwSwapInterval(0);
		
		System.out.println("OpenGL: " + glGetString(GL_VERSION));
	}
	
	public boolean shouldCloseWindow() {
		return glfwWindowShouldClose(window);
	}
	
	public void update() {
		
		glfwPollEvents();
		
	}
	
	public void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}
	
	public void swapBuffer() {
		glfwSwapBuffers(window);
		glFlush();
		glFinish();
	}
}
