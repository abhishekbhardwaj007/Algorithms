
public class Board {

	private int N;
	private int[][] blocks;
	private int RowOfZero;
	private int ColOfZero;
	private Queue<Board> Q;
	
	private int abs(int in) {

		if (in < 0)
			return -in;

		return in;
	}

	private boolean isLegal(int x, int y)
	{
		if ((x < 0 ) || (x >= N))
			return false;

		if ((y < 0 ) || (y >= N))
			return false;

		return true;
	}
	
	private void swap(Board B, int i, int j, int x, int y) {
		
		int temp = B.blocks[i][j];
		B.blocks[i][j] = B.blocks[x][y];
		B.blocks[x][y] = temp;
	}

	// construct a board from an N-by-N array of blocks
	// (where blocks[i][j] = block in row i, column j)
	public Board(int[][] blocks) {

		this.N = blocks.length;

		this.blocks = new int[N][N];
		
		for (int i = 0; i < blocks.length; i++)
		{
			for(int j = 0; j < blocks.length; j++)
			{
				this.blocks[i][j] = blocks[i][j];

				if (blocks[i][j] == 0)
				{
					RowOfZero = i;
					ColOfZero = j;
				}
			}
		}

		// Init Q for neighbors
		Q = new Queue<Board>();
	}

	// board dimension N
	public int dimension() {
		return this.N;
	}

	// number of blocks out of place
	public int hamming() {

		int hamming = 0;

		for (int i = 0; i < (N - 1); i++)
		{
			for (int j = 0; j < N; j++) {

				if (blocks[i][j] != ((i * N) + j + 1)){
					hamming++;
				}
			}
		}

		// Last Row
		for (int j = 0; j < (N - 1); j++) {

			if (blocks[N - 1][j] != (((N - 1) * N) + j + 1)){
				hamming++;
			}

		}

		return hamming;
	}

	// sum of Manhattan distances between blocks and goal
	public int manhattan() {

		int manhattan = 0;

		for (int i = 0; i < N; i++)
		{
			for (int j = 0; j < N; j++) {
				
				if ((blocks[i][j] != ((i * N) + j + 1)) && (blocks[i][j] != 0)){
					
					int Num = blocks[i][j] - 1;
					int Row = Num / N;
					int Col = Num % N;
					
					manhattan += abs(Row - i) + abs(Col - j);
				}
			}
		}

		return manhattan;
	}

	// is this board the goal board?
	public boolean isGoal() {

		// If the goal is met both hamming and manhattan must be 0
		if ((hamming() == 0) && (manhattan() == 0)) {
			return true;
		}

		return false;
	}

	// a board obtained by exchanging two adjacent blocks in the same row
	public Board twin() {

		//
		// We find the first row that does not have Zero element
		//
		int Row = 0;

		while (Row < N) {

			if (Row != RowOfZero) {
				break;
			}

			Row++;
		}

		// This should never happen
		if ((Row < 0) && (Row == N)) {
			return null;
		}

		// We found the first Row that does not have the Zero 
		// we switch the 0 and the 1st element of the Row
		Board Twin = new Board(this.blocks);

		swap(Twin, Row, 0, Row, 1);

		return Twin;
	}

	// does this board equal y?
	public boolean equals(Object y) {

		if (y == this) return true;
		if (y == null) return false;
		if (y.getClass() != this.getClass()) return false;

		Board that = (Board) y;
		
		if (dimension() != that.dimension())
			return false;
		
		for (int i = 0; i < N; i++)
		{
			for (int j = 0; j < N; j++) {
				
				if (this.blocks[i][j] != that.blocks[i][j])
					return false;
			}
		}
		
		return true;
	}

	// all neighboring boards
	public Iterable<Board> neighbors() {
		
		//
		// Generate all possible 4 combinations 
		//
		
		Board B;
		
		// Top
		if (isLegal(RowOfZero - 1, ColOfZero)) {
			B = new Board(this.blocks);
			swap(B, RowOfZero, ColOfZero, RowOfZero - 1, ColOfZero);
						
			Q.enqueue(B);
		}
		
		// Bottom
		if (isLegal(RowOfZero + 1, ColOfZero)) {
			B = new Board(this.blocks);
			swap(B, RowOfZero, ColOfZero, RowOfZero + 1, ColOfZero);
			
			Q.enqueue(B);
		}
		
		// Left
		if (isLegal(RowOfZero, ColOfZero - 1)) {
			B = new Board(this.blocks);
			swap(B, RowOfZero, ColOfZero, RowOfZero, ColOfZero - 1);
			
			Q.enqueue(B);
		}
		
		// Right
		if (isLegal(RowOfZero, ColOfZero + 1)) {
			B = new Board(this.blocks);
			swap(B, RowOfZero, ColOfZero, RowOfZero, ColOfZero + 1);
			
			Q.enqueue(B);
		}
		
		return Q;
	}

	// string representation of the board (in the output format specified below)
	public String toString() {

		StringBuilder s = new StringBuilder();
		s.append(N + "\n");

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				s.append(String.format("%2d ", this.blocks[i][j]));
			}
			s.append("\n");
		}

		return s.toString();
	}

	// Just for unit testing;
	public static void main(String[] Args) {
		
		int[][] Array = { {8, 1, 3}, {4, 0, 2}, {7, 6, 5} };
		int[][] GoalArray = { {1, 2, 3}, {4, 5, 6}, {7, 8, 0} };
		
		Board B = new Board(Array);
		Board GB = new Board(GoalArray);
		 
		// Test Dimension
		System.out.println("Dimension" + " " + B.dimension());
		
		// Test To String
		System.out.println("Board B " + B);
		
		// Test Twins
		System.out.println("Board B twin " + B.twin());
		
		// Test equals
		System.out.println(GB.equals(B));
		System.out.println(GB.equals(GB));
		
		// Test Hamming
		System.out.println("Hamming  " + B.hamming());
		
		// Test Manhattan
		System.out.println("Manhattan  " + B.manhattan());
		
		
		// Test Neighbours
		Iterable<Board> It = B.neighbors();
		
		for (Board b: It) {
			System.out.println("Iterator " + b);
		}
		
		
		// Test isGoal()
		System.out.println(GB.equals(B));
		System.out.println(GB.equals(GB));
		
	}

}
