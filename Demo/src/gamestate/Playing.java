package gamestate;

import org.lwjgl.glfw.GLFW;

import input.InputMouse;
import play.Board;

public class Playing extends GameState {
	Board gameBoard;
	
	public Playing(StateManager gsm) {
		super(gsm, false);
	}

	@Override
	public void init() {
		gameBoard = new Board();
	}

	@Override
	public void update() {
		if(!gameBoard.checkForGameOver())
			gameBoard.update();
		else {
			gameBoard.getWinner();
			if(InputMouse.click[GLFW.GLFW_MOUSE_BUTTON_LEFT]) {
				gameBoard.resetPiece();
			}
		}
	}

	@Override
	public void render() {
		gameBoard.render();
	}
}
