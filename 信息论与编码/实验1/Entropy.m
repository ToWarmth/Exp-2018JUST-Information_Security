function [H] = Entropy(p)
 n = length(p);
 H =0;
 for i =1: n
 I(i)=-log2(p(i)) ;
 H = H + p(i)*I(i);
 end