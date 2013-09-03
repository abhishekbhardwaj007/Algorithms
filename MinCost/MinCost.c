//
// Minimum Cost
// DP
// www.geeksforgeeks.org/dynamic-programming-set-6-min-cost-path/ 
//
#include <stdio.h>
#include <string.h>
#include <stdlib.h>

#define R 3
#define C 3

int Min(int a, int b, int c)
{
	if (a < b)
	{
		return (((a) < (c)) ? (a) : (c));
	}
	else
	{
		return (((b) < (c)) ? (b) : (c));
	}
}


int MinCost(int cost[R][C], int m, int n)
{
	int i,j;
	int tc[R][C];

	tc[0][0] = cost[0][0];

	for (i = 1; i <= m; i++)
	{
		tc[i][0] = tc[i-1][0] + cost[i][0];
	}	

	for (j = 1; j <= n; j++)
	{
		tc[0][j] = tc[0][j-1] + cost[0][j];
	}

	for (i = 1; i <= m; i++)
	{
		for (j = 1; j <= n; j++)
		{
			tc[i][j] = cost[i][j] + Min(tc[i-1][j-1],tc[i][j-1], tc[i-1][j]);
		}
	}

	return tc[m][n];
}


int main(int argc, char** argv)
{   


   int cost[R][C] = { {1, 2, 3},
                      {4, 8, 2},
                      {1, 5, 3} };
   printf(" %d ", MinCost(cost, 2, 1));
   return 0;

}

