#include <iostream>
using namespace std;

typedef struct Node
{
	int id, key;
	struct Node *next;
}*ListNode;

typedef ListNode *LinkList;
ListNode head, feet, p, q;

void CreateList(int n, int *a) {
	head = new Node;
	head->id = 1;
	head->key = a[0];
	head->next = NULL;
	p = head;
	for (int i = 2; i <= n; i++) {
		q = new Node;
		q->id = i;
		q->key = a[i - 1];
		q->next = NULL;
		p->next = q;
		p = q;
	}
	p->next = head;
	feet = p;
}

void Johnsphus(int m, int n) {
	while (1 > 0) {
		p = head;
		q = feet;
		for (int num = 1; num < m; num++) {//找报到数m的那个节点
			q = q->next;
			p = p->next;
		}
		if (p->next == q) {
			cout << p->id << "\t";
			q->next=q; 
			cout << q->id;
			delete q;
			break;
		}
		cout << p->id << "\t";
		m = p->key;
		q->next = p->next;
		p = q->next;
		head = p;
		feet = q;
	}
}

void PrintList() {
	p = head;
	cout << "游戏者的序号\t对应密码\n";
	do {
		cout << p->id << "\t\t" << p->key << "\n";
		p = p->next;
	} while (p != head);
	cout << "\n";
}

int main() {
	int n, m;
	cout << "请输入玩约瑟夫游戏的人数：";
	cin >> n;
	int *a = new int[n];
	cout << "请依次输入每个人所设置的密码:\n";
	for (int i = 0; i <= n - 1; i++) {
		cin >> a[i];
	}
	CreateList(n, a);
	PrintList();
	cout << "请指示一个正整数作为报数上线值:";
	cin >> m;
	cout << "出列序列为：";
	Johnsphus(m, n);
}
