module test( clk, rst, start, d_finish, datain, dataout ); 
 input clk; 
 input rst; 
 input start; 
 input datain; 
 input d_finish; 
 output reg dataout; 
 reg[2:0]cyclic_reg; 
 reg[1:0]state; 
 reg[1:0]count; 
 parameter idle=2'b00; 
 parameter compute=2'b01; 
 parameter finish=2'b10; 
 always@(posedge clk or negedge rst) 
 begin 
 case(state) 
 idle:begin 
 if(start) 
 state<=compute; 
 else 
 state<=idle; 
 end 
 compute:begin 
 if(d_finish) 
 state<=finish; 
 else 
 state<=compute; 
 end 
 finish:begin 
 if(count==3) 
 state<=idle; 
 else 
 count<=count+1; 
 end 
 endcase 
 if(!rst) 
 begin 
 cyclic_reg[2:0]<=3'b000; 
 count<=2'b00; 
 state<=idle; 
 end 
 else 
 case(state) 
 idle:begin 
 cyclic_reg[2:0]<=3'b000; 
 end 
 compute:begin 
 cyclic_reg[0]<=cyclic_reg[2]^datain; 
 cyclic_reg[1]<=cyclic_reg[0]^cyclic_reg
[2]^datain; 
 cyclic_reg[2]<=cyclic_reg[1]; 
 dataout<=datain; 
 end 
 finish:begin 
 dataout<=cyclic_reg[2]; 
 cyclic_reg[2:0]<={cyclic_reg[1:0],1'b0}
; 
 end 
 endcase 
 end 
endmodule