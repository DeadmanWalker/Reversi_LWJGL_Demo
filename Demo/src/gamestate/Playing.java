package gamestate;

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
			gsm.pushState(new GameOver(gsm, gameBoard));
		}
	}

	@Override
	public void render() {
		gameBoard.render();
	}
}
