package play;

import main.WindowConstains;
import math.Vector3f;
import ui.Numbers;

public class ScoreDisplay {
	private int[] scores = new int[2];
	
	private static Vector3f pos = new Vector3f(WindowConstains.WIDTH / (2 * WindowConstains.SIZE_MOD) - 100, WindowConstains.HEIGHT / WindowConstains.SIZE_MOD - 130, 0.0f);;
	private static Piece[] players = new Piece[] {
			new Piece(pos.x + 180, pos.y, 0),
			new Piece(pos.x, pos.y, 1)
		};
	
	private static Numbers[] player_black_score = new Numbers[] {
			new Numbers(pos.x + Piece.getSize() + 1 - 5 , pos.y),
			new Numbers(pos.x + Piece.getSize() + Numbers.getWidth() + 1 - 10, pos.y)
		};
	
	private static Numbers[] player_white_score = new Numbers[] {
			new Numbers(pos.x + 195 - (Piece.getSize() + 2 * Numbers.getWidth() + 1 - 10), pos.y),
			new Numbers(pos.x + 195 - (Piece.getSize() + Numbers.getWidth() + 1 - 5), pos.y)
		};
	
	public ScoreDisplay() {
		
	}
	
	public void update(int[] scores) {
		this.scores = scores;
	}
	
	public void render() {
		for(int i = 0; i < 2; ++i) {
			players[i].render();
		}
		
		player_black_score[1].render(scores[1] % 10);
		player_black_score[0].render(scores[1] / 10);
		
		player_white_score[1].render(scores[0] % 10);
		player_white_score[0].render(scores[0] / 10);
		
	}
}
