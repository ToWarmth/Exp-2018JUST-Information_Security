function [c,Pa]=Compute_Channel_Capacity(P)
 [m,n]=size(P);
 Error_Tor=1*10^(-2);
 if(min(size(P))<=1)
 error('输入的信道转移矩阵太小或出错，请检查');
 end
 if(nargin<2)
 Error_Tor=1*10^(-2); 
 end
 [NumOfIn,NumOfOut]=size(P); 
 Pa_Temper=ones(1,NumOfIn)*(1/NumOfIn); 
 Channel_Cap_Temper=1+Error_Tor; 
 Channel_Cap_AUX=0; 
 Num_OF_Cyc=0; 
 while abs(Channel_Cap_Temper-Channel_Cap_AUX)>=Error_Tor
 Pb_Temper=Pa_Temper*P; 
 for i=1:NumOfIn
 Alaph(i)=2^(sum(P(i,:).*log2(P(i,:)./Pb_Temper+eps))); 
 end
 PA_Temper=Pa_Temper.*Alaph; 
 Channel_Cap_Temper=log2(sum(PA_Temper)+eps); 
 Channel_Cap_AUX=log2(max(Alaph)+eps); 
 Pa_Temper=PA_Temper/sum(PA_Temper); 
 Num_OF_Cyc=Num_OF_Cyc+1; 
 end
 c=Channel_Cap_Temper; 
 Pa=Pa_Temper;