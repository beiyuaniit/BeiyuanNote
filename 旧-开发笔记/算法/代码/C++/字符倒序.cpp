#include "stdio.h"
void reverse()
{
	char ch;
	scanf("%c",&ch);
	if(ch!='?')reverse();
	if(ch!='?')printf("%c",ch);
}
int main()
{
	reverse();
}
