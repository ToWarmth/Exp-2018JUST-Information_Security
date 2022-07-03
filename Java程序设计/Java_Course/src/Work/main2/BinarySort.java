package Work.main2;

import java.util.Scanner;

//定义结构体二叉排序，用于排序。
class BiTree{
    int value;
    BiTree lnext;
    BiTree rnext;
};

//实现数组类
public class BinarySort {
    BiTree Head;
    int n=1;
    int[] num;

    BinarySort(){//构造函数，传参，确定需要排序的数组
        System.out.print("请输入你想要排序的数组长度：");
        Scanner scanner=new Scanner(System.in);
        n=scanner.nextInt();
        num=new int[n];
        int i=0;
        while(i<n){
            System.out.print("请输入第"+i+"个数组元素：");
            num[i]=scanner.nextInt();
            i++;
        }
        System.out.println("\n数组初始化完毕，如下：");
        for(i=0;i<n;i++){
            System.out.println(num[i]+"\t");
        }
    }

    public void createTree(){//二叉排序树的非递归实现
        Head=new BiTree();
        Head.value=num[0];
        Head.lnext=null;
        Head.rnext=null;
        for (int i = 1; i < n; i++){
            InsertNode(Head,num[i]);
        }
    }

    public void InsertNode(BiTree p,int num) {//二叉排序树的非递归实现
        if(num<=p.value){
            if(p.lnext==null){
                BiTree q=new BiTree();
                q.value=num;
                q.lnext=null;
                q.rnext=null;
                p.lnext=q;
            }
            else InsertNode(p.lnext,num);
        }
        if(num>p.value){
            if(p.rnext==null){
                BiTree q=new BiTree();
                q.value=num;
                q.lnext=null;
                q.rnext=null;
                p.rnext=q;
            }
            else InsertNode(p.rnext,num);
        }
    }

    public void printStatus(BiTree p){//中序遍历
        if(p.lnext!=null){
            printStatus(p.lnext);
        }
        System.out.println(p.value);
        if(p.rnext!=null){
            printStatus(p.rnext);
        }
    }

    public static void main(String[] args){
        BinarySort A=new BinarySort();
        A.createTree();
        System.out.println("\n排序后，数组如下：");
        A.printStatus(A.Head);
    }
}
