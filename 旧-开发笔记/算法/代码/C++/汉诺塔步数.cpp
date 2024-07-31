#include "stdio.h"
int Hanoi(int i)
{
	if(i<2)
		return 1;
	else
		return 2*Hanoi(i-1)+1;
}
int main()
{
	for(int i=1;i<10;i++)
		printf("%d ",Hanoi(i));
}
