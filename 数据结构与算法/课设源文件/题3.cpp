#include <iostream>
#include<stdlib.h>
#define m 8   
#define n 8	   
using namespace std;

int maze[m][n] = {
	{0,0,0,1,1,1,0,1},
	{0,1,0,1,1,1,0,1},
	{0,0,1,1,0,0,1,0},
	{1,0,0,0,0,1,0,1},
	{1,1,1,0,0,1,1,1},
	{1,0,1,1,0,0,1,0},
	{1,0,0,0,0,0,0,1},
	{0,0,1,1,1,0,0,0}
};

int Move[4][2] = { {0,1},{1,0},{0,-1},{-1,0} };

int MAZE(int x, int y)
{
	if (x<0 || x>m || y<0 || y>n) {
		return 0;
	}
	int a, b;
	int tag = 0;
	if (x == 7 && y == 7)
		return 1;
	for (int i = 0; i < 4; i++)
	{
		a = x + Move[i][0];
		b = y + Move[i][1];
		if (maze[a][b] == 0)
		{
			maze[a][b] = 2;
			tag = MAZE(a, b);
			if (tag) {
				cout<<"("<<a+1<<","<<b+1<<")←";
				return 1;
			}
		}
	}
	return 0;
}

void Print(){
	cout<<"迷宫打印如下（0表示通路，1表示障碍）：\n";
	for(int i=0;i<m;i++)
	for(int j=0;j<n;j++){
		cout<<maze[i][j]<<"\t";
		if(j!=0&&j%(n-1)==0) cout<<endl;
	}
	cout<<endl;
}

int main()
{
	Print();
	cout<<"递归求迷宫的通路：\n";
	MAZE(0, 0);
	cout<<"(1,1)←入口\n";
	system("pause");
}

