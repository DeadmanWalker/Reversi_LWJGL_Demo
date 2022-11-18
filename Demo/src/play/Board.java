package play;

import static org.lwjgl.opengl.GL13.GL_TEXTURE1;
import static org.lwjgl.opengl.GL13.glActiveTexture;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Vector;

import org.lwjgl.glfw.GLFW;
import graphics.Shader;
import graphics.Texture;
import graphics.VertexArray;
import input.InputCursor;
import input.InputMouse;
import main.WindowConstains;
import math.Matrix4f;
import math.Vector3f;
import meshdata.RectMesh;

public class Board {
	private VertexArray mesh;
	private Texture texture;
	
	private int cell_size = 20;
	private int n_col = 8;
	private int n_row = 8;
	//Board state
	private Map<Integer, Piece> cellsMap = new HashMap<>();
	private Map<Integer, Vector<Integer>> moveMap = new HashMap<>();
	private Vector<MoveVisualizer> moveVisualizers = new Vector<>();
	//Players data
	private Queue<Integer> player_turn = new LinkedList<>();
	private int last_winner = 0;
	private boolean isFirstTurn;
	private int[] players_score = {0, 0};
	private int turns_without_move = 0;
	
	private static final int SIZE = 174;
	private static final int WIDTH = 174;
	private static final int HEIGHT = 177;
	private static Vector3f position = new Vector3f(640 - (SIZE / 2) * WindowConstains.SIZE_MOD, 270 - (SIZE / 2) * WindowConstains.SIZE_MOD, 0.0f);
	private static Shader BOARD_SHADER;
	
	private static Map<Integer, Vector3f> direction = new HashMap<>();
	
	
	public static void load() {
		direction.put(1, new Vector3f(-1, 1, 0));
		direction.put(2, new Vector3f(0, 1, 0));
		direction.put(3, new Vector3f(1, 1, 0));
		direction.put(4, new Vector3f(-1, 0, 0));
		direction.put(6, new Vector3f(1, 0, 0));
		direction.put(7, new Vector3f(-1, -1, 0));
		direction.put(8, new Vector3f(0, -1, 0));
		direction.put(9, new Vector3f(1, -1, 0));
		
		glActiveTexture(GL_TEXTURE1);
		BOARD_SHADER = new Shader("shaders/board.vert", "shaders/board.frag");
		BOARD_SHADER.enable();
		BOARD_SHADER.setUniformMat4f("pr_matrix", Shader.pr_matrix);
		BOARD_SHADER.setUniformMat4f("ml_matrix", Matrix4f.translate(position));
		BOARD_SHADER.setUniform1i("tex", 1);
		BOARD_SHADER.disable();
		
		Piece.load();
		MoveVisualizer.load();
	}
	
	public Board() {
		
		RectMesh rect = new RectMesh((WIDTH) * WindowConstains.SIZE_MOD, (HEIGHT) * WindowConstains.SIZE_MOD, 0.1f);
		
		mesh = new VertexArray(rect.getVetices(), rect.getIndices(), rect.getTextureCoords());
		texture = new Texture("res/board.png");
		
		Piece.create();
		MoveVisualizer.create();
		resetPiece();
	}
	
	public void resetPiece() {
		player_turn.clear();
		if(last_winner == -1) {
			last_winner = (int)Math.random() % 2;
		}
		player_turn.add((last_winner + 1) % 2);
		player_turn.add(last_winner);
		cellsMap.clear();
		for(int i = n_col / 2 - 1; i <= n_col / 2; ++i) {
			for(int j = n_row / 2 - 1; j <= n_row / 2; ++j) {
				int hashcode = hashFunc(i, j);
				Vector3f draw_pos = tranlateHashToDrawPos(hashcode);
				cellsMap.put(hashcode, new Piece(draw_pos.x, draw_pos.y, (i + j) % 2));
			}
		}
		isFirstTurn = true;
	}
	
	private void checkForMove(int hashcode)
	{
		for (int i = 1; i <= 9; ++i)
		{
			if(i == 5)
				continue;
			Vector3f takeDirect = new Vector3f((i - 1) % 3 - 1, (i - 1) / 3 - 1, 0);
			Vector3f temp = dehashFunc(hashcode);
			temp.add(takeDirect);
			Piece cell = cellsMap.get(hashFunc(temp));
			Vector<Integer> takeTemp = new Vector<>();
			if (cellsMap.containsKey(hashFunc(temp)) && cell.getPlayerID() != player_turn.peek()) {
				while(isInBound(temp)) {
					if (!(cell != null))
					{
						int cellhash = hashFunc(temp);
						
						if(!(moveMap.get(cellhash) != null)) {
							moveMap.put(cellhash, new Vector<>());
							addMoveVisualizer(cellhash);
						}
						
						Vector<Integer> takePtr = moveMap.get(cellhash);
						takePtr.addAll(takeTemp);
						break;
					}
					else if (cell.getPlayerID() == player_turn.peek())
					{
						break;
					}
					takeTemp.add(hashFunc(temp));
					temp.add(takeDirect);
					cell = cellsMap.get(hashFunc(temp));
				}
			}
		}
	}
	
