#include <iostream>
using namespace std;

int n, m;

struct BiSortTreeNode {
	int data;
	BiSortTreeNode *lchild;
	BiSortTreeNode *rchild;
}*first,*p,*q,*r;

void seekLocation() {
	if (m < (p->data)) {
		if (p->lchild != NULL)   p = p->lchild;
	    else { p->lchild = q; }
		seekLocation();
	}
	if (m > (p->data)) {
		if (p->rchild != NULL) p = p->rchild;
		else { p->rchild = q; }
		seekLocation();
		}
	}

void CreatTree() {
	cout << "您想排序的数据有多少个？请输入：";
	cin >> n;
	cout << "请您依次输入您想排序的数据:";
	cin >> m;
	p = new BiSortTreeNode();
	p->data = m;
	p->lchild = NULL;
	p->rchild = NULL;
	first = p;
	r = p;
	int i = 1;
	do {
		cin >> m;
		q = new BiSortTreeNode();
		q->data = m;
		seekLocation();
		i++;
		p = r;
	} while (i != n);
}

void PreOrder(BiSortTreeNode *p) {
	if (p == NULL) return;
	else {
		cout << p->data << "\t";
		PreOrder(p->lchild);
		PreOrder(p->rchild);
	}
}

void Order(BiSortTreeNode *p) {
	if (p == NULL) return;
	else {
		Order(p->lchild);
		cout << p->data << "\t";
		Order(p->rchild);
	}
}

int main() {
	CreatTree();
	cout << "前序遍历该二叉树，结果为:";
	PreOrder(first);
	cout << "\n中序遍历该二叉排序树，结果为：";
	Order(first);
}
