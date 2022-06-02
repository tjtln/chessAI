package Chess3;

public class Main {
	public static void main(String[] args) {
		Game game = new Game();
		Move move = new Move(7,6,5,5);
		Move move1 = new Move(1,1,3,1);
		if(game.isLegal(move)) {
			game.update(move);
		}
		if(game.isLegal(move1)) {
			game.update(move1);
		}
	}
}
