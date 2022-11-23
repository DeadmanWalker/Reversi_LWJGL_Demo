package main;

import gamestate.MainScreen;
import gamestate.StateManager;
import play.Board;
import ui.Announcements;
import ui.Background;
import ui.Banner;
import ui.Button;
import ui.Numbers;

public class Game implements Runnable {
	private StateManager gameStateManager;
	private Thread thread;
	private boolean is_running = false;
	private GameWindow gameWindow;
	private Background gameBG;
	
	
	public void start() {
		is_running = true;
		thread = new Thread(this, "Game");
		thread.start();
	}
	
	private void load() {
		Background.load();
		Board.load();
		Announcements.load();
		Numbers.load();
		Button.load();
		Banner.load();
	}
	
	private void initialize() {
		gameWindow = new GameWindow();
		gameBG = new Background();
		gameStateManager = new StateManager();
		gameStateManager.pushState(new MainScreen(gameStateManager));
	}
	
	@Override
	public void run() {
		
		initialize();
		load();
		
		long last_time = System.nanoTime();
		double update_timer = 0.0;
		double render_timer = 0.0;
		double ups = 1000000000.0 / 60.0;
		double fps = 1000000000.0 / 60.0;
		
		
		while(is_running) {
			long now = System.nanoTime();
			
			update_timer += (now - last_time) / ups;
			render_timer += (now - last_time) / fps;
			
			last_time = now;
			
			if(update_timer >= 1.0) {
				update();
				--update_timer;
			}
			
			if(render_timer >= 1.0) {
				render();
				--render_timer;
			}
			
			
			is_running = !gameWindow.shouldCloseWindow() && !gameStateManager.isEmpty();
		}
	}
	
	private void update() {
		gameWindow.update();
		gameStateManager.update();
	}
	
	private void render() {
		gameWindow.render();
		gameBG.render();
		gameStateManager.render();
		gameWindow.swapBuffer();
	}

}
