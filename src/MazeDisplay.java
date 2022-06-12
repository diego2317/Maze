import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class MazeDisplay extends JPanel implements KeyListener{

	Maze maze;
	int offsetX = 10;
	int offsetY = 10;
	int cellSize = 20;

	Integer moveCounter = 0;
	int pointX, pointY, oldX, oldY;
	boolean erase;
	boolean win = false;
	
	public MazeDisplay() {
		maze = new Maze();
		pointX = offsetX + cellSize/2;
		pointY = offsetY + cellSize/2;
		oldX = pointX;
		oldY = pointY;
		addKeyListener(this);
	}

	public MazeDisplay(Maze maze) {
		this.maze = maze;
		pointX = offsetX + cellSize/2;
		pointY = offsetY + cellSize/2;
		oldX = pointX;
		oldY = pointY;
		addKeyListener(this);
	}

	public MazeDisplay(Maze m1, int cellSize) {
		maze = m1;
		this.cellSize = cellSize;
		pointX = offsetX + cellSize/2;
		pointY = offsetY + cellSize/2;
		oldX = pointX;
		oldY = pointY;
		addKeyListener(this);
	}


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * Deals with moving the player when they press an arrow key
	 */
	@SuppressWarnings("static-access")
	@Override
	public void keyPressed(KeyEvent key) {
		oldX = pointX;
		oldY = pointY;

		///If down or S keys were pressed
		if(key.getKeyCode() == key.VK_DOWN || key.getKeyCode() == key.VK_S) {
			pointY = pointY + cellSize;
			if(pointY > getBounds().height) {
				pointY = getBounds().height;
			}
		}

		//if up or W keys were pressed
		if(key.getKeyCode() == key.VK_UP || key.getKeyCode() == key.VK_W) {
			pointY = pointY - cellSize;
			if(pointY > getBounds().height) {
				pointY = getBounds().height;
			}
		}

		//if left or A keys were pressed
		if(key.getKeyCode() == key.VK_LEFT || key.getKeyCode() == key.VK_A) {
			pointX = pointX - cellSize;
			if(pointX < 0) {
				pointX = 0;
			}
		}

		//if right or D keys were pressed
		if(key.getKeyCode() == key.VK_RIGHT || key.getKeyCode() == key.VK_D) {
			pointX = pointX + cellSize;
			if(pointX > getBounds().width) {
				pointX = getBounds().width;
			}
		}
		if(!win) {
			repaint();
		}
		else {
			
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	private void doDrawing(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;

		g2d.setColor(Color.blue);

		Dimension size = getSize();
		Insets insets = getInsets();

		int width = size.width - insets.left - insets.right;
		int height = size.height - insets.top - insets.bottom;

		g2d.setBackground(Color.white);
		g2d.clearRect(0, 0, width, height);

		Path2D mazeShape = new Path2D.Double();

		int x,y;

		for(Integer i = 0; i < maze.sizeX; i++) {
			x = i * cellSize + offsetX;
			for(Integer j = 0; j < maze.sizeY; j++) {
				y = j * cellSize + offsetY;

				if(maze.cells[i][j].walls[0]==1) {
					mazeShape.moveTo(x, y);
					mazeShape.lineTo(x + cellSize, y);
					g2d.drawLine(x, y, x + cellSize, y);
				}

				if(maze.cells[i][j].walls[1]==1) {
					mazeShape.moveTo(x + cellSize, y);
					mazeShape.lineTo(x + cellSize, y + cellSize);
					g2d.drawLine(x + cellSize, y, x + cellSize, y + cellSize);
				}

				if(maze.cells[i][j].walls[2]==1) {
					mazeShape.moveTo(x, y + cellSize);
					mazeShape.lineTo(x + cellSize, y + cellSize);
					g2d.drawLine(x, y + cellSize, x + cellSize, y + cellSize);
				}

				if(maze.cells[i][j].walls[3]==1) {
					mazeShape.moveTo(x, y);
					mazeShape.lineTo(x, y + cellSize);
					g2d.drawLine(x, y, x, y + cellSize);
				}
			}
		}


		//clipping code
		x = (oldX - offsetX - cellSize / 2) / cellSize;
		y = (oldY - offsetY - cellSize / 2) / cellSize;

		//Left
		if(x >= 0 && x < maze.sizeX && oldX > pointX && maze.cells[x][y].walls[3] == 1) {
			pointX = oldX;
			pointY = oldY;
		}

		//Right
		else if(x >= 0 && x < maze.sizeX && oldX < pointX && maze.cells[x][y].walls[1]==1) {
			pointX = oldX;
			pointY = oldY;
		}

		//Up
		else if(y >= 0 && y < maze.sizeY && oldY > pointY && maze.cells[x][y].walls[0]==1) {
			pointX = oldX;
			pointY = oldY;
		}

		//Down
		else if(y >= 0 && y < maze.sizeY && oldY < pointY && maze.cells[x][y].walls[2]==1) {
			pointX = oldX;
			pointY = oldY;
		}

		if(pointX != oldX || pointY != oldY) {
			moveCounter++;
		}

		g2d.drawString("Moves: " + moveCounter.toString(), maze.sizeX * cellSize + offsetX + 20, 20);
		g2d.drawString("Move: Arrow or WASD",maze.sizeX*cellSize + offsetX + 20, 40);

		if(y == maze.sizeY - 1 && x == maze.sizeX-1) {
			g2d.drawString("You won!", maze.sizeX * cellSize + offsetX + 20, 100);
			win = true;
		}

		//draw a gray square where the player was on the previous move
		g.setColor(Color.gray);
		g.fillRect(oldX-2, oldY-2, 4, 4);

		//draw a black square where the player currently is
		g.setColor(Color.black);
		g.fillRect(pointX-2, pointY-2, 4, 4);
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		doDrawing(g);
	}

}
