clc;
clear all;
wi = imread('huntermarked.bmp');%读入载体图像
[row col] =size(wi);
watermarklen =24;%嵌入的水印信息的长度
l1 = floor(row/watermarklen);%载体图像分块后的长度
l2 = floor(col/watermarklen);%载体图像分块后的宽度
pixelblack(1,watermarklen)=0;%某一个分块中黑色像素的个数
pixelwhite(1,watermarklen)=0;%某一个分块中白色像素的个数
n = 1;
while n <=watermarklen
for i = l1* (n -1)+1:l1* n
for j = l2* (n -1)+1:l2* n
if wi(i , j ) ==0
pixelblack(1,n) = pixelblack(1,n)+1;%计算每个分块中的黑色像素的个数
else
pixelwhite(1,n) = pixelwhite(1,n)+1;%计算每个分块中的白色像素的个数
end
end
end
n = n + 1;
end
n =1;
while n<= watermarklen
if pixelwhite(1,n) >pixelblack(1,n)%如果白色像素块多于黑色像素块,秘密信息为1
message(n,1)= 1;
else
message(n,1)=0;%如果黑色像素块多于白色像素块,秘密信息为0
end
n =n +1;
end
% msgfid = fopen( 'hidden.txt' , 'r');
% [ msg , count ] = fread(msgfid);
% fclose(msgfid);
% msg = str2bit(msg);
% message= msg';
message
%将提取的秘密信息转换成字符串
out = bit2str(message);
fid = fopen('message.txt' , 'wt');
fwrite(fid,out);
fclose(fid);
