import java.awt.Point;

public class Tetris
{
	//Constants
	private int RED=1;
	private int BLUE=2;
	private int GREEN=3;
	private int ORANGE=4;
	private int YELLOW=5;
	private int PINK=6;
	private int PURPLE=7;
	private int CURRENT_PIECE=8;

	private boolean falling,gameOver;
	private int[][] grid;
	private int nextPiece;
	private int savedPiece;
	private Point[] movingPosition;
	private int movingPiece;
	private boolean moved;
	private int rowsCleared;
	private int rotationNum;

	public Tetris()
	{
		gameOver=false;
		movingPosition= new Point[4];
		movingPiece=0;
		rotationNum=-1;
		nextPiece=(int) (Math.random()*7) +1;
		falling = false;
		rowsCleared=0;
		grid = new int[28][10];

		for(int row = 0; row < grid.length; row++)
		{
			for(int col = 0; col< grid[0].length; col++)
			{
				grid[row][col]=0;
			}
		}
	}

	public void tick()
	{
		if(moved)
		{
			moved=false;
		}
		else if(falling)
		{
			tickFalling();
		}

		else if(!gameOver)
		{
			addNewPiece();
		}

		clearRows();

	}

	public String printBoard()
	{
		String result="";
		for(int row = 0; row < grid.length; row++)
		{
			for(int col = 0; col< grid[0].length; col++)
			{
				result+=grid[row][col];
			}
			result+="\n";
		}
		return result;
	}

	public int getSpace(int row, int col)
	{
		return grid[row][col];
	}

	public void drop()
	{
		while(falling)
			tickFalling();
	}

