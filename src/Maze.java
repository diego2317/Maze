import java.util.*;

class Maze{
	int sizeX;
	int sizeY;
	Cell[][] cells;

	public Maze() {
		sizeX = 25;
		sizeY = 25;
		cells = new Cell[sizeX][sizeY];
		initializeCells();
		generateMaze();
	}

	public Maze(int x, int y) {
		sizeX = x;
		sizeY = y;
		cells = new Cell[sizeX][sizeY];
		initializeCells();
		generateMaze();
	}

	public void printAllCells() {
		for(int i = 0; i < sizeX; i++) {
			for(int j = 0; j < sizeY; j++) {
				System.out.println(i + " " + j);
				cells[i][j].printCell();
				System.out.println("\n");
			}
		}
	}

	//N=0,E=1,S=2,W=3
	//Initializes all the cells in the array
	private void initializeCells() {
		for(int i = 0; i < sizeX; i++) {
			for(int j = 0; j < sizeY; j++) {
				cells[i][j] = new Cell();
				cells[i][j].x = i;
				cells[i][j].y = j;
				if(i==0) {
					cells[i][j].borders[0]=1;
				}
				if(j==0) {
					cells[i][j].borders[3]=1;
				}
				if(i==sizeX-1) {
					cells[i][j].borders[2]=1;
				}
				if(j==sizeY-1) {
					cells[i][j].borders[1]=1;
				}
			}
		}
	}

	//N=0,E=1,S=2,W=3
	//Maze generated using what I think is a DFS algorithm
	private void generateMaze() {
		Random r = new Random();

		int x = r.nextInt(sizeX);//starting location
		int y = r.nextInt(sizeY);

		Stack<Cell> cellStack = new Stack<Cell>();//Cells that have been visited
		int totalCells = sizeX * sizeY;
		int visitedCells = 1;//counter to make sure every cell is visited
		Cell currentCell = cells[x][y];//random starting cell

		ArrayList<Vector> neighborCellList = new ArrayList<Vector>();//holds neighboring cells

		Vector temp = new Vector();

		while(visitedCells<totalCells) {
			neighborCellList.clear();

			temp = new Vector();//clear variable
			if(y-1 >= 0 && cells[x][y-1].checkWalls() == true) {
				temp.x1 = x;
				temp.y1 = y;
				temp.x2 = x;
				temp.y2 = y-1;
				temp.wall1 = 0;
				temp.wall2 = 2;
				neighborCellList.add(temp);
			}

			temp = new Vector();//clear variable
			if(y+1 < sizeY && cells[x][y+1].checkWalls() == true) {
				temp.x1 = x;
				temp.y1 = y;
				temp.x2 = x;
				temp.y2 = y+1;
				temp.wall1 = 2;
				temp.wall2 = 0;
				neighborCellList.add(temp);
			}

			temp = new Vector();//clear variable
			if(x-1 >= 0 && cells[x-1][y].checkWalls() == true) {
				temp.x1 = x;
				temp.y1 = y;
				temp.x2 = x-1;
				temp.y2 = y;
				temp.wall1 = 3;
				temp.wall2 = 1;
				neighborCellList.add(temp);
			}

			temp = new Vector();//clear variable
			if(x+1 < sizeX && cells[x+1][y].checkWalls() == true) {
				temp.x1 = x;
				temp.y1 = y;
				temp.x2 = x+1;
				temp.y2 = y;
				temp.wall1 = 1;
				temp.wall2 = 3;
				neighborCellList.add(temp);
			}

			//if found an unvisited neighbor cell
			if(neighborCellList.size() >= 1) {
				//randomly choose the next cell to visit
				int r1 = r.nextInt(neighborCellList.size());
				temp = neighborCellList.get(r1);

				//knock down the walls between the cells
				cells[temp.x1][temp.y1].walls[temp.wall1]=0;
				cells[temp.x2][temp.y2].walls[temp.wall2]=0;

				//push the current cell so it can be revisited
				cellStack.push(currentCell);

				//make the new cell the current cell
				currentCell = cells[temp.x2][temp.y2];

				//update x,y
				x = currentCell.x;
				y = currentCell.y;

				//Increment the visitedcells counter
				visitedCells++;
			}
			//Else get the last cell from the stack and use it as the current cell
			else {
				currentCell = cellStack.pop();
				x = currentCell.x;
				y = currentCell.y;
			}
		}
		//set the entrance/exit of the maze. for my purposes, it will always be the top left/bottom right
		cells[0][0].walls[3]=0;
		cells[sizeX-1][sizeY-1].walls[1]=0;
	}
}
