`timescale 1ns / 1ps
module Test_2(clk, 
 rst_n, 
 data, 
 syn_out_flag); 
 
input clk,rst_n;//时钟及复位 
input[7:0] data; //数据流 
output syn_out_flag;//同步输出 
 
parameter FRAMEHEAD = 8'H47; //帧同步信号 47H 
parameter FRAMECOUNT= 10; // 帧长为 10 字节 
parameter SEA_CAP = 3 ; // 搜捕态同步信号持续次数 
parameter ERROR_ALLOW = 3; // 稳定输出时允许同步信号连续出错次数 
// state code 
parameter SEARCH = 4'b0001;//搜索帧头态 
parameter CHECK = 4'b0010;//搜捕态 
parameter LOCATE = 4'b0100;//稳定同步态 
parameter ERROR = 4'b1000;//容错态 
// regs & wires 
reg[3:0] state;//状态机当前状态变量 
reg[3:0] next_state;//状态机下一状态变量 
reg[3:0] cnt;//定时计数器，为帧同步提供锁定位标志信号 
reg[1:0] s;//搜捕计数器 
reg[1:0] r;//容错计数器 
wire syn_out_flag;//同步输出 
always @ ( posedge clk ) 
 if ( !rst_n ) 
 state <= SEARCH; 
 else 
 state <= next_state; 
always @ ( state or data or cnt or s or r ) 
begin 
 case ( state ) 
 SEARCH: begin //搜索帧头态，‘是’进入搜捕态，‘否’继续搜索帧头 
 if (data == FRAMEHEAD) 
 next_state = CHECK; 
 else 
 next_state = SEARCH; 
 end 
 CHECK : begin //搜捕态 
 if ( cnt == FRAMECOUNT - 1 && data == FRAMEHEAD && s == SEA_CAP) 
 next_state = LOCATE; //搜索到同步信号且搜捕计数器到达设定次数，进入稳定同步态 
 else if ( cnt == FRAMECOUNT - 1 && data == FRAMEHEAD && s != SEA_CAP) 
 next_state = CHECK; //搜索到同步信号但搜捕计数器未到达设定次数，进入稳定同步态 
 else if ( cnt == FRAMECOUNT - 1 && data != FRAMEHEAD) 
 next_state = SEARCH; //未搜索到同步信号进入搜索帧头态 
 else 
 next_state = CHECK; 
 end 
 LOCATE: begin //稳定同步态 
 if ( cnt == FRAMECOUNT - 1 && data != FRAMEHEAD ) 
 next_state = ERROR; //未搜索到同步信号，进入容错态 
 else 
 next_state = LOCATE; //搜索到同步信号继续为稳定同步态 
 end 
 
 ERROR: begin //容错态 
 if ( cnt == FRAMECOUNT - 1 && data != FRAMEHEAD && r == ERROR_ALLOW ) 
 next_state = SEARCH; //未搜索到同步信号且搜捕计数器到达设定次数（本次实例为 3 次），进入搜索帧头态 
 else if ( cnt == FRAMECOUNT - 1 && data != FRAMEHEAD && r != ERROR_ALLOW ) 
 next_state = ERROR; //未搜索到同步信号但搜捕计数器未到达设定次数继续容错 
 else if ( cnt == FRAMECOUNT - 1 && data == FRAMEHEAD ) 
 next_state = LOCATE;//搜索到同步信号进入稳定同步态 
 else 
 next_state = ERROR; 
 end 
 endcase 
end 
wire cnt_en=( state == CHECK || state == LOCATE || state == ERROR )? 1'b1 : 1'b0; //定时计数器使能 
wire s_n=(state == CHECK )? 1'b1 : 1'b0;//搜捕计数器使能 
wire r_n=(state == ERROR )? 1'b1 : 1'b0;//容错计数器使能 
always @ ( posedge clk ) 
 if ( !rst_n ) 
 begin 
 s <= 0; 
 r <= 0; 
 cnt <= 0; 
 end 
 else 
 begin 
 if ( !s_n ) //搜捕计数器 
 s <= 0; 
 else 
 s <= ( cnt == FRAMECOUNT - 1 && s_n ) ? s + 1 : s ; 
 if ( !r_n ) //容错计数器 
 r <= 0; 
 else 
 r <= ( cnt == FRAMECOUNT - 1 && r_n ) ? r + 1: r ; 
 if ( cnt == FRAMECOUNT - 1 ) //定时计数器 
 cnt <= 0; 
 else 
 cnt <= ( cnt_en ) ? cnt + 1 : 0; 
 end 
assign syn_out_flag=(cnt==FRAMECOUNT-1) ? ( state == LOCATE || state == ERROR ) ? 1'b1 : 1'b0 
 : 1'b0; //在 LOCATA 和 ERROR 态时同步输出 
endmodule

