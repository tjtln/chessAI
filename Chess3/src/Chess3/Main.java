package Chess3;

public class Main {
	public static void main(String[] args) {
		Game game = new Game();
		Move move = new Move(6,4,4,4);
		Move move1 = new Move(1,4,3,4);
		Move move2 = new Move(7,6,5,5);
		Move move3 = new Move(0,6,2,5);
		Move move4 = new Move(7,5,4,2);
		Move move5 = new Move(0,5,3,2);
		Move move6 = new Move(7,4,7,6);
		game.update(move);
		game.update(move1);
		game.update(move2);
		game.update(move3);
		game.update(move4);
		game.update(move5);
		if (game.isLegal(move6)) {
			game.update(move6);
		}
	}
}
