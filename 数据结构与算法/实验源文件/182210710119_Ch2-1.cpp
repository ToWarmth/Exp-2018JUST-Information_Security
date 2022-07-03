#include <iostream>
using namespace std;

struct Node {
	int data;   //表示每个节点所存储的数据
	Node *next;   //每个节点的后继，指向下一个节点
};

class SeqList {
public: friend struct Node;
		Node *first;   //单链表中指向头节点的头指针

		SeqList();   //无参构造函数，构造空表
		SeqList(int *num, int n);   //有参构造函数，构造非空单链表
		~SeqList();   //析构函数，起销毁单链表、释放内存空间的作用
		void PrintList();    //遍历链表的函数，作用是遍历链表，并输出
		void AgainList();    //逆置链表的函数
		void Delete(int n);   //删除单个节点的函数
};

SeqList::SeqList() {
	first = new Node;
	first->next = NULL;
}

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
	cout << "\n程序结束，所有节点业已删除";
};

void SeqList::PrintList()
{
	cout << "您所创建或修改的的链表数据依次为:";
	Node *p = first;
	p = p->next;
	while (p != NULL) {
		cout << p->data << "\t";
		p = p->next;
	}
}

void SeqList::AgainList()
{
	Node *p = first->next;
	first->next = NULL;
	while (p != NULL) {
		Node *q = p->next;
		p->next = first->next;
		first->next = p;
		p = q;
	}
	p = first->next;
	cout << "\n逆置后，链表数据依次为：";
	while (p != NULL) {
		cout << p->data << "\t";
		p = p->next;
	}
}

void SeqList::Delete(int n)
{
	int  location;
	cout << "\n请输入您想删除节点的位置（即删除第几个数据）:";
	cin >> location;
	if (location <= 0) throw("位置");
	else {
		while (location > n) {
			cout << "您想要删除的位置超出了链表的范围，请重新输入删除节点的位置:";
			cin >> location;
		}
		Node *p = first;
		for (int counter = 0; counter < location - 1; counter++)
		{
			p = p->next;
		}
		Node *q = p->next;
		p->next = q->next;
		delete q;
		this->PrintList();
	}
}

int main()
{
	int n;   //保存用户想要建立的链表长度
	cout << "请输入您想建立的链表长度(请您不要输入小数，谢谢！):";
	cin >> n;
	while (n < 0){
		cout << "链表长度不能为负数，请您重新输入:";
		cin >> n;
	};

	if (n == 0) {
		new SeqList();
		cout << "您建立了一个空表！";
	}
	else {
		int *num = new int[n]; //动态建立数据数组，保存后存到链表（节点）里
		cout << "请从第一个节点开始依次输入节点内存储的数据值:\n";
		for (int i = 0; i < n; i++)
			cin >> num[i];
		SeqList * a = new SeqList(num, n);
		a->PrintList();
		a->Delete(n);
		a->AgainList();
		delete a;
	}
}
