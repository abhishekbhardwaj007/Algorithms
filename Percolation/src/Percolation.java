
public class Percolation {

	private int N;
	private int[][] Grid;
	private int VirtualTop;
	private int VirtualBottom;
	private WeightedQuickUnionUF uf;

	private static final int BLOCKED = 0;
	private static final int OPEN    = 1;

	/*public void UnitTest()
	{
		Percolation p = new Percolation(10);
		p.open(1, 1);
		p.open(1, 2);
		
		System.out.println(p.uf.connected(xyto1D(1, 1), xyto1D(1, 2)));	
	}*/
	
	private boolean IsSiteOutOfBounds(int i, int j)
	{
		if ((i < 1) || (i > N))
		{
			return true;
		}

		if ((j < 1) || (j > N))
		{
			return true;
		}

		return false;
	}

	private void ThrowExceptionIfIllegal(int i, int j)
	{
		// Check if indice is legal
		if (IsSiteOutOfBounds(i, j))
		{
			throw new  java.lang.IndexOutOfBoundsException();
		}
	}

	private int xyto1D(int i, int j)
	{
		return ((i*(N + 1)) + j);
	}

	private void UnionPoints(int i, int j, int x, int y)
	{
		if (!IsSiteOutOfBounds(x, y))
		{
			if ((Grid[x][y] == OPEN) && (!uf.connected(xyto1D(i, j), xyto1D(x, y))))
			{
				uf.union(xyto1D(i, j), xyto1D(x, y));
			}
		}
	}

	private void UnionAllNeighbours(int i, int j)
	{
		// If the point is in the first row then union with VirtualTop
		if (i == 1)
		{
			uf.union(xyto1D(i, j), VirtualTop);
		}

		// If the point is in the bottom row then union with VirtualBottom
		if (i == N)
		{
			uf.union(xyto1D(i, j), VirtualBottom);
		}

		// Union with all neighbours

		// Neighbour above
		UnionPoints(i, j, i - 1, j);

		// Neighbour below
		UnionPoints(i, j, i + 1, j);

		// Neighbour left
		UnionPoints(i, j, i, j - 1);

		// Neighbour right
		UnionPoints(i, j, i, j + 1);
	}

	public Percolation(int N)
	{
		// Indices vary from 1 to N
		this.N = N; 

		this.Grid = new int[N + 1][N + 1];

		// Initing union find
		uf = new WeightedQuickUnionUF((N + 1)*(N + 1));

		// Initing Grid to all blocked
		for (int i = 1; i <= N; i++)
		{
			for (int j = 1; j <= N; j++)
			{
				Grid[i][j] = BLOCKED;
			}
		}

		// Getting our virtual top and bottom from unused row and
		// column
		VirtualTop = xyto1D(0, 1);
		VirtualBottom = xyto1D(1, 0);
	}

	public void open(int i, int j)
	{
		// Check if indice is legal
		ThrowExceptionIfIllegal(i, j);

		// Open indice
		Grid[i][j] = OPEN;

		// Union with all open neighbours
		UnionAllNeighbours(i, j);
	}

	public boolean isOpen(int i, int j)
	{
		// Check if indice is legal
		ThrowExceptionIfIllegal(i, j);
		
		if (Grid[i][j] == BLOCKED)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	public boolean isFull(int i, int j)
	{
		// Check if indice is legal
		ThrowExceptionIfIllegal(i, j);
		
		// A full site is a site that is connected via multiple open sites
		// to cells in the top row
		return uf.connected(xyto1D(i, j), VirtualTop);
		
	}
	
	public boolean percolates()
	{
		return uf.connected(VirtualTop, VirtualBottom);
	}
	
	/*public static void main(String[] args)
	{
		// Empty for now
	}*/
	

}
