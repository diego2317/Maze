import javax.swing.*;

@SuppressWarnings("serial")
public class MazeGame extends JFrame{
	
	int sizeX = 100;
	int sizeY = 100;
	int cellSize = 15;
	Maze maze = new Maze(sizeX, sizeY);
	
	public MazeGame() {
		startGame();
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				MazeGame mazeGame = new MazeGame();
				mazeGame.setVisible(true);
			}
		});
	}
	
	private void startGame() {
		setTitle("Maze Game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(sizeX * cellSize + 200, sizeY * cellSize + 75);
		
		MazeDisplay mazeDisplay = new MazeDisplay(maze, cellSize);
		add(mazeDisplay);
		addKeyListener(mazeDisplay);
		setContentPane(mazeDisplay);
		mazeDisplay.setFocusable(true);
		setLocationRelativeTo(null);
	}
}
