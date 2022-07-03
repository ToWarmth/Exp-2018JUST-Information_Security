package sample;

import java.io.IOException;
import java.util.*;

public class Main {
    static int Aimed;   //命中次数
    static int Error;   //失效次数
    static int Msize;   //用户内存容量
    static int num;  //记录请求次数
    static double Effective;  //命中率
    static int[] instruction=new int[320];  //模拟连续执行的代码指令

    public static void init() {//生成地址流
        Random random=new Random();
        int m,m1,m2,i=0;
        while(i<=319) {
            m = random.nextInt(320);//[0,319]
            instruction[i++] = m;
            instruction[i++] = m + 1;
            m1 = random.nextInt(m + 1);
            instruction[i++] = m1;
            m2 = random.nextInt(320 - m1 - 1) + m1 + 1;
            instruction[i++] = m2;
        }
    }

    public static void OPT() {//最优置换法
        Msize = 2;
        while (Msize <= 32) {
            num = 1;
            Aimed = 0;  //每次开始前，命中次数赋为0
            Error = 0;  //每次开始前，未命中次数赋为0
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i <= 319; i++) {
                if (list.size() < Msize) {
                    if (list.contains(instruction[i] / 10)) {
                        Aimed++;
                        if (Msize == 2) {
                            System.out.print("第" + (num++) + "次置换，需要页面" + (instruction[i] / 10) + "，成功命中。");
                            printStatus(list);
                        }
                    } else {
                        list.add(instruction[i] / 10);
                        Error++;
                        if (Msize == 2) {
                            System.out.print("第" + (num++) + "次置换，需要页面" + (instruction[i] / 10) + "，未能命中，将页面" + (instruction[i] / 10) + "装入页表。");
                            printStatus(list);
                        }
                    }
                } else {//满了
                    if (list.contains(instruction[i] / 10)) {
                        Aimed++;
                        if (Msize == 2) {
                            System.out.print("第" + (num++) + "次置换，需要页面" + (instruction[i] / 10) + "，成功命中。");
                            printStatus(list);
                        }
                    } else {
                        int state=1;
                        int Disappear=i+1;//记录出现的次数
                        if(Disappear==320) Disappear=318;
                        here:
                        for(int j=0;j<Msize;j++){
                            if(i<319)
                                for(int k=i+1;k<=319;k++){
                                if(list.get(j)==instruction[k]/10){//该页面后续出现
                                    if(k>Disappear||Disappear==i+1) {
                                        Disappear=k;
                                    }
                                    break;
                                }
                                if(list.get(j)!=(instruction[k]/10)&&k==319) {//该页面后续不出现，直接剔除
                                    list.remove(j);
                                    state=0;
                                    break here;
                                }
                            }
                        }
                        if(state==1) {
                            list.remove((Object) (instruction[Disappear] / 10));
                        }
                        list.add(instruction[i] / 10);
                        Error++;
                        if (Msize == 2) {
                            System.out.print("第" + (num++) + "次置换，需要页面" + (instruction[i] / 10) + "，未能命中，将页面" + (instruction[i] / 10) + "装入页表。");
                            printStatus(list);
                        }
                    }
                }
            }
            printStatus(1);
            Msize++;
        }
    }

    public static void FIFO(){
        Msize=2;
        while(Msize<=32){
            num=1;
            Aimed=0;  //每次开始前，命中次数赋为0
            Error=0;  //每次开始前，未命中次数赋为0
            List<Integer> list =new ArrayList<Integer>();
            for(int i=0;i<=319;i++){
                if(list.size()<Msize){
                    if(list.contains(instruction[i]/10)) {
                        Aimed++;
                        if (Msize == 2) {
                            System.out.print("第" + (num++) + "次置换，需要页面" + (instruction[i] / 10) + "，成功命中。");
                            printStatus(list);
                        }
                    }
                    else{
                        list.add(instruction[i]/10);
                        Error++;
                        if (Msize == 2) {
                            System.out.print("第" + (num++) + "次置换，需要页面" + (instruction[i] / 10) + "，未能命中，将页面" + (instruction[i] / 10) + "装入页表。");
                            printStatus(list);
                        }
                    }
                }
                else{
                    if(list.contains(instruction[i]/10)) {
                        Aimed++;
                        if (Msize == 2) {
                            System.out.print("第" + (num++) + "次置换，需要页面" + (instruction[i] / 10) + "，成功命中。");
                            printStatus(list);
                        }
                    }
                    else {
                        list.remove(0);
                        list.add(instruction[i] / 10);
                        Error++;
                        if (Msize == 2) {
                            System.out.print("第" + (num++) + "次置换，需要页面" + (instruction[i] / 10) + "，未能命中，将页面" + (instruction[i] / 10) + "装入页表。");
                            printStatus(list);
                        }
                    }
                }
            }
            printStatus(2);
            Msize++;
        }
    }

    public static void LRU(){//最近最少使用算法
        Msize=2;
        while(Msize<=32){
            num=1;
            Aimed=0;  //每次开始前，命中次数赋为0
            Error=0;  //每次开始前，未命中次数赋为0
            List<Integer> list =new LinkedList<>();//最新使用的在最前面
            for(int i=0;i<=319;i++){
                if(list.size()<Msize){
                    if(list.contains(instruction[i]/10)) {
                        Aimed++;
                        list.remove((Integer)(instruction[i]/10));
                        list.add(0,instruction[i]/10);
                        if (Msize == 2) {
                            System.out.print("第" + (num++) + "次置换，需要页面" + (instruction[i] / 10) + "，成功命中。");
                            printStatus(list);
                        }
                    }
                    else {
                        list.add(0,instruction[i]/10);
                        Error++;
                        if (Msize == 2) {
                            System.out.print("第" + (num++) + "次置换，需要页面" + (instruction[i] / 10) + "，未能命中，将页面" + (instruction[i] / 10) + "装入页表。");
                            printStatus(list);
                        }
                    }
                }
                else{
                    if(list.contains(instruction[i]/10)) {
                        Aimed++;
                        list.remove((Integer)(instruction[i]/10));
                        list.add(0,instruction[i]/10);
                        if (Msize == 2) {
                            System.out.print("第" + (num++) + "次置换，需要页面" + (instruction[i] / 10) + "，成功命中。");
                            printStatus(list);
                        }
                    }
                    else{
                        list.remove(list.size()-1);
                        list.add(instruction[i]/10);
                        Error++;
                        if (Msize == 2) {
                            System.out.print("第" + (num++) + "次置换，需要页面" + (instruction[i] / 10) + "，未能命中，将页面" + (instruction[i] / 10) + "装入页表。");
                            printStatus(list);
                        }
                    }
                }
            }
            printStatus(3);
            Msize++;
        }
    }

    public static void LFU(){//最少访问页面算法
        Msize=2;
        while(Msize<=32){
            num=1;
            Aimed=0;  //每次开始前，命中次数赋为0
            Error=0;  //每次开始前，未命中次数赋为0
            List<Integer> list =new LinkedList<Integer>();
            int[] record=new int[32]; //共32页
            for(int i=0;i<=319;i++){
                if(list.size()<Msize) {
                    if (list.contains(instruction[i] / 10)) {
                        Aimed++;
                        record[instruction[i]/10]++;
                        if (Msize == 2) {
                            System.out.print("第" + (num++) + "次置换，需要页面" + (instruction[i] / 10) + "，成功命中。");
                            printStatus(list);
                        }
                    }
                    else{
                        Error++;
                        list.add(instruction[i]/10);
                        record[instruction[i]/10]++;
                        if (Msize == 2) {
                            System.out.print("第" + (num++) + "次置换，需要页面" + (instruction[i] / 10) + "，未能命中，将页面" + (instruction[i] / 10) + "装入页表。");
                            printStatus(list);
                        }
                    }
                }
                else{
                    if(list.contains(instruction[i]/10)){
                        Aimed++;
                        record[instruction[i]/10]++;
                        if (Msize == 2) {
                            System.out.print("第" + (num++) + "次置换，需要页面" + (instruction[i] / 10) + "，成功命中。");
                            printStatus(list);
                        }
                    }
                    else{
                        Error++;
                        int num_min=0;  //最小的那个下标
                        int MIN=0; //最小值
                        for(int index=0;index<Msize;index++){//找出使用次数最少的那个
                            if(record[list.get(index)]<MIN){
                                MIN=record[list.get(index)];
                                num_min=index;
                            }
                        }
                        list.remove(num_min);
                        list.add(instruction[i]/10);
                        record[instruction[i]/10]++;
                        if (Msize == 2) {
                            System.out.print("第" + (num++) + "次置换，需要页面" + (instruction[i] / 10) + "，未能命中，将页面" + (instruction[i] / 10) + "装入页表。");
                            printStatus(list);
                        }
                    }
                }
            }
            printStatus(4);
            Msize++;
        }
    }

    public static void printStatus(List<Integer> list){
        System.out.print("内存现装有：");
        for(int i=0;i<list.size();i++){
            System.out.print("页面"+list.get(i)+"\t");
        }
        System.out.println();
    }

    public static void printStatus(int which){
        Effective=1-(double)(Error/(double)(320));
        switch ((which)){
            case 1:{System.out.print("OPT :Msize:");break;}
            case 2:{System.out.print("FIFO :Msize:");break;}
            case 3:{System.out.print("LRU :Msize:");break;}
            case 4:{System.out.print("LFU :Msize:");break;}
            default:{}
        }
        System.out.println(Msize+"\t缺失个数："+Error+"\t命中个数"+Aimed+"\t命中率："+Effective);
    }

    public static void main(String[] args) {
        System.out.println("Start memory management.");
        System.out.println("Producing address flow, wait for while, please.");
        init();
        do {
            //选择算法
            System.out.println("\nThere are algorithms in the program\n1、 Optimization algorithm\n2、 First in first out algorithm\n3、 Least recently used algorithm \n4、 Least frequently used algorithm ");
            System.out.print("Select an algorithm number, please： ");
            Scanner scanner=new Scanner(System.in);
            int choi=scanner.nextInt();
            while(!(choi<=4&&choi>=1)){
                System.out.print("不存在您选择的算法序号，请重新选择管理算法：");
                choi=scanner.nextInt();
            };
            switch(choi){
                case 1:{ OPT();break;}
                case 2:{ FIFO();break;}
                case 3:{ LRU();break;}
                case 4:{ LFU();break;}
            }
            System.out.print("do you try again with anther algorithm(y/n)”:");
            try {
                char choo=(char)System.in.read();
                while(choo!='Y'&&choo!='y'&&choo!='N'&&choo!='n'){
                    System.out.print("您输入的格式有误，请重新输入(y/n)：");
                    choo=(char)System.in.read();
                }
                if(choo=='Y'||choo=='y') ;
                else break;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }while(true);
    }
}
