#include "stdio.h"
int fac(int n)
{
	if(n==0)return 1;
	return n*fac(n-1);
}
int main()
{
	for(int i=0;i<10;i++)
		printf("%d ",fac(i));
}
