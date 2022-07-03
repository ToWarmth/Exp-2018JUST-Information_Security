/*
182210710119 陈四贵 第三次实验  成绩：
*/
/*
实验内容：逆波兰式的产生和计算
*/


#include<iostream>
#include<stdio.h>
#include<stdlib.h>
#include<algorithm>
#include<cctype>
#include<cstring>
using namespace std;

char str[50];   //用于存放原来的表达式
int top;    //栈顶指针
char stack[50];     //定义栈，用于计算逆波兰式
char ex[50];    //存放后缀表达式
double _stack[50]; //定义栈，用于计算逆波兰式子
int flag[50];   //用于区分+、-号的含义，0表示运算符，1表示正负号

//生成逆波兰式
void NiBolan()
{
	memset(flag, 0, sizeof(flag));    //flag初始值设为0
	char ch = str[0];
	int i = 1, t = 0;
	top = 0;

	while (ch != '#')
	{
		switch (ch)
		{
		case '(':
			top++;
			stack[top] = ch;
			break;
		case ')':
			while (stack[top] != '(')
			{
				ex[t] = stack[top];
				top--;
				t++;
			}
			top--;
			break;
		case '^':
			while (stack[top] == '^')  //设置^运算符优先级为最高
			{
				ex[t] = stack[top];
				top--;
				t++;
			}
			top++;
			stack[top] = ch;
			break;
		case '+':
		case '-':
			//当ch为+、-号是，若前面相邻字符不是')'或数字且后面相邻字符是数字时表示正负号
			if (isdigit(str[i]) && !isdigit(str[i - 2]) && str[i - 2] != ')')
			{
				flag[t] = 1;  //标记符号为正负号
				ex[t++] = ch;
				ch = str[i++];
				while ((ch >= '0'&&ch <= '9') || ch == '.')  //判别小数点
				{
					ex[t] = ch;
					t++;
					ch = str[i];
					i++;
				}
				i--;
				ex[t] = '&';
				t++;
			}
			else
			{
				while (top != 0 && stack[top] != '(')
				{
					ex[t] = stack[top];
					top--;
					t++;
				}
				top++;
				stack[top] = ch;
			}
			break;
		case '*':
		case '/':
			while (stack[top] == '*' || stack[top] == '/' || stack[top] == '^')    //运算符^优先级高于*和/
			{
				ex[t] = stack[top];
				top--;
				t++;
			}
			top++;
			stack[top] = ch;
			break;
		case ' ':
			break;
		default:
			while ((ch >= '0'&&ch <= '9') || ch == '.')  //判别小数点
			{
				ex[t] = ch;
				t++;
				ch = str[i];
				i++;
			}
			i--;
			ex[t] = '&';
			t++;
		}
		ch = str[i];
		i++;
	}
	while (top != 0)
		if (stack[top] != '(')
		{
			ex[t] = stack[top];
			t++;
			top--;
		}
		else
		{
			printf("error");
			top--;
			exit(0);
		}
	ex[t] = '#';
	ex[t + 1] = '\0';
	printf("后缀表达式：%s\n", ex);

}

//计算逆波兰式
void Calculate()
{
	char ch = ex[0];
	int t = 0;
	top = -1;

	while (ch != '#')
	{
		if (ch == '&') {
			ch = ex[++t];
			continue;
		}
		switch (ch)
		{
		case '+':
			if (flag[t]) //'+'表示正号
			{
				ch = ex[++t];
				double d = 0;
				while (ch >= '0'&&ch <= '9')
				{
					d = 10.0*d + double(ch - '0');
					ch = ex[++t];
				}
				if (ch == '.')     //判断是否为小数
				{
					ch = ex[++t];
					double k = 1.0;
					while (ch >= '0'&&ch <= '9')
					{
						d = d + double(ch - '0') / (10.0*k);
						k = k + 1.0;
						ch = ex[++t];
					}
				}
				top++;
				_stack[top] = d;
			}
			else
			{
				_stack[top - 1] = _stack[top - 1] + _stack[top];
				top--;
				t++;
			}
			break;
		case '-':
			if (flag[t]) //'-'表示负号
			{
				ch = ex[++t];
				double d = 0;
				while (ch >= '0'&&ch <= '9')
				{
					d = 10.0*d + double(ch - '0');
					ch = ex[++t];
				}
				if (ch == '.')
				{
					ch = ex[++t];
					double k = 1.0;
					while (ch >= '0'&&ch <= '9')
					{
						d = d + double(ch - '0') / (10.0*k);
						k = k + 1.0;
						ch = ex[++t];
					}
				}
				top++;
				_stack[top] = -d;
			}
			else
			{
				_stack[top - 1] = _stack[top - 1] - _stack[top];
				top--;
				t++;
			}
			break;
		case '^':   //运算符为'^'
			if (_stack[top] == 0)
			{
				_stack[top - 1] = 1;
			}
			else
			{
				int temp;
				temp = _stack[top - 1];
				while (--_stack[top])
				{
					_stack[top - 1] *= temp;
				}
			}
			top--;
			t++;
			break;
		case '*':
			_stack[top - 1] = _stack[top - 1] * _stack[top];
			top--;
			t++;
			break;
		case '/':
			if (_stack[top] != 0)
				_stack[top - 1] = _stack[top - 1] / _stack[top];
			else
			{
				printf("\n\tchu0error!\n");
				exit(0);
			}
			top--;
			t++;
			break;
		default:
			double d = 0;
			while (ch >= '0'&&ch <= '9')
			{
				d = 10.0*d + double(ch - '0');
				ch = ex[++t];
			}
			if (ch == '.') //判断是否为小数
			{
				ch = ex[++t];
				double k = 1.0;
				while (ch >= '0'&&ch <= '9')
				{
					d = d + double(ch - '0') / (10.0*k);
					k = k + 1.0;
					ch = ex[++t];
				}
			}
			top++;
			_stack[top] = d;
		}
		ch = ex[t];
	}
	cout << "计算结果：" << _stack[top] << endl;
	//printf("计算结果：%lf\n",_stack[top]);
}

int main()
{
	printf("请输入中缀表达式：");
	cin>>str;   //输入原表达式
	printf("原来表达式：%s\n", str);
	NiBolan();  //生成逆波兰式
	Calculate();    //计算逆波兰式
	return 0;
}

//  测试样例               结果
//  21+((42-2)*15+6)-18#    609
//  1+(-5)+-3*-1#           -1
//  1+2^3*2#                17
//  1+-2^3*2#               -15
//  1+(1-3)^3#              -7
//  1*2^0#                  1