	public void tickFalling()
	{
		boolean keepMoving=true;

		//CHECK FALLING

		for(int i=0; i<movingPosition.length; i++)
		{
			if((int)movingPosition[i].getY()+1>27)
			{
				keepMoving=false;
			}
			else
			{
				int squareBelow = grid[(int)movingPosition[i].getY()+1] [(int)movingPosition[i].getX()];
				if(squareBelow!=CURRENT_PIECE&&squareBelow!=0)
				{
					keepMoving=false;
				}
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
			{
				grid[(int)movingPosition[i].getY()] [(int)movingPosition[i].getX()]=CURRENT_PIECE;
			}
		}

		//Done Falling
		else
		{
			falling=false;
			for(int i=0; i<movingPosition.length; i++)
			{
				grid[(int)movingPosition[i].getY()] [(int)movingPosition[i].getX()]=movingPiece;
			}
		}
	}

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
			{
				grid[(int)movingPosition[i].getY()] [(int)movingPosition[i].getX()]=CURRENT_PIECE;
			}

			moved=true;
		}
	}

	public void rotate()
	{
		rotationNum++;
		if(rotationNum>3)
			rotationNum=0;
		switch(movingPiece)
		{
			case 1 : if(rotationNum==0)
					 {
						grid[(int)movingPosition[0].getY()] [(int)movingPosition[0].getX()]=0;
						movingPosition[0]=new Point((int)movingPosition[0].getX(),(int)movingPosition[0].getY()-2);
						grid[(int)movingPosition[1].getY()] [(int)movingPosition[1].getX()]=0;
						movingPosition[1]=new Point((int)movingPosition[1].getX()-1,(int)movingPosition[1].getY()-1);
						grid[(int)movingPosition[3].getY()] [(int)movingPosition[3].getX()]=0;
						movingPosition[3]=new Point((int)movingPosition[3].getX()-1,(int)movingPosition[3].getY()+1);
					 }
					 else if(rotationNum==1)
					 {
						grid[(int)movingPosition[0].getY()] [(int)movingPosition[0].getX()]=0;
						movingPosition[0]=new Point((int)movingPosition[0].getX()+2,(int)movingPosition[0].getY());
						grid[(int)movingPosition[1].getY()] [(int)movingPosition[1].getX()]=0;
						movingPosition[1]=new Point((int)movingPosition[1].getX()+1,(int)movingPosition[1].getY()-1);
						grid[(int)movingPosition[3].getY()] [(int)movingPosition[3].getX()]=0;
						movingPosition[3]=new Point((int)movingPosition[3].getX()-1,(int)movingPosition[3].getY()-1);
					 }
					 else if(rotationNum==2)
					 {
						grid[(int)movingPosition[0].getY()] [(int)movingPosition[0].getX()]=0;
						movingPosition[0]=new Point((int)movingPosition[0].getX(),(int)movingPosition[0].getY()+2);
						grid[(int)movingPosition[1].getY()] [(int)movingPosition[1].getX()]=0;
						movingPosition[1]=new Point((int)movingPosition[1].getX()+1,(int)movingPosition[1].getY()+1);
						grid[(int)movingPosition[3].getY()] [(int)movingPosition[3].getX()]=0;
						movingPosition[3]=new Point((int)movingPosition[3].getX()+1,(int)movingPosition[3].getY()-1);
					 }
					 else if(rotationNum==3)
					 {
						grid[(int)movingPosition[0].getY()] [(int)movingPosition[0].getX()]=0;
						movingPosition[0]=new Point((int)movingPosition[0].getX()-2,(int)movingPosition[0].getY());
						grid[(int)movingPosition[1].getY()] [(int)movingPosition[1].getX()]=0;
						movingPosition[1]=new Point((int)movingPosition[1].getX()-1,(int)movingPosition[1].getY()+1);
						grid[(int)movingPosition[3].getY()] [(int)movingPosition[3].getX()]=0;
						movingPosition[3]=new Point((int)movingPosition[3].getX()+1,(int)movingPosition[3].getY()+1);
					 }
					 break;
			case 2 : break;
			case 3 : break;
			case 4 : break;
			case 5 : break;
			case 6 : break;
			case 7 : break;
		}
	}

	public void clearRows()
	{
		boolean fullRow;
		for(int i = grid.length-1; i>=0; i--)
		{
			fullRow=true;

			for(int j = 0; j<grid[i].length; j++)
			{
				if(grid[i][j]==0||grid[i][j]==8)
					fullRow=false;
			}

			if(fullRow)
			{
				for(int row = i-1; row>=0; row--)
				{
					for(int col = 0; col<grid[row].length; col++)
					{
						grid[row+1][col]=grid[row][col];
						grid[row][col]=0;
					}
				}
			}
		}

	}


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
			{
				grid[(int)movingPosition[i].getY()] [(int)movingPosition[i].getX()]=CURRENT_PIECE;
			}

			moved=true;
		}
	}


	public int getMovingPiece()
	{
		return movingPiece;
	}

	public void addNewPiece()
	{
		rotationNum=-1;
		switch(nextPiece)
		{
			/*RIGHT STAIR RED*/case 1 : grid[4][4] = CURRENT_PIECE;
									  	grid[4][5] = CURRENT_PIECE;
									  	grid[3][5] = CURRENT_PIECE;
									  	grid[3][6] = CURRENT_PIECE;
									  	movingPosition=new Point[4];
									  	movingPosition[0]=new Point(4,4);
									 	movingPosition[1]=new Point(5,4);
									  	movingPosition[2]=new Point(5,3);
									 	movingPosition[3]=new Point(6,3);
									 	movingPiece=RED;
									 	break;

			/*LINE*/case 2 : 	grid[1][4] = CURRENT_PIECE;
							    grid[2][4] = CURRENT_PIECE;
							    grid[3][4] = CURRENT_PIECE;
							    grid[4][4] = CURRENT_PIECE;
							    movingPosition=new Point[4];
								movingPosition[0]=new Point(4,1);
								movingPosition[1]=new Point(4,2);
								movingPosition[2]=new Point(4,3);
								movingPosition[3]=new Point(4,4);
								movingPiece=BLUE;
							    break;

			/*LEFT STAIR*/case 3 :  grid[3][4] = CURRENT_PIECE;
								    grid[3][5] = CURRENT_PIECE;
								    grid[4][5] = CURRENT_PIECE;
								    grid[4][6] = CURRENT_PIECE;
									movingPosition=new Point[4];
									movingPosition[0]=new Point(4,3);
									movingPosition[1]=new Point(5,3);
									movingPosition[2]=new Point(5,4);
									movingPosition[3]=new Point(6,4);
									movingPiece=GREEN;
									break;

			/*RIGHT L*/case 4 : grid[2][4] = CURRENT_PIECE;
							    grid[3][4] = CURRENT_PIECE;
							    grid[4][4] = CURRENT_PIECE;
							    grid[4][5] = CURRENT_PIECE;
							    movingPosition=new Point[4];
								movingPosition[0]=new Point(4,2);
								movingPosition[1]=new Point(4,3);
								movingPosition[2]=new Point(4,4);
								movingPosition[3]=new Point(5,4);
								movingPiece=ORANGE;
								break;

			/*SQUARE*/case 5 : 	grid[4][4] = CURRENT_PIECE;
								grid[4][5] = CURRENT_PIECE;
								grid[3][4] = CURRENT_PIECE;
								grid[3][5] = CURRENT_PIECE;
								movingPosition=new Point[4];
								movingPosition[0]=new Point(4,4);
								movingPosition[1]=new Point(5,4);
								movingPosition[2]=new Point(4,3);
								movingPosition[3]=new Point(5,3);
								movingPiece=YELLOW;
								break;

			/*LEFT L*/case 6 : 	grid[2][5] = CURRENT_PIECE;
								grid[3][5] = CURRENT_PIECE;
								grid[4][5] = CURRENT_PIECE;
								grid[4][4] = CURRENT_PIECE;
								movingPosition=new Point[4];
								movingPosition[0]=new Point(5,2);
								movingPosition[1]=new Point(5,3);
								movingPosition[2]=new Point(5,4);
								movingPosition[3]=new Point(4,4);
								movingPiece=PINK;
								break;

			/*T*/case 7 : 	grid[3][3] = CURRENT_PIECE;
						    grid[3][4] = CURRENT_PIECE;
						    grid[3][5] = CURRENT_PIECE;
						    grid[4][4] = CURRENT_PIECE;
						    movingPosition=new Point[4];
							movingPosition[0]=new Point(3,3);
							movingPosition[1]=new Point(4,3);
							movingPosition[2]=new Point(5,3);
							movingPosition[3]=new Point(4,4);
							movingPiece=PURPLE;
							break;
		}

		nextPiece=(int) (Math.random()*7)+1;
		falling=true;
	}

}