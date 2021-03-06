module test(
 clk,
 start,
 d_finish,
 datain, 
 outbuf, 
 dataout,
);
input clk; 
input start;      
input datain;     
input d_finish;     
output reg [3:0] dataout;  
output reg [6:0] outbuf;  
reg [2:0] cyclic_reg=3'b000; 
reg [1:0] state=2'b00;     
reg [2:0] rout;     
reg [6:0] databuf=7'b0000000; 
integer index=6; 
integer done=0;    
parameter idle=2'b00;   
parameter compute=2'b01;  
parameter finish=2'b10;   
parameter none=3'b000;   
parameter first=3'b011; 
parameter second=3'b110;  
parameter third=3'b111;   
parameter fourth=3'b101;  
parameter fifth=3'b001;   
parameter sixth=3'b010; 
 
parameter seventh=3'b100;   
always@(posedge clk) 
begin 
 case(state) 
  idle:begin 
   if(start)   
    state <= compute; 
   else    
    state <= idle; 
  end 
  compute:begin 
   if(d_finish)   
    state <= finish; 
   else    
    state <= compute; 
  end 
  finish:begin 
   state <= finish; 
  end 
 endcase 
 end 
always@(posedge clk) 
begin    
   case(state) 
    idle:begin 
     dataout[3:0] <= 
4'b0000;  
 
     outbuf[6:0] <= 
7'b0000000;  
     databuf[6] <= datain; 
     cyclic_reg[0] <= 
cyclic_reg[2] ^ datain; 
     cyclic_reg[1] <= 
cyclic_reg[0] ^ cyclic_reg[2] ^ datain; 
     cyclic_reg[2] <= 
cyclic_reg[1];    
     index=5; 
    end 
    compute:begin 
     cyclic_reg[0] <= 
cyclic_reg[2] ^ datain; 
     cyclic_reg[1] <= 
cyclic_reg[0] ^ cyclic_reg[2] ^ datain; 
     cyclic_reg[2] <= 
cyclic_reg[1]; 
     databuf[index] <= 
datain; 
     index <= index-1; 
     
end 
 
    finish:begin 
     rout[0] <= 
cyclic_reg[0]; 
     rout[1] <= 
cyclic_reg[1]; 
     rout[2] <= 
cyclic_reg[2]; 
     done=1;  
    end 
   endcase 
  if(done==1) 
  begin  
   case(rout) 
    none:begin   
     outbuf[6:0] <= 
databuf[6:0]; 
    end 
    first:begin    
     outbuf[6:0] <= 
databuf[6:0]^7'b0000001; 
    end 
    second:begin   
     outbuf[6:0] <= 
databuf[6:0]^7'b0000010; 
    end 
    third:begin    
     outbuf[6:0] <= 
databuf[6:0]^7'b0000100; 
    end 
    fourth:begin   
     outbuf[6:0] <= 
databuf[6:0]^7'b0001000; 
    end 
 
    fifth:begin    
     outbuf[6:0] <= 
databuf[6:0]^7'b0010000; 
    end 
    sixth:begin    
     outbuf[6:0] <= 
databuf[6:0]^7'b0100000; 
    end 
    seventh:begin   
     outbuf[6:0] <= 
databuf[6:0]^7'b1000000; 
    end  
   endcase 
   dataout[3:0] <=outbuf[6:3]; 
  end 
end 
endmodule
