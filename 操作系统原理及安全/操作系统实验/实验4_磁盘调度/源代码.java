package sample;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main  {
    static int Now_number,index,temp,variable;
    static char Now_direction;
    static IO p;
    static List<IO> list= new LinkedList<>();
    static Scanner scanner=new Scanner(System.in);

    public static void init() throws IOException{//初始化I/O请求表
        System.out.println("我们需要您初始化I/O请求表，需要您的配合，谢谢。");
        System.out.print("请您输入访问磁道的进程数量(范围：4~8)：");
        int num=scanner.nextInt();
        while(num<4||num>8){
            System.out.print("进程数过大或过小，请重新输入：");
            num=scanner.nextInt();
        }
        int pid,trackNum;
        System.out.println("请您依次输入访问进程的Pid和访问磁道号（例：1  2）：");
        for(int i=0;i<=num-1;i++){
            pid=scanner.nextInt();
            trackNum=scanner.nextInt();
            p=new IO(pid,trackNum);
            list.add(p);
        }
        System.out.print("请您输入当前所在磁道号：");
        Now_number=scanner.nextInt();
        System.out.print("请您输入当前移臂方向(B/b代表向大的方向前进，S/s代表向小的方向前进）:");
        Now_direction = (char)System.in.read();
        while(Now_direction!='B'&&Now_direction!='b'&&Now_direction!='S'&&Now_direction!='s'){
            System.out.print("输入格式错误，请您重新输入移臂方向：");
            Now_direction = (char)System.in.read();
        }
    }

    public static void DiskScheduled() throws IOException{
        do{
            System.out.print("现需要您随机输入一个[0,1]的随机数（模拟进程调度，便于控制流程）控制程序流程（<=0.5则进行磁盘调度，>0.5则接受请求）：");
            Scanner scanner=new Scanner(System.in);
            double choi=scanner.nextDouble();
            if(choi<=0.5) {//磁盘调度
                index =temp=variable=0; //variable记录最近值
                if (Now_direction == 'B' || Now_direction == 'b') {//向大的方向走
                    seekB();
                    //若没有找到（悬臂到头啦），则调转过来，向另一方向进军
                    if((list.get(index).isControl)||((!list.get(index).isControl)&&(list.get(index).TrackNum<Now_number))){//已被控制，或者是未被控制但不满足调度
                        Now_direction='s';
                        seekS();
                    }
                } else {//向小的方向走
                    seekS();
                    if((list.get(index).isControl)||((!list.get(index).isControl)&&(list.get(index).TrackNum>Now_number))){
                        Now_direction='b';
                        seekB();
                    }
                }
                list.get(index).isControl=true;
                Now_number=list.get(index).TrackNum;
                printStatus();
            }
            else{//接受请求
                System.out.print("请输入新的进程名和磁道号：");
                int pid,trackNum;
                pid=scanner.nextInt();
                trackNum=scanner.nextInt();
                p=new IO(pid,trackNum);
                list.add(p);
                printStatus();
            }
            System.out.print("是否继续？(y/n)：");
            char dir=(char) System.in.read();
            while(dir!='y'&&dir!='Y'&&dir!='n'&&dir!='N'){
                System.out.print("格式错误，请您重新输入：");
                dir=(char) System.in.read();
            }
            if(dir=='y'||dir=='Y');
            else break;
        }while(true);
    }

    public static void seekB(){
        for (int i = 0; i < list.size(); i++) {//遍历查找
            if ((!list.get(i).isControl) && list.get(i).TrackNum >= Now_number) {//未被调用（访问）且在旋臂方向上
                temp=list.get(i).TrackNum-Now_number;
                if ((temp>=0)&&((variable!=0&&temp<=variable)||(variable==0))) {//磁道号最小
                    variable = temp;
                    index = i;
                }
            }
        }
    }

    public static void seekS(){
        for (int i = 0; i < list.size(); i++) {//遍历查找
            if ((!list.get(i).isControl) && list.get(i).TrackNum <= Now_number) {//未被调用（访问）且在旋臂方向上
                temp=Now_number-list.get(i).TrackNum;
                if ((temp>=0)&&((variable!=0&&temp<=variable)||(variable==0))) {
                    variable =  Now_number-list.get(i).TrackNum;
                    index = i;
                }
            }
        }
    }

    public static void printStatus(){
        System.out.println("进程id\t磁道\t是否调度");
        for(int i=0;i<list.size();i++){
            System.out.println(list.get(i).Pid+"\t\t"+list.get(i).TrackNum+"\t\t"+list.get(i).isControl);
        }
    }

    public static void main(String[] args) throws IOException {
        init();
        DiskScheduled();
    }

    public static class IO{
        public int Pid;  //进程名
        public int TrackNum;   //请求磁道号
        public boolean isControl;  //调度状态量，true表示正在被调度，false表示未被调度

        IO(int pid,int trackNum){
            this.Pid=pid;
            this.TrackNum=trackNum;
            this.isControl=false;
        }
    }
}
