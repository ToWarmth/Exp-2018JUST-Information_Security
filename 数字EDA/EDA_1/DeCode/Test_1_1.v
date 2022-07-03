module Test_1_1(clkin,datain,tmp,flag1,flag2,dataout,fail); 
input clkin,datain; 
output flag1,flag2,dataout,fail;//flag 是开始转化标志，flag 是两个译码的标志，fail 是错误标志 
output [1:0] tmp; 
reg[1:0] tmp; 
reg flag1=0; 
reg flag2=0; 
reg dataout; 
reg fail=0; 
always @(posedge clkin) 
begin 
 tmp<={datain,tmp[1]}; 
 if(tmp==2'b00 || tmp==2'b11) 
 flag1<=1;//表示可以开始转化，因为 00 后面一定是 1,11 后面一定是 0，可以确定开始，相当于 rst 
end 
 
 always@(negedge clkin) 
begin 
 if(flag1==1) 
 flag2<=~flag2;//因为是要两个译码，时钟慢一倍，即译码周期是输入时钟周期的 2 倍。 
end 
always @(posedge flag2) 
begin 
 if(tmp==2'b10) 
 begin 
 dataout<=0; 
 fail<=0; 
 end 
 else if(tmp==2'b01) 
 begin 
 dataout<=1; 
 fail<=0; 
 end 
 else if(tmp==2'b00||tmp==2'b11) 
 begin 
 dataout<=0; 
 fail<=1;//有错就置 1，输出置为 0 
 end 
end 
endmodule