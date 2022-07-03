#include <iostream>
#include <string.h>
using namespace std;

struct File {//抽象文件
	char name[20];//文件名
	char content[100];//文件内容，供写文件用
	bool opened;//指针标志，指示文件是否打开。1为被打开，0为未打开。
	File *next;
};

class Catalog {//二级文件管理系统
private:
	int num;//用户的个数（主目录个数）
	File *p, *q;//工作指针
	File *Head_MFD;//指向目录的指针

public:
	Catalog() {//构造函数，初始化“邻接表”,用户目录为空（此时没有文件）
		cout << "请输入用户个数：";
		cin >> num;
		if (num <= 0) {
			cout << "输入非法，请重新输入：";
			cin >> num;
		}
		while (num > 5) {
			cout << "用户太多，超出所能承受范围，请重新输入：";
			cin >> num;
		}
		Head_MFD = new File[num];
		for (int i = 0; i < num; i++) {
			Head_MFD[i].name[0] = (char)(i + 1);
			Head_MFD[i].next = NULL;
		}
		cout << "主目录构建成功！" << endl;
	}

	void menu_Control() {//正常工作状态，循环菜单栏
		do {
			cout << "\n*******************************************\n1.建立文件\n2.打开文件\n3.删除文件\n4.关闭文件\n5.读文件\n6.写文件" << endl;
			cout << "请输入您想选择的功能：";
			int direction;
			cin >> direction;
			switch (direction) {
			case 1:file_Build(); break;
			case 2:file_Open(); break;
			case 3:file_Delete(); break;
			case 4:file_Close(); break;
			case 5:file_Read(); break;
			case 6:file_Write(); break;
			}
		} while (1 > 0);
	}

	void file_Build() {//函数·建立文件
		int temp;
		cout << "\n目前有" << num << "个用户，分别是：";
		for (int i = 0; i <= num - 1; i++) {
			cout << "用户" << i + 1 << "\t";
		}
		cout << "\n请选择在哪个用户文件夹里建立文件：";
		cin >> temp;
		while (temp > num || temp <= 0) {
			cout << "输入非法，请重新输入：";
			cin >> temp;
		}
		p = Head_MFD[temp - 1].next;
		q = new File;
		cout << "请输入新建文件名：";
		cin >> q->name;
		cout << "请输入文件内容：";
		cin >> q->content;
		q->next = NULL;
		q->opened = 1;
		if (p == NULL) {//直接插文件
			Head_MFD[temp - 1].next = q;
		}
		else {//文件夹中存在文件的情况，在链尾插
			
			if (!file_Check(p, q->name)) {
				cout << "该目录中已存在该文件。\n";
				return;
			}
			
			while (p->next != NULL) {
				p = p->next;
			}
			p->next = q;
		}
		cout << "文件创建完毕！位于目录" << temp << "中\n";
	}

	void file_Open() {//函数·打开文件
		int choice;
		char name[20];
		cout << "请问你想打开哪个用户文件夹中的文件（输入数字)：";
		cin >> choice;
		if (Head_MFD[choice - 1].next == NULL) {
			cout << "该文件夹中不存在文件。\n";
			return;
		}
		cout << "请问你想打开用户" << choice << "中哪个文件？（输入名字）：";
		cin >> name;
		p = Head_MFD[choice - 1].next;
		while (p != NULL) {
			if (!strcmp(p->name, name)) {
				p->opened = 1;
				break;
			}
			p = p->next;
		}
		if (p == NULL) {
			cout << "未找到该文件，无法打开。\n";
			return;
		}
		cout << "已打开" << p->name << "文件，内容是" << p->content << "！\n";
	}

	void file_Delete() {//函数·删除文件
		int choice;
		char name[20];
		cout << "请问你想删除哪个用户文件夹中的文件（输入数字)：";
		cin >> choice;
		if (Head_MFD[choice - 1].next == NULL) {
			cout << "该文件夹中不存在文件。\n";
			return;
		}
		cout << "请问你想删除用户" << choice << "中哪个文件？（输入名字）：";
		cin >> name;
		p = Head_MFD[choice - 1].next;
		if (!strcmp(p->name, name)) {//第一个位置就是要找的文件
			Head_MFD[choice - 1].next = Head_MFD[choice - 1].next->next;
		}
		else {//要找的文件在后面，或者不存在
			q = p->next;
			while (q != NULL) {
				if (!strcmp(q->name, name)) {
					p->next = q->next;
					break;
				}
			}
			if (q == NULL) {
				cout << "未找到该文件，无法删除。\n";
				return;
			}
		}
		cout << "已删除" << q->name << "文件！\n";
	}

	void file_Close() {//函数·关闭文件
		int choice;
		char name[20];
		cout << "请问你想关闭哪个用户文件夹中的文件（输入数字)：";
		cin >> choice;
		if (Head_MFD[choice - 1].next == NULL) {
			cout << "该文件夹中不存在文件。\n";
			return;
		}
		cout << "请问你想关闭用户" << choice << "中哪个文件？（输入名字）：";
		cin >> name;
		p = Head_MFD[choice - 1].next;
		while (p != NULL) {
			if (!strcmp(p->name, name)) {
				p->opened = 0;
				break;
				}
				p = p->next;
			}
		if (p == NULL) {
			cout << "未找到该文件，无法关闭。\n";
			return;
			}
		cout << "已关闭" << p->name << "文件"  << "！\n";
	}

	void file_Read() {//函数·读文件
		file_Open();
	}

	void file_Write() {//函数·写文件
		int choice;
		char name[20];
		cout << "请问你想修改哪个用户文件夹中的文件（输入数字)：";
		cin >> choice;
		if (Head_MFD[choice - 1].next == NULL) {
			cout << "该文件夹中不存在文件。\n";
			return;
		}
		cout << "请问你想修改用户" << choice << "中哪个文件？（输入名字）：";
		cin >> name;
		p = Head_MFD[choice - 1].next;
		if (!strcmp(p->name, name)) {//第一个就找到文件
			cout << "请您输入修改后的文件名：";
			cin >> p->name;
			cout << "请您输入修改后的文件内容：";
			cin >> p->content;
			p->opened = 1;
		}
		else {//要找的文件不在第一个（在后面的位置或不存在）
			while (p != NULL) {
				if (!strcmp(p->name, name)) {
					cout << "请您输入修改后的文件名：";
					cin >> p->name;
					cout << "请您输入修改后的文件内容：";
					cin >> p->content;
					p->opened = 1;
					break;
				}
				p = p->next;
			}
			if (p == NULL) {
				cout << "未找到该文件，无法打开。\n";
				return;
			}
		}
		cout << "已修改" << p->name << "文件，内容是" << p->content << "！\n";
	}

	bool file_Check(File *l,char s[]) {//函数·创建文件时查重
		while (l != NULL) {
			if (!strcmp(l->name, s)) {
				return false;
			}
			l = l->next;
		}
		return true;
	}
};

int main()
{
	cout << "欢迎来到多用户文件管理系统！\n设计者：陈四贵 仲响 周泽江\n";
	Catalog *test = new Catalog();
	test->menu_Control();
}