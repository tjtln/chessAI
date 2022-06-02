package Chess3;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class PossibleMovesTest {

	@Test
	void rookTest() {
		Game game = new Game();
		ArrayList<Move> moves = new ArrayList<Move>();
		assertEquals(game.findPossibleMoves(0, 0), moves);
		assertEquals(game.findPossibleMoves(0, 7), moves);
		assertEquals(game.findPossibleMoves(7, 0), moves);
		assertEquals(game.findPossibleMoves(7, 7), moves);
	}
	@Test
	void knightTest() {
		Game game = new Game();
		ArrayList<Move> moves = new ArrayList<Move>();
		assertEquals(game.findPossibleMoves(0, 1), moves);
		assertEquals(game.findPossibleMoves(0, 6), moves);
		assertEquals(game.findPossibleMoves(7, 1), moves);
		assertEquals(game.findPossibleMoves(7, 6), moves);
	}
	@Test
	void bishopTest() {
		Game game = new Game();
		ArrayList<Move> moves = new ArrayList<Move>();
		assertEquals(game.findPossibleMoves(0, 2), moves);
		assertEquals(game.findPossibleMoves(0, 5), moves);
		assertEquals(game.findPossibleMoves(7, 2), moves);
		assertEquals(game.findPossibleMoves(7, 5), moves);
	}
	@Test
	void queenTest() {
		Game game = new Game();
		ArrayList<Move> moves = new ArrayList<Move>();
		assertEquals(game.findPossibleMoves(0, 3), moves);
		assertEquals(game.findPossibleMoves(7, 3), moves);
	}
	@Test
	void kingTest() {
		Game game = new Game();
		ArrayList<Move> moves = new ArrayList<Move>();
		assertEquals(game.findPossibleMoves(0, 4), moves);
		assertEquals(game.findPossibleMoves(7, 4), moves);
	}
}
