package Work.main5;

//WordCount.java
import java.io.File;
import java.util.Scanner;

public class Wordcount {
    private File file;
    public Wordcount(File file) {
        this.file=file;
    }

    public int retrieve(){ //检索单词数量
        int num = 0;
        try {
            Scanner in = new Scanner(file);
            while (in.hasNextLine()) {//逐行读取信息
                String temp = in.nextLine();

                String[] row;
                row = temp.split(" |!|,");//以!、,或空格为分隔符，划分单词。本来打算将.也作为分隔符，但一使用就出错
                for (int i = 0; i < row.length; i++) {//去掉换行符，将.替换为空格
                    row[i]=row[i].replaceAll("['\r\n']]","");
                    row[i]=row[i].replace('.',' ' );
                }
                for(int i=0;i<row.length;i++) {
                    System.out.println(row[i]);
                }
                num += row.length; //逐行累加记录单词数量
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return num;
    }

    public static void main(String[] args) {
        try {
            File file = new File("C:\\Users\\Sce_dio\\IdeaProjects\\Java_Course\\src\\Work\\main5\\infor.txt");
            System.out.println("目标文档中的单词数量为：" + new Wordcount(file).retrieve());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
