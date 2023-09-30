import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.security.*;

public class UserAuthentication {
    Link_MySql linkMySql = new Link_MySql();
    User user = new User();
    Hash hash = new Hash();
    ArrayList arrayListUser = new ArrayList<User>();

    Scanner scanner= new Scanner(System.in);

    public boolean registerUser(User user) throws ClassNotFoundException {

        boolean flag = true;


            try {
                //System.out.println("注册要求：\n用户名不小于5个字符\n密码长度大于8个字符，必须包含大小写字母、数字和标点符号\n电话号码为中国大陆手机号\n邮箱要求输入域名");
                if (isValidPassword(user.password) && isValidUsername(user.username)&& isValidPhoneNumber(user.phoneNumber) && isValidEmail(user.email) ) {
                    Connection connection = linkMySql.linkMysql();
                   user.UserRegistrationTime=User.setUserRegistrationTime();

                    String sql = "INSERT INTO users (username, password,phoneNumber,email,UserRegistrationTime,userLevel,totalAmountSpent,numberOfPurchases) VALUES ( ?, ?,?,?,?,?,?,?)";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, user.username);
                    user.password=hash.md5(user.password);
                    preparedStatement.setString(2, user.password);
                    preparedStatement.setString(3, user.phoneNumber);
                    preparedStatement.setString(4, user.email);
                    preparedStatement.setString(5, user.UserRegistrationTime);
                    preparedStatement.setString(6, "铜牌用户");
                    preparedStatement.setString(7, "0元");
                    preparedStatement.setInt(8, 0);
                    int rowsAffected = preparedStatement.executeUpdate();
                    return rowsAffected > 0;
                } else {
                    return false;
                }
            } catch (SQLException e) {
                return false;
            }
    }

    private void openRegistrationWindow(User user) {
        System.out.println(user.username+"chuangkou");
        System.out.println(user.password);
        JFrame registrationFrame;
        JPanel registrationPanel;
        JTextField phoneNumberField;
        JTextField emailField;
        JButton registerButton;
        registrationFrame = new JFrame("注册窗口:请认真填写");
        registrationPanel = new JPanel();
        registrationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 关闭弹窗时不退出应用程序
        registrationFrame.setBounds(600,400,300, 150);
        registrationFrame.add(registrationPanel);

        registrationPanel.setLayout(new GridLayout(3, 2));

        JLabel phoneNumberLabel = new JLabel("电话:");
        registrationPanel.add(phoneNumberLabel);
        phoneNumberField = new JTextField(20);
        registrationPanel.add(phoneNumberField);
        JLabel emailLabel = new JLabel("邮箱:");
        registrationPanel.add(emailLabel);
        emailField = new JTextField(20);
        registrationPanel.add(emailField);



        registerButton = new JButton("注册");

        registrationPanel.add(registerButton);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                user.phoneNumber=phoneNumberField.getText();
                user.email=emailField.getText();
                System.out.println(user.phoneNumber);
            }
        });
        registrationFrame.setVisible(true);
    }

//

    public  boolean authenticateUser(User user) throws SQLException, ClassNotFoundException {
            Connection connection = linkMySql.linkMysql();
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            user.password=hash.md5(user.password);
            preparedStatement.setString(1, user.username);
            preparedStatement.setString(2, user.password);
        System.out.println(user.password);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
    }

    public  boolean authenticateUser(Admin admin) throws SQLException, ClassNotFoundException {//管理员登陆验证
        Connection connection = linkMySql.linkMysql();
        String sql = "SELECT * FROM admin WHERE username = ? AND password = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, admin.username);
        preparedStatement.setString(2, admin.password);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }
    public  boolean authenticateUser(Reception reception )throws SQLException, ClassNotFoundException {//前台登陆验证
        Connection connection = linkMySql.linkMysql();
        String sql = "SELECT * FROM reception WHERE username = ? AND password = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, reception.username);
        preparedStatement.setString(2, reception.password);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }
    public  boolean authenticateUser(Manager manager) throws SQLException, ClassNotFoundException {//经理登陆验证
        Connection connection = linkMySql.linkMysql();
        String sql = "SELECT * FROM manager WHERE username = ? AND password = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, manager.username);
        preparedStatement.setString(2, manager.password);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }

    // 验证账号是否为>=5个字符
    public static boolean isValidUsername(String username) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9_]{5,10}$");//10个字符>=账号>=5个字符
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }

    public static boolean isValidPhoneNumber(String username) {
        Pattern pattern = Pattern.compile("^(?:\\d{3}-\\d{8}|\\d{4}-\\d{7}|\\d{11})$");//电话号码是否有效
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }
    public static boolean isValidEmail(String username) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");//邮箱验证
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }

    public static boolean isValidPassword(String password) {
        //8<密码<15位且只包含字母和数字
        Pattern pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-zA-Z]).{8,15}$");
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public  ArrayList<User> readMySqL() throws SQLException, ClassNotFoundException {
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
                user.password = resultSet.getString("password");

                arrayListUser.add(user);
            }

            // 关闭连接
            resultSet.close();
            preparedStatement.close();
            connection.close();

            return arrayListUser;
        }
        else{
            System.out.println("连接失败!!!");
            return null;
        }
    }


    public static void main(String[] args) throws ClassNotFoundException {
        UserAuthentication userAuthentication = new UserAuthentication();
        User user1 = new User();
        user1.username="1";
        user1.password="1";
        userAuthentication.registerUser(user1);

    }

}

