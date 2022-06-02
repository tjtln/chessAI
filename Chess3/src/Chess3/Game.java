package Chess3;

import java.util.ArrayList;

public class Game {
	//array of the pieces
	//NOTE: *Square* is not referring to the square itself, but the piece on it, meaning Square.color refers to the piece's color instead of the square's color
	public Square[][] board = new Square[8][8];
	//History of all moves played this game
	ArrayList<Move> history = new ArrayList<Move>();
	//Which color is next to move
	Color nextToMove = Color.White;
	//All attacked squares
	public ArrayList<int[]> allAttackedSquares = new ArrayList<int[]>();
	public Game() {
		//Initializing all of the pieces
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				Square square = new Square();
				board[i][j] = square;
				board[i][j].x = i;
				board[i][j].y = j;
				if (i < 2) {
					board[i][j].color = Color.Black;
					if(i == 1) {
						board[i][j].type = Piece.Pawn;
					} else if(i == 0) {
						if(j == 0 || j == 7) {
							board[i][j].type = Piece.Rook;
						} else if(j == 1 || j == 6) {
							board[i][j].type = Piece.Knight;
						} else if(j == 2 || j == 5) {
							board[i][j].type = Piece.Bishop;
						} else if(j == 3) {
							board[i][j].type = Piece.Queen;
						} else {
							board[i][j].type = Piece.King;
						}
					}
				} else if (i > 5) {
					board[i][j].color = Color.White;
					if(i == 6) {
						board[i][j].type = Piece.Pawn;
					} else if(i == 7) {
						if(j == 0 || j == 7) {
							board[i][j].type = Piece.Rook;
						} else if(j == 1 || j == 6) {
							board[i][j].type = Piece.Knight;
						} else if(j == 2 || j == 5) {
							board[i][j].type = Piece.Bishop;
						} else if(j == 3) {
							board[i][j].type = Piece.Queen;
						} else {
							board[i][j].type = Piece.King;
						}
					}
				}
			}
		}
		findAttackedSquares();
	}
	//if given a move, checks if the move is legal
	public boolean isLegal(Move move){
		//check correct color
		if(board[move.startingX][move.startingY].color != nextToMove) {
			return false;
		}
		//check if move is possible
		ArrayList<Move> possibleMoves = findPossibleMoves(move.startingX, move.startingY);
		boolean flag = true;
		for(int i = 0; i < possibleMoves.size(); i++) {
			if(possibleMoves.get(i).startingX == move.startingX && possibleMoves.get(i).startingY == move.startingY && possibleMoves.get(i).endingX == move.endingX && possibleMoves.get(i).endingY == move.endingY) {
				flag = false;
			}
		}
		if(flag == true) {
			return false;
		}
		//check if it would put yourself in check
		if(wouldPutSelfInCheck(move)) {
			return false;
		}
		return true;
	}

	private boolean wouldPutSelfInCheck(Move move) {
		//make move on simulated board
		Game nextBoard = new Game();
		nextBoard.updateMoves(history);
		nextBoard.update(move);
		int kingX = -1;
		int kingY = -1;
		outer:
			for(int i = 0; i < 8; i++) {
				for(int j = 0; j < 8; j++) {
					if(nextBoard.board[i][j].type == Piece.King && nextBoard.board[i][j].color != nextBoard.nextToMove) {
						kingX = i;
						kingY = j;
						break outer;
					}
				}
			}

		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				if(nextBoard.nextToMove == nextBoard.board[i][j].color) {
					ArrayList<Move> moves = nextBoard.findPossibleMoves(i, j);
					for(int k = 0; k < moves.size(); k++) {
						if(moves.get(k).endingX == kingX && moves.get(k).endingY == kingY) {
							return true;
						}
					}
				}
			}
		}

		return false;
	}

	ArrayList<Move> findPossibleMoves(int x, int y){
		ArrayList<Move> possibleMoves = new ArrayList<Move>();
		Piece piece = board[x][y].type;
		int i = x;
		int j = y;
		switch(piece) {
		case Rook:
			while(i < 8) {	//find open squares in the x+ direction
				if(i != x) {
					if(board[i][j].type == null) {
						possibleMoves.add(new Move(x,y,i,j));
					} else {
						if(board[i][j].color != board[x][y].color) {
							possibleMoves.add(new Move(x,y,i,j));
						}
						break;
					}
				}
				i++;
			}
			//reset i and j to piece's coords
			i = x;
			j = y;
			while(i > -1) {	//find open squares in the x- direction
				if(i != x) {
					if(board[i][j].type == null) {
						possibleMoves.add(new Move(x,y,i,j));
					} else {
						if(board[i][j].color != board[x][y].color) {
							possibleMoves.add(new Move(x,y,i,j));;
						}
						break;
					}
				}
				i--;
			}
			//reset i and j to piece's coords
			i = x;
			j = y;
			while(j < 8) {	//find open squares in the y+ direction
				if(j != y) {
					if(board[i][j].type == null) {
						possibleMoves.add(new Move(x,y,i,j));
					} else {
						if(board[i][j].color != board[x][y].color) {
							possibleMoves.add(new Move(x,y,i,j));
						}
						break;
					}
				}
				j++;
			}
			//reset i and j to piece's coords
			i = x;
			j = y;
			while(j > -1) {	//find open squares in the y- direction
				if(j != y) {
					if(board[i][j].type == null) {
						possibleMoves.add(new Move(x,y,i,j));
					} else {
						if(board[i][j].color != board[x][y].color) {
							possibleMoves.add(new Move(x,y,i,j));
						}
						break;
					}
				}
				j--;
			}
			break;
		case Knight:
			//x and y being repurposed for an increment to the new value rather than the new value (as rook does)
			for(i = -2; i < 3; i++) {
				for(j = -2; j < 3; j++) {
					if(x+i < 8 && x+i > -1 && y+j < 8 && y+j > -1 && (Math.abs(i) + Math.abs(j) == 3)) { // if x and y are not 0 && move is within bounds && wouldn't take a piece of its own color, add it to possible moves
						if(board[x][y].color != board[x+i][y+j].color) {
							possibleMoves.add(new Move(x, y, x + i, y + j));
						}
					}
				}
			}
			break;
		case Bishop:
			while(i < 8 && j < 8) {	//find open squares in the x+ y+ direction
				if(j != y && i != x) {
					if(board[i][j].type == null) {
						possibleMoves.add(new Move(x,y,i,j));
					} else {
						if(board[i][j].color != board[x][y].color) {
							possibleMoves.add(new Move(x,y,i,j));
						}
						break;
					}
				}
				i++;
				j++;
			}
			//reset i and j to x and y coords
			i = x;
			j = y;
			while(i < 8 && j > -1) {	//find open squares in the x+ y- direction
				if(x != i && j != y) {
					if(board[i][j].type == null) {
						possibleMoves.add(new Move(x,y,i,j));
					} else {
						if(board[i][j].color != board[x][y].color) {
							possibleMoves.add(new Move(x,y,i,j));
						}
						break;
					}
				}
				i++;
				j--;
			}
			//reset i and j to x and y coords
			i = x;
			j = y;
			while(i > -1 && j < 8) {	//find open squares in the x- y+ direction
				if(i != x && j != y) {
					if(board[i][j].type == null) {
						possibleMoves.add(new Move(x,y,i,j));
					} else {
						if(board[i][j].color != board[x][y].color) {
							possibleMoves.add(new Move(x,y,i,j));
						}
						break;
					}
				}
				i--;
				j++;
			}
			//reset i and j to x and y coords
			i = x;
			j = y;
			while(i > -1 && j > -1) {	//find open squares in the x- y- direction
				if(i != x && j != y) {
					if(board[i][j].type == null) {
						possibleMoves.add(new Move(x,y,i,j));
					} else {
						if(board[i][j].color != board[x][y].color) {
							possibleMoves.add(new Move(x,y,i,j));
						}
						break;
					}
				}
				i--;
				j--;
			}
			break;
		case Queen:
			while(i < 8 && j < 8) {	//find open squares in the x+ y+ direction
				if(j != y && i != x) {
					if(board[i][j].type == null) {
						possibleMoves.add(new Move(x,y,i,j));
					} else {
						if(board[i][j].color != board[x][y].color) {
							possibleMoves.add(new Move(x,y,i,j));
						}
						break;
					}
				}
				i++;
				j++;
			}
			//reset i and j to x and y coords
			i = x;
			j = y;
			while(i < 8 && j > -1) {	//find open squares in the x+ y- direction
				if(x != i && j != y) {
					if(board[i][j].type == null) {
						possibleMoves.add(new Move(x,y,i,j));
					} else {
						if(board[i][j].color != board[x][y].color) {
							possibleMoves.add(new Move(x,y,i,j));
						}
						break;
					}
				}
				i++;
				j--;
			}
			//reset i and j to x and y coords
			i = x;
			j = y;
			while(i > -1 && j < 8) {	//find open squares in the x- y+ direction
				if(i != x && j != y) {
					if(board[i][j].type == null) {
						possibleMoves.add(new Move(x,y,i,j));
					} else {
						if(board[i][j].color != board[x][y].color) {
							possibleMoves.add(new Move(x,y,i,j));
						}
						break;
					}
				}
				i--;
				j++;
			}
			//reset i and j to x and y coords
			i = x;
			j = y;
			while(i > -1 && j > -1) {	//find open squares in the x- y- direction
				if(i != x && j != y) {
					if(board[i][j].type == null) {
						possibleMoves.add(new Move(x,y,i,j));
					} else {
						if(board[i][j].color != board[x][y].color) {
							possibleMoves.add(new Move(x,y,i,j));
						}
						break;
					}
				}
				i--;
				j--;
			}
			//reset i and j to piece's coords
			i = x;
			j = y;
			while(i < 8) {	//find open squares in the x+ direction
				if(i != x) {
					if(board[i][j].type == null) {
						possibleMoves.add(new Move(x,y,i,j));
					} else {
						if(board[i][j].color != board[x][y].color) {
							possibleMoves.add(new Move(x,y,i,j));
						}
						break;
					}
				}
				i++;
			}
			//reset i and j to piece's coords
			i = x;
			j = y;
			while(i > -1) {	//find open squares in the x- direction
				if(i != x) {
					if(board[i][j].type == null) {
						possibleMoves.add(new Move(x,y,i,j));
					} else {
						if(board[i][j].color != board[x][y].color) {
							possibleMoves.add(new Move(x,y,i,j));;
						}
						break;
					}
				}
				i--;
			}
			//reset i and j to piece's coords
			i = x;
			j = y;
			while(j < 8) {	//find open squares in the y+ direction
				if(j != y) {
					if(board[i][j].type == null) {
						possibleMoves.add(new Move(x,y,i,j));
					} else {
						if(board[i][j].color != board[x][y].color) {
							possibleMoves.add(new Move(x,y,i,j));
						}
						break;
					}
				}
				j++;
			}
			//reset i and j to piece's coords
			i = x;
			j = y;
			while(j > -1) {	//find open squares in the y- direction
				if(j != y) {
					if(board[i][j].type == null) {
						possibleMoves.add(new Move(x,y,i,j));
					} else {
						if(board[i][j].color != board[x][y].color) {
							possibleMoves.add(new Move(x,y,i,j));
						}
						break;
					}
				}
				j--;
			}
			break;
		case King:
			//x and y being repurposed for an increment to the new value rather than the new value (as rook does)
			for(i = -1; i < 2; i++) {
				for(j = -1; j < 2; j++) {
					if(x + i < 8 && x + i > -1 && y + j > -1 && y + j < 8) { //if in bounds and not taking a piece of it's own color, add it to possibleMoves
						if(board[x+i][y+j].color != board[x][y].color) {
							possibleMoves.add(new Move(x,y,x+i,y+j));
						}
					}
				}
			}
			//testing for white castling short
			if(x == 7 && y == 4 && board[x][y].color == Color.White && board[x][y].hasMoved == false && board[7][7].hasMoved == false && board[7][7].type == Piece.Rook && board[7][7].color == Color.White) {
				//King and Rook have not moved
				//TODO
			}
			break;
		case Pawn:
			if(board[x][y].color == Color.White) {
				if(x > 0) {
					if(board[x-1][y].type == null) {	//if square ahead is empty, add to possible moves
						possibleMoves.add(new Move(x,y,x-1,y));
						if(x > 1) {
							if(board[x-2][y].type == null && !board[x][y].hasMoved) { //if square 2 ahead is empty (assuming square one ahead is empty) and pawn has not moved, add it to possible moves
								possibleMoves.add(new Move(x,y,x-2,y));
							}
						}
					}
					if(y > 0) {
						if(board[x-1][y-1].color != board[x][y].color && board[x-1][y-1].type != null) {	//if piece diagonally is opposite color, add move
							possibleMoves.add(new Move(x,y,x-1,y-1));
						}
						if(board[x][y-1].color != board[x][y].color && board[x][y-1].moved2) {	//en passant
							possibleMoves.add(new Move(x,y,x-1,y-1));
						}
					}
					if(y < 7) {
						if(board[x-1][y+1].color != board[x][y].color && board[x-1][y+1].type != null) {	//if piece diagonally is opposite color, add move
							possibleMoves.add(new Move(x,y,x-1,y+1));
						}
						if(board[x][y+1].color != board[x][y].color && board[x][y+1].moved2) {	//en passant
							possibleMoves.add(new Move(x,y,x-1,y+1));
						}
					}
				}
			} else {
				if(x < 7) {
					if(board[x+1][y].type == null) {	//if square ahead is empty, add to possible moves
						possibleMoves.add(new Move(x,y,x+1,y));
						if(x < 6) {
							if(board[x+2][y].type == null && !board[x][y].hasMoved) { //if square 2 ahead is empty (assuming square one ahead is empty) and pawn has not moved, add it to possible moves
								possibleMoves.add(new Move(x,y,x+2,y));
							}
						}
					}
					if(y > 0) {
						if(board[x+1][y-1].color != board[x][y].color && board[x+1][y-1].type != null) {	//if piece diagonally is opposite color, add move
							possibleMoves.add(new Move(x,y,x+1,y-1));
						}
						if(board[x][y-1].color != board[x][y].color && board[x][y-1].moved2) {	//en passant
							possibleMoves.add(new Move(x,y,x+1,y-1));
						}
					}
					if(y < 7) {
						if(board[x+1][y+1].color != board[x][y].color && board[x+1][y+1].type != null) {	//if piece diagonally is opposite color, add move
							possibleMoves.add(new Move(x,y,x+1,y+1));
						}
						if(board[x][y+1].color != board[x][y].color && board[x][y+1].moved2) {	//en passant
							possibleMoves.add(new Move(x,y,x+1,y+1));
						}
					}
				}
			}

			break;
		}
		return possibleMoves;
	}

	//updates allAttackedSquares
	ArrayList<int[]> findAttackedSquares(){
		allAttackedSquares.clear();
		ArrayList<Move> temp = new ArrayList<Move>();
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				if(board[i][j].type != null && board[i][j].color == nextToMove) {
					if(board[i][j].type == Piece.Pawn) {
						if(board[i][j].color == Color.White) {
							if(j < 7 && i > 0) {
								int[] move = new int[2];
								move[0] = i - 1;
								move[1] = j + 1;
								allAttackedSquares.add(move);
							}
							if(j > 0 && i > 0) {
								int[] move = new int[2];
								move[0] = i - 1;
								move[1] = j - 1;
								allAttackedSquares.add(move);
							}
						} else {
							if(j < 7 && i < 7) {
								int[] move = new int[2];
								move[0] = i + 1;
								move[1] = j + 1;
								allAttackedSquares.add(move);
							}
							if(j > 0 && i < 7) {
								int[] move = new int[2];
								move[0] = i + 1;
								move[1] = j - 1;
								allAttackedSquares.add(move);
							}

						}
					} 
					temp = findPossibleMoves(i, j);
					for(int k = 0; k < temp.size(); k++) {
						if(!(board[i][j].type == Piece.Pawn && temp.get(k).startingY == temp.get(k).endingY)) {
							int[] move = new int[2];
							move[0] = temp.get(k).endingX;
							move[1] = temp.get(k).endingY;
							allAttackedSquares.add(move);
						}
					}
				}
			}
		}
		return allAttackedSquares;
	}

	//calls findAttackedSquares to ensure it is updated
	public boolean isAttacked(int x, int y) {
		findAttackedSquares();
		for(int i = 0; i < allAttackedSquares.size(); i++) {
			if(allAttackedSquares.get(i)[0] == x && allAttackedSquares.get(i)[1] == y) {
				return true;
			}
		}
		return false;
	}

	public void update(Move move){	//updates board with a given moves without any checks
		//updates turn color
		if(nextToMove == Color.White) {
			nextToMove = Color.Black;
		} else {
			nextToMove = Color.White;
		}
		//place piece at ending square
		board[move.endingX][move.endingY].type = board[move.startingX][move.startingY].type;
		board[move.endingX][move.endingY].color = board[move.startingX][move.startingY].color;
		board[move.endingX][move.endingY].hasMoved = true;
		//en passant case (remove taken pawn)
		if(board[move.startingX][move.startingY].type == Piece.Pawn && board[move.startingX][move.endingY].type == Piece.Pawn && board[move.startingX][move.endingY].moved2 == true) {
			board[move.startingX][move.endingY].type = null;
			board[move.startingX][move.endingY].moved2 = false;
			board[move.startingX][move.endingY].color = null;
		}

		//make every pieces' move2 false
		for(int i = 0; i < 8; i++) {		
			for(int j = 0; j < 8; j++) {
				board[i][j].moved2 = false;
			}
		}
		if(board[move.startingX][move.startingY].type == Piece.Pawn && Math.abs(move.startingX - move.endingX) == 2) {	//make move2 true if it is a pawn and just moved 2 squares
			board[move.endingX][move.endingY].moved2 = true;
		}
		//remove piece at old square
		board[move.startingX][move.startingY].type = null;
		board[move.startingX][move.startingY].moved2 = false;
		board[move.startingX][move.startingY].color = null;


		//add to history
		history.add(move);
		
		//find new attacked squares
		findAttackedSquares();
	}
	public void updateMoves(ArrayList<Move> moves) {
		for(int i = 0; i < moves.size(); i++) {
			update(moves.get(i));
		}
	}
}

