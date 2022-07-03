%第一步，图像处理，将普通图像先转为灰度图，再转为二值图像。
clear all;clc;                %清屏，清除变量
x=imread('PIC1.jpg');   %读取图片
subplot(2,2,1)     %两行两列布置中，第一张图
imshow(x);         %输出彩色图像
x=rgb2gray(x);     %将对象x进行灰度处理
subplot(2,2,2)     %两行两列布置中，第二张图
imshow(x);         %输出灰度图像
xbw=imbinarize(x,0.65); %再转为二值图像，二值化阈值为0.65
subplot(2,2,3)     %两行两列布置中，第三张图
imshow(xbw)        %输出黑白二值图像
imwrite(xbw,'PIC1_2.jpg');    %将其二值图像保存为PIC1_1.jpg
[m,n]=size(xbw)               %输出二值图像矩阵的行数m与列数n
print(xbw,m,n)                %Insert之前的输出
Insert(xbw,m,n);

function Insert(xbw,m,n)
x = abs('JUST');   %将嵌入信息转为二进制数据流
str='';
for i=1:4
    str=strcat(str,dec2bin(x(i)))
end
x=str2num(str(:))
x=x.';                  %矩阵转置
j=1;
for i=1:m
    xbw(i,n)=x(j);
    j=j+1;
    if(j==28) break;
    end
end
print(xbw,m,n)
subplot(2,2,4);     %两行两列布置中，第四张图
imshow(xbw);        %输出黑白二值图像
Decode(xbw,m,n);
end

function Decode(xbw,m,n)
y=zeros(1,28);
for i=1:m
    y(i)=xbw(i,n);
    if(i==28) break;
    end
end
y
str="";
for i=1:7:28
    num=y(i)*64+y(i+1)*32+y(i+2)*16+y(i+3)*8+y(i+4)*4+y(i+5)*2+y(i+6)
    temp=char(num)
    str=strcat(str,temp);
end
str
end

function to_0_percent = print(xbw,m,n)
counter_0=0;       %变量counter_0用于表示黑色像素(0)的个数
counter_1=0;       %变量counter_1用于表示白色像素(1)的个数
for i=1:m          %遍历矩阵查看黑白像素点个数
    for j=1:n
        if(xbw(i,j)==1) 
            counter_1=counter_1+1;
        end
        if(xbw(i,j)==0) 
            counter_0=counter_0+1;
        end 
    end
end
counter_0          %输出黑像素点(0)的个数
counter_1          %输出白像素点(1)的个数
to_0_percent=counter_0/(counter_0+counter_1)     %输出黑像素点1的占比
end