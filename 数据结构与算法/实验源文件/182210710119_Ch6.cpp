#include <iostream>
#include <cstring>
#include <queue>
#define MAXVEX 100
#define INFINITY 65535
using namespace std;


int visited[MAXVEX] = { 0 };
int stack[MAXVEX];
queue<int> q;

typedef struct stGraph
{
	char vexs[MAXVEX];//存放图中顶点的数组
	int arc[MAXVEX][MAXVEX];//邻接矩阵
	int VNum, ENum;//顶点数、边数
}MGraph;

void CreatMGraph(MGraph *G)
{
	int i = 0, j = 0, k = 0;
	cout << "请输入顶点数和边数(示例输入6、8)：" << endl;
	cin >> G->VNum >> G->ENum;
	cout << "请输入图的顶点（示例输入0、1、2、3、4、5）：\n";
	for (i = 0; i < G->VNum; i++)
	{
		cin >> G->vexs[i];
	}

	for (i = 0; i < G->VNum; i++)
		for (j = 0; j < G->VNum; j++)
		{
			G->arc[i][j] = INFINITY;
		}

	for (k = 0; k < G->ENum; k++)
	{
		cout << "请输入(vi,vj)上的下标，i,j（示例输入0-1、0-2、0-5、1-2、2-3、2-4、3-4、3-5）：" << endl;
		cin >> i >> j ;
		G->arc[i][j] = 1;
		G->arc[j][i] = 1;
	}
}

void DFSTraverse(MGraph *G, int v)
{
	int top = -1;
	int j;
	cout << G->vexs[v] << " "; visited[v] = 1;
	stack[++top] = v;
	while (top != -1)
	{
		v = stack[top];
		for (j = 0; j < G->VNum; j++)
			if (G->arc[v][j] != INFINITY && visited[j] == 0) {
				cout << G->vexs[j] << " ";
				visited[j] = 1;
				stack[++top] = j;
				break;
			}
		if (j == G->VNum) top--;
	}
}

void BFSTraverse(MGraph *G)
{
	int i, j;
	for (i = 0; i < G->VNum; i++) visited[i] = 0;

	queue<int> q;
	for (i = 0; i < G->VNum; i++)
	{
		if (visited[i] == 0)
		{
			cout << G->vexs[i] << " ";
			visited[i] = 1;
			q.push(i);
			while (!q.empty())
			{
				i = q.front();
				q.pop();
				for (j = 0; j < G->VNum; j++)
					if (G->arc[i][j] != INFINITY && visited[j] == 0) {
						cout << G->vexs[j] << " ";
						visited[j] = 1;
						j = q.front();
					}
			}
		}
	}
}

int main()
{
	MGraph *G = new MGraph;
	int v;
	CreatMGraph(G);
	cout << "请输入要作为源点的顶点的下标值（示例输入：0）：" ;
	cin>>v;
	cout << "\n该图的深度优先遍历顺序为:" << endl;
	DFSTraverse(G, v);
	cout << endl;
	cout << "该图的广度优先遍历顺序为：" << endl;
	BFSTraverse(G);
	return 0;
}
