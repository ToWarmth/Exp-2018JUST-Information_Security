module Test_1(clk,datain,dataout); 
input clk, datain; //时钟输入，数据输入 
output dataout; //曼彻斯特编码输出 
reg dataout,flag; //flag 为标志信号 
reg [1:0] com; 
always @(posedge clk) 
 begin 
 if(flag == 1'b0) 
 begin 
 if(datain == 1'b0)//当数据为 0 时，转
 begin 
 com <= 2'b01; 
 end 
 else //当数据为 1 时，转换成 10 
 begin 
 com <= 2'b10; 
 end 
 end 
 end 
always @(posedge clk) //曼彻斯特编码输出过程 
 begin 
 if(flag == 1'b1) 
 begin 
 dataout <=com[1]; 
 flag <=~flag; 
 end 
 else 
 begin 
 dataout <= com[0]; 
 flag <=~flag; 
 end 
 end 
endmodule