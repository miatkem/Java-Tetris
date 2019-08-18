import java.awt.Point;

public class Tetris
{
    //Constant
    private int BLACK=0;
    private int RED=1;
    private int BLUE=2;
    private int GREEN=3;
    private int ORANGE=4;
    private int YELLOW=5;
    private int PINK=6;
    private int PURPLE=7;
    private int CURRENT_PIECE=8;
   
    //variables
    private boolean falling, fastFalling,rotated;
    private boolean saveUsed;
    private int[][] grid;
    private int score;
    private int level;
    private int nextPiece;
    private int savedPiece;
    private Point[] movingPosition;
    private int movingPiece;
    private int rowsCleared;
    private int rotationBuffer;
    
    //Constructor
    public Tetris()
    {
        rotationBuffer=0;
        saveUsed=false;
        score=0;
        level=0;
        movingPosition= new Point[4];
        movingPiece=0;
        savedPiece=-1;
        nextPiece=(int) (Math.random()*7) +1;
        falling = fastFalling = rotated = false;
        rowsCleared=0;
        grid = new int[28][10];
        
        for(int row = 0; row < grid.length; row++)
        {
            for(int col = 0; col< grid[0].length; col++)
                grid[row][col]=0;
        }
    }
    
    //Game Cylcle
    public void tick()
    {
        if(!gameOver())
        {
            if(falling)
                tickFalling();
            
            if(!falling)
            {
                addNewPiece(nextPiece);
                nextPiece=(int) (Math.random()*7)+1;
            }
            clearRows();
        }
        
        rotated=false;
    }
    
    public boolean gameOver()
    {
        for(int row = 0; row < 4; row++)
        {
            for(int col = 0; col< grid[0].length; col++)
            {
                if(!(grid[row][col]==0 || grid[row][col]==8))
                {
                    return true;
                }
            }
        }
        return false;
    }
    
    //Allows player to save the current piece falling for later use
    public void savePiece()
    {
        if(!saveUsed)
        {
            saveUsed=true;
            
            int temp = savedPiece;
            savedPiece=movingPiece;
            
            for(int i=0; i<movingPosition.length; i++)
                grid[(int)movingPosition[i].getY()][(int)movingPosition[i].getX()]=0;
            
            if(temp==-1)
                addNewPiece(nextPiece);
            else
                addNewPiece(temp);
        }
    }
    
    //Attempt to move the current piece down one. Check for OBE and for other pieces.
    public void tickFalling()
    {
        boolean keepMoving=true;
        
        if(fastFalling)
            score+=1;
        
        //CHECK FALLING
        for(int i=0; i<movingPosition.length; i++)
        {
            if((int)movingPosition[i].getY()+1>27)
                keepMoving=false;
            else
            {
                int squareBelow = grid[(int)movingPosition[i].getY()+1] [(int)movingPosition[i].getX()];
                if(squareBelow!=CURRENT_PIECE&&squareBelow!=0)
                    keepMoving=false;
            }
        }
        //Keep Falling
        if(keepMoving)
        {
            for(int i=0; i<movingPosition.length; i++)
            {
                grid[(int)movingPosition[i].getY()+1] [(int)movingPosition[i].getX()]=CURRENT_PIECE;
                grid[(int)movingPosition[i].getY()] [(int)movingPosition[i].getX()]=0;
                movingPosition[i]=new Point((int)movingPosition[i].getX(),(int)movingPosition[i].getY()+1);
            }
            for(int i=0; i<movingPosition.length; i++)
                grid[(int)movingPosition[i].getY()] [(int)movingPosition[i].getX()]=CURRENT_PIECE;
        }
        //Done Falling
        else
        {
            if(rotationBuffer>0 && rotated)
                rotationBuffer--;
            else{
                saveUsed=false;
                falling=false;
                for(int i=0; i<movingPosition.length; i++)
                    grid[(int)movingPosition[i].getY()] [(int)movingPosition[i].getX()]=movingPiece;
            }
        }
    }
    
    //fast drop the current piece to its bottom position 
    public void drop()
    {
        while(falling)
        {
            score+=1;
            tickFalling();
        }
        
        addNewPiece(nextPiece);
        nextPiece=(int) (Math.random()*7)+1;
        
        clearRows();
    }
    
    //Check for and clear any row that is completely full of nonmoving pieces
    public void clearRows()
    {
        int lines=0;
        boolean fullRow;
        for(int i = grid.length-1; i>=0; i--)
        {
            fullRow=true;
            
            for(int j = 0; j<grid[i].length; j++){
                if(grid[i][j]==0||grid[i][j]==8)
                    fullRow=false;
            }
            
            if(fullRow)
            {
                lines++;
                for(int row = i-1; row>=0; row--)
                {
                    for(int col = 0; col<grid[row].length; col++)
                    {
                        if(grid[row+1][col]!=8 && grid[row][col]!=8){
                            grid[row+1][col]=grid[row][col];
                            grid[row][col]=0;
                        }
                    }
                }
            }
        }
        
        switch(lines)
        {
            case 1: score+=40*(level+1); break;
            case 2: score+=100*(level+1); break;
            case 3: score+=300*(level+1); break;
            case 4: score+=1200*(level+1); break;
        }
        
    }
    
    //Use rotateOnOrigin to rotate each piece. Each piece has a different origin to rotate on
    public void rotate()
    {
        if(rotationBuffer==0)
           rotationBuffer=4;
        rotated=true;
        
        switch(movingPiece)
        {
            case 1 : rotateOnOrgin((int)movingPosition[2].getX(), (int)movingPosition[2].getY());
            break;
            case 2 : rotateOnOrgin((int)movingPosition[2].getX(), (int)movingPosition[2].getY());
            break;
            case 3 : rotateOnOrgin((int)movingPosition[2].getX(), (int)movingPosition[2].getY());
            break;
            case 4 : rotateOnOrgin((int)movingPosition[1].getX(), (int)movingPosition[1].getY());
            break;
            case 5 : rotateOnOrgin((int)movingPosition[3].getX(), (int)movingPosition[3].getY());
            break;
            case 6 : rotateOnOrgin((int)movingPosition[1].getX(), (int)movingPosition[1].getY());
            break;
            case 7 : rotateOnOrgin((int)movingPosition[1].getX(), (int)movingPosition[1].getY());
            break;
        }
        
        for(int i=0; i<movingPosition.length; i++)
                grid[(int)movingPosition[i].getY()][(int)movingPosition[i].getX()]=CURRENT_PIECE;
    }
    
    //rotate the current piece on a certain origin using rotation mathematics. Each tetris piece has a different origin (see rotate())
    //Check for obe and if so push piece over to fit, check for collision for other pieces.
    public void rotateOnOrgin(int orginX, int orginY)
    {
        boolean boundsChecked=true;
        
        for(int i=0; i<movingPosition.length; i++)
        {
            int xTranslation=(int)movingPosition[i].getX()-orginX;
            int yTranslation=(int)movingPosition[i].getY()-orginY;
            int newX = (int)Math.round( (xTranslation) * Math.cos(Math.PI/2) - (yTranslation) * Math.sin(Math.PI/2));
            int newY = (int)Math.round((xTranslation) * Math.sin(Math.PI/2) + (yTranslation) * Math.cos(Math.PI/2));
            
            if(!(newX+orginX>=0&&newX+orginX<=9&&newY+orginY>=0&&newY+orginY<=27))
                boundsChecked=false;
            
            else if(!(grid[newY+orginY][newX+orginX]==0||grid[newY+orginY][newX+orginX]==8))
                boundsChecked=false;
        }
        
        if(boundsChecked)
        {
            for(int i=0; i<movingPosition.length; i++)
            {
                int xTranslation=(int)movingPosition[i].getX()-orginX;
                int yTranslation=(int)movingPosition[i].getY()-orginY;
                int newX = (int)Math.round( (xTranslation) * Math.cos(Math.PI/2) - (yTranslation) * Math.sin(Math.PI/2));
                int newY = (int)Math.round((xTranslation) * Math.sin(Math.PI/2) + (yTranslation) * Math.cos(Math.PI/2));
                
                grid[(int)movingPosition[i].getY()][(int)movingPosition[i].getX()]=0;
                movingPosition[i]=new Point(newX+orginX,newY+orginY);
            }
        }
    }
    
    //Attempt to move current piece right. Check for obe and other pieces.
    public void moveRight()
    {
        boolean canMoveRight=true;
        
        for(int i=0; i<movingPosition.length; i++)
        {
            if(movingPosition[i].getX()+1<10)
            {
                int pieceRight=grid[(int)movingPosition[i].getY()] [(int)movingPosition[i].getX()+1];
                if(pieceRight!=CURRENT_PIECE&&pieceRight!=0)
                    canMoveRight=false;
            }
            else
                canMoveRight=false;
        }
        
        if(canMoveRight)
        {
            for(int i=0; i<movingPosition.length; i++)
            {
                grid[(int)movingPosition[i].getY()] [(int)movingPosition[i].getX()]=0;
                movingPosition[i]=new Point((int)movingPosition[i].getX()+1,(int)movingPosition[i].getY());
            }
            for(int i=0; i<movingPosition.length; i++)
                grid[(int)movingPosition[i].getY()] [(int)movingPosition[i].getX()]=CURRENT_PIECE;
        }
    }
    
    //Attempt to move current piece left. Check for obe and other pieces.
    public void moveLeft()
    {
        boolean canMoveLeft=true;
        
        for(int i=0; i<movingPosition.length; i++)
        {
            if(movingPosition[i].getX()-1>-1)
            {
                int pieceLeft=grid[(int)movingPosition[i].getY()] [(int)movingPosition[i].getX()-1];
                if(pieceLeft!=CURRENT_PIECE&&pieceLeft!=0)
                    canMoveLeft=false;
            }
            else
                canMoveLeft=false;
        }
        
        if(canMoveLeft)
        {
            for(int i=0; i<movingPosition.length; i++)
            {
                grid[(int)movingPosition[i].getY()] [(int)movingPosition[i].getX()]=0;
                movingPosition[i]=new Point((int)movingPosition[i].getX()-1,(int)movingPosition[i].getY());
            }
            for(int i=0; i<movingPosition.length; i++)
                grid[(int)movingPosition[i].getY()] [(int)movingPosition[i].getX()]=CURRENT_PIECE;
        }     
    }
    
    
    //prints the boards using characters, allows for play in command prompt
    public String printBoard()
    {
        String result="";
        for(int row = 0; row < grid.length; row++)
        {
            for(int col = 0; col< grid[0].length; col++)
                result+=grid[row][col];
            result+="\n";
        }
        return result;
    }
    
