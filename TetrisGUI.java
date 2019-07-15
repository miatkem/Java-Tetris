import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.Timer;

public class TetrisGUI extends JFrame implements ActionListener, KeyListener
{
	/*OBJECTS*/
	private Tetris game;
	private int sec;

	//Buttons and LAbels
	private JLabel[][] grid;
	private Timer time;
	private JFrame window;


	public static void main(String[] args)
	{
		new TetrisGUI();
	}

	public TetrisGUI()
	{
		Font f = new Font("Times Roman", Font.PLAIN, 7);
		Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
		window = new JFrame("Tetris");
		window.setBackground(new Color(192,192,192));
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setTitle("Tetris");
		window.setSize(370,750);
		window.setFocusable(true);
		//window.setResizable(false);

		game=new Tetris();

		//Center on Screen
		window.setLocationRelativeTo(null);

		JPanel userInterface = new JPanel();
		userInterface.setLayout(new BorderLayout(20,10));
		userInterface.setBackground(new Color(192,192,192));

		JPanel boardDisplay = new JPanel();
		boardDisplay.setBackground(new Color(192,192,192));
		boardDisplay.setLayout(new GridLayout(24,10));
		grid = new JLabel[28][10];

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

		userInterface.add(boardDisplay, BorderLayout.CENTER);

		window.add(userInterface);
		window.setVisible(true);
		window.addKeyListener(this);

		time = new Timer(100, this);
		time.setInitialDelay(0);
		sec=0;
		time.start();
	}

	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==time)
		{
			sec++;
			game.tick();
			refresh();
		}
	}


	public void refresh()
	{
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

				//grid[y+4][x].setText("" + game.getSpace(y+4,x));
			}
		}
	}

	public void keyTyped(KeyEvent e) {}
	public void keyPressed(KeyEvent e)
	{
		if(e.getKeyCode()==KeyEvent.VK_RIGHT)
		{
			game.moveRight();
		}

		if(e.getKeyCode()==KeyEvent.VK_LEFT)
		{
			game.moveLeft();
		}
		if(e.getKeyCode()==KeyEvent.VK_SPACE)
		{
			game.drop();
		}
		if(e.getKeyCode()==KeyEvent.VK_UP)
		{
			game.rotate();
		}

		if(e.getKeyCode()==KeyEvent.VK_DOWN)
		{
			time = new Timer(20, this);
			time.start();
		}
	}
	public void keyReleased(KeyEvent e)
	{
		if(e.getKeyCode()==KeyEvent.VK_DOWN)
		{
			time = new Timer(100, this);
			time.start();
		}
	}
}