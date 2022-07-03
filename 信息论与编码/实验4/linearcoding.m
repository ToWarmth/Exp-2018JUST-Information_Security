% Purpose:
% Liner encoding and decoding
function linearcoding
% Define variables:
% G 生成矩阵
% u 编码输出
% input_nk 输入序列
% input_C 纠错输出码序列
% R 信道输出码
% H 校验矩阵
% e 差错图案
% s 伴随式
 %信道编码
 input_nk=input('enter the symbol:');
 G=input('input G:');
 [c,d]=size(G);
 O=G(:,1:c);
 Q=inv(O);
 G=Q*G;
 G=abs(rem(G,2)); %
 P=G(:,c+1:d);
 a=d;
 b=c;
 if(rem(length(input_nk),b)~=0)
 input_nk=[input_nk,zeros(1,b-rem(length(input_nk),b))];
 end
 n=length(input_nk)/b;
 u=zeros(1,n);
 j=1;
 for i=1:b:n*b
 for p=1:1:a
 sum=0; 
 for q=1:1:b
 sum=input_nk(1,q+i-1)*G(q,p)+sum;
 end
 u(j)=rem(sum,2);
 j=j+1;
 end
 end
 %信道译码
 R=input('input R:');
 H=[P',eye(a-b)] ;F=H';
 if(rem(length(R),a)~=0)
 R=[R,zeros(1,a-rem(length(R),a))];
 end
 n=length(R)/a;
 s=zeros(1,n*(a-b));
 q=1;
 for i=1:a:n*a
 for j=1:1:(a-b)
 sum=0;
 for k=1:1:a
 sum=R(1,k+i-1)*F(k,j)+sum;
 end
 s(q)=rem(sum,2);
 q=q+1;
 end
 end
 e=zeros(1,length(R));
 l=zeros(1,n*(a-b));
 z=1;
 for k=1:(a-b):n*(a-b)
 for i=1:1:a
 e(1,z)=1;
 for j=1:1:(a-b)
 if(F(i,j)==1)l(j)=s(j+k-1);
 else l(j)=1-s(j+k-1);end;
 e(1,z)=l(j)*e(1,z);
 end
 z=z+1;
 end
 end
 for v=1:1:length(R)
 input_C(1,v)=rem(R(1,v)+e(1,v),2);
 end
 %输出结果
 disp('the output code:');
 disp(u);
 disp('the corrected code:');
 disp(input_C);
