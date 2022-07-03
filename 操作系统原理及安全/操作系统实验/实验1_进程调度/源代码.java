package OS;

import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        System.out.print("请您输入数量在4~8的进程数：");
        Scanner scanner=new Scanner(System.in);
        int num= scanner.nextInt();
        while(num<4||num>8){
            System.out.print("您输入的数据范围不对，请输入数量在4~8的进程数：");
            scanner=new Scanner(System.in);
            num=scanner.nextInt();
        }
        new Priority(num);
        new Rotation(num);
    }

    public static class PCB {
        public int Pid;  //进程名
        public char State;  //所处状态，A为ready,B为running,C为finish
        public int ExeTime;  //时间片、运行时间
        public int PriorityPower;  //动态产生的优先权
        public int Frequency;   //动态产生的轮转次数
        public int Counter;   //计数，记录已轮转次数
        public PCB next;

        public PCB(int pid){
            Random random=new Random();
            this.Pid=pid;
            this.State='A';
            this.ExeTime= (random.nextInt(5)+1);  //避免运行时间为0
            this.PriorityPower=random.nextInt(30);
            this.Frequency=random.nextInt(6)+1;
            this.Counter=0;
            this.next=null;
        }
    }

    public static class Priority {//优先权调度类
        PCB first_1 = new PCB(0);  //就绪队列队头，链首处于运行状态，其他为就绪
        PCB first_2= new PCB(0);   //完成队列

        public Priority(int num) {
            //创建就绪队列(实质是链表）
            PCB p;
            PCB q;
            PCB temp;
            for (int i = 1; i <= num; i++) {
                p = first_1;
                q = new PCB(i);
                if (p.next == null) {//头节点后没有，直接插
                    p.next = q;
                } else {
                    p=p.next;
                    if(q.PriorityPower>=p.PriorityPower){//头插
                        q.next=p;
                        first_1.next=q;
                        continue;
                    }
                    while (p.next != null) {//中间插
                        if (q.PriorityPower <= p.PriorityPower && q.PriorityPower > p.next.PriorityPower) {
                            q.next = p.next;
                            p.next = q;
                            break;
                        } else {
                            p = p.next;
                        }
                    }
                    if (p.next == null) {//尾插
                        p.next = q;
                    }
                }
            }

            //交换完毕后，打印各进程的优先权，测试是否成功
            p = first_1;
            while (p.next != null) {
                p = p.next;
                System.out.println("进程" + p.Pid + "的初始优先权为" + p.PriorityPower + "，进程所需CPU时间为" + p.ExeTime+"，状态:ready");
            }
            System.out.println("*************************************************");
            //模拟运行、调度
            long startTime,endTime,last;
            while(first_1.next!=null) {
                p = first_1.next;
                startTime = System.currentTimeMillis();
                do {
                    endTime = System.currentTimeMillis();
                    last = endTime - startTime;
                    p.State = 'B';
                } while (last < p.ExeTime);

                q = p;
                System.out.println("进程运行中，CPU正被占用。");
                System.out.println("进程" + p.Pid + "的优先权为" + p.PriorityPower + "，进程所需CPU时间为" + p.ExeTime + "，状态：running。进程运行时间："+p.ExeTime);
                while (q.next != null) {
                    q = q.next;
                    System.out.println("进程" + q.Pid + "的优先权为" + q.PriorityPower + "，进程所需CPU时间为" + q.ExeTime + "，状态：ready");
                }
                q=first_2;
                if(q.next==null);
                else{
                    while(q.next!=null){
                        q=q.next;
                        System.out.println("进程" + q.Pid + "的优先权为" + q.PriorityPower + "，进程所需CPU时间为" + q.ExeTime + "，状态：finish");
                    }
                }
                System.out.println("*************************************************");

                p.PriorityPower -= 3;
                p.ExeTime -= 1;
                if (p.ExeTime <= 0) {//运行时间到，剔除出队列，插入完成队列
                    p.State = 'C';
                    first_1.next = p.next;
                    p.next=null;
                    q=p;
                    //插入完成队列
                    p=first_2;
                    while(p.next!=null){
                        p=p.next;
                    }
                    p.next=q;
                } else {//时间未归零，插入就绪队列
                    first_1.next = first_1.next.next;
                    q = p;
                    q.next = null;
                    p = first_1;
                    if (p.next == null) {//头节点后没有，直接插
                        p.next = q;
                        q.State = 'B';
                    } else {
                        p = p.next;
                        if (q.PriorityPower >= p.PriorityPower) {//头插
                            q.next = p;
                            first_1.next = q;
                        } else {
                            while (p.next != null) {//中间插
                                if (q.PriorityPower <= p.PriorityPower && q.PriorityPower >= p.next.PriorityPower) {
                                    q.next = p.next;
                                    p.next = q;
                                    q.State = 'A';
                                    break;
                                } else {
                                    p = p.next;
                                }
                            }
                            if (p.next == null) {//
                                q.next = null;
                                p.next = q;
                                q.State = 'A';
                            }
                        }
                    }
                }
                p = first_1;q=first_2;
                System.out.println("进程运行完毕后，CPU调度下一进程前。");
                if (p.next == null) {
                    while(q.next!=null){
                        q=q.next;
                        System.out.println("进程" + q.Pid + "的初始优先权为" + q.PriorityPower + "，进程所需CPU时间为" + q.ExeTime + "，状态：finish");
                    }
                } else {
                    while (p.next != null) {
                        p = p.next;
                        System.out.println("进程" + p.Pid + "的初始优先权为" + p.PriorityPower + "，进程所需CPU时间为" + p.ExeTime + "，状态：ready");
                    }
                    while(q.next!=null){
                        q=q.next;
                        System.out.println("进程" + q.Pid + "的初始优先权为" + q.PriorityPower + "，进程所需CPU时间为" + q.ExeTime + "，状态：finish");
                    }
                    System.out.println("*************************************************");
                }
            }
        }
    }

    public static class Rotation {//轮转法调度类
        PCB p, q, first_1, first_2;

        public Rotation(int num) {
            first_1 = new PCB(0);
            first_2 = new PCB(0);
            for (int i = 1; i <= num; i++) {
                q = new PCB(i);
                p = first_1;
                while (p.next != null) {
                    p = p.next;
                }
                p.next = q;
            }
            //测试构成情况
            p = first_1;

            while (p.next != null) {
                p = p.next;
                System.out.println("进程" + p.Pid + "的轮转次数为" + p.Frequency + "，进程所需CPU时间为" + p.ExeTime + "，状态:ready");
            }
            System.out.println("*************************************************");

            long startTime, endTime, last;

            while(first_1.next != null) {
                p = first_1.next;
                startTime = System.currentTimeMillis();
                do {
                    endTime = System.currentTimeMillis();
                    last = endTime - startTime;
                } while (last < p.ExeTime);
                p.State = 'B';
                System.out.println("进程运行中，CPU正被占用。");
                System.out.println("进程" + p.Pid + "的轮转次数为" + p.Frequency + "，进程所需CPU时间为" + p.ExeTime + "，状态：running。进程运行时间：" + p.ExeTime);
                q=p;
                while (q.next != null) {
                    q = q.next;
                    System.out.println("进程" + q.Pid + "的轮转次数为" + q.Frequency + "，进程所需CPU时间为" + q.ExeTime + "，状态：ready");
                }
                q = first_2;
                if (q.next == null) ;
                else {
                    while (q.next != null) {
                        q = q.next;
                        System.out.println("进程" + q.Pid + "的轮转次数为" + q.PriorityPower + "，进程所需CPU时间为" + q.ExeTime + "，状态：finish");
                    }
                }
                System.out.println("***********************************************************");

                p.ExeTime -= 1;
                p.Counter++;
                if (p.ExeTime <= 0) {//时间片到了，不能再继续运行
                    first_1.next = first_1.next.next;
                    p.next = null;
                    p.State = 'C';
                    q = first_2;
                    while (q.next != null) {
                        q = q.next;
                    }
                    q.next = p;
                    if (first_1.next == null) {
                    }
                } else {//时间片未到
                    if (p.Frequency == p.Counter) {//占用时间片等于轮转次数，放至就绪队列队尾
                        first_1.next = first_1.next.next;
                        p.Counter = 0;
                        p.State = 'A';
                        p.next = null;
                        q = first_1;
                        while (q.next != null) {
                            q = q.next;
                        }
                        q.next = p;
                    } else {//该进程继续运行
                    }
                }
                p = first_1;
                q = first_2;
                System.out.println("进程运行完毕后，CPU调度下一进程前。");
                if (p.next == null) {//就绪队列为空
                    while (q.next != null) {
                        q = q.next;
                        System.out.println("进程" + q.Pid + "的轮转数为" + q.Frequency + "，占用时间片数为" + q.Counter + "，进程所需CPU时间为" + q.ExeTime + "，状态：finish");
                    }
                } else {//就绪队列不为空
                    while (p.next != null) {
                        p = p.next;
                        System.out.println("进程" + p.Pid + "的轮转数为" + p.Frequency + "，占用时间片数为" + p.Counter + "，进程所需CPU时间为" + p.ExeTime + "，状态：ready");
                    }
                    while (q.next != null) {
                        q = q.next;
                        System.out.println("进程" + q.Pid + "的轮转数" + q.Frequency + "，占用时间片数为" + q.Counter + "，进程所需CPU时间为" + q.ExeTime + "，状态：finish");
                    }
                    System.out.println("***********************************************************");
                }
            }
        }
    }
}
