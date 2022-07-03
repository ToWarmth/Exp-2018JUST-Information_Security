#include <iostream>
using namespace std;

struct Node {
	char character;
};

class BiTree {
public:
	BiTree(int num, int n);
	~BiTree();
	void PreOrder(int i, int num);
	Node *item;
};

BiTree::BiTree(int num, int n) {
	int top = 0;
	item = new Node[num];
	cout << "\n请按层序依次输入您所建立的完全二叉树的节点所存储的值（值为字符，且尽量不要重复，如若为填补的节点，则输入#）(示例输入：ABC#D#E）：\n";
	for (int i = 0; i < num; i++) {
		cin >> item[i].character;
		if (item[i].character != '#') top++;
		if (top == n) {
			i++;
			do {
				item[i].character = '#';
				i++;
			} while (i < num);
		}
	}
}

BiTree::~BiTree() {
	cout << "\n该树已完全删除，谢谢！程序结束！";
}

void BiTree::PreOrder(int i, int num) {   //前序遍历算法,如若孩子的值为#，则不输出。
	if (item[i].character != '#') cout << item[i].character;
	if (2 * i + 1 < num)  PreOrder(2 * i + 1, num);
	if (2 * i + 2 < num)  PreOrder(2 * i + 2, num);
}

int Power(int n) {
		int s = 1;
		for (int i = 0; i < n; i++)
			s *= 2;
		s -= 1;
		return s;
	}

	int main()
	{
		int n;
		cout << "输入规则：将二叉树补为完全二叉树，按层序编号。依次输入节点内存储的值（存储值为字符），如若节点在二叉树中并不存在，则输入#。达到您建立的原二叉树要求后，后续节点均会初始化为#。整体输入示例如下。\n\n\n";
		cout << "请输入您建立的原二叉树的节点数目（示例输入：5）:";
		cin >> n;
		BiTree *q = new BiTree(Power(n), n);   //把二叉树作为完全二叉树处理，避免存储空间不足
		cout << "前序遍历序列为：";
		q->PreOrder(0,Power(n));   //前序遍历
		delete q;
	}

