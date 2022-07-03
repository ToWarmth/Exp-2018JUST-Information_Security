package Work.main1;

//实验一：实现乘法口诀表
//1*1=1
//1*2=2 2*2=4
//1*3=3 2*3=6 3*3=9
public class Multiplication{
    public static void main(String[] args){
        for(int i=1;i<=9;i++){
            for(int j=1;j<=9;j++){
                System.out.print(j+"*"+i+"="+(i*j)+"\t");
                if(j==i) {
                    System.out.println();
                    break;
                }
            }
        }
    }
}
