package gamestate;

import main.WindowConstains;
import math.Vector3f;
import ui.Banner;
import ui.Button;

public class MainScreen extends GameState{
	
	private Button[] buttons = new Button[] {
			new Button(new Vector3f((WindowConstains.WIDTH / WindowConstains.SIZE_MOD - 126) / 2, 3 * (WindowConstains.HEIGHT / WindowConstains.SIZE_MOD) / 5, 0), "res/buttons/play_button.png"),
			new Button(new Vector3f((WindowConstains.WIDTH / WindowConstains.SIZE_MOD - 116) / 2, 3 * (WindowConstains.HEIGHT / WindowConstains.SIZE_MOD) / 5 + 50, 0), "res/buttons/quit_button.png")
	};

	public MainScreen(StateManager gsm) {
		super(gsm, false);
	}

	@Override
	public void init() {
		
	}

	@Override
	public void update() {
		if(buttons[0].isClick()) {
			gsm.popState();
			gsm.pushState(new Playing(gsm));
		}
		if(buttons[1].isClick()) {
			gsm.clear();
		}
	}

	@Override
	public void render() {
		buttons[0].render();
		buttons[1].render();
		Banner.render();
	}

}
