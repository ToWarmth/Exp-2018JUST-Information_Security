clc;
clear all;
msgfid = fopen( 'hidden.txt' , 'r');%打开秘密文件

[msg,count] = fread(msgfid);
fclose(msgfid);
msg = str2bit(msg);
msg = msg';
count = count * 8;
io = imread('PIC1.jpg');%读入载体图像
subplot(2,2,1)     
imshow( io );
io=rgb2gray(io);     %将对象x进行灰度处理
subplot(2,2,2)     %两行两列布置中，第二张图
imshow(io);         %输出灰度图像
io=imbinarize(io,0.65); %再转为二值图像，二值化阈值为0.65
subplot(2,2,3)     %两行两列布置中，第三张图
imshow(io)        %输出黑白二值图像
watermarklen = count;%嵌入水印信息长度,也就是载体图像分块的数量值
[row,col] = size(io);
l1=floor(row/watermarklen);%载体图像分块后的长度
l2= floor(col/watermarklen);%载体图像分块后的宽度
pixelcount = l1* l2;%每个分块总像素的数量值
percent = ceil(pixelcount/2);
iw = io;
ioblack(1,watermarklen)=0;%某一个分块中黑色像素的个数
iowhite(1,watermarklen)=0;%某一个分块中白色像素的个数
n= 1;
while n<=watermarklen
for i = l1* (n -1)+1:l1* n
for j = l2* (n - 1)+1:l2* n
if io( i ,j )==0
ioblack(1, n)= ioblack(1 , n)+1;%计算每个分块中的黑色像素的个数
else
iowhite(1 ,n)= iowhite(1,n)+1;%计算每个分块中的白色像素的个数
end
end
end
n = n +1;
end
n = 1;
while n<=watermarklen
if msg(n, 1) ==1 %需要嵌入1
if ioblack(1,n)>=percent %嵌入1的时候,黑色像素比白色像素多,需要修改一些像素的颜色
modcount(1,n)=ioblack(1,n)-percent + 1;
k = 1;
for i = l1* (n-1 )+1:l1* n
for j = l2* (n -1)+1:l2* n
if (iw( i ,j) ==0&&k <= modcount(1,n))
iw( i , j )=1;k =k+1;
end
end
end
end
else
if iowhite(1,n) >=percent %嵌入0的时候,白色像素比罴色像素多,需要修改一些像素的颜色
modcount (1,n) = iowhite(1,n) - percent + 1;
k = 1;
for i = l1* (n -1)+1:l1* n
for j = l2* (n-1)+1:l2* n
if (iw(i , j )== 1&&k <= modcount(1,n))
iw( i , j)=0;k =k +1;
end
end
end
end
end
n=n + 1;
end
n=1;

iwblack(1 ,watermarklen)=0;%某一个分块中黑色像素的个数
iwwhite( 1,watermarklen)=0;%某一个分块中白色像素的个数
while n <= watermarklen
for i = l1* (n -1)+ 1:l1* n
for j = l2* (n-1)+1:l2* n
if iw( i , j) ==0
iwblack(1,n) = iwblack(1,n)+1;%计算每个分块中的黑色像素的个数
else
iwwhite(1,n)= iwwhite(1,n)+1;%计算每个分块中的白色像素的个数
end
end
end
n=n + 1;
end

imwrite(iw,'huntermarked.bmp' );
%figure;
subplot(2,2,4)
imshow( 'huntermarked.bmp' );