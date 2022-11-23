package gamestate;

import java.util.ArrayDeque;

public class StateManager {
	private ArrayDeque<GameState> states;
	private final int transition_cooldown = 10;
	private int cd_counter = 0;
	
	public StateManager() {
		states = new ArrayDeque<GameState>();
	}
	
	public void update() {
		if(cd_counter <= 0)
			states.peek().update();
		else
			--cd_counter;
	}
	
	public void render() {
		for(GameState state: states) {
			state.render();
			if(!state.getRenderBelow())
				break;
		}
	}
	
	public void pushState(GameState state) {
		states.push(state);
		cd_counter = transition_cooldown;
	}
	
	public void popState() {
		states.pop();
		cd_counter = transition_cooldown;
	}
	
	public boolean isEmpty() {
		return states.isEmpty();
	}
	
	public void clear() {
		states.clear();
	}
}
