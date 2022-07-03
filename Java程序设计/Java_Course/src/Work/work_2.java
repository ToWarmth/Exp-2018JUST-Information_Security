package Work;

import java.util.Arrays;

class work_2 //集合
{
    public int QVector[];//这里的集合其实也就是整型数组。
    int Len, Size;

    work_2()//构造函数，初始化一个长度为10个单位的集合
    {
        QVector = new int[10];
        Len = 0;//下标
        Size = 10;//集合大小
    }

    public void Extend()//扩充集合大小
    {
        QVector = Arrays.copyOf(QVector, Size + 5);
        Size += 5;
    }

    public void Insert(int Data, int Pos)//往集合中插入新元素
    {
        if (Len == Size)//集合满了就扩容
            Extend();

        for (int i = Len - 1; i >= Pos; i--)//没满就往Pos位置插元素Data
            QVector[i + 1] = QVector[i];
        QVector[Pos] = Data;

        Len++;
    }

    public int Delete(int Pos)//删除集合下标为Pos的元素
    {
        int t = QVector[Pos];
        for (int i = Pos + 1; i < Len; i++)
            QVector[i - 1] = QVector[i];
        Len--;
        return t;
    }

    public int Search(int Data)//寻找集合中数据为Data的元素
    {
        int Pos = -1;
        for (int i = 0; i < Len; i++)
            if (Data == QVector[i]) {
                Pos = i;
                break;
            }
        return Pos;
    }

    public void Print() {
        for (int i = 0; i < Len; i++)
            System.out.println(QVector[i]);
    }

    public work_2 Intersection(work_2 b) {//求交集
        work_2 c = new work_2();
        if (this.Len >= b.Len)
            for (int i = 0; i <= b.Len; i++) {
                if (this.Search(b.QVector[i]) != -1) c.QVector[Len++] = b.QVector[i];
            }
        if (this.Len < b.Len)
            for (int i = 0; i <= this.Len; i++) {
                if (b.Search(this.QVector[i]) != -1) c.QVector[Len++] = this.QVector[i];
            }
        return c;
    }

    public work_2 Union(work_2 b) {//求并集
        for (int i = 0; i <= b.Len; i++) {
            if (this.Search(b.QVector[i]) == -1) this.QVector[Len++] = b.QVector[i];
        }
        return this;
    }

    public boolean isEqual(work_2 a, work_2 b) {//判断两个集合是否相等
        if (a.Len != b.Len) return false;//集合元素个数不一样肯定不相等
        for (int i = 0; i <= a.Len; i++) {//只要一个集合中的任一元素在另一个集合里找不到，就不相等
            if (b.Search(a.QVector[i]) == -1) return false;
        }
        return true;
    }

    public work_2 Difference(work_2 a, work_2 b) {//求集合a相对于集合b的差
        work_2 c = new work_2();
        for (int i = 0; i <= a.Len; i++) {
            if (b.Search(a.QVector[i]) == -1) c.QVector[Len++] = a.QVector[i];
        }
        return c;
    }

    public work_2 Symmetrical_difference(work_2 b) {//求集合a与集合b的对称差
        work_2 c = Difference(Intersection(b), Union(b));//交集相对于并集的差即为对称差
        return c;
    }

    public static void main(String[] args) {
        work_2 test_a = new work_2();
        work_2 test_b = new work_2();
        test_a.Insert(10, 0);
        test_a.Insert(5, 1);
        test_a.Insert(6, 2);
        test_b.Insert(7, 0);
        test_b.Insert(5, 1);
    }
}


