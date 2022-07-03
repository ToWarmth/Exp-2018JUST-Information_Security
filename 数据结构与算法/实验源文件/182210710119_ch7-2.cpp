#include <iostream>
using namespace std;

int n, m;

struct BiSortTreeNode{
	int data;
	BiSortTreeNode *lchild;
	BiSortTreeNode *rchild;
}; BiSortTreeNode *first;

void CreatTree(){
	cout << "您想排序的数据有多少个？请输入：";
	cin >> n;
	cout << "请您依次输入您想排序的数据:\n";
	cin >> m;
	BiSortTreeNode *p= new BiSortTreeNode();
	p->data = m;
	p->lchild = NULL;
	p->rchild = NULL;
	first = p;
	BiSortTreeNode *r = p;
	int i=1;
	do{
		cin >> m;
		BiSortTreeNode *q = new BiSortTreeNode();
		q->data = m;
		while (m < (p->data)){
			if (p->lchild != NULL)   p = p->lchild;
			else { p->lchild = q; }
			if (m > (p->data)) {
				if (p->rchild != NULL) p = p->rchild;
				else { p->rchild = q; }
			}
		};
		while (m > (p->data)) {
			if (p->rchild != NULL) p = p->rchild;
			else { p->rchild = q; }
			if (m < (p->data)) {
				if (p->lchild != NULL)   p = p->lchild;
				else { p->lchild = q; }
			}
		};		
		i++;
		p = r;
	}while (i != n );
}

void Sort(BiSortTreeNode *p) {
	if (p == NULL) return;
	else {
		Sort(p->lchild);
		cout << p->data<<"\t";
		Sort(p->rchild);
	}
}

void SearchLayer() {
	int Searching, Layer = 1;
	cout << "\n请输入您想查找的数据:";
	cin >> Searching;
	BiSortTreeNode *p = first;
	if (Searching == p->data) cout << "该数据在原序所在层数为第" << Layer << "层";
	else {
		while (p->data != Searching) {
			while (Searching < p->data) {
				p = p->lchild;
				Layer++;
				if ((p->lchild == NULL) && (p->data != Searching)) {
					cout << "很遗憾，未能找到该数据。";
					return;
				}
			};
			while (Searching > p->data) {
				p = p->rchild;
				Layer++;
				if ((p->rchild == NULL) && (p->data != Searching)) {
					cout << "很遗憾，未能找到该数据。";
					return;
				}
			}
		};
		cout << "该数据在原序所在层数为第" << Layer << "层";
	}
}

int main(){
	CreatTree();
	cout << "数据为从小到大排序为：";
	Sort(first);
	SearchLayer();
}
