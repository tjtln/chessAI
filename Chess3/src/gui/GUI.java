package gui;

import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

import Chess3.*;

import java.awt.*;
import java.awt.Color;

public class GUI extends JPanel implements ActionListener {
	public JButton[][] squares = new JButton[8][8];
	public boolean first = true;
	Game board = new Game();
	int[] sCoords = new int[2];
	int[] eCoords = new int[2];
	Icon blackKnight = new ImageIcon(ImageIO.read(new File("C:/Users/TJ Thielen/eclipse-workspace/Chess2/src/gui/blackKnight.png")));
	Icon blackRook = new ImageIcon(ImageIO.read(new File("C:/Users/TJ Thielen/eclipse-workspace/Chess2/src/gui/blackRook.png")));
	Icon blackBishop = new ImageIcon(ImageIO.read(new File("C:/Users/TJ Thielen/eclipse-workspace/Chess2/src/gui/blackBishop.png")));
	Icon blackQueen = new ImageIcon(ImageIO.read(new File("C:/Users/TJ Thielen/eclipse-workspace/Chess2/src/gui/blackQueen.png")));
	Icon blackKing = new ImageIcon(ImageIO.read(new File("C:/Users/TJ Thielen/eclipse-workspace/Chess2/src/gui/blackKing.png")));
	Icon blackPawn = new ImageIcon(ImageIO.read(new File("C:/Users/TJ Thielen/eclipse-workspace/Chess2/src/gui/blackPawn.png")));
	Icon whiteKnight = new ImageIcon(ImageIO.read(new File("C:/Users/TJ Thielen/eclipse-workspace/Chess2/src/gui/whiteKnight.png")));
	Icon whiteRook = new ImageIcon(ImageIO.read(new File("C:/Users/TJ Thielen/eclipse-workspace/Chess2/src/gui/whiteRook.png")));
	Icon whiteBishop = new ImageIcon(ImageIO.read(new File("C:/Users/TJ Thielen/eclipse-workspace/Chess2/src/gui/whiteBishop.png")));
	Icon whiteQueen = new ImageIcon(ImageIO.read(new File("C:/Users/TJ Thielen/eclipse-workspace/Chess2/src/gui/whiteQueen.png")));
	Icon whiteKing = new ImageIcon(ImageIO.read(new File("C:/Users/TJ Thielen/eclipse-workspace/Chess2/src/gui/whiteKing.png")));
	Icon whitePawn = new ImageIcon(ImageIO.read(new File("C:/Users/TJ Thielen/eclipse-workspace/Chess2/src/gui/whitePawn.png")));

	public static void main(String[] args) throws IOException {
		GUI gui = new GUI();
		gui.setVisible(true);
	}

	public GUI() throws IOException {
		super();
		JPanel top = new JPanel();
		JFrame frame = new JFrame("Chess");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.add(BorderLayout.NORTH, top);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		JPanel board = new JPanel(new GridLayout(8,8,1,1));
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				JButton button = new JButton();
				button.addActionListener(this);
				button.setName("" + i + j);
				if((i+j) % 2 == 0) {
					button.setBackground(new Color(240,240,240));
				} else {
					button.setBackground(new Color(0,120,0));
				}
				squares[i][j] = button;
				board.add(squares[i][j]);
			}
		}
		squares[0][0].setIcon(blackRook);
		squares[0][1].setIcon(blackKnight);
		squares[0][2].setIcon(blackBishop);
		squares[0][3].setIcon(blackQueen);
		squares[0][4].setIcon(blackKing);
		squares[0][5].setIcon(blackBishop);
		squares[0][6].setIcon(blackKnight);
		squares[0][7].setIcon(blackRook);
		squares[7][0].setIcon(whiteRook);
		squares[7][1].setIcon(whiteKnight);
		squares[7][2].setIcon(whiteBishop);
		squares[7][3].setIcon(whiteQueen);
		squares[7][4].setIcon(whiteKing);
		squares[7][5].setIcon(whiteBishop);
		squares[7][6].setIcon(whiteKnight);
		squares[7][7].setIcon(whiteRook);
		for(int i = 0; i < 8; i++) {
			squares[1][i].setIcon(blackPawn);
		}
		for(int i = 0; i < 8; i++) {
			squares[6][i].setIcon(whitePawn);
		}

		frame.add(board);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton a = (JButton) e.getSource();
		if(first && a.getIcon() != null) {
			sCoords[0] = a.getName().charAt(0) - 48;
			sCoords[1] = a.getName().charAt(1) - 48;
			first = false;
		} else if(!first){
			eCoords[0] = a.getName().charAt(0) - 48;
			eCoords[1] = a.getName().charAt(1) - 48;
			first = true;
			Move move = new Move(sCoords[0], sCoords[1], eCoords[0], eCoords[1]);
			if(board.isLegal(move)) { //change to if move is possible
				board.update(move);
				for(int i = 0; i < 8; i++) {
					for(int j = 0; j < 8; j++) {
						//setting piece icons
						Piece piece = board.board[i][j].type;
						if(piece != null) {
							if (board.board[i][j].color == Chess3.Color.White) {
								switch(piece) {
								case Pawn:
									squares[i][j].setIcon(whitePawn);
									break;
								case Knight:
									squares[i][j].setIcon(whiteKnight);
									break;
								case Rook:
									squares[i][j].setIcon(whiteRook);
									break;
								case Bishop:
									squares[i][j].setIcon(whiteBishop);
									break;
								case King:
									squares[i][j].setIcon(whiteKing);
									break;
								case Queen:
									squares[i][j].setIcon(whiteQueen);
									break;
								}
							} else {
								switch(piece) {
								case Pawn:
									squares[i][j].setIcon(blackPawn);
									break;
								case Knight:
									squares[i][j].setIcon(blackKnight);
									break;
								case Rook:
									squares[i][j].setIcon(blackRook);
									break;
								case Bishop:
									squares[i][j].setIcon(blackBishop);
									break;
								case King:
									squares[i][j].setIcon(blackKing);
									break;
								case Queen:
									squares[i][j].setIcon(blackQueen);
									break;
								}
							}
						} else {
							squares[i][j].setIcon(null);
						}
						//setting attacked squares
						if(board.isAttacked(i,j)) {
							squares[i][j].setBackground(Color.red);
						} else {
							if((i+j) % 2 == 0) {
								squares[i][j].setBackground(new Color(240,240,240));
							} else {
								squares[i][j].setBackground(new Color(0,120,0));
							}
						}

					}
				}
			}
		}
	}
}
