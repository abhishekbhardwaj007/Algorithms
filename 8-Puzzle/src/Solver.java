import java.util.Comparator;


public class Solver {

	private MinPQ<Node> PQ;
	private Queue<Board> Q;
	private int moves;

	private Board GetMinBoard(MinPQ<Node> pq) {
		return pq.delMin().B;
	}
	
	private boolean solve(Board initial)
	{
		moves = 0;
		PQ = new MinPQ<Node>();
		Q =  new Queue<Board>(); 

		Board PrevSearch;
		Board B;

		PrevSearch = null;

		// Solving with original board
		PQ.insert(new Node(initial, 0));
		B = GetMinBoard(PQ);
		
		while ((B != null) && (B.isGoal() != true)) {

			System.out.println("Solve " + B);
			Q.enqueue(B);

			// Insert all neighbours of B in PQ
			moves++;

			Iterable<Board> It = B.neighbors();

			for (Board N: It) {

				// Only add in PQ 
				if (!N.equals(PrevSearch)) {
					PQ.insert(new Node(N, moves));
				}
			}

			PrevSearch = B;
			
			B = GetMinBoard(PQ);
		}

		// Need to implement this
		return true;
	}

	// find a solution to the initial board (using the A* algorithm)
	public Solver(Board initial) {
		
		solve(initial);
	}

	// is the initial board solvable?
	public boolean isSolvable()   {
		return true;
	}

	// min number of moves to solve initial board; -1 if no solution
	public int moves() {
		return this.moves;
	}

	// sequence of boards in a shortest solution; null if no solution
	public Iterable<Board> solution() {
		return Q;
	}
	
	private static class Node implements Comparable<Node> {
		
		private Board B;
		private int moves;
		
		public Node(Board board, int moves) {
			this.B = board;
			this.moves = moves;
		}
		
		public int compareTo(Node that) {
			
			if (that == null)
	    	{
	    		throw new java.lang.NullPointerException();
	    	}
			
			if ((this.B.manhattan() + this.moves) < (that.B.manhattan() + that.moves)) {
				return -1;
			}
			
			if ((this.B.manhattan() + this.moves) > (that.B.manhattan() + that.moves)) {
				return  1;
			}
			
			if ((this.B.manhattan() + this.moves) == (that.B.manhattan() + that.moves)) {
				return  0;
			}
			
			return 0;
			
		}
	
	}

	// solve a slider puzzle (given below)
	public static void main(String[] args) {

		// create initial board from file
		In in = new In(args[0]);
		int N = in.readInt();

		int[][] blocks = new int[N][N];

		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				blocks[i][j] = in.readInt();

		Board initial = new Board(blocks);

		// solve the puzzle
		Solver solver = new Solver(initial);

		// print solution to standard output
		if (!solver.isSolvable())
			StdOut.println("No solution possible");
		else {
			StdOut.println("Minimum number of moves = " + solver.moves());
			for (Board board : solver.solution())
				StdOut.println(board);
		}

	}

}
