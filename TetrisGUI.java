import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.Timer;

public class TetrisGUI extends JFrame implements ActionListener, KeyListener
{
	//Variables
	private Tetris game;
	private int sec;
	private Timer time;
	
	//GUI Objects
	private JLabel lblScore;
	private JLabel lblLevel;
	private JLabel lblNextTitle;
	private JLabel lblNextPiece;
	private JLabel lblSavedTitle;
	private JLabel lblSavedPiece;
	private JLabel[][] grid;
	private JFrame window;

	public static void main(String[] args)
	{
		new TetrisGUI(); /*initialize GUI*/
	}

	public TetrisGUI()
	{
		//Setup Window
		Font f = new Font("Times Roman", Font.PLAIN, 7);
		Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
		window = new JFrame("Tetris");
		window.setBackground(new Color(192,192,192));
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setTitle("Tetris");
		window.setSize(370,750);
		window.setFocusable(true);
		window.setResizable(false);
		window.setLocationRelativeTo(null);
		
		//Initialize Tetris game object
		game=new Tetris();

		//Create border layout jpanel user display
		JPanel userInterface = new JPanel();
		userInterface.setLayout(new BorderLayout(20,10));
		userInterface.setBackground(new Color(192,192,192));
		
		//Create right panelgrid layout with score, level, next piece, saved piece
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new GridLayout(24,10));
		lblScore = new JLabel("Score: 0");
		rightPanel.add(lblScore);
		lblLevel = new JLabel("Level: 1");
		rightPanel.add(lblLevel);
		lblNextTitle = new JLabel("Next:");
		rightPanel.add(lblNextTitle);
		lblNextPiece = new JLabel("");
		lblNextPiece.setOpaque(true);
		rightPanel.add(lblNextPiece);
		lblSavedTitle = new JLabel("Saved:");
		rightPanel.add(lblSavedTitle);
		lblSavedPiece = new JLabel("");
		lblSavedPiece.setOpaque(true);
		rightPanel.add(lblSavedPiece);
		
		//Create tetris grid with grid layout in center of the user Interface's border layout
		JPanel boardDisplay = new JPanel();
		boardDisplay.setBackground(new Color(192,192,192));
		boardDisplay.setLayout(new GridLayout(24,10));
		grid = new JLabel[28][10];
		//loop through grid cells, initialize and add borders
		for(int y = 0; y < 24; y++)
		{
			for(int x = 0; x < 10; x++)
			{
				grid[y+4][x] = new JLabel("");
				grid[y+4][x].setOpaque(true);
				grid[y+4][x].setBorder(border);
				boardDisplay.add(grid[y+4][x]);
			}
		}
		
		//Add tetris grid and right panel to the main jpanel
		userInterface.add(boardDisplay, BorderLayout.CENTER);
		userInterface.add(rightPanel, BorderLayout.EAST);
		
		//add the main panel to the window is set it visble
		window.add(userInterface);
		window.setVisible(true);
		window.addKeyListener(this); /*add key listener to window for user input*/
		
		//Create game timer
		time = new Timer(100, this);
		time.setInitialDelay(0);
		sec=0;
		
		//START GAME
		time.start();
	}
	
	//perform game cycle in tetris object every tick and refresh the gui display
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==time)
		{
			sec++;
			game.tick();
			refresh();
		}
	}

	//Refresh gui display of tetris object by getting score, level, next piece, saved piece 
	//and looping through each grid cell and checking its color
	public void refresh()
	{
		lblScore.setText("Score: "+ game.getScore());
		lblLevel.setText("Level: "+ game.getLevel());
		for(int y = 0; y < 24; y++)
		{
			for(int x = 0; x < 10; x++)
			{
				int color=game.getSpace(y+4,x);

				if(color==8)
					color=game.getMovingPiece();
				switch(color)
				{
					case 0 : grid[y+4][x].setBackground(Color.BLACK); break;
					case 1 : grid[y+4][x].setBackground(Color.RED); break;
					case 2 : grid[y+4][x].setBackground(Color.BLUE); break;
					case 3 : grid[y+4][x].setBackground(Color.GREEN); break;
					case 4 : grid[y+4][x].setBackground(Color.ORANGE); break;
					case 5 : grid[y+4][x].setBackground(Color.YELLOW); break;
					case 6 : grid[y+4][x].setBackground(Color.PINK); break;
					case 7 : grid[y+4][x].setBackground(Color.MAGENTA); break;
				}
			}
		}

		switch(game.getNextPiece())
		{
			case 0 : lblNextPiece.setBackground(Color.BLACK); break;
			case 1 : lblNextPiece.setBackground(Color.RED); break;
			case 2 : lblNextPiece.setBackground(Color.BLUE); break;
			case 3 : lblNextPiece.setBackground(Color.GREEN); break;
			case 4 : lblNextPiece.setBackground(Color.ORANGE); break;
			case 5 : lblNextPiece.setBackground(Color.YELLOW); break;
			case 6 : lblNextPiece.setBackground(Color.PINK); break;
			case 7 : lblNextPiece.setBackground(Color.MAGENTA); break;
		}

		switch(game.getSavedPiece())
		{
			case 0 : lblSavedPiece.setBackground(Color.BLACK); break;
			case 1 : lblSavedPiece.setBackground(Color.RED); break;
			case 2 : lblSavedPiece.setBackground(Color.BLUE); break;
			case 3 : lblSavedPiece.setBackground(Color.GREEN); break;
			case 4 : lblSavedPiece.setBackground(Color.ORANGE); break;
			case 5 : lblSavedPiece.setBackground(Color.YELLOW); break;
			case 6 : lblSavedPiece.setBackground(Color.PINK); break;
			case 7 : lblSavedPiece.setBackground(Color.MAGENTA); break;
		}

	}
	
	//Player key input catching
	public void keyTyped(KeyEvent e) {}
	public void keyPressed(KeyEvent e)
	{
		if(e.getKeyCode()==KeyEvent.VK_RIGHT) //Move right
		{
			game.moved();
			game.moveRight();
		}

		if(e.getKeyCode()==KeyEvent.VK_LEFT) //Move left
		{
			game.moved();
			game.moveLeft();
		}
		if(e.getKeyCode()==KeyEvent.VK_SPACE) //Quick Drop
		{
			game.moved();
			game.drop();
		}
		if(e.getKeyCode()==KeyEvent.VK_UP) //Rotate
		{
			game.moved();
			game.rotate();
			refresh();
		}
		if(e.getKeyCode()==KeyEvent.VK_C) //Save Piece
		{
			game.moved();
			game.savePiece();
		}

		if(e.getKeyCode()==KeyEvent.VK_DOWN) //Speed Drop Start
		{
			time = new Timer(20, this);
			time.start();
		}
	}
	public void keyReleased(KeyEvent e)
	{
		if(e.getKeyCode()==KeyEvent.VK_DOWN) //Speed Drop End
		{
			time = new Timer(100, this);
			time.start();
		}
	}
}
