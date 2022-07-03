#include <iostream>
using namespace std;
void Move(int n,char a,char b,char c)
  {if (n==1)
     cout<<a<<"-->"<<c<<endl;
     else
         {Move(n-1,a,c,b);
          cout<<a<<"-->"<<c<<endl;
          Move(n-1,b,a,c);
		 }
  }
  
  int main()
  { int m;
    cout<<"Please input the number of disks:";
    cin>>m;
    Move(m,'A','B','C');
  }
