package input;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

public class InputMouse extends GLFWMouseButtonCallback{
	public static boolean[] click = new boolean[100];
	public static boolean[] release = new boolean[100];
	public static boolean[] buttons = new boolean[100];
	@Override
	public void invoke(long window, int button, int action, int mods) {
		click[button] = action != GLFW.GLFW_RELEASE && !buttons[button];
		release[button] = action == GLFW.GLFW_RELEASE && buttons[button];
		buttons[button] = action != GLFW.GLFW_RELEASE;
	}
}
