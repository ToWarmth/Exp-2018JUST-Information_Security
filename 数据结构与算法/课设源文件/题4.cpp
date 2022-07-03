#include <iostream>
using namespace std;

const int n = 2;   //n为停车场上限
const int m = 5;
int a, c, key1 = 0, key2 = 0, key3 = 0, i = 0, Percost = 3, LastExit;
char b, d;

struct Cars {
	int ID;
	char State;
	int Time;
	Cars *next;
}*p, *q, *r, *head, *near;

Cars *Stack1[n], *Stack2[m];  

void parkingmode() {
	head = new Cars;
	cout << "请按时间先后顺序输入汽车的状态、汽车牌照、汽车进行该状态的时刻（格式：A 1 1）：";
	cin >> b >> a >> c;
	head->ID = a;
	head->State = b;
	head->Time = c;
	p = head;
	Stack1[0] = head;
	key1++;
	cout << "牌照为" << Stack1[key1 - 1]->ID << "的车辆停在停车场从北往南第" << key1 << "个位置。\n";
	do {
		i = 0;
		cout << "请按时间先后顺序输入汽车的状态、汽车牌照、汽车进行该状态的时刻（格式：A 1 1）：";
		cin >> b >> a >> c;
		if (b == 'E') {
			cout << "程序结束";
			break;
		}
		if (b == 'A') {
			q = new Cars;
			q->ID = a;
			q->State = b;
			q->Time = c;
			if (key1 - 1 < n - 1) { //停车场还未装满
				key1++;
				Stack1[key1 - 1] = q;
				for (int j = 0; j <= key1 - 1; j++)
					cout << "牌照为" << Stack1[j]->ID << "的车辆停在停车场从北往南第" << j + 1 << "个位置。\n";
			}
			else {  //停车场已满
				if (key3 == 0) { //过道没车在排队
					head = q;
					head->next = NULL;
					r = head;
					key3++;
				}
				else {   //过道有车在排队
					q->next = NULL;
					r->next = q;   // 若程序出bug，可能是这里的问题
					key3++;
				}
				for (int j = 0; j <= key1 - 1; j++)
					cout << "牌照为" << Stack1[j]->ID << "的车辆停在停车场从北往南第" << j + 1 << "个位置。\n";
				cout << "牌照为" << q->ID << "的车辆停在便道第" << key3 << "个位置。\n";
			}
		}
		if (b == 'D') {
			while (Stack1[i]->ID != a) {
				i++;
			}
			for (int j = key1 - 1; j > i; j--) {//Stack2的栈顶是下标大的那端，后面的车（靠门近）先退回来
				Stack2[key2++] = Stack1[j];
				Stack1[j] = NULL;
			}
			cout << "牌照为" << Stack1[i]->ID << "的车辆停留了" << (c - Stack1[i]->Time) << "小时，应收费" << (c - Stack1[i]->Time)*Percost << "元。\n";
			for (int j = key2 - 1, tmp = i; j >= i; j--) {//Stack2的车辆依次出栈，移到Stack1停车场内
				Stack1[tmp++] = Stack2[j];
				Stack2[j] = NULL;
				key2--;
			}
			key1--;
			//就算没新车进来，以前在排队的车也要进来
			if (key3 == 0) { //过道没车在排队

			}
			else {   //过道有车在排队
				LastExit = c;
				key1++;
				Stack1[key1 - 1] = head;
				Stack1[key1 - 1]->Time = LastExit;
				head = head->next;
				key3--;
			}
		}
	} while (1 > 0);
}

int main() {
	parkingmode();
}
