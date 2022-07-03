onerror {quit -f}
vlib work
vlog -work work Test_1_1.vo
vlog -work work Test_1_1.vt
vsim -novopt -c -t 1ps -L cycloneii_ver -L altera_ver -L altera_mf_ver -L 220model_ver -L sgate work.Test_1_1_vlg_vec_tst
vcd file -direction Test_1_1.msim.vcd
vcd add -internal Test_1_1_vlg_vec_tst/*
vcd add -internal Test_1_1_vlg_vec_tst/i1/*
add wave /*
run -all
