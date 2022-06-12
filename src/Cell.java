public class Cell {
	byte[] walls = {1,1,1,1};//start with all the walls up
	byte[] borders = {0,0,0,0};
	Integer x;
	Integer y;
	
	//prints content of cell for testing
	public void printCell() {
		System.out.println(" " + walls[0] + " ");
		System.out.println(walls[3] + " " + walls[1]);
		System.out.println(" " + walls[2] + " ");
	}
	
	//check if all the walls are up
	public boolean checkWalls() {
		if(walls[0] == 1 && walls[1] == 1 && walls[2] == 1 && walls[3] == 1) {
			return true;
		}
		else {
			return false;
		}
	}
}