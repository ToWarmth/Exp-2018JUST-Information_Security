package Work.main6;

//JdbcUtil.java
import java.sql.*;

public class JdbcUtil {
    static{
        try {
            //加载驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //获取连接
    public static Connection getConnection(){
        Connection connection = null;
        try {
            //获取连接——信息与自己的数据库内容对应
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai", "root", "123456");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    //关闭数据库对象
    public static void close(Connection connection, PreparedStatement pstmt, ResultSet rs){
        try {
            if(rs != null){
                rs.close();
            }
            if(pstmt != null){
                pstmt.close();
            }
            if(connection != null){
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

