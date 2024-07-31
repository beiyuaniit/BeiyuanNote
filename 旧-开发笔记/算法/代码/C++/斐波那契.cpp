#include "stdio.h"
int Fib(int i)
{
	if(i<2)
		return i==0?0:1;//1返回1,0返回0；
return Fib(i-1)+Fib(i-2);
}
int main()
{
	int a;
	for(int i=0;i<30;i++) 
	printf("%d ",Fib(i));
}
