# Java-Tetris
A remake of the infamous Tetris, a tile-matching puzzle video game.

# Resources
<ul><li>Javax.Swing
<li>Java.Awt
<li>Java.Util</ul>

# Features
<ul><li>Uses the 7 original tetris tiles
<li>Score keeping
<li>Rotating and moving (left/right) tiles
<li>Level induced speed up
<li>Quick drop - tile immediately snaps to position if it fell to the bottom
<li>Fast drop - speed that the tile falls increases
<li>Saving tiles
<li>Display next tile</ul>

# Tetris GUI
The Tetris GUI class extends JFrame to create a user interface so that a player could play the game. 
This class contains the main that just calls the TetrisGUI constructor. In the constructor the 
window is setup creating the tetris grid and the info panel containg information like level, next
tile, saved tile and score. To hold all the game data there is an instance of a Tetris object that 
is ticked with the timer once the game beings. Each tick the Tetris object is updated and the graphics
are then refreshed. The GUI also has a keyboard listener that is used to read user input to move and 
save tiles.

# Tetris
The Tetris class is the game object containg all the information regarding the game state. It holds 
score, level, grid state, and other important varibles. It also holds the game cycle that is ticked 
everytime the timer is called in the Tetris GUI. The game cycle moves tiles, clears rows and adds
new pieces when one is done falling. There are also other methods called by key inputs in the
Tetris GUI. These are savePiece(), drop(), moveRight(), moveLeft() and rotate(). 
