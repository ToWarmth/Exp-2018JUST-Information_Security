#include <iostream>
using namespace std;

double S(int n)
{ int s=0,l;
  for(int m=1;m<=n;m++)
  { s+=m;
    l=s;
  }
  return l;
}

double P(int n)
{ int s=1,l;
  for(int m=1;m<=n;m++)
  { s*=m;
    l=s;
  }
    return l;
}

double f(int n)
{ double s=0;
  double l;
  for(double m=1;m<=n;m++)
  { s+=1.0/m;
    l=s;
  }
    return l;
}

int main()
{ double n;
  cout<<"Please input n:";
  cin>>n;
  cout<<"S(n)="<<S(n)<<endl;
  cout<<"P(n)="<<P(n)<<endl;
  cout<<"f(n)="<<f(n)<<endl; 
}
