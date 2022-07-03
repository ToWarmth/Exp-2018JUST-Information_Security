package Work.main6;

//UserDatebase.java
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDatebase {

    //添加一个用户信息
    public void add(User user){
        //声明JDBC对象
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            //获取连接
            connection = JdbcUtil.getConnection();
            //获取处理器
            pstmt = connection.prepareStatement("insert into BroFan values(?,?)");
            //注入参数
            pstmt.setString(1, user.getUserId());
            pstmt.setString(2, user.getUserName());
            //执行新增
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.close(connection, pstmt, null);
        }
    }

    public void update(User user){
        //声明JDBC对象
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            //获取连接
            connection = JdbcUtil.getConnection();
            //获取处理器
            pstmt = connection.prepareStatement("update BroFan set name=? where userid=?");
            //注入参数
            pstmt.setString(2, user.getUserId());
            pstmt.setString(1, user.getUserName());
            //执行新增
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.close(connection, pstmt, null);
        }
    }

    //查询用户信息
    public User findById(String userId){
        //声明返回值
        User user = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            //获取连接
            connection = JdbcUtil.getConnection();
            //获取处理器
            pstmt = connection.prepareStatement("select * from BroFan where userid=?");
            pstmt.setString(1, userId);
            //执行查询
            rs = pstmt.executeQuery();
            while(rs.next()){
                //创建user对象，用于存储查询的数据
                user = new User(rs.getString(1),rs.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.close(connection, pstmt, rs);
        }
        return user;
    }

    //查询用户信息
    public List<User> findAll(){
        //声明返回值
        List list = new ArrayList<User>();
        User user = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            connection = JdbcUtil.getConnection();     //获取连接
            pstmt = connection.prepareStatement("select * from BroFan");  //获取处理器
            rs = pstmt.executeQuery();   //执行查询
            while(rs.next()){//创建user对象并放入list
                user = new User(rs.getString(1),rs.getString(2));
                list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.close(connection, pstmt, rs);
        }
        return list;
    }

    //根据编号删除用户
    public String delete(String userId){
        User user = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        String result="";
        try {

            connection = JdbcUtil.getConnection();
            pstmt = connection.prepareStatement("delete from BroFan where userid=?");
            pstmt.setString(1, userId);
            //执行查询
            result=String.valueOf(pstmt.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.close(connection, pstmt,null);
        }
        return result;
    }
}
