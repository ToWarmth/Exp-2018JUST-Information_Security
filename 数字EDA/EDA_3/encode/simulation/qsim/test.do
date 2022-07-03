onerror {quit -f}
vlib work
vlog -work work test.vo
vlog -work work test.vt
vsim -novopt -c -t 1ps -L cyclonev_ver -L altera_ver -L altera_mf_ver -L 220model_ver -L sgate work.test_vlg_vec_tst
vcd file -direction test.msim.vcd
vcd add -internal test_vlg_vec_tst/*
vcd add -internal test_vlg_vec_tst/i1/*
add wave /*
run -all
