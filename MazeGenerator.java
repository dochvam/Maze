import java.util.*;

public class MazeGenerator {

	public enum Direction {
		UP, DOWN, LEFT, RIGHT
	};

	// _TOP_ _RIGHT_ _BOTTOM_ _LEFT_ 

	public static final int UNVISITED = 0xF;
	public static final int TOPWALL = 1 << 3;
	public static final int RIGHTWALL = 1 << 2;
	public static final int BOTTOMWALL = 1 << 1;
	public static final int LEFTWALL = 1;


	public static int[][] initGrid(int n, int m) {

		int[][] board = new int[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				board[i][j] = 0xF;
			}
		}
		return board;
	}

	public static ArrayList<Direction> getPossibleMoves(int[][] board, Point current) {

		ArrayList<Direction> validMoves = new ArrayList<>();

		if (current.y != 0 && board[current.x][current.y - 1] == UNVISITED) 
			validMoves.add(Direction.UP);
		if (current.y != (board[0].length - 1) && board[current.x][current.y + 1] == UNVISITED) 
			validMoves.add(Direction.DOWN);
		if (current.x != 0 && board[current.x - 1][current.y] == UNVISITED) 
			validMoves.add(Direction.LEFT);
		if (current.x != (board.length - 1) && board[current.x + 1][current.y] == UNVISITED) 
			validMoves.add(Direction.RIGHT);

		return validMoves;
	}

	public static void displayBoard(int[][] board, Point p1, Point p2){

		StdDraw.clear();

		StdDraw.setPenColor(StdDraw.MAGENTA);
		StdDraw.filledRectangle(p1.x + 0.5, p1.y + 0.5, 0.5, 0.5);
		StdDraw.setPenColor(StdDraw.GREEN);
		StdDraw.filledRectangle(p2.x + 0.5, p2.y + 0.5, 0.5, 0.5);

		double pr1 = 0.01 * (20 / board.length);
		StdDraw.setPenRadius(pr1);
		StdDraw.setPenColor(StdDraw.BLACK);

		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {

				int walls = board[i][j];

				if ((TOPWALL & walls) != 0) 
					StdDraw.line(i, j, i+1, j);
				if ((BOTTOMWALL & walls) != 0)
					StdDraw.line(i, j+1, i+1, j+1);
				if ((LEFTWALL & walls) != 0)
					StdDraw.line(i, j, i, j+1);
				if ((RIGHTWALL & walls) != 0)
					StdDraw.line(i+1, j, i+1, j+1);

			}
		}

		// StdDraw.pause(5);
		StdDraw.show();
	}

	public static void main(String[] args) {

		int n = 10;

		if (args.length != 0) 
			n = Integer.parseInt(args[0]);
		
		StdDraw.setCanvasSize(800, 800);
		StdDraw.setScale(0, n);
   		StdDraw.enableDoubleBuffering();

		Stack<Point> pathstack = new Stack<>();

		Point starter = new Point(n/2, n/2);

		Point current = starter;
		pathstack.push(current);

		int[][] board = initGrid(n, n);

		// for (int i = 0; i<board.length; i++) {
		// 	for (int j = 0; j<board[0].length; j++) {
		// 		System.out.print(board[i][j] + "\t");
		// 	}
		// 	System.out.println("");
		// }

		Point furthest = current;
		int longestPath = 0;

		while (!pathstack.isEmpty()) {

			ArrayList<Direction> moves = getPossibleMoves(board, current);

			if (moves.isEmpty()) {
				// int dist = pathstack.size();
				current = pathstack.pop();

				// if (dist > longestPath) {
					// longestPath = dist;
					// furthest = current;
				// }
				continue;
			}


			Direction choice = moves.get((int) (Math.random() * moves.size()));

			// System.out.println(choice);

			switch (choice) {

				case UP:
					board[current.x][current.y] ^= TOPWALL;
					current = new Point(current.x, (current.y - 1));
					board[current.x][current.y] ^= BOTTOMWALL;
					break;

				case DOWN:
					board[current.x][current.y] ^= BOTTOMWALL;
					current = new Point(current.x, (current.y + 1));
					board[current.x][current.y] ^= TOPWALL;
					break;

				case LEFT:
					board[current.x][current.y] ^= LEFTWALL;
					current = new Point(current.x - 1, current.y);
					board[current.x][current.y] ^= RIGHTWALL;
					break;

				case RIGHT:
					board[current.x][current.y] ^= RIGHTWALL;
					current = new Point(current.x + 1, current.y);
					board[current.x][current.y] ^= LEFTWALL;
					break;
			}
			
			pathstack.push(current);

			int dist = pathstack.size();
			if (dist > longestPath) {
				longestPath = dist;
				furthest = current;
			}

			displayBoard(board, furthest, starter);


		}

		// for (int i = 0; i<board.length; i++) {
		// 	for (int j = 0; j<board[0].length; j++) {
		// 		System.out.print(board[j][i] + "\t");
		// 	}
		// 	System.out.println("");
		// }

		displayBoard(board, furthest, starter);
	}
}



