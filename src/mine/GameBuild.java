package mine;

import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class GameBuild implements MouseListener {

	JFrame frame;
	GameButtons[][] board = new GameButtons[10][10];
	int safeButtons;

	public GameBuild() {
		safeButtons=0;
		frame = new JFrame("MineField");
		frame.setSize(800, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridLayout(10, 10));

		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board[0].length; c++) {
				GameButtons button = new GameButtons(r, c);
				frame.add(button);
				button.addMouseListener(this);
				board[r][c] = button;
			}
		}
		minePlanter();
		updateBox();
		//printGame();

		frame.setVisible(true);

	}

	public void minePlanter() {

		int i = 0;
		while (i < 10) {
			int randRow = (int) (Math.random() * board.length);
			int randCol = (int) (Math.random() * board[0].length);

			while (board[randRow][randCol].isMine()) {
				randCol = (int) (Math.random() * board[0].length);
				randRow = (int) (Math.random() * board.length);
			}

			board[randRow][randCol].setMine(true);
			i++;

		}

	}

	public void printGame() {

		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board[0].length; c++) {

				if (board[r][c].isMine()) {
					board[r][c].setIcon(new ImageIcon("./assets/bomb.png"));
				} else {
					board[r][c].setText(board[r][c].getCount()+"");
					board[r][c].setEnabled(false);
				}

			}
		}
	}
	
	
	

	public void updateBox() {

		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board[0].length; c++) {

				if (board[r][c].isMine()) {
					counting(r, c);
				}

			}
		}

	}

	public void counting(int r, int c) {
		for (int i = r - 1; i <= r + 1; i++) {
			for (int k = c - 1; k <= c + 1; k++) {

				try {
					int value = board[i][k].getCount();
					board[i][k].setCount(++value);
				} catch (Exception e) {

				}

			}
		}
	}
	
	public void safe(int r, int c) {
		if(r<0 || c<0 || r>=board.length || c>= board[0].length || board[r][c].getText().length()>0 || board[r][c].isEnabled()==false) {
			return;
		}else if(board[r][c].getCount()!=0) {
			board[r][c].setText(board[r][c].getCount()+"");
			board[r][c].setEnabled(false);
			safeButtons++;
		}else {
			safeButtons++;
			board[r][c].setEnabled(false);
			safe(r-1,c);
			safe(r+1,c);
			safe(r,c+1);
			safe(r,c-1);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		GameButtons b = (GameButtons) e.getComponent();
		if (e.getButton() == 1) {

			if (b.isMine()) {
				JOptionPane.showMessageDialog(frame, "Mine Exploded! Game OvER!");
				printGame();
			} else {
				safe(b.getRow(),b.getCol());
				
				if(safeButtons== (board.length * board[0].length)-10) {
					JOptionPane.showMessageDialog(frame, "Congrats! You Won the Game!");
					printGame();
				}
			}

		} else if (e.getButton() == 3) {
			if (!b.isFlag()) {
				b.setIcon(new ImageIcon("./assets/flag.png"));
				b.setFlag(true);
			} else {
				b.setIcon(null);
				b.setFlag(false);
			}
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
