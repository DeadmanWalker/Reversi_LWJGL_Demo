package gamestate;

import play.Board;
import play.ScoreDisplay;

public class Playing extends GameState {
	Board gameBoard;
	ScoreDisplay sc_display;
	
	public Playing(StateManager gsm) {
		super(gsm, false);
	}

	@Override
	public void init() {
		gameBoard = new Board();
		sc_display = new ScoreDisplay();
	}

	@Override
	public void update() {
		if(!gameBoard.checkForGameOver()) {
			gameBoard.update();
			sc_display.update(gameBoard.getScores());
		}
		else {
			gsm.pushState(new GameOver(gsm, gameBoard));
		}
	}

	@Override
	public void render() {
		gameBoard.render();
		sc_display.render();
	}
}
