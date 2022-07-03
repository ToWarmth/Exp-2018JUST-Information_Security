/*
182210710119 陈四贵 第一次实验  成绩：
*/
/*
实验内容：词法分析
*/



#include <iostream>
#include <string>

using namespace std;

// 关键字表置初始值，序号为类型编码，值为关键字。
string keyword[30] = { "#", "begin", "if", "then", "while", "do", "end", "", "", "",
					  "letter(letter|digit)*", "digitdigit*", "", "+", "-", "*", "/",
					  ":", ":=", "", "<", "<>", "<=", ">", ">=", "=", ";", "(", ")" };

class word {//识别类，它的对象是一个识别出的单位，即是单词、还是关键字。
public:
	int syn{};//类型编码
	string token;//值
};


/*以下这些函数都不是类里面包含的函数*/
// 处理单词的函数
word letterAnalysis(const string &subCode) {
	word item;
	//看此单词是否是如下列出的“关键字”
	if (subCode.substr(0, 5) == "begin") {
		item.syn = 1;
	}
	else if (subCode.substr(0, 2) == "if") {
		item.syn = 2;
	}
	else if (subCode.substr(0, 4) == "then") {
		item.syn = 3;
	}
	else if (subCode.substr(0, 5) == "while") {
		item.syn = 4;
	}
	else if (subCode.substr(0, 2) == "do") {
		item.syn = 5;
	}
	else if (subCode.substr(0, 3) == "end") {
		item.syn = 6;
	}
	else {
		// 如果是其它单词，截取到第一个非字符
		for (int i = 0; i < subCode.length(); ++i) {
			if (!(subCode[i] > 'a' && subCode[i] < 'z')) {
				item.syn = 10;
				keyword[item.syn] = subCode.substr(0, i);
				break;
			}
		}
	}
	item.token = keyword[item.syn];
	return item;
}

// 处理数字的函数
word numberAnalysis(string subCode) {
	word item;
	item.syn = 11;
	for (int i = 0; i < subCode.length(); ++i) {
		// 截取到第一个非数字字符
		if (!(subCode[i] >= '0' && subCode[i] <= '9')) {
			keyword[item.syn] = subCode.substr(0, i);
			break;
		}
	}
	item.token = keyword[item.syn];
	return item;
}

// 处理字符的函数
word charAnalysis(string subCode) {
	word item;
	switch (subCode[0]) {
	case '#':
		item.syn = 0;
		break;
	case '+':
		item.syn = 13;
		break;
	case '-':
		item.syn = 14;
		break;
	case '*':
		item.syn = 15;
		break;
	case '/':
		item.syn = 16;
		break;
	case ':':
		if (subCode[1] == '=') {//即识别出":="运算符
			item.syn = 18;
		}
		else {//即识别出"//"运算符
			item.syn = 17;
		}
		break;
	case '<':
		if (subCode[1] == '>') {//即识别出"<>"
			item.syn = 21;
		}
		else if (subCode[1] == '=') {//即识别出"<="
			item.syn = 22;
		}
		else {//即"<"
			item.syn = 20;
		}
		break;
	case '>':
		if (subCode[1] == '=') {//即识别出">="
			item.syn = 24;
		}
		else {//即识别出">"
			item.syn = 23;
		}
		break;
	case '=':
		item.syn = 25;
		break;
	case ';':
		item.syn = 26;
		break;
	case '(':
		item.syn = 27;
		break;
	case ')':
		item.syn = 28;
		break;
	}
	item.token = keyword[item.syn];
	return item;
}


void scanner(const string &code) {// 词法分析函数。参数为const string型，函数不改变字符串值，也改变不了。
	//遍历读入的字符串
	for (int i = 0; i < code.length(); ++i) {
		word item;
		if (code[i] > 'a' && code[i] < 'z') {
			// 开头是字母，极可能是单词->处理单词
			item = letterAnalysis(code.substr(i, code.length() - i + 1));
		}
		else if (code[i] >= '0' and code[i] <= '9') {
			// 开头是数字，极可能是数字->处理数字
			item = numberAnalysis(code.substr(i, code.length() - i + 1));
		}
		else if (code[i] == ' ') {
			// 如果是空格，直接跳过
			continue;
		}
		else {
			// 处理特殊符号
			item = charAnalysis(code.substr(i, code.length() - i + 1));
		}
		i += int(item.token.length()) - 1;
		cout << "(" << item.syn << "," << item.token << ")" << endl;
	}
}

int main() {
	string code;
	cout << "Please input string:";
	// 读入一行代码，因为代码中有空格，所以要用 getline
	getline(cin, code);
	//调用scanner函数，分析读入的字符串code
	scanner(code);
	return 0;
}
