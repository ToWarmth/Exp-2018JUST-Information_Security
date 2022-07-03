package Work;
import java.util.Scanner;

public class work_1 {
    int counter = 0;

    public int[] getFactor(int n) {//核心思想：先得因数，再判断这个因数是不是素数
        //先遍历一遍，试探性地判断有多少个质因数
        for (int i = 2; i <= n; i++) {//自己也可能是自己的质因数，因此需要除到自己，不然会被is_primeFactor()漏掉
            if (n % i == 0) {
                if (is_primeFactor(i)) {//数+1
                    counter++;
                }
            }
        }
        //根据质因数个数创建恰好容纳下全部质因数的数组，再次遍历，赋值
        int[] All = new int[counter];
        counter = 0;
        for (int i = 2; i <= n; i++) {
            if (n % i == 0) {
                if (is_primeFactor(i)) {//数+1
                    All[counter++] = i;
                }
            }
        }
        return All;
    }

    public boolean is_primeFactor(int i) {//判断因数是否为素数
        if (i == 2) return true;
        //只要从2到i-1有一个数能整除该因数，该因数就不是素数。
        for (int j = 2; j < i; j++) {
            if (i % j == 0) return false;
        }
        //从2到i-1都不能整除i，i是素数无疑，返回真
        return true;
    }

    public static void main(String[] args) {
        int n;
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入您想要分解的数：");
        n = scanner.nextInt();
        int[] All = new work_1().getFactor(n);
        System.out.println("计算完毕，数" + n + "有" + All.length + "个素因数，如下所示：");
        for (int i = 0; i < All.length; i++) {
            System.out.print(All[i] + "\t");
        }
    }
}