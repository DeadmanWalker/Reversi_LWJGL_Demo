package input;

import org.lwjgl.glfw.GLFWCursorPosCallback;

import main.WindowConstains;

public class InputCursor extends GLFWCursorPosCallback {
	
	public static float POS_X;
	public static float POS_Y;
	@Override
	public void invoke(long window, double x, double y) {
		POS_X = (float)x / WindowConstains.SIZE_MOD;
		POS_Y = (float)y / WindowConstains.SIZE_MOD;
	}

}
