package Chess3;

public class Main {

	public static void main(String[] args) {
		Game game = new Game();
		Move move = new Move(6,4,4,4);
		Move move1 = new Move(1,4,3,4);
		Move move2 = new Move(7,3,5,5);
		Move move3 = new Move(1,1,2,1);
		Move move4 = new Move(5,5,1,5);
		Move move5 = new Move(0,4,1,5);
		game.update(move);
		game.update(move1);
		game.update(move2);
		game.update(move3);
		game.update(move4);
		if(game.isLegal(move5)) {
			game.update(move5);
		}
	}
}
