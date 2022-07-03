clc;
clear all;
msgfid = fopen( 'hidden.txt ' , 'r');%打开秘密文件
[ msg,count ] = fread(msgfid);
fclose(msgfid);
msg = dec2bin(msg)
str='';
for i=1:4
    str=strcat(str,dec2bin(msg(i)));
end
msg = str;
count = count* 8;
io = imread( 'PIC1.jpg');%读入载体图像
watermarklen = count;%嵌入水印信息长度,也就是载体图像分块的数量值
[ row col ] =size(io);
l1 = floor(row/watermarklen);%载体图像分块后的长度
l2 = f1oor(col/watermarklen);%载体图像分块后的宽度
pixelcount = l1*12;%每个分块总像素的数量值
percent = ceil(pixelcount/2 );
iw = io;
ioblack( 1 ,watermarklen)=0;%某一个分块中黑色像素的个数
iowhite(1,watermarklen) =0;%某一个分块中白色像素的个数
n = 1;
while n <= watermarklen
for i = l1* (n -1)+1:11* n
    for j = 12* (n -1)+1:12* n
        if io(i ,j)==0
ioblack(1 ,n) = ioblack(1,n)+1;%计算每个分块中的黑色像素的个数
        else
            iowhite(l,m) =iowhite(1 ,n)+1;%计算每个分块中的台色像素的个骶end
        end
    end
    end
    n=n+1;
end
    n =1;
while n <= watermarklen
if msg(m,1)==1%需要嵌入1
if ioblack(1,n)>= percent ;%嵌入1的时候,黑色僳素比白色像素多,需要修改一些像素的颜色
modcount(l ,m)= ioblack(ll.n) - percent +l;
k =1;
for i=l1* (n-1)+1:ll*n
for j=l2* (n-1)+1:12*n
if (iw( i ,j ) == 1&&k <= modcount(1,n))
iw( i , j)=0;k =k +1;
end
end
end
end
end
n =n + 1;
end
n=1;
iwblack(1, watermarklen)=O;%某一个分块中黑色像素的个数iwwhite(1,wat ermarklen)=0;%某一个分块中白色像素的个数while n< = watermarklen
for i = l1* (n -1)+1:11* n
    for j = 12* (n-1)+1:12* n
        if iw( i , j ) ==0
iwblack(1,n) = iwblack(1 ,n)+1;%计算每个分块中的黑色像素的个数else
iwwhite(1,n) = iwwhite(1,n)+1;%计算每个分块中的白色像素的个数end
end
    end
    n = n +1;
end
figure;
imshow( 'hunter.bmp' );
imwrite( iw ,'huntermarked.bmp' );
figure;
imshow( 'huntermarked.bmp ' );


