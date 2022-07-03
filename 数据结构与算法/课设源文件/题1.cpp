#include <iostream>
using namespace std;

typedef struct Node
{
	int data;
	struct Node *next;
}*ListNode;

typedef ListNode *LinkList;
ListNode head, p, q;

void CreateList(int *a,int n) {
	head = new Node;
	head->next = NULL;
	p = new Node;
	head->next = p;
	p->data = a[0];
	p->next = NULL;
	for (int i = 1; i < n; i++) {
		q = new Node;
		q->data = a[i];
		q->next = NULL;
		p->next = q;
		p = q;
	}
}

void PrintList() {
	p = head->next;
	cout << "单链表所有数据依次为：";
	while (p != NULL) {
		cout << p->data << "\t";
		p = p->next;
	}
	cout << "\n";
}

void InsertList(int location, int value) {
	p = head;
	int counter = 0;
	while (counter != location - 1) {
		p = p->next;
		counter++;
	}
	q = new Node;
	q->data = value;
	q->next = p->next;
	p->next = q;
	p = head->next;
	cout << "在第" << location << "个位置插入" << value << "后，所有数据依次为：";
	while (p != NULL) {
		cout << p->data << "\t";
		p = p->next;
	}
	cout << "\n";
}

void DeleteList(int location) {
	p = head;
	int counter = 0;
	while ((counter != location - 1) && p != NULL) {
		p = p->next;
		counter++;
	}
	p->next = p->next->next;
	p = head->next;
	cout << "删除第" << location << "个位置的节点后，单链表数据为：";
	while (p != NULL) {
		cout << p->data << "\t";
		p = p->next;
	}
}

int main() {
	int n,location;
	cout << "请输入单链表中数据的个数:";
	cin >> n;
	int *a = new int[n];
	cout << "请依次输入单链表中的数据:";
	for (int i = 0; i < n; i++) cin >> a[i];
	CreateList(a,n);
	PrintList();
	cout << "请输入您想插入数据元素的位置、值:";
	cin >> location >> n;
	InsertList(location,n );
	cout << "请输入您想删除数据元素的位置:";
	cin >> location;
	DeleteList(location);
}
