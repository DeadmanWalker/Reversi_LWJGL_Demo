package gamestate;

public abstract class GameState {
	protected StateManager gsm;
	protected boolean render_below;
	
	protected GameState(StateManager gsm, boolean render_below) {
		this.gsm = gsm;
		this.render_below = render_below;
		init();
	}
	
	public abstract void init();
	public abstract void update();
	public abstract void render();
	
	public boolean getRenderBelow() {
		return render_below;
	}
}
