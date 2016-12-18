public class CellAutStdD{
	
	public static boolean[][] updateBoard(boolean[][] board, double prob){

		int dim = board.length;

		boolean[][] nextBoard = new boolean[dim][dim];

		for(int i = 0; i < dim; i++){
			for(int j = 0; j < dim; j++){

				int neighbors = getNeighbors(board,i,j);

				// GAME OF LIFE
				// if (!board[i][j] && neighbors == 3) nextBoard[i][j] = true;
				// else if (board[i][j] && neighbors < 2) nextBoard[i][j] = false;
				// else if (board[i][j] && neighbors > 3) nextBoard[i][j] = false;
				// else nextBoard[i][j] = board[i][j];

				//Maze generator
				if (!board[i][j] && neighbors == 3) nextBoard[i][j] = true;
				else if (board[i][j] && neighbors < 1) nextBoard[i][j] = false;
				else if (board[i][j] && neighbors > 4) nextBoard[i][j] = false;
				else nextBoard[i][j] = board[i][j];


				// RANDOM FLIPPING
				// if (neighbors > 4) nextBoard[i][j] = true;
				// else if (neighbors < 4) nextBoard[i][j] = false;
				// else nextBoard[i][j] = board[i][j];
				// boolean inverter = (Math.random() < prob);
				// if (inverter) nextBoard[i][j] = !nextBoard[i][j];

				// CHANCE OF UPDATING
				// double r = Math.random();
				// if (neighbors > 4 && r < 0.5) nextBoard[i][j] = true;
				// else if (neighbors < 4 && r < 0.5) nextBoard[i][j] = false;
				// else nextBoard[i][j] = board[i][j];
	
				// ATTEMPT TO SWAP
			// 	if (neighbors < 5 && board[i][j]) {
			// 		boolean noNumFound = true;
			// 		int c = 0;
			// 		int d = 0;
			// 		while(noNumFound){
			// 			c = (int) (Math.random() * dim);
			// 			d = (int) (Math.random() * dim);
			// 			if (!board[c][d]) noNumFound = false;
			// 		}
			// 		if (getNeighbors(board,c,d) > 4){
			// 			nextBoard[i][j] = false;
			// 			nextBoard[c][d] = true;
			// 		}
			// 		else nextBoard[i][j] = board[i][j];
			// 	}

			// 	else if (neighbors > 3 && !board[i][j]){
			// 		boolean noNumFound = true;
			// 		int c = 0;
			// 		int d = 0;
			// 		while(noNumFound){
			// 			c = (int) (Math.random() * dim);
			// 			d = (int) (Math.random() * dim);
			// 			if (board[c][d]) noNumFound = false;
			// 		}
			// 		if (getNeighbors(board,c,d) < 4) {
			// 			nextBoard[i][j] = true;
			// 			nextBoard[c][d] = false;
			// 		}
			// 		else nextBoard[i][j] = board[i][j];
			// 	}
			// 	else nextBoard[i][j] = board[i][j];
			}
		}

		return nextBoard;
	}

	public static void printBoard(boolean[][] board){

		int dim = board.length;

		for(int i = 0; i < dim; i++){
			for(int j = 0; j < dim; j++){
				if (board[i][j] == true) System.out.print("X");
				else System.out.print("O");
			}
			System.out.println("");
		}
	}

	public static void drawBoard(boolean[][] board, int size){

		for(int i = 0; i<size; i++){
			for(int j = 0; j<size; j++){
				StdDraw.setPenColor(StdDraw.BLACK);
				if (board[i][j]) StdDraw.filledSquare(i, j, 0.5);
			}
		}

	}

	public static int getNeighbors(boolean[][] board, int i, int j){
		int dim = board.length;
		int neighbors = 0;
		for (int x = -1; x < 2; x++){
			for (int y = -1; y < 2; y++){
				int a = i+x;
				int b = j+y;

				if (a < 0) a = dim-1;
				else if (a >= dim) a = 0;

				if (b < 0) b = dim-1;
				else if (b >= dim) b = 0;

				if (board[a][b] == true) neighbors += 1;
			}
		}
		if (board[i][j]) neighbors -= 1;

		return neighbors;
	}


	public static void main(String[] args) {
	    int dim = 50;
	    if (args.length > 0) dim = Integer.parseInt(args[0]);

	    //parameters
	    double live_prob = 0.8;
	    double random_change_prob = 0.2;
	    int timestep = 0;


	    boolean[][] board = new boolean[dim][dim];
	    StdDraw.enableDoubleBuffering();
		StdDraw.setScale(0,dim+1);

		for(int i = 0; i < dim; i++){
			for(int j = 0; j < dim; j++){
				if (Math.random() < live_prob) board[i][j] = true;
			}
		}

		while(true){
			StdDraw.clear();
			drawBoard(board, dim);
			board = updateBoard(board, random_change_prob);
			StdDraw.show();
			StdDraw.pause(timestep);
		}
    }
}