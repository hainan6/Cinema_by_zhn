import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Admin {
    String username;
    String password;

    static Link_MySql linkMySql = new Link_MySql();

    ArrayList<User> arrayListUser = new ArrayList();

    UserAuthentication userAuthentication = new UserAuthentication();

    boolean updateUserPassword(User user) throws SQLException, ClassNotFoundException {
        Connection connection = linkMySql.linkMysql();
        Hash hash = new Hash();

        if(connection != null){
            try{
                String updateSQL = "UPDATE users SET password = ? WHERE username = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);//执行sql语句

                preparedStatement.setString(1, hash.md5(user.password));

                preparedStatement.setString(2, user.username); // 根据ID更新数据

                // 执行更新操作
                int rowsUpdated = preparedStatement.executeUpdate();

                connection.close();//关闭连接
                return rowsUpdated>0;

            }
            catch (Exception e){
                e.printStackTrace();
            }
            return false;
        }
        else
            return false;

    }

    boolean updateAdminPassword(String newPassword) throws SQLException, ClassNotFoundException {
        Connection connection = linkMySql.linkMysql();

        if(connection != null){
            try{
                String updateSQL = "UPDATE admin SET password = ? WHERE username = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);//执行sql语句

                preparedStatement.setString(1, newPassword);
                preparedStatement.setString(2, "admin"); // 根据ID更新数据

                // 执行更新操作
                int rowsUpdated = preparedStatement.executeUpdate();

                connection.close();//关闭连接

                return rowsUpdated>0;

            }
            catch (Exception e){
                e.printStackTrace();
                return false;
            }
        }
        else {
            return false;
        }
    }

    public ArrayList<User> readUserInformation() throws SQLException, ClassNotFoundException {

        Connection connection = linkMySql.linkMysql();
        if(connection != null){
            String selectSQL = "SELECT * FROM users";
            // 创建PreparedStatement对象，用于执行SQL查询
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);

            // 执行查询操作
            ResultSet resultSet = preparedStatement.executeQuery();

            // 遍历结果集并输出数据
            while (resultSet.next()) {
                User user = new User();
                user.username = resultSet.getString("username");
                user.phoneNumber = resultSet.getString("phoneNumber");
                user.email = resultSet.getString("email");
                user.UserRegistrationTime = resultSet.getString("UserRegistrationTime");
                user.userLevel = resultSet.getString("userLevel");
                user.totalAmountSpent = resultSet.getString("totalAmountSpent");
                user.numberOfPurchases = resultSet.getInt("numberOfPurchases");
                arrayListUser.add(user);
            }

            // 关闭连接
            resultSet.close();
            preparedStatement.close();
            connection.close();

            return arrayListUser;
        }
        else{
            return null;
        }

    }

    public static boolean deleteUserByUsername(String username) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // 创建数据库连接
            connection = linkMySql.linkMysql();

            // SQL语句，根据用户名删除用户信息
            String deleteSQL = "DELETE FROM users WHERE username = ?";
            preparedStatement = connection.prepareStatement(deleteSQL);

            // 设置参数
            preparedStatement.setString(1, username);

            // 执行SQL语句
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

}
