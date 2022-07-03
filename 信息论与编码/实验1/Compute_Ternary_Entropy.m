%Ternary source
function [H] = Compute_Ternary_Entropy()
 p=0:0.001:1;
 n=length(p);
 p2=repmat(p,n,1);
 p1=p2';
 p3=1-p1-p2;
 p1(p3<0)=0;
 p2(p3<0)=0;
 p3(p3<0)=0;
 H=(-p1.*log2(p1)-p2.*log2(p2)-p3.*log(p3));
 H(isnan(H))=0;
 mesh(p1,p2,H);