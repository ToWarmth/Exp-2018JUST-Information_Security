#include <iostream>
#include <cmath>
using namespace std;
template <typename T>
class Complex{
private:
	T real,img;
public:
	Complex(T r=0,T i=0){
		real=r;
		img=i;
	}
	Complex operator+(Complex &c){
		Complex ret;
		ret.real=real+c.real;
		ret.img=img+c.img;
		return ret;
	}
	Complex operator=(const Complex &c){
		real=c.real;
		img=c.img;
		return *this;
	}
	Complex operator*(const Complex &c){
		Complex ret;
		ret.real=real*c.real-img*c.img;
		ret.img=img*c.real+c.img*real;
		return ret;
	}
	Complex operator/(const Complex &c){
		Complex ret;
		double r=sqrt(c.real*c.real+c.img*c.img);
		ret.real=(real*c.real+img*c.img)/r;
		ret.img=(-img*c.real+c.img*real)/r;
		return ret;
	}
	Complex operator-(const Complex &c){
		Complex ret;
		ret.real=real-c.real;
		ret.img=img-c.img;
		return ret;
	}

	void print(){
		cout<<real<<","<<img<<endl;
	}
};

int main(){
	Complex<double> c1(2,3),c2(4,5),c3(0,0),c4(0,0),c5(0,0),c6(0,0);
	c3=c1+c2;
	c3.print();
	c4=c1-c2;
	c4.print();
	c5=c1*c2;
	c5.print();
	c6=c1/c2;
	c6.print();

	system("pause");
	return 0;
}
