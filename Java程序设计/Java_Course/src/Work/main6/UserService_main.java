package Work.main6;

//写一个sql数据库，增删改查四个操作实现两个即可
//UserService_main.java
import java.util.List;
import java.util.Scanner;

public class UserService_main {
    public static void main(String[] args) {
        //初始化一个数据库对象
        UserDatebase userDatebase = new UserDatebase();
        String name,id;
        boolean flag=true;
        User user;
        Scanner in =new Scanner(System.in);
        System.out.println("Dear users, welcome to the student status management system. Here, you can manage student information (only student number and name are supported).");
        //控制台输出循环
        while(flag){
            System.out.print("\n"+"The function options are as follows:"+"\n"+"1. Insert student information" +
                    "\n"+"2. Query all students' information"+"\n"+"3. Delete student information" +
                    "\n"+"4. Modify student information"+"\n"+"5. Query individual student information"+"\n"+"-1. Exit the system"+"\n"+"Please select an operation option:");
            int num =in.nextInt();
            boolean isExist=false;
            boolean isSus=true;
            switch (num){
                //插入
                case 1:
                    System.out.println("Please enter the information of the added student:");
                    System.out.print("Student number:");
                    id=in.next();
                    while(id.length()!=12||userDatebase.findById(id)!=null){
                        System.out.print("Little brother, the student number is illegal. It can only be 12 digits, and it can't duplicate the existing one in the database. Please re-enter (enter - 1 to return to the previous level operation):");
                        id=in.next();
                        if(id.equals("-1")){
                            isSus=false;
                            break;
                        }
                    }
                    if(isSus) {
                        System.out.print("full name:");
                        name = in.next();
                        user = new User(id, name);
                        userDatebase.add(user);
                    }
                    break;
                //查询所有信息
                case 2:
                    List<User> list= userDatebase.findAll();
                    int count=1;
                    System.out.println("Serial number"+"\t"+"student number"+"\t"+"name");
                    for (User u : list) {
                        System.out.println((count++)+"\t"+u.getUserId()+"\t"+u.getUserName());
                        if(u!=null) isExist=true;
                    }
                    if(!isExist){
                        System.out.println("Database empty, no information!");
                    }
                    break;
                //删除
                case 3:
                    System.out.print("Please enter the student ID to be deleted:");
                    id=in.next();
                    while(userDatebase.findById(id)==null){
                        System.out.print("Little brother, the student information you want to delete does not exist! Please re-enter the student number (enter - 1 to return to the previous level operation)");
                        id=in.next();
                        if(id.equals("-1")){
                            isSus=false;
                            break;
                        }
                    }
                    if(isSus) {
                        userDatebase.delete(id);
                        System.out.println("Successfully deleted");
                    }
                    break;
                case 4:
                    System.out.print("Please enter the student number to be modified:");
                    id=in.next();
                    while(userDatebase.findById(id)==null){
                        System.out.print("Little brother, the student information you want to modify does not exist! Please re-enter the student number (enter - 1 to return to the previous level operation)");
                        id=in.next();
                        if(id.equals("-1")){
                            isSus=false;
                            break;
                        }
                    }
                    if(isSus) {
                        System.out.print("Revised Name:");
                        name = in.next();
                        user = new User(id, name);
                        userDatebase.update(user);
                    }
                    break;
                case 5:
                    System.out.print("Please enter the student number you want to find:");
                    id=in.next();
                    while(userDatebase.findById(id)==null){
                        System.out.print("Little brother, the student information you are looking for does not exist! Please re-enter the student number (enter - 1 to return to the previous level operation)");
                        id=in.next();
                        if(id.equals("-1")){
                            isSus=false;
                            break;
                        }
                    }
                    if(isSus) {
                        user = userDatebase.findById(id);
                        System.out.println(user.getUserId() + "\t" + user.getUserName());
                    }
                    break;
                case -1:
                    break;
            }
        }
    }
}
