#include <iostream>
using namespace std;

int *p,n,m;   //n为顺序表的长度，m为想要查找的数据。

void CreatList() {//建立一个顺序表
	cout << "请输入您想要建立的顺序表中存储的数据的个数:";
	cin >> n;
	p = new int[n+1];
	cout << "请依次输入您的顺序表中存储的数据：\n";
	for (int i = 0; i <= n-1; i++) 
		cin >> p[i];
	cout << "顺序表已建立！";
}

void OrderSearch() {//顺序表的顺序查找算法
	int k;
	cout << "请输入您想查找的数据：";
	cin >> k;
	p[n] = k;
	int i = 0;
	while (p[i] != k)
		i++;
	if (i != n ) cout << "查找成功，该数在顺序表第" << i + 1 << "位，数组下标是"<<i<<"!";
	else cout << "查找失败";
}

int main()
{  
	CreatList();
	OrderSearch();
}
