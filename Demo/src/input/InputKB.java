package input;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

public class InputKB extends GLFWKeyCallback{
	public static boolean[] click = new boolean[1000];
	public static boolean[] release = new boolean[1000];
	public static boolean[] keys = new boolean[1000];
	@Override
	public void invoke(long window, int key, int scancode, int action, int mods) {
		click[key] = action != GLFW.GLFW_RELEASE && !keys[key];
		release[key] = action == GLFW.GLFW_RELEASE && keys[key];
		keys[key] = action != GLFW.GLFW_RELEASE;
	}

}
