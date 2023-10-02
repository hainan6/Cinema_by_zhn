import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Scanner;
public class User {
     String username;
     String phoneNumber;
     String password;
     String email;
     int numberOfPurchases;
     String totalAmountSpent;
     String userLevel;
     String UserRegistrationTime;
     int loginAttempts;
     boolean is_locked;


     static Link_MySql linkMySql = new Link_MySql();

     public static Scanner scanner = new Scanner(System.in);

    @Override
    public String toString() {
        return "用户名:"+this.username+"\n手机号："+this.phoneNumber+"\n邮箱："+this.email+"\n注册时间："+this.UserRegistrationTime+"\n用户等级："+this.userLevel+"\n消费总金额："+this.totalAmountSpent+"\n消费总次数："+this.numberOfPurchases;
    }

     User creatUser(){
        Date currentDate = new Date();
        // 创建 SimpleDateFormat 对象以指定日期时间格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 使用 SimpleDateFormat 格式化日期时间
        String registrationTime = dateFormat.format(currentDate);
        User user = new User();
        System.out.println("请输入账号:");
        user.username=User.scanner.next();
        System.out.println("请输入密码:");
        user.password=User.scanner.next();
        System.out.println("请输入电话号码");
        user.phoneNumber=User.scanner.next();
        System.out.println("请输入邮箱");
        user.email=User.scanner.next();
        user.UserRegistrationTime = registrationTime;
        return user;
    }
    public static String setUserRegistrationTime(){
        Date currentDate = new Date();
        // 创建 SimpleDateFormat 对象以指定日期时间格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 使用 SimpleDateFormat 格式化日期时间
        String registrationTime = dateFormat.format(currentDate);
        return registrationTime;
    }

    void purchaseTicket(){

    }

    boolean updatePassword() throws SQLException, ClassNotFoundException {

        UserAuthentication userAuthentication = new UserAuthentication();
        userAuthentication.authenticateUser(new User());
        Connection connection = linkMySql.linkMysql();
        if(connection != null){
            try {
                    String updateSQL = "UPDATE users SET password = ? WHERE username = ?";
                    PreparedStatement preparedStatement1 = connection.prepareStatement(updateSQL);//执行sql语句

                    // 设置要更新的值和条件
                    preparedStatement1.setInt(1, 50);

                    preparedStatement1.setString(2, "1"); // 根据ID更新数据

                    // 执行更新操作
                    int rowsUpdated = preparedStatement1.executeUpdate();

                    if (rowsUpdated > 0) {
                        System.out.println("更新成功，影响行数: " + rowsUpdated);
                    } else {
                        System.out.println("未更新任何行");
                    }

                    connection.close();//关闭连接
            }
            catch (Exception e){
                e.printStackTrace();
            }

            return true;
        }
        else
            return false;
    }

    boolean isLocked(User user) throws SQLException, ClassNotFoundException {
        Connection connection = linkMySql.linkMysql();

        int loginAttempts = 0;

        boolean tag = false;//标记是否需要被锁定

        if(connection != null){
            try {
                String selectSQL = "SELECT * FROM users";
                // 创建PreparedStatement对象，用于执行SQL查询
                PreparedStatement preparedStatementforFind = connection.prepareStatement(selectSQL);
                // 执行查询操作
                ResultSet resultSet = preparedStatementforFind.executeQuery();
                // 遍历结果集并输出数据
                while (resultSet.next()) {
                    if (resultSet.getString("username").equals(user.username)){
                        if (resultSet.getInt("loginAttempts") >= 5){
                            tag = true;
                        }
                        break;
                    }
                }
                // 关闭连接


                if (tag){
                    String updateSQL = "UPDATE users SET is_locked = ? WHERE username = ?";
                    PreparedStatement preparedStatementforIncrease = connection.prepareStatement(updateSQL);//执行sql语句

                    // 设置要更新的值和条件
                    preparedStatementforIncrease.setBoolean(1, true);

                    preparedStatementforIncrease.setString(2, user.username); // 根据ID更新数据

                    // 执行更新操作
                    int rowsUpdated = preparedStatementforIncrease.executeUpdate();

                    connection.close();//关闭连接
                    resultSet.close();
                    preparedStatementforFind.close();

                    return rowsUpdated>0;
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        return false;
    }

 boolean increaseLoginAttempts(User user) throws SQLException, ClassNotFoundException {//登录次数
        Connection connection = linkMySql.linkMysql();
        int loginAttempts = 0;

            try {

                String selectSQL = "SELECT * FROM users";
                // 创建PreparedStatement对象，用于执行SQL查询
                PreparedStatement preparedStatementforFind = connection.prepareStatement(selectSQL);
                // 执行查询操作
                ResultSet resultSet = preparedStatementforFind.executeQuery();
                // 遍历结果集并输出数据
                while (resultSet.next()) {
                   if (resultSet.getString("username").equals(user.username)){
                       loginAttempts=resultSet.getInt("loginAttempts");
                       break;
                   }
                }

                // 关闭连接
                resultSet.close();
                preparedStatementforFind.close();

                String updateSQL = "UPDATE users SET loginAttempts = ? WHERE username = ?";
                PreparedStatement preparedStatementforIncrease = connection.prepareStatement(updateSQL);//执行sql语句

                // 设置要更新的值和条件
                preparedStatementforIncrease.setInt(1, ++loginAttempts);

                preparedStatementforIncrease.setString(2, user.username); // 根据ID更新数据

                // 执行更新操作
                int rowsUpdated = preparedStatementforIncrease.executeUpdate();
                connection.close();//关闭连接
                return rowsUpdated>0;
            }
            catch (Exception e){
                e.printStackTrace();
                return false;
            }
 }

    boolean initLoginAttempts(User user) throws SQLException, ClassNotFoundException {//初始化登录次数
        Connection connection = linkMySql.linkMysql();
        try {
            String updateSQL = "UPDATE users SET loginAttempts = ? WHERE username = ?";
            PreparedStatement preparedStatementforIncrease = connection.prepareStatement(updateSQL);//执行sql语句
            // 设置要更新的值和条件
            preparedStatementforIncrease.setInt(1, 0);//初始化登录次数
            preparedStatementforIncrease.setString(2, user.username); // 根据ID更新数据
            // 执行更新操作
            int rowsUpdated = preparedStatementforIncrease.executeUpdate();
            connection.close();//关闭连接
            return rowsUpdated>0;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        User user = new User();
        user.username="sss";
        user.loginAttempts=5;

        user.isLocked(user);
    }

}
