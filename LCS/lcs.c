//
// Longest Common Subsequence of characters 
// DP
// www.geeksforgeeks.org/dynamic-programming-set-4-longest-common-subsequence/ 
//
#include <stdio.h>
#include <string.h>
#include <stdlib.h>

#define MAX(a,b) ((a) > (b) ? (a) : (b))

static int** L;

int LCS(char* String1, char* String2, int m, int n)
{
    int i,j;

    int init_val = (String1[0] && String2[0]);

    for (i = 0; i < m; i++)
    {
        L[i][0] = init_val;
    }
    
    for (j = 0; j < n; j++)
    {
        L[0][j] = init_val;
    }

    for (i = 1; i < m; i++)
    {
        for (j = 1; j < n; j++)
        {
            if (String1[i] == String2[j])
            {
                L[i][j] = L[i-1][j-1] + 1;
            }
            else
            {
                L[i][j] = MAX(L[i-1][j], L[i][j-1]);
            }
        }
    }

	return L[m-1][n-1];
}


int main(int argc, char** argv)
{
	char X[] = "ABCDGH";
	char Y[] = "AEDFHR";

	int i = 0;
	int m = strlen(X);
	int n = strlen(Y);

	L = (int**) malloc(m * sizeof(int*));

	for (i = 0; i < m; i++)
	{
		L[i] = (int*) malloc(n * sizeof(int));
	}

	printf("Length of LCS is %d\n", LCS( X, Y, m, n ) );

	for (i = 0; i < m; i++)
	{
		free(L[i]);
	}

	free(L);
}

