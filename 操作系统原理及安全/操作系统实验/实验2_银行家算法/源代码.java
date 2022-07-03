package OS;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;

public class Main_2  {
    static int m,n;   //m个系统资源，n个进程数
    static int[] Available;  //可利用资源向量，是一个含有m个元素的数组。每一个元素代表每一类资源的数目
    static int[][] Max;   //最大需求矩阵（n×m）。定义了系统中n个进程对m类资源的最大需求
    static int[][] Allocation;   //分配矩阵（n×m）。表示进程i已经分到Rj资源的数目为k个
    static int[][] Need;   //需求矩阵(n×m）。表示进程i还需要Rj类资源的数目为k个
    static boolean[] isReleased;
    static Scanner scanner=new Scanner(System.in);
    //满足如下关系：Need=Max-Allocation

    public static void main(String[] args){
        System.out.print("请您输入系统资源数：");
        m=scanner.nextInt();
        Available=new int[m];
        System.out.print("请您输入各个资源的数量（用空格隔开）：");
        for(int i=0;i<=m-1;i++){
            Available[i]=scanner.nextInt();
        }
        System.out.print("请您输入进程数：");
        n=scanner.nextInt();
        Max=new int[n][m];
        Allocation=new int[n][m];  //初始化，分配矩阵默认值为0
        Need=new int[n][m];
        isReleased=new boolean[n];
        for(int i=0;i<=n-1;i++){
            isReleased[i]=false;
        }
        for(int i=0;i<=n-1;i++){
            System.out.print("请您输入进程" + i + "对各资源的需求数（用空格隔开,请不要越界）：");
            for(int j=0;j<=m-1;j++) {
                Max[i][j]=scanner.nextInt();
                Need[i][j]=Max[i][j]-Allocation[i][j];
            }
        }
        while(true){
            applyResource();
        }
    }

    static void applyResource(){//申请资源
        printStatus();
        int[] Request=new int[m];   //某进程对m个资源的需求量
        System.out.print("请您输入您要申请资源的进程编号(0~n-1)：");
        int i=scanner.nextInt();
        while(isReleased[i]){
            System.out.print("进程"+i+"已释放，不能再申请资源。请您重新选择进程：");
            i=scanner.nextInt();
        }
        System.out.print("请您输入为进程"+i+"的申请资源（不同种资源用空格隔开，0表示不申请该类资源）：");
        int step=0;   //判断应跳转步骤
        while(step==0) {
            for (int j = 0; j <= m - 1; j++) {
                Request[j] = scanner.nextInt();
                if (Request[j] > Available[j]) {
                    step = 1;
                }
                if (Request[j] > Max[i][j]) {
                    step = 2;
                }
            }
            switch (step) {
                case 1 :{
                    System.out.print("您的请求超过了系统的最大资源量，请重新输入：");
                    step = 0;
                    break;
                }
                case 2 :{
                    System.out.print("您不需要这么多资源，不要太贪心哦。请重新输入：");
                    step = 0;
                    break;
                }
                default: {
                    step = 3;
                }
            }
        }
        if(isFinished(i,Request)){
            System.out.println("所有进程完成资源分配，分配结束。");
            System.exit(0);
        }
    }

    static boolean isFinished(int index,int[] Request){ //判断某进程是否完成所有资源分配
        int[] work=Available.clone();
        boolean[] finish=isReleased.clone();
        int[][] need_temp=new int[n][m];
        int[][] allocate_temp=new int[n][m];
        for(int i=0;i<n;i++){
            need_temp[i]=Need[i].clone();
            allocate_temp[i]=Allocation[i].clone();
        }
        for(int i=0;i<m;i++){
            if(Need[index][i]<Request[i]){
                Request[i]=Need[index][i];
                work[i]-=Request[i];
                allocate_temp[index][i]+=Request[i];
            }
        }
        //重置need_temp
        for(int i=0;i<m;i++){
            need_temp[index][i]-=Request[i];
        }
        if(!isSafe(work,finish,need_temp,allocate_temp)){
            System.out.println("分配会造成系统不安全，取消分配");
            return false;
        }
        else{
            System.out.println("分配成功");
            for(int i=0;i<m;i++){
                Available[i]-=Request[i];
                Allocation[index][i]+=Request[i];
                Need[index][i]-=Request[i];
            }
            if(!isReleased[index]){
                boolean judge=false;
                for(int j=0;j<m;j++){
                    if(Need[index][j]!=0) judge=true;
                }
                if(!judge){
                    isReleased[index]=true;
                    for(int j=0;j<m;j++){
                        Available[j]+=Allocation[index][j];
                    }
                }
            }
        }
        boolean isFinished=true;
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(Need[i][j]!=0){
                    isFinished=false;
                }
            }
        }
        return isFinished;
    }

    static boolean isSafe(int[] work,boolean[] finish,int[][] need_temp,int[][] allocation_temp){
        Queue<Integer> queue=new ArrayDeque<Integer>();  //假设能完成的队列
        int time=0;
        while(true){
            boolean loop=true;
            for(int i=0;i<n;i++){
                time++;
                if(!finish[i]){
                    boolean b=false;
                    for(int j=0;j<m;j++){
                        if(work[j]<need_temp[i][j]){
                            b=true;
                        }
                        if(b) break;
                    }
                    if(!b){
                        time=0;
                        queue.add(i);
                        finish[i]=true;
                        for(int j=0;j<m;j++){
                            work[j]+=allocation_temp[i][j];
                            allocation_temp[i][j]+=need_temp[i][j];
                            need_temp[i][j]=0;
                        }
                        System.out.println();
                    }
                }
            }
            boolean isFinish=false;
            for(int i=0;i<n;i++){
                if(!finish[i]){
                    isFinish=true;
                    break;
                }
            }
            if(!isFinish){return true;}
            if(time>n){return false;}
        }
    }

    static void printStatus(){ //打印当前状态
        for(int i=0;i<n;i++)
        {
            for(int j=0;j<m;j++)
            {
                Need[i][j]=Max[i][j]-Allocation[i][j];
            }
        }
        for(int i=0;i<n;i++)
        {
            System.out.print("进程"+(i)+"的状态为：Max: ");
            for(int j=0;j<m;j++) {System.out.print(" "+Max[i][j]+" ");}
            System.out.print("\tAllocation: ");
            for(int j=0;j<m;j++) {System.out.print(" "+Allocation[i][j]+" ");}
            System.out.print("\tNeed: ");
            for(int j=0;j<m;j++) {System.out.print(" "+Need[i][j]+" ");}
            System.out.print("\tAvailable: ");
            for(int j=0;j<m;j++) {System.out.print(" "+Available[j]+" ");}
            System.out.println();
        }
    }
}
