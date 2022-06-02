package Chess3;
//Move that contains X and Y coordinates for both starting position and ending position
public class Move {
	int startingX;
	int startingY;
	int endingX;
	int endingY;
	public Move(int a, int b, int c, int d){
		startingX = a;
		startingY = b;
		endingX = c;
		endingY = d;
	}
	public String toString(){
		return ("(" + startingX + ", " + startingY + ") ->" + "(" + endingX + ", " + endingY + ")" );
		
	}
}
