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
        Border border = BorderFactory.createLineBorder(new Color(50,50,50), 1);
        window = new JFrame("Tetris");
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
        userInterface.setLayout(new BorderLayout());
        
        //Create right panelgrid layout with score, level, next piece, saved piece
        JPanel rightPanel = new JPanel(); //right panel
        rightPanel.setBackground(new Color(50,50,50));
        rightPanel.setLayout(new GridLayout(4,0));
        rightPanel.setPreferredSize(new Dimension(100,400));
        lblScore = new JLabel("Score: 0",SwingConstants.CENTER);
        lblScore.setForeground(new Color(202,202,202));
        rightPanel.add(lblScore);
        lblLevel = new JLabel("Level: 1",SwingConstants.CENTER);
        lblLevel.setForeground(new Color(202,202,202));
        rightPanel.add(lblLevel);
        
        JPanel next = new JPanel(new GridLayout(2,0)); //next tile panel
        next.setBackground(new Color(50,50,50));
        lblNextTitle = new JLabel("Next:", SwingConstants.CENTER);
        lblNextTitle.setVerticalAlignment(SwingConstants.BOTTOM);
        lblNextTitle.setForeground(new Color(202,202,202));
        next.add(lblNextTitle);
        lblNextPiece = new JLabel("");
        lblNextPiece.setOpaque(true);
        lblNextPiece.setBackground(Color.GREEN);
        next.add(lblNextPiece);
        rightPanel.add(next);
        
        JPanel saved = new JPanel(new GridLayout(2,0)); //saved tile panel
        saved.setBackground(new Color(50,50,50));
        lblSavedTitle = new JLabel("Saved Piece('c'):", SwingConstants.CENTER);
        lblSavedTitle.setVerticalAlignment(SwingConstants.BOTTOM);
        lblSavedTitle.setForeground(new Color(202,202,202));
        saved.add(lblSavedTitle);
        lblSavedPiece = new JLabel("");
        lblSavedPiece.setOpaque(true);
        lblSavedPiece.setBackground(new Color(50,50,50));
        saved.add(lblSavedPiece);
        rightPanel.add(saved);
        
        //Create tetris grid with grid layout in center of the user Interface's border layout
        JPanel boardDisplay = new JPanel();
        boardDisplay.setLayout(new GridLayout(24,10));
        grid = new JLabel[28][10];
        //loop through grid cells, initialize and add borders
        for(int y = 0; y < 24; y++)
        {
            for(int x = 0; x < 10; x++)
            {
                grid[y+4][x] = new JLabel("");
                grid[y+4][x].setPreferredSize(new Dimension(20, 20));
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
        window.pack();
        window.setVisible(true);
        window.addKeyListener(this); /*add key listener to window for user input*/
        
        //Create game timer
        time = new Timer(400, this);
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
        if(game.gameOver())
        {
            endMessage();
            reset();
        }
        if(game.getScore() > game.getLevel() * 500)
        {
            game.levelUp();
            time.stop();
            time.setDelay(300-(15*game.getLevel()));
            time.start();
        }
        lblScore.setText("Score: "+ game.getScore());
        lblLevel.setText("Level: "+ game.getLevel());
        for(int y = 0; y < 24; y++)
        {
            for(int x = 0; x < 10; x++)
            {
                int color=game.getSpace(y+4,x);
                
                if(color==8)
                    color=game.getMovingPiece();
                
                grid[y+4][x].setBackground(getColor(color));
            }
        }
        
        lblNextPiece.setBackground(getColor(game.getNextPiece()));
    }
    
    public Color getColor(int num)
    {
        switch(num)
        {
            case 0 : return Color.BLACK;
            case 1 : return Color.RED;
            case 2 : return Color.BLUE;
            case 3 : return Color.GREEN;
            case 4 : return Color.ORANGE;
            case 5 : return Color.YELLOW;
            case 6 : return Color.PINK;
            case 7 : return Color.MAGENTA;
        }
        
        return Color.BLACK;
    }
    
    public void endMessage()
    {
        JOptionPane.showMessageDialog(null, "Oh man! Looks like you lost. Your score was " + game.getScore() + ". Better luck next time!", "Gameover!", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void reset()
    {
        game=new Tetris();
        lblSavedPiece.setBackground(new Color(50,50,50));
        time = new Timer(300, this);
        time.setInitialDelay(0);
        sec=0;
        time.start();
    }
    
    //Player key input catching
    public void keyTyped(KeyEvent e) {}
    public void keyPressed(KeyEvent e)
    {
        if(e.getKeyCode()==KeyEvent.VK_RIGHT) //Move right
        {
            game.moveRight();
            refresh();
        }
        
        if(e.getKeyCode()==KeyEvent.VK_LEFT) //Move left
        {
            game.moveLeft();
            refresh();
        }
        if(e.getKeyCode()==KeyEvent.VK_SPACE) //Quick Drop
        {
            game.drop();
            refresh();
        }
        if(e.getKeyCode()==KeyEvent.VK_UP) //Rotate
        {
            game.rotate();
            refresh();
            
        }
        if(e.getKeyCode()==KeyEvent.VK_C) //Save Piece
        {
            game.savePiece();
            lblSavedPiece.setBackground(getColor(game.getSavedPiece()));
        }
        
        if(e.getKeyCode()==KeyEvent.VK_DOWN) //Speed Drop Start
        {
            time = new Timer(20, this);
            game.fastDropping(true);
            time.start();
        }
    }
    public void keyReleased(KeyEvent e)
    {
        if(e.getKeyCode()==KeyEvent.VK_DOWN) //Speed Drop End
        {
            time = new Timer(300-(15*game.getLevel()), this);
            game.fastDropping(false);
            time.start();
        }
    }
}