	public void update() {
		
		if(isFirstTurn) {
			updateMove();
			isFirstTurn = false;
		}
		
		Vector3f mouse_coord = tranlatePosToBoardCoord((int)InputCursor.POS_X, (int)InputCursor.POS_Y);
		
		if(!moveMap.isEmpty()) {
			turns_without_move = 0;
			if(InputMouse.click[GLFW.GLFW_MOUSE_BUTTON_LEFT] && mouse_coord != null) {
				if(moveMap.get(hashFunc(mouse_coord)) != null) {
					placePiece(mouse_coord, player_turn.peek());
					takePieces(mouse_coord);
					nextTurn();
					updateMove();
				}
			}
		}
		else {
			nextTurn();
			++turns_without_move;
		}
	}
	
	public boolean checkForGameOver() {
		if(turns_without_move >= 2 || cellsMap.size() >= n_col * n_row) {
			checkForWinner();
			return true;
		}
		return false;
	}
	
	private void checkForWinner() {
		
		if(players_score[0] > players_score[1]) {
			last_winner = 0;
		}
		else if (players_score[0] < players_score[1]) {
			last_winner = 1;
		}
		else {
			last_winner = -1;
		}
	}
	
	public int getWinner() {
		return last_winner;
	}
	
	private void nextTurn() {
		int temp = player_turn.peek();
		player_turn.remove();
		player_turn.add(temp);
	}
	
	private Vector3f tranlatePosToBoardCoord(int x, int y) {
		
		int coord_x = (x - ((int)position.x + 3 * WindowConstains.SIZE_MOD)) / WindowConstains.SIZE_MOD;
		int coord_y = (y - ((int)position.y + 3 * WindowConstains.SIZE_MOD)) / WindowConstains.SIZE_MOD;
		
		if(coord_x >= 0 && coord_x < (SIZE - 6)
			&& coord_y >= 0 && coord_y < (SIZE - 6)
			&& coord_x % (cell_size + 1) != 0
			&& coord_y % (cell_size + 1) != 0)
		{
			if(coord_x > SIZE / 2 - 3)
				--coord_x;
			if(coord_y > SIZE / 2 - 3)
				--coord_y;
			
			return new Vector3f((coord_x) / (cell_size + 1), (coord_y) / (cell_size + 1), 0.0f);
		} 
		else {
			return null;
		}
	}
	
	private void placePiece(Vector3f board_coord, int id) {
		int hashcode = hashFunc((int)board_coord.x, (int)board_coord.y);
		Vector3f draw_pos = tranlateHashToDrawPos(hashcode);
		cellsMap.put(hashcode, new Piece(draw_pos.x, draw_pos.y, id));
		++players_score[id];
	}
	
	private void addMoveVisualizer(int hashcode) {
		Vector3f draw_pos = tranlateHashToDrawPos(hashcode);
		moveVisualizers.add(new MoveVisualizer(draw_pos.x, draw_pos.y));
	}
	
	private void renderPiece() {
		cellsMap.forEach((k,v) -> v.render());
	}
	
	private void renderMove() {
		moveVisualizers.forEach((m) -> m.render());
	}
	
	public void render() {
		texture.bind();
		BOARD_SHADER.enable();
		mesh.render();
		BOARD_SHADER.disable();
		texture.unbind();
		
		renderPiece();
		renderMove();
	}
	
	private int hashFunc(int col, int row) {
		return col + n_col * row;
	}
	
	private int hashFunc(Vector3f piece_coord) {
		return (int)piece_coord.x + n_col * (int)piece_coord.y;
	}
	
	private Vector3f dehashFunc(int hash) {
		return new Vector3f(hash % n_col, hash / n_col, 0.0f);
	}
	
	private Vector3f tranlateHashToDrawPos(int hashcode) {
		Vector3f board_coord = dehashFunc(hashcode);
		
		float pos_x = position.x + (3 + board_coord.x * (cell_size + 1)) * WindowConstains.SIZE_MOD;
		float pos_y = position.y + (3 + board_coord.y * (cell_size + 1)) * WindowConstains.SIZE_MOD;
		
		if(board_coord.x >= n_col / 2)
			pos_x += 1 * WindowConstains.SIZE_MOD;
		if(board_coord.y >= n_row / 2)
			pos_y += 1 * WindowConstains.SIZE_MOD;
		return new Vector3f(pos_x, pos_y, 0.0f);
	}
	
	private boolean isInBound(Vector3f coord) {
		return coord.x >= 0 && coord.x < n_col &&
				coord.y >= 0 && coord.y < n_row;
	}
	
	private void updateMove() {
		moveMap.clear();
		moveVisualizers.clear();
		cellsMap.forEach((k, v) -> {if(v.getPlayerID() == player_turn.peek()) checkForMove(k);});
	}
	
	private void takePieces(Vector3f board_coord)
	{
		Vector<Integer> take_set = moveMap.get(hashFunc(board_coord));

		for (int i = 0; i < take_set.size(); ++i)
		{
			Vector3f temp = dehashFunc(take_set.get(i));
			placePiece(temp, player_turn.peek());
		}
		
		players_score[player_turn.peek()] += take_set.size();
		players_score[(player_turn.peek() + 1) % 2] -= take_set.size();
	}
	
	public int[] getScores() {
		return players_score;
	}
}
