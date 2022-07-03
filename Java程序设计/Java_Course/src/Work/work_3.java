package Work;

//归并排序的实现
public class work_3 {
    int[] toSorted;//待排序的数组

    work_3(int[] a) {//构造函数，初始化待排序数组
        toSorted=a;
    }

    public void display() {//显示待排序的数组
        for (int i = 0; i < this.toSorted.length; i++) {
            System.out.println(this.toSorted[i]);
        }
    }

    public void Merge( int low, int mid, int high) {
        //low为第1有序区的第1个元素，i指向第1个元素, mid为第1有序区的最后1个元素
        int i = low, j = mid + 1, k = 0;  //mid+1为第2有序区第1个元素，j指向第1个元素
        int[] temp = new int[high - low + 1]; //temp数组暂存合并的有序序列
        while (i <= mid && j <= high)//顺序选取两个有序区的较小元素，存储到t数组中
        {
            if (this.toSorted[i] <= this.toSorted[j])//较小的先存入temp中
                temp[k++] = this.toSorted[i++];
            else
                temp[k++] = this.toSorted[j++];
        }
        while (i <= mid)//若比较完之后，第一个有序区仍有剩余，则直接复制到t数组中
            temp[k++] = this.toSorted[i++];
        while (j <= high)//同上
            temp[k++] = this.toSorted[j++];
        for (i = low, k = 0; i <= high; i++, k++)//将排好序的存回arr中low到high这区间
            this.toSorted[i] = temp[k];
    }

    void MergeSort(int low, int high) {//二路归并排序的递归实现
        if (low < high)//分组
        {
            int mid = (low + high) / 2;
            MergeSort(low, mid);
            MergeSort(mid + 1, high);
            Merge(low, mid, high);
        }
    }

    public static void main(String[] args) {
        int[] tsData = {12, 8, 4, 5, 6, 7};
        work_3 ts = new work_3(tsData);
        ts.MergeSort( 0, 5);
        ts.display();
    }
}