    //get the color/shape in each grid cell
    public int getSpace(int row, int col) { return grid[row][col]; }
    
    //return the current level (further implementation requried for levels)
    public int getLevel() { return level; }
    
    public void levelUp() { level++; }
    
    //return score
    public int getScore() { return score; }
    
    //return the currently falling piece type/color
    public int getMovingPiece() { return movingPiece; }
    
    //returns what peice is saved
    public int getSavedPiece() { return savedPiece; }
    
    //returns what piece is next
    public int getNextPiece() { return nextPiece; }
   
    public void fastDropping(boolean bool) { fastFalling = bool; }
    
    //add the new piece above the displayed grid to begin it descent
    public void addNewPiece(int piece)
    {
        switch(piece)
        {
            /*RIGHT STAIR RED*/
            case 1 :grid[3][4] = CURRENT_PIECE;
            grid[3][5] = CURRENT_PIECE;
            grid[2][5] = CURRENT_PIECE;
            grid[2][6] = CURRENT_PIECE;
            movingPosition=new Point[4];
            movingPosition[0]=new Point(4,3);
            movingPosition[1]=new Point(5,3);
            movingPosition[2]=new Point(5,2);
            movingPosition[3]=new Point(6,2);
            movingPiece=RED;
            break;
            /*LINE*/
            case 2 :grid[0][4] = CURRENT_PIECE;
            grid[1][4] = CURRENT_PIECE;
            grid[2][4] = CURRENT_PIECE;
            grid[3][4] = CURRENT_PIECE;
            movingPosition=new Point[4];
            movingPosition[0]=new Point(4,0);
            movingPosition[1]=new Point(4,1);
            movingPosition[2]=new Point(4,2);
            movingPosition[3]=new Point(4,3);
            movingPiece=BLUE;
            break;
            /*LEFT STAIR*/
            case 3 :grid[2][4] = CURRENT_PIECE;
            grid[2][5] = CURRENT_PIECE;
            grid[3][5] = CURRENT_PIECE;
            grid[3][6] = CURRENT_PIECE;
            movingPosition=new Point[4];
            movingPosition[0]=new Point(4,2);
            movingPosition[1]=new Point(5,2);
            movingPosition[2]=new Point(5,3);
            movingPosition[3]=new Point(6,3);
            movingPiece=GREEN;
            break;
            /*RIGHT L*/
            case 4 :grid[1][4] = CURRENT_PIECE;
            grid[2][4] = CURRENT_PIECE;
            grid[3][4] = CURRENT_PIECE;
            grid[3][5] = CURRENT_PIECE;
            movingPosition=new Point[4];
            movingPosition[0]=new Point(4,1);
            movingPosition[1]=new Point(4,2);
            movingPosition[2]=new Point(4,3);
            movingPosition[3]=new Point(5,3);
            movingPiece=ORANGE;
            break;
            /*SQUARE*/
            case 5 :grid[3][4] = CURRENT_PIECE;
            grid[3][5] = CURRENT_PIECE;
            grid[2][4] = CURRENT_PIECE;
            grid[2][5] = CURRENT_PIECE;
            movingPosition=new Point[4];
            movingPosition[0]=new Point(4,3);
            movingPosition[1]=new Point(5,3);
            movingPosition[2]=new Point(4,2);
            movingPosition[3]=new Point(5,2);
            movingPiece=YELLOW;
            break;
            /*LEFT L*/
            case 6 :grid[1][5] = CURRENT_PIECE;
            grid[2][5] = CURRENT_PIECE;
            grid[3][5] = CURRENT_PIECE;
            grid[3][4] = CURRENT_PIECE;
            movingPosition=new Point[4];
            movingPosition[0]=new Point(5,1);
            movingPosition[1]=new Point(5,2);
            movingPosition[2]=new Point(5,3);
            movingPosition[3]=new Point(4,3);
            movingPiece=PINK;
            break;
            /*T*/
            case 7 :grid[2][3] = CURRENT_PIECE;
            grid[2][4] = CURRENT_PIECE;
            grid[2][5] = CURRENT_PIECE;
            grid[3][4] = CURRENT_PIECE;
            movingPosition=new Point[4];
            movingPosition[0]=new Point(3,2);
            movingPosition[1]=new Point(4,2);
            movingPosition[2]=new Point(5,2);
            movingPosition[3]=new Point(4,3);
            movingPiece=PURPLE;
            break;
        }
        falling=true;
    }
    
}
