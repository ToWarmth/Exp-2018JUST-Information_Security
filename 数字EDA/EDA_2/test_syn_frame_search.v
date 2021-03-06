`timescale 1ns / 1ps
module test_syn_frame_search; 
 // Inputs 
 reg clk; 
 reg rst_n; 
 reg [7:0] data; 
 reg [7:0] i; 
 // Outputs 
 wire syn_out_flag; 
 // Instantiate the Unit Under Test (UUT) 
Test_2 uut ( 
 .clk(clk), 
 .rst_n(rst_n), 
 .data(data), 
 .syn_out_flag(syn_out_flag) 
 ); 
 initial begin 
 // Initialize Inputs 
 clk = 0; 
 rst_n = 0; 
 data = 0; 
 // Wait 100 ns for global reset to finish 
 #100; 
 #1000 $finish; 
 // Add stimulus here 
 end 
 always #3 clk=~clk; 
 initial begin 
 #100 rst_n=1; 
 end 
 initial 
 begin 
 for(i=1;i<=70;i=i+1) 
 begin 
 @(negedge clk) 
 if (data==8'b0000_0000) 
 data<=8'b1111_0111; 
 else 
 data<=data+8'b0000_0001; 
 end 
 #20 data=8'b0000_0000; 
 end 
endmodule