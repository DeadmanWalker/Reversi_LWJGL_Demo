package gamestate;

import org.lwjgl.glfw.GLFW;

import input.InputKB;
import input.InputMouse;
import play.Board;
import ui.Announcements;

public class GameOver extends GameState {
	
	private Board brd;
	
	public GameOver(StateManager gsm, Board brd) {
		super(gsm, true);
		this.brd = brd;
	}

	@Override
	public void init() {
		Announcements.create();
	}

	@Override
	public void update() {
		if(InputKB.click[GLFW.GLFW_KEY_SPACE] ||
			InputMouse.click[GLFW.GLFW_MOUSE_BUTTON_LEFT]) {
			brd.resetPiece();
			gsm.popState();
		}
	}

	@Override
	public void render() {
		Announcements.renderGameOver(brd.getWinner());
	}
	
}
