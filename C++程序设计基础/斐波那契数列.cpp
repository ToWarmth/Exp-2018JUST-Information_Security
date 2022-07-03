#include<iostream>
using namespace std;
long long fibonacci(long long  n)
{   if(n==0||n==1) return n;
    long long *p = new long long[n+1];
    p[0] = 0;
    p[1] = 1;
    for(int i = 2;i < n+1; i++)
       p[i] = p[i-1]+p[i-2];
    return p[n];
}

int main()
{   long n,*p;
    cout<<"Please input n:";
    cin>>n;
  cout<<"第"<<n<<"项的斐波那契数列值为："<<fibonacci(n);
}
