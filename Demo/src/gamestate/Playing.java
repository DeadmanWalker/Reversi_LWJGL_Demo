package gamestate;

import main.WindowConstains;
import math.Vector3f;
import play.Board;
import play.ScoreDisplay;
import ui.Button;

public class Playing extends GameState {
	Board gameBoard;
	ScoreDisplay sc_display;
	Button backButton;
	
	public Playing(StateManager gsm) {
		super(gsm, false);
	}

	@Override
	public void init() {
		gameBoard = new Board();
		sc_display = new ScoreDisplay();
		backButton = new Button(new Vector3f(5, WindowConstains.HEIGHT / WindowConstains.SIZE_MOD - 40, 0), "res/buttons/back_button.png");
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
		
		if(backButton.isClick()) {
			gsm.popState();
			if(gsm.isEmpty())
				gsm.pushState(new MainScreen(gsm));
		}
	}

	@Override
	public void render() {
		gameBoard.render();
		sc_display.render();
		backButton.render();
	}
}
