#include <iostream>
using namespace std;

struct Node {
	int data;
	Node *next;
};

class Queue {
public: friend Node;
		Node *rear;
		Queue(int *que, int n);   //创建队列
		~Queue();   //删除队列所有数据元素方法
		void Into();   //入队方法
		void Away();   //出队方法
		void Print();   //打印函数
};

Queue::Queue(int *que, int n) {
	Node *s=new Node;
	s->data = que[0];
	s->next = NULL;
	Node *q = s;
	for (int i = 1; i <= n - 1; i++) {
		Node *p = new Node;
		p->data = que[i];
		p->next = NULL;
		q->next = p;
		q = p;
	}
	rear = q;
	rear->next = s;
}

Queue::~Queue(){
	cout << "\n您好，队列所有数据元素均已删除！";
}

void Queue::Into() {
	Node *p = new Node;
	cout << "\n请输入入队元素的值:";
	cin >> p->data;
	p->next = rear->next;
	rear->next = p;
	rear = p;
}

void Queue::Away() {
	Node *p = rear->next;
	rear->next = p->next;
	delete p;
}

void Queue::Print() {
	cout << "当前队列，从队头排到队尾应为：";
	Node *temporary = rear->next;
	do{
		cout << temporary->data<<'\t';
		temporary = temporary->next;
	}while (temporary != rear->next);
}

int main()
{
	int n;
	cout << "请输入您想建立的初始队列的长度:";
	cin >> n;
	while (n <= 0) {
		cout << "您不能建立空队列或负队列，这毫无意义，请重新输入：";
		cin >> n;
	};
	int *num = new int[n];
	cout << "请依次输入您所创建的初始队列所存储的数据（从队头到队尾）：\n";
	for (int i = 0; i <= n - 1; i++)
		cin >> num[i];
	Queue *test = new Queue(num, n);
	test->Print();
	test->Into();
	test->Print();
	test->Away();
	cout << endl<<"队头元素出队后：";
	test->Print();
	delete test;
}
