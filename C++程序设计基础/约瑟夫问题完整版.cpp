#include<iostream>
using namespace std;
struct Node    //Node中文意思为节点
{ int data;    //储存数据 ，所以名字叫data。data里存储的数据就是节点的序数。eg:p->data==2，则说明该节点为第2个节点。这是一种抽象 
  Node*next;    //指向下一个节点的指针，所以指针叫next
};
Node *head=NULL;    
int n,i,m;       //定义全局变量性质的量，以使得所有函数都能使用同一个数据，避免乱套 

/*下列函数不使用参数比使用参数更加简便，即没用形参，直接使用实际参数。由于使用实际参数，很多函数没必要设返回值产生临时变量，减小程序开销*/ 
void CreatLoop()   //创建约瑟夫环，有几个数据就有几个节点。其中倒数第一个数据是第n个节点。
{ Node*p=new Node;
  p->data=1;
  p->next=NULL;   //创建第一个节点，此时data指的是环的第几个数据成员，即第几个人 
  head=p;        //保存头指针，以保证最后一个节点能指向这个节点（固定位置）   
/*依次创建之后的节点2,3,4,5,...,n，再把它们连接起来*/
  for (int k=2;k<=n;k++) 
  { Node *q=new Node;
    q->data=k;
	q->next=NULL;
	p->next=q;  //当前节点变成p
	p=q;
  }
	p->next=head;  //首尾连接 
};

Node *Start()  //确定指针从哪个节点开始循环，即决定从第几个人开始报数
{   if (i==1)  return head;  //若从第一个人开始报数，则不需要确定指针从哪个节点开始遍历（已知） 
	Node *p=head;
	for (int k=2;k<=i;k++) 
	{p=p->next;}   //不断循环，当i就停止循环 （节点的data步长为1，这里的k就是data的缩影）。目的是找到data为i的那个节点 
	return p;     //产生一个临时变量，使得下次循环中的指针从此开始 
}

void Count(Node*&p, Node*&q)  //报数中 
/*关于报数 ：实质是找到q->data==m+i（报到数m）的数据，然后把它拎出来，即所谓的删除节点。按我们的习惯，一般删除的是节点指针q所指向的节点
若m==1，p所指向的节点是第i个节点，且p->data==i，我们需要令q->data==i，再删除它。再双指针遍历，依次删除这些指针所指向的节点 
若m==2，p所指向的节点是第i个节点，且p->data==i，q->data=i+1 (默认q=p->next)，也就是说，直接删除q所指向的节点即可。再通过双指针遍历 
若m>=3，p所指向的节点是第i个节点，且p->data==i，我们需要循环到q->data==i+m，再删除指针
需要注意的是，需要给程序一个删除到最后一个节点的解决方法，否则最后一个数将无法报出来*/ 
{   if (m==1)       //m==1的情况 
   {q=p;         //q赋为p，即将节点指针q前移，以达到删除指针q所指向的节点的目的。但是，注意：这个函数并没有删除节点 
	while (true) 
	  {if (p->next==q) break;   //注意，这是在q=p的前提下p->next==q。换句话说就是p->next==p。也就是说，只剩下一个当前的节点指针p所指向的节点时，强行终止该循环 
		p=p->next;      //找到了q->data==i的节点后，一次又一次地循环，直到只剩一个节点时，终止循环 
	  }  
	  return;
   }
	q=p->next;         //准备启用双指针遍历。注意：无论前方m是否等于1，该语句都要执行 
	for(int k=3;k<=m;k++) 
	{p=q;
	 q=p->next;      //这里的k相当于只是一个中间性质的量，它的作用只是用来找到i>=3时，报数为m的节点 
	}
}

void Josephus() 
{ CreatLoop();   //创建约瑟夫环 
  Node *p=Start();   //将Start函数的返回值赋给结点指针p 
  Node *q=p->next;    //准备双指针遍历 
  while (p!=NULL)     //若指针p存在，即所谓的还存在节点时，一直重复该循环 
      {if (p==q)       //只剩下一个节点的情况 
	      {cout<<q->data<<"   ";    //将报的最后一个数显示出来 
	       delete q;            // 删除最后一个报出的节点 
	       p=q=NULL;            //删除节点后，对双指针赋空值 
	       return;              //习惯性返回 
          }
  Count(p,q);      //m==1和m>2的情况 
  p->next=q->next;    //相当于把p赋为q 
  cout<<q->data<<"   ";      //输出所报的数。注意：这在循环里面 
  delete q;
  p=p->next;
  q=p->next;   
	  }
}

int main() 
{cout<<"请输入参与约瑟夫游戏的人数:";
 cin>>n;
 cout<<endl<<"请决定从第几个人开始报数:";
 cin>>i;
 cout<<endl<<"请决定报到哪个数的人离场:";
 cin>>m;
 Josephus();
 system("pause");
 return 0;
}
