#include <iostream>
using namespace std;

struct Node {
	int data;   //表示每个节点所存储的数据
	Node *next;   //每个节点的后继，指向下一个节点
};

class SeqList {
public: friend struct Node;
		Node *first;   //单链表中指向头节点的头指针

		SeqList(int *num, int n);   
		~SeqList();   //析构函数，起销毁单链表、释放内存空间的作用
		void PrintList();    //遍历链表的函数，作用是遍历链表，并输出
		void Delete(int n);   //删除单个节点的函数
};

SeqList::SeqList(int *num, int n) {
	first = new Node;
	Node *p = first;
	for (int i = 0; i < n; i++)
	{
		Node *s = new Node;
		s->data = num[i];
		p->next = s;
		p = s;
	}
	p->next = NULL;
}

SeqList::~SeqList() {
	while (first != NULL) {
		Node *q = first;
		first = first->next;
		delete q;
	}
	cout << "程序结束，所有节点业已删除";
};

void SeqList::PrintList()
{
	cout << "从第一个节点开始节点内存储的数据值为:";
	Node *p = first;
	p = p->next;
	while (p != NULL) {
		cout << p->data << "\t";
		p = p->next;
	}
}

void SeqList::Delete(int n)
{
		Node *p = first->next;
		while (p->next != NULL){
			if (p->data == p->next->data){
			Node *q = p->next;
			p->next = q->next;
			delete q; 
			}
			else {
				p = p->next;   //若节点存储的有序数据都不同，则仍使节点跳转，直至遍历完跳出循环
			}
		}
		cout << endl<<"删除值相同的节点后,";
		this->PrintList();
}

int main()
{
	int n;   ////保存用户想要建立的链表长度
	cout << "请输入您想要建立的链表长度:";
	cin >> n;
	int *num=new int[n];   
	cout << "请依次输入您所建立链表的所存数据（务必输入非递减有序数列）:\n";
	for (int i = 0; i < n; i++)
		cin >> num[i];
	SeqList *a = new SeqList(num, n);
	a->PrintList();
	a->Delete(n);
	cout << endl;
	delete a;
}
