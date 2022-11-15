package main;



public class Game implements Runnable {
	
	private Thread thread;
	private boolean is_running = false;
	GameWindow gameWindow;
	
	
	public void start() {
		is_running = true;
		thread = new Thread(this, "Game");
		thread.start();
		
	}
	
	public void load() {
		
	}
	
	public void initialize() {
		gameWindow = new GameWindow();
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
		int updates = 0;
		int frames = 0;
		
		long last_report = System.currentTimeMillis();
		
		
		while(is_running) {
			long now = System.nanoTime();
			
			update_timer += (now - last_time) / ups;
			render_timer += (now - last_time) / fps;
			
			last_time = now;
			
			if(update_timer >= 1.0) {
				update();
				++updates;
				--update_timer;
			}
			
			if(render_timer >= 1.0) {
				render();
				++frames;
				--render_timer;
			}
			
			long now_report = System.currentTimeMillis();
			
			if(now_report - last_report >= 1000) {
				last_report = now_report;
				System.out.println("ups: " + updates + " fps: " + frames);
				updates = 0;
				frames = 0;
			}
			
			is_running = !gameWindow.shouldCloseWindow();
		}
	}
	
	private void update() {
		gameWindow.update();
	}
	
	private void render() {
		gameWindow.render();
		gameWindow.swapBuffer();
	}

}
