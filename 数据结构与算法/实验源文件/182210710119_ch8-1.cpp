#include <iostream>
using namespace std;

int num;
struct Node {
	int data;
	Node *next;
} *p, *q,*r;

class List {
public:
	 List();
	~List();
	void Print();
private:
	Node *first1;   //first1内存储节点值为空，是为插入方便设置的
	Node *first2;   //first2不是空节点，是建立的无需部分单链表的第一个节点，内有存储数据
};

List::List() {
	int a;
	cout << "请依次输入需要排序的数据\n";
	first1 = new Node;
	first1->next = NULL;
	cin >> a;
	p = new Node;
	p->data = a;
	p->next = NULL;
	first1->next = p;
	cin >> a;
	first2 = new Node;
	first2->data = a;
	first2->next = NULL;
	p = first2;
	for (int i = 3; i <= num; i++) {
		cin >> a;
		q = new Node;
		q->data = a;
		q->next = NULL;
		p->next = q;
		p = p->next;
	}

	while (first2 != NULL) {
		p = first1->next;
		q = first2;
		if (p->data > q->data) {//头插的情况：所插入节点小于有序区第一个节点的值
			first2 = q->next;
			q->next = p;
			first1->next = q;
			p = first1->next;
			q = first2;
		}
		while ( (p->data <= q->data)&&(first2!=NULL)) {
			if ((p->next == NULL) && (p->data <= q->data)) {//尾插判定及尾插操作 
				first2 = q->next;
					q->next = NULL;
					p->next = q;
					break;
			}
			if (p->next->data > q->data) {//中间插判定及中间插操作 
				first2 = q->next;
				r = p->next;
				q->next = r;
				p->next = q;
				p = first1->next;
				q = first2;
				break;
			}
			else p = p->next;
		};
	};
	}

List::~List() {
	while (first1->next != NULL) {
		q = first1->next;
		first1->next = q->next;
		delete q;
	};
	cout << "\n单链表已删除，程序结束!";
}

void List::Print() {
	p = first1;
	do {
		p = p->next;
		cout << p->data<<"\t";
	} while (p->next!=NULL);
}

int main() {
	cout << "请输入您想排序的数的个数:";
	cin >> num;
	List *s = new List();
	s->Print();
	delete s;
}
