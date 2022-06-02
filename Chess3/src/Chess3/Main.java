package Chess3;

public class Main {
	public static void main(String[] args) {
		Game game = new Game();
		Move move = new Move(7,6,5,5);
		if(game.isLegal(move)) {
			game.update(move);
		}
	}
}
