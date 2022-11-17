package gamestate;

import org.lwjgl.glfw.GLFW;

import input.InputKB;
import input.InputMouse;

public class MainScreen extends GameState{

	public MainScreen(StateManager gsm) {
		super(gsm, false);
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		if(InputKB.click[GLFW.GLFW_KEY_SPACE] || InputMouse.click[GLFW.GLFW_MOUSE_BUTTON_LEFT]) {
			gsm.pushState(new Playing(gsm));
		}
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		
	}

}
