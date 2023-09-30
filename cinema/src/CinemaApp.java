import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class CinemaApp {
    private JFrame logAndSignframe;
    private JPanel panel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private JComboBox<String> userTypeComboBox;

    UserAuthentication userAuthentication = new UserAuthentication();

    public void cinemaApp() {

        logAndSignframe = new JFrame("云大影院");
        panel = new JPanel();
        logAndSignframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        logAndSignframe.setBounds(600,400,350, 200);
        logAndSignframe.add(panel);

        panel.setLayout(new GridBagLayout()); // 使用GridBagLayout来更精确控制布局

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(5, 5, 5, 5); // 设置组件之间的间距

        // 添加账号和密码的JLabel，通过constraints来控制位置和大小
        JLabel usernameLabel = new JLabel("账号:");
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        panel.add(usernameLabel, constraints);

        JLabel passwordLabel = new JLabel("密码:");
        constraints.gridy = 1;
        panel.add(passwordLabel, constraints);

        usernameField = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        panel.add(usernameField, constraints);

        passwordField = new JPasswordField(20);
        constraints.gridy = 1;
        panel.add(passwordField, constraints);

        JLabel userTypeLabel = new JLabel("用户类型:");
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        panel.add(userTypeLabel, constraints);

        String[] userTypes = {"用户", "管理员", "经理", "前台"};
        userTypeComboBox = new JComboBox<>(userTypes);
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        panel.add(userTypeComboBox, constraints);

        loginButton = new JButton("登录");
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        panel.add(loginButton, constraints);

        registerButton = new JButton("注册");
        constraints.gridx = 3;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        panel.add(registerButton, constraints);

        // 使用空白Label占据一行以实现登录和注册按钮各占一半的效果
        JLabel blankLabel = new JLabel("");
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 3;
        panel.add(blankLabel, constraints);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = passwordField.getText();
                // 根据用户类型进行不同的处理
                String selectedUserType = (String) userTypeComboBox.getSelectedItem();

                if (selectedUserType.equals("用户")) {
                    // 用户登录逻辑
                    boolean flag = false;
                    UserAuthentication userAuthentication = new UserAuthentication();
                    User user = new User();
                    user.username = username;
                    user.password = password;

                    try {
                        flag = userAuthentication.authenticateUser(user);
                        if(flag == true){
                            showLoginSuccessPopup();
                        }else{
                            showLoginFailPopup();
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    } catch (ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                } else if (selectedUserType.equals("管理员")) {
                    // todo 管理员登录逻辑
                    boolean flag = false;
                    UserAuthentication userAuthentication = new UserAuthentication();
                    Admin admin= new Admin();
                    admin.username = username;
                    admin.password = password;
                    try {
                        flag = userAuthentication.authenticateUser(admin);
                        if(flag == true){
                            JOptionPane.showMessageDialog(logAndSignframe, "登陆成功");
                            adminSystem();
                            logAndSignframe.dispose();
                        }else{
                            showLoginFailPopup();
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    } catch (ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                } else if (selectedUserType.equals("经理")) {
                    // todo 经理登录逻辑
                    boolean flag = false;
                    UserAuthentication userAuthentication = new UserAuthentication();
                    Manager manager= new Manager();
                    manager.username = username;
                    manager.password = password;
                    try {
                        flag = userAuthentication.authenticateUser(manager);
                        if(flag == true){
                            managerSystem();
                        }else{
                            showLoginFailPopup();
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    } catch (ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                } else if (selectedUserType.equals("前台")) {
                    // todo 前台登录逻辑
                    boolean flag = false;
                    UserAuthentication userAuthentication = new UserAuthentication();
                    Reception reception= new Reception();
                    reception.username = username;
                    reception.password = password;
                    try {
                        flag = userAuthentication.authenticateUser(reception);
                        if(flag == true){
                            showLoginSuccessPopup();
                        }else{
                            showLoginFailPopup();
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    } catch (ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        }
        );


        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // todo 注册逻辑
                String username = usernameField.getText();
                String password = passwordField.getText();

                String selectedUserType = (String) userTypeComboBox.getSelectedItem();

                //todo 注册逻辑
                if (selectedUserType.equals("用户")) {
                    // todo 用户注册逻辑
                    User user = new User();
                    user.username = username;
                    user.password = password;

                    JFrame addInfoUserframe = new JFrame("用户信息输入");
                    addInfoUserframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    addInfoUserframe.setSize(400, 200);
                    addInfoUserframe.setLayout(new GridLayout(2, 1));

                    JLabel phoneLabel = new JLabel("手机号码:");
                    JLabel emailLabel = new JLabel("邮箱:");
                    JTextField phoneField = new JTextField();
                    JTextField emailField = new JTextField();
                    JButton confirmButton = new JButton("确定");
                    JButton backButton = new JButton("返回");

                    JPanel inputPanel = new JPanel();
                    inputPanel.setLayout(new GridLayout(2, 2));
                    inputPanel.add(phoneLabel);
                    inputPanel.add(phoneField);
                    inputPanel.add(emailLabel);
                    inputPanel.add(emailField);

                    JPanel buttonPanel = new JPanel();
                    buttonPanel.setLayout(new FlowLayout());
                    buttonPanel.add(confirmButton);
                    buttonPanel.add(backButton);

                    addInfoUserframe.add(inputPanel);
                    addInfoUserframe.add(buttonPanel);

                    confirmButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            user.phoneNumber = phoneField.getText();
                            user.email = emailField.getText();
//todo 注册
                            // 在这里执行输入处理逻辑，可以使用phone和email的值
                            try {
                                if (userAuthentication.registerUser(user)) {
                                    JOptionPane.showMessageDialog(addInfoUserframe, "注册成功！");
                                    addInfoUserframe.dispose();
                                }
                                else{
                                    JOptionPane.showMessageDialog(addInfoUserframe, "不符合要求注册要求：\n用户名不小于5个字符\n密码长度大于8个字符，必须包含大小写字母、数字和标点符号\n电话号码为中国大陆手机号\n邮箱要求输入域名");
                                    addInfoUserframe.dispose();
                                }
                            } catch (ClassNotFoundException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                    });

                    backButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // 返回上一步，关闭当前窗口
                            addInfoUserframe.dispose();
                        }
                    });

                    addInfoUserframe.setVisible(true);
                } else
                    //showRegisterNoPower();
                JOptionPane.showMessageDialog(logAndSignframe,"您无此权限，请联系系统管理员!");
            }
        });

        logAndSignframe.setVisible(true);
    }


    private void showLoginSuccessPopup() {//登陆成功
        logAndSignframe.setVisible(false);
        JFrame successFrame = new JFrame("登录成功");
        successFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 关闭弹出窗口时不影响主窗口
        successFrame.setBounds(600,400,300, 100);
        JLabel successLabel = new JLabel("登录成功！");
        successLabel.setHorizontalAlignment(SwingConstants.CENTER);
        successFrame.add(successLabel);
        successFrame.setVisible(true);
    }
    private void showLoginFailPopup() {//登陆失败
        JFrame successFrame = new JFrame("");
        successFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 关闭弹出窗口时不影响主窗口
        successFrame.setBounds(600,400,300, 100);
        JLabel successLabel = new JLabel("登录失败或密码错误！");
        successLabel.setHorizontalAlignment(SwingConstants.CENTER);
        successFrame.add(successLabel);
        successFrame.setVisible(true);
    }

    private void showLoginNoUsernameAndPassword() {
        JDialog dialog = new JDialog(logAndSignframe, "登录提示", true);
        dialog.setLayout(new BorderLayout());

        JLabel label = new JLabel("请输入账号和密码");
        dialog.add(label, BorderLayout.NORTH);

        JButton okButton = new JButton("确定");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose(); // 关闭对话框
            }
        });

        dialog.add(okButton, BorderLayout.SOUTH);

        dialog.setBounds(400,400,200, 100);
        dialog.setVisible(true);
    }
    private void showRegisterNoPower() {
        // 创建一个新的Swing窗口
        JFrame frame = new JFrame("权限提示");
        //创建一个标签用于显示提示信息
        JLabel label = new JLabel("您无此权限，请联系管理员");

        JButton closeButton = new JButton("关闭");

        // 添加关闭按钮的事件处理程序
        closeButton.addActionListener(e -> {
            frame.dispose(); // 关闭窗口
        });

        // 创建一个面板，并将标签和按钮添加到面板中
        JPanel panel = new JPanel();
        panel.add(label);
        panel.add(closeButton);
        // 将面板添加到窗口中
        frame.getContentPane().add(panel);
        // 设置窗口大小
        frame.setBounds(620,420,300, 100);
        // 设置窗口可见
        frame.setVisible(true);
        // 创建一个按钮，用于关闭弹窗

    }

    private void openSystem(User user){
        logAndSignframe.dispose();
    }
    private static void adminSystem() {
        JFrame adminSystemframe = new JFrame("用户管理系统");
        adminSystemframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        adminSystemframe.setSize(400, 200);
        adminSystemframe.setLayout(new GridLayout(3, 1));

        JButton passwordManagementButton = new JButton("密码管理");
        JButton userManagementButton = new JButton("用户管理");
        JButton logoutButton = new JButton("退出登录");

        adminSystemframe.add(passwordManagementButton);
        adminSystemframe.add(userManagementButton);
        adminSystemframe.add(logoutButton);

        passwordManagementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adminSystemframe.setVisible(false);
                // 创建密码管理界面
                JFrame passwordManagementFrame = new JFrame("密码管理");
                passwordManagementFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                passwordManagementFrame.setSize(300, 150);
                passwordManagementFrame.setLayout(new GridLayout(3, 1));

                JButton changePasswordButton = new JButton("修改自身密码");
                JButton resetPasswordButton = new JButton("重置指定用户密码");
                JButton backButton = new JButton("返回");

                passwordManagementFrame.add(changePasswordButton);
                passwordManagementFrame.add(resetPasswordButton);
                passwordManagementFrame.add(backButton);

                changePasswordButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // 在这里处理修改自身密码的逻辑，可以打开新的对话框
                        //JOptionPane.showMessageDialog(passwordManagementFrame, "执行修改自身密码操作");
                        JDialog passwordChangeDialog = new JDialog(adminSystemframe, "修改管理员密码");
                        passwordChangeDialog.setVisible(true);

                        passwordManagementFrame.setVisible(false);
                        //todo managerframe 可视化

                        passwordChangeDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        passwordChangeDialog.setSize(300, 150);
                        passwordChangeDialog.setLayout(new GridLayout(4, 2));

                        JLabel currentPasswordLabel = new JLabel("原密码:");
                        JTextField currentPasswordField = new JPasswordField();
                        JLabel newPasswordLabel = new JLabel("新密码:");
                        JTextField newPasswordField = new JPasswordField();
                        JButton confirmButton = new JButton("确定");
                        JButton backButton = new JButton("返回");

                        passwordChangeDialog.add(currentPasswordLabel);
                        passwordChangeDialog.add(currentPasswordField);
                        passwordChangeDialog.add(newPasswordLabel);
                        passwordChangeDialog.add(newPasswordField);
                        passwordChangeDialog.add(confirmButton);
                        passwordChangeDialog.add(backButton);

                        confirmButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                String prePassword = currentPasswordField.getText();
                                String newPassword = newPasswordField.getText();

                                UserAuthentication userAuthentication = new UserAuthentication();
                                Admin admin = new Admin();
                                admin.username="admin";
                                admin.password=prePassword;
                                // 验证原密码是否正确
                                try {
                                    if (userAuthentication.authenticateUser(admin)) {//此处用登陆验证原密码正确
                                        //确认新密码符合要求
                                        if (UserAuthentication.isValidPassword(newPassword)) {
                                            // 修改密码
                                            if(admin.updateAdminPassword(newPassword)){
                                                JOptionPane.showMessageDialog(passwordChangeDialog, "密码修改成功！");
                                                passwordChangeDialog.dispose();
                                            }
                                            else JOptionPane.showMessageDialog(passwordChangeDialog, "连接故障，请重试！");
                                        }
                                        else {
                                            JOptionPane.showMessageDialog(passwordChangeDialog, "新密码不符合规范 8<密码<15位且只包含字母和数字，请重试！");
                                            currentPasswordField.setText("");
                                            newPasswordField.setText("");
                                        }

                                    } else {
                                        JOptionPane.showMessageDialog(passwordChangeDialog, "原密码不正确，请重试！");
                                        currentPasswordField.setText("");
                                        newPasswordField.setText("");
                                    }
                                } catch (SQLException ex) {
                                    throw new RuntimeException(ex);
                                } catch (ClassNotFoundException ex) {
                                    throw new RuntimeException(ex);
                                }
                            }
                        });

                        backButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                // 返回上一步，关闭当前对话框
                                passwordChangeDialog.dispose();
                                passwordManagementFrame.setVisible(true);
                            }
                        });
                    }
                });

                resetPasswordButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // 在这里处理重置指定用户密码的逻辑，可以打开新的对话框

                        //JOptionPane.showMessageDialog(passwordManagementFrame, "执行重置指定用户密码操作");
                        // 在这里处理修改自身密码的逻辑，可以打开新的对话框
                        //JOptionPane.showMessageDialog(passwordManagementFrame, "执行修改自身密码操作");
                        JDialog userPasswordChangeDialog = new JDialog(adminSystemframe, "重置用户密码");//todo 指定密码需要用哈希MD5加密
                        userPasswordChangeDialog.setVisible(true);

                        passwordManagementFrame.setVisible(false);
                        //todo managerframe 可视化


                        userPasswordChangeDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        userPasswordChangeDialog.setSize(300, 150);
                        userPasswordChangeDialog.setLayout(new GridLayout(4, 2));

                        JLabel usernameLabel = new JLabel("用户名:");
                        JTextField usernameField = new JTextField();
                        JLabel newPasswordLabel = new JLabel("新密码:");
                        JTextField newPasswordField = new JPasswordField();
                        JButton confirmButton = new JButton("确定");
                        JButton backButton = new JButton("返回");

                        userPasswordChangeDialog.add(usernameLabel);
                        userPasswordChangeDialog.add(usernameField);
                        userPasswordChangeDialog.add(newPasswordLabel);
                        userPasswordChangeDialog.add(newPasswordField);
                        userPasswordChangeDialog.add(confirmButton);
                        userPasswordChangeDialog.add(backButton);

                        confirmButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                //todo 重置用户名密码
                                String username = usernameField.getText();
                                String newPassword = newPasswordField.getText();

                                UserAuthentication userAuthentication = new UserAuthentication();
                                User user = new User();
                                Admin admin = new Admin();
                                user.username=username;
                                user.password=newPassword;
                                // 验证原密码是否正确
                                try {
                                        //确认新密码符合要求
                                        if (UserAuthentication.isValidPassword(newPassword)) {
                                            // 修改密码
                                            if(admin.updateUserPassword(user)){
                                                JOptionPane.showMessageDialog(userPasswordChangeDialog, "密码重置成功！");
                                                userPasswordChangeDialog.dispose();
                                                passwordManagementFrame.setVisible(true);
                                            }
                                            else{
                                                JOptionPane.showMessageDialog(userPasswordChangeDialog, "系统内无该名用户！");
                                                usernameField.setText("");
                                                newPasswordField.setText("");
                                            }

                                        }
                                        else {
                                            JOptionPane.showMessageDialog(userPasswordChangeDialog, "新密码不符合规范 8<密码<15位且只包含字母和数字，请重试！");
                                            usernameField.setText("");
                                            newPasswordField.setText("");
                                        }


                                } catch (SQLException ex) {
                                    throw new RuntimeException(ex);
                                } catch (ClassNotFoundException ex) {
                                    throw new RuntimeException(ex);
                                }
                            }
                        });

                        backButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                // 返回上一步，关闭当前对话框
                                userPasswordChangeDialog.dispose();
                                passwordManagementFrame.setVisible(true);
                            }
                        });
                    }
                });

                backButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        passwordManagementFrame.dispose();//返回上一步
                        adminSystemframe.setVisible(true);
                    }
                });

                passwordManagementFrame.setVisible(true);
            }
        });

        userManagementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 在这里处理用户管理的逻辑，可以打开新的窗口或对话框
                //JOptionPane.showMessageDialog(frame, "执行用户管理操作");
                // 创建用户管理对话框
                adminSystemframe.setVisible(false);
                JDialog userManagementDialog = new JDialog(adminSystemframe, "用户管理");
                userManagementDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                userManagementDialog.setSize(300, 150);
                userManagementDialog.setLayout(new GridLayout(4, 1));

                JButton listUsersButton = new JButton("列出所有用户信息");
                JButton deleteUserButton = new JButton("删除用户信息");
                JButton findUserButton = new JButton("查询用户信息");
                JButton backButton = new JButton("返回上一步");

                userManagementDialog.add(listUsersButton);
                userManagementDialog.add(deleteUserButton);
                userManagementDialog.add(findUserButton);
                userManagementDialog.add(backButton);

                listUsersButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Admin admin = new Admin();
                        ArrayList<User> userList = new ArrayList<>();
                        try {
                             userList=admin.readUserInformation() ;
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        } catch (ClassNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }
                        // 在这里执行列出所有用户信息的逻辑
                        // 这里仅显示一个弹窗来演示
                        //JOptionPane.showMessageDialog(userManagementDialog, "列出所有用户信息");
                        // 创建用户列表对话框
                        JDialog userListDialog = new JDialog(adminSystemframe, "用户列表");
                        userListDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        userListDialog.setLayout(new BorderLayout());

                        JTextArea textArea = new JTextArea(10, 30);
                        textArea.setEditable(false); // 禁止编辑文本区域

                        // 将用户列表信息添加到文本区域
                        StringBuilder userListText = new StringBuilder();
                        for (User user : userList) {
                            userListText.append("用户名: ").append(user.username).append("\n");
                            userListText.append("手机号: ").append(user.phoneNumber).append("\n");
                            userListText.append("邮箱: ").append(user.email).append("\n");
                            userListText.append("注册时间: ").append(user.UserRegistrationTime).append("\n");
                            userListText.append("用户等级：").append(user.userLevel).append("\n");
                            userListText.append("消费总金额：").append(user.totalAmountSpent).append("\n");
                            userListText.append("消费总次数：").append(user.numberOfPurchases).append("\n");
                            userListText.append("_________________________\n");
                        }
                        textArea.setText(userListText.toString());

                        JScrollPane scrollPane = new JScrollPane(textArea);
                        userListDialog.add(scrollPane, BorderLayout.CENTER);

                        userListDialog.pack();
                        userListDialog.setVisible(true);
                    }
                });

                deleteUserButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // 在这里执行删除用户信息的逻辑
                        // 这里仅显示一个弹窗来演示
                        //JOptionPane.showMessageDialog(userManagementDialog, "删除用户信息");
                        JFrame deleteUserframe = new JFrame("用户信息删除");
                        deleteUserframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        deleteUserframe.setSize(400, 150);
                        deleteUserframe.setLayout(new GridLayout(2, 1));

                        JLabel usernameLabel = new JLabel("请输入用户名:");
                        JTextField usernameField = new JTextField(20); // 增加文本框大小
                        JButton confirmButton = new JButton("确认");
                        JButton backButton = new JButton("返回上一步");

                        JPanel inputPanel = new JPanel(new GridBagLayout()); // 使用GridBagLayout布局
                        GridBagConstraints constraints = new GridBagConstraints();
                        constraints.insets = new Insets(5, 5, 5, 5); // 增加间距
                        inputPanel.add(usernameLabel, constraints);

                        constraints.gridx = 1;
                        inputPanel.add(usernameField, constraints);

                        JPanel buttonPanel = new JPanel();
                        buttonPanel.setLayout(new FlowLayout());
                        buttonPanel.add(confirmButton);
                        buttonPanel.add(backButton);

                        deleteUserframe.add(inputPanel);
                        deleteUserframe.add(buttonPanel);

                        confirmButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                String username = usernameField.getText();
                                // 弹出确认删除窗口
                                JDialog deletedialog = new JDialog(deleteUserframe, "确认删除用户");
                                deletedialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                                deletedialog.setSize(300, 150);
                                deletedialog.setLayout(new GridLayout(2, 1));

                                JLabel confirmationLabel = new JLabel("确认删除用户 " + username + " 的信息吗？");
                                JButton deleteButton = new JButton("删除");
                                JButton cancelButton = new JButton("取消");

                                JPanel confirmationPanel = new JPanel();
                                confirmationPanel.setLayout(new FlowLayout());
                                confirmationPanel.add(confirmationLabel);

                                JPanel buttonPanel = new JPanel();
                                buttonPanel.setLayout(new FlowLayout());
                                buttonPanel.add(deleteButton);
                                buttonPanel.add(cancelButton);

                                deletedialog.add(confirmationPanel);
                                deletedialog.add(buttonPanel);

                                deleteButton.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        // 在这里执行删除用户信息的逻辑
                                        //todo delete user
                                        if (Admin.deleteUserByUsername(username)){
                                            JOptionPane.showMessageDialog(deletedialog, "用户信息已删除。");
                                        }
                                       else{
                                            JOptionPane.showMessageDialog(deletedialog, "暂无该名用户信息。");
                                        }
                                        deletedialog.dispose();
                                    }
                                });

                                cancelButton.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        // 取消删除，关闭确认删除窗口
                                        deletedialog.dispose();
                                    }
                                });

                                deletedialog.setVisible(true);
                            }
                        });

                        backButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                // 返回上一步，关闭当前窗口
                                deleteUserframe.dispose();
                                userManagementDialog.setVisible(true);
                            }
                        });

                        deleteUserframe.setVisible(true);
                    }

                });

                findUserButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // 在这里执行查询用户信息的逻辑
                        JFrame findUserframe = new JFrame("用户信息查询");
                        findUserframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        findUserframe.setSize(400, 150);
                        findUserframe.setLayout(new GridLayout(2, 1));

                        JLabel usernameLabel = new JLabel("请输入用户名:");
                        JTextField usernameField = new JTextField(20); // 增加文本框大小

                        JButton confirmButton = new JButton("确认");
                        JButton backButton = new JButton("返回上一步");

                        JPanel inputPanel = new JPanel(new GridBagLayout()); // 使用GridBagLayout布局
                        GridBagConstraints constraints = new GridBagConstraints();
                        constraints.insets = new Insets(5, 5, 5, 5); // 增加间距
                        inputPanel.add(usernameLabel, constraints);

                        constraints.gridx = 1;
                        inputPanel.add(usernameField, constraints);

                        JPanel buttonPanel = new JPanel();
                        buttonPanel.setLayout(new FlowLayout());
                        buttonPanel.add(confirmButton);
                        buttonPanel.add(backButton);

                        findUserframe.add(inputPanel);
                        findUserframe.add(buttonPanel);

                        findUserframe.setVisible(true);

                        confirmButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                String findUsername = usernameField.getText();
                                ArrayList<User> userArrayList = new ArrayList<>();
                                Admin admin = new Admin();
                                try {
                                     userArrayList = admin.readUserInformation();
                                } catch (SQLException ex) {
                                    throw new RuntimeException(ex);
                                } catch (ClassNotFoundException ex) {
                                    throw new RuntimeException(ex);
                                }
                                boolean tag = true;
                                for (int i = 0; i < userArrayList.size(); i++) {
                                    userArrayList.get(i).toString();

                                    if (userArrayList.get(i).username.equals(findUsername)) {
                                        JOptionPane.showMessageDialog(findUserframe, userArrayList.get(i).toString());
                                        tag = false;
                                        findUserframe.dispose();
                                        userManagementDialog.setVisible(true);
                                   }

                                }
                                if (tag){
                                    JOptionPane.showMessageDialog(findUserframe, "暂无该用户!");
                                    findUserframe.dispose();
                                    userManagementDialog.setVisible(true);
                                }

                            }
                        });

                        backButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                // 返回上一步，关闭当前窗口
                                findUserframe.dispose();
                                userManagementDialog.setVisible(true);
                            }
                        });
                    }
                });

                backButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // 返回上一步，关闭当前对话框
                        userManagementDialog.dispose();
                        adminSystemframe.setVisible(true);
                    }
                });
                userManagementDialog.setVisible(true);
            }
    });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 在这里处理退出登录的逻辑，可以关闭当前窗口或返回登录界面
                JOptionPane.showMessageDialog(adminSystemframe, "退出登录");
                adminSystemframe.dispose();
            }
        });

        adminSystemframe.setVisible(true);
    }

    static void managerSystem(){
        JFrame managerSystemframe = new JFrame("经理系统");
        managerSystemframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        managerSystemframe.setSize(400, 200);
        managerSystemframe.setLayout(new GridLayout(3, 1));

        JButton moivesManagementButton = new JButton("影片管理");
        JButton arrangeMoivesManagementButton = new JButton("排片管理");
        JButton logoutButton = new JButton("退出登录");

        managerSystemframe.add(moivesManagementButton);
        managerSystemframe.add(arrangeMoivesManagementButton);
        managerSystemframe.add(logoutButton);

        moivesManagementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                managerSystemframe.setVisible(false);
                JDialog moivesManagementDialog = new JDialog(managerSystemframe, "用户管理");
                moivesManagementDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                moivesManagementDialog.setSize(300, 300);
                moivesManagementDialog.setLayout(new GridLayout(6, 1));

                JButton listMoivesButton = new JButton("列出所有影片信息");
                JButton addMoivesButton = new JButton("添加影片信息");
                JButton updateMoivesButton = new JButton("修改影片信息");
                JButton deleteMoivesButton = new JButton("删除影片信息");
                JButton findMoivesButton = new JButton("查询影片信息");
                JButton backButton = new JButton("返回上一步");



                moivesManagementDialog.add(listMoivesButton);
                moivesManagementDialog.add(addMoivesButton);
                moivesManagementDialog.add(updateMoivesButton);
                moivesManagementDialog.add(deleteMoivesButton);
                moivesManagementDialog.add(findMoivesButton);
                moivesManagementDialog.add(backButton);

                listMoivesButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Moive moive = new Moive();
                        ArrayList<Moive> moivesList = new ArrayList<>();

                        JTextArea moivesListArea = new JTextArea(30, 50);
                        moivesListArea.setEditable(false); // 禁止编辑文本区域
                        moivesListArea.setLineWrap(true);
                        moivesListArea.setText("");

                        try {
                            moivesList=moive.readInformation();
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        } catch (ClassNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }
                        JDialog moivesListDialog = new JDialog(managerSystemframe, "电影列表");
                        moivesListDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        moivesListDialog.setLayout(new BorderLayout());
                        moivesListDialog.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosing(WindowEvent e) {
                                moivesListArea.setText("");
                                moivesListDialog.dispose();
                            }
                        });
                        moivesListArea.setText("");
                        // 将用户列表信息添加到文本区域
                        StringBuilder userListText = new StringBuilder();
                        for (Moive moive1 : moivesList) {
                            userListText.append("电影名: ").append(moive1.name).append("\n");
                            userListText.append("导演: ").append(moive1.director).append("\n");
                            userListText.append("主演: ").append(moive1.star).append("\n");
                            userListText.append("简介: ").append(moive1.synopsis).append("\n");
                            userListText.append("时长：").append(moive1.duration).append("min\n");
                            userListText.append("_________________________\n");
                        }
                        moivesListArea.setText(userListText.toString());
                        moivesListArea.paintImmediately(moivesListArea.getBounds());
                        JScrollPane scrollPane = new JScrollPane(moivesListArea);
                        moivesListDialog.add(scrollPane, BorderLayout.CENTER);

                        moivesListDialog.pack();
                        moivesListDialog.setVisible(true);
                    }
                });

                addMoivesButton.addActionListener(new ActionListener() {//添加电影信息
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        moivesManagementDialog.setVisible(false);
                        JTextField movieNameField, directorField, actorsField, durationField;
                         JTextArea descriptionArea;
                         JDialog moiveInfoinput = new JDialog(managerSystemframe,"电影信息输入");
                        moiveInfoinput.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                        moiveInfoinput.setSize(400, 300);
                        moiveInfoinput.setLocationRelativeTo(null);

                        JPanel panel = new JPanel(new GridBagLayout());
                        GridBagConstraints constraints = new GridBagConstraints();
                        constraints.fill = GridBagConstraints.HORIZONTAL;
                        constraints.insets = new Insets(5, 5, 5, 5);

                        JLabel movieNameLabel = new JLabel("电影名:");
                        constraints.gridx = 0;
                        constraints.gridy = 0;
                        panel.add(movieNameLabel, constraints);

                        movieNameField = new JTextField(20);
                        constraints.gridx = 1;
                        constraints.gridy = 0;
                        panel.add(movieNameField, constraints);

                        JLabel directorLabel = new JLabel("导演名:");
                        constraints.gridx = 0;
                        constraints.gridy = 1;
                        panel.add(directorLabel, constraints);

                        directorField = new JTextField(20);
                        constraints.gridx = 1;
                        constraints.gridy = 1;
                        panel.add(directorField, constraints);

                        JLabel actorsLabel = new JLabel("主演:");
                        constraints.gridx = 0;
                        constraints.gridy = 2;
                        panel.add(actorsLabel, constraints);

                        actorsField = new JTextField(20);
                        constraints.gridx = 1;
                        constraints.gridy = 2;
                        panel.add(actorsField, constraints);

                        JLabel descriptionLabel = new JLabel("简介:");
                        constraints.gridx = 0;
                        constraints.gridy = 3;
                        panel.add(descriptionLabel, constraints);

                        descriptionArea = new JTextArea(4, 20);
                        descriptionArea.setLineWrap(true);
                        descriptionArea.setWrapStyleWord(true);
                        JScrollPane scrollPane = new JScrollPane(descriptionArea);
                        constraints.gridx = 1;
                        constraints.gridy = 3;
                        panel.add(scrollPane, constraints);

                        JLabel durationLabel = new JLabel("时长:");
                        constraints.gridx = 0;
                        constraints.gridy = 4;
                        panel.add(durationLabel, constraints);

                        durationField = new JTextField(10);
                        constraints.gridx = 1;
                        constraints.gridy = 4;
                        panel.add(durationField, constraints);

                        JButton cancelButton = new JButton("返回上一步");
                        constraints.gridx = 0;
                        constraints.gridy = 5;
                        panel.add(cancelButton, constraints);

                        JButton okButton = new JButton("确定");
                        constraints.gridx = 1;
                        constraints.gridy = 5;
                        panel.add(okButton, constraints);

                        moiveInfoinput.setVisible(true);

                        // 添加确定按钮的点击事件监听器
                        okButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                // 在这里获取输入的电影信息并进行处理
                                Moive moive = new Moive();

                                moive.name = movieNameField.getText();
                                moive.director = directorField.getText();
                                moive.star = actorsField.getText();
                                moive.synopsis = descriptionArea.getText();
                                moive.duration = durationField.getText();

                                try {
                                    if(moive.isEmpty()){
                                        JOptionPane.showMessageDialog(moiveInfoinput, "请完整输入电影信息!");
                                    }
                                    else{
                                        if (moive.insertMoives(moive)) {
                                            JOptionPane.showMessageDialog(moiveInfoinput, "添加影片成功!");
                                            moiveInfoinput.dispose();
                                            moivesManagementDialog.setVisible(true);
                                        }
                                        else{
                                            JOptionPane.showMessageDialog(moiveInfoinput, "添加影片失败!");
                                            moiveInfoinput.dispose();
                                            moivesManagementDialog.setVisible(true);
                                        }
                                    }

                                } catch (SQLException ex) {
                                    throw new RuntimeException(ex);
                                } catch (ClassNotFoundException ex) {
                                    throw new RuntimeException(ex);
                                }

                            }
                        });

                        // 添加取消按钮的点击事件监听器
                        cancelButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                // 返回上一步，关闭弹窗
                                moiveInfoinput.dispose();
                                moivesManagementDialog.setVisible(true);
                            }
                        });

                        moiveInfoinput.getContentPane().add(panel);
                    }
                });

                updateMoivesButton.addActionListener(new ActionListener() {// 修改电影信息
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // TODO: 2023-09-29  电影信息修改
                        JTextField movieNameField, directorField, actorsField, durationField;
                        JTextArea descriptionArea;
                        JDialog moiveInfoUpdate = new JDialog(managerSystemframe,"电影信息更新");
                        moiveInfoUpdate.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        moiveInfoUpdate.setSize(400, 300);
                        moiveInfoUpdate.setLocationRelativeTo(null);

                        JPanel panel = new JPanel(new GridBagLayout());
                        GridBagConstraints constraints = new GridBagConstraints();
                        constraints.fill = GridBagConstraints.HORIZONTAL;
                        constraints.insets = new Insets(5, 5, 5, 5);

                        JLabel movieNameLabel = new JLabel("电影名:");
                        constraints.gridx = 0;
                        constraints.gridy = 0;
                        panel.add(movieNameLabel, constraints);

                        movieNameField = new JTextField(20);
                        constraints.gridx = 1;
                        constraints.gridy = 0;
                        panel.add(movieNameField, constraints);

                        JLabel directorLabel = new JLabel("导演名:");
                        constraints.gridx = 0;
                        constraints.gridy = 1;
                        panel.add(directorLabel, constraints);

                        directorField = new JTextField(20);
                        constraints.gridx = 1;
                        constraints.gridy = 1;
                        panel.add(directorField, constraints);

                        JLabel actorsLabel = new JLabel("主演:");
                        constraints.gridx = 0;
                        constraints.gridy = 2;
                        panel.add(actorsLabel, constraints);

                        actorsField = new JTextField(20);
                        constraints.gridx = 1;
                        constraints.gridy = 2;
                        panel.add(actorsField, constraints);

                        JLabel descriptionLabel = new JLabel("简介:");
                        constraints.gridx = 0;
                        constraints.gridy = 3;
                        panel.add(descriptionLabel, constraints);

                        descriptionArea = new JTextArea(4, 20);
                        descriptionArea.setLineWrap(true);
                        descriptionArea.setWrapStyleWord(true);
                        JScrollPane scrollPane = new JScrollPane(descriptionArea);
                        constraints.gridx = 1;
                        constraints.gridy = 3;
                        panel.add(scrollPane, constraints);

                        JLabel durationLabel = new JLabel("时长:");
                        constraints.gridx = 0;
                        constraints.gridy = 4;
                        panel.add(durationLabel, constraints);

                        durationField = new JTextField(10);
                        constraints.gridx = 1;
                        constraints.gridy = 4;
                        panel.add(durationField, constraints);

                        JButton cancelButton = new JButton("返回上一步");
                        constraints.gridx = 0;
                        constraints.gridy = 5;
                        panel.add(cancelButton, constraints);

                        JButton okButton = new JButton("确定");
                        constraints.gridx = 1;
                        constraints.gridy = 5;
                        panel.add(okButton, constraints);

                        moiveInfoUpdate.setVisible(true);

                        // 添加确定按钮的点击事件监听器
                        okButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                // 在这里获取输入的电影信息并进行处理
                                Moive moive = new Moive();

                                moive.name = movieNameField.getText();
                                moive.director = directorField.getText();
                                moive.star = actorsField.getText();
                                moive.synopsis = descriptionArea.getText();
                                moive.duration = durationField.getText();

                                try {
                                    if(moive.isEmpty()){
                                        JOptionPane.showMessageDialog(moiveInfoUpdate, "请完整输入电影信息!");
                                    }
                                    else{
                                        if (moive.insertMoives(moive)) {
                                            JOptionPane.showMessageDialog(moiveInfoUpdate, "添加影片成功!");
                                            moiveInfoUpdate.dispose();
                                            moivesManagementDialog.setVisible(true);
                                        }
                                        else{
                                            JOptionPane.showMessageDialog(moiveInfoUpdate, "添加影片失败!");
                                            moiveInfoUpdate.dispose();
                                            moivesManagementDialog.setVisible(true);
                                        }
                                    }

                                } catch (SQLException ex) {
                                    throw new RuntimeException(ex);
                                } catch (ClassNotFoundException ex) {
                                    throw new RuntimeException(ex);
                                }

                            }
                        });

                        // 添加取消按钮的点击事件监听器
                        cancelButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                // 返回上一步，关闭弹窗
                                moiveInfoUpdate.dispose();
                            }
                        });

                        moiveInfoUpdate.getContentPane().add(panel);
                        //todo 修改信息
                    }

                });

                deleteMoivesButton.addActionListener(new ActionListener() {//   删除影片
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //todo 删除影片
                        JFrame deleteMoiveframe = new JFrame("电影信息删除");
                        deleteMoiveframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        deleteMoiveframe.setSize(400, 150);
                        deleteMoiveframe.setLayout(new GridLayout(2, 1));

                        JLabel moivenameLabel = new JLabel("请输入电影名:");
                        JTextField moivenameField = new JTextField(20); // 增加文本框大小
                        JButton confirmButton = new JButton("确认");
                        JButton backButton = new JButton("返回上一步");

                        JPanel inputPanel = new JPanel(new GridBagLayout()); // 使用GridBagLayout布局
                        GridBagConstraints constraints = new GridBagConstraints();
                        constraints.insets = new Insets(5, 5, 5, 5); // 增加间距
                        inputPanel.add(moivenameLabel, constraints);

                        constraints.gridx = 1;
                        inputPanel.add(moivenameField, constraints);

                        JPanel buttonPanel = new JPanel();
                        buttonPanel.setLayout(new FlowLayout());
                        buttonPanel.add(confirmButton);
                        buttonPanel.add(backButton);

                        deleteMoiveframe.add(inputPanel);
                        deleteMoiveframe.add(buttonPanel);

                        confirmButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                String moivename = moivenameField.getText();
                                // 弹出确认删除窗口
                                JDialog deletedialog = new JDialog(deleteMoiveframe, "确认删除信息");
                                deletedialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                                deletedialog.setSize(300, 150);
                                deletedialog.setLayout(new GridLayout(2, 1));

                                JLabel confirmationLabel = new JLabel("确认删除电影 " + moivename + " 的信息吗？");
                                JButton deleteButton = new JButton("删除");
                                JButton cancelButton = new JButton("取消");

                                JPanel confirmationPanel = new JPanel();
                                confirmationPanel.setLayout(new FlowLayout());
                                confirmationPanel.add(confirmationLabel);

                                JPanel buttonPanel = new JPanel();
                                buttonPanel.setLayout(new FlowLayout());
                                buttonPanel.add(deleteButton);
                                buttonPanel.add(cancelButton);

                                deletedialog.add(confirmationPanel);
                                deletedialog.add(buttonPanel);

                                deleteButton.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        // 在这里执行删除信息的逻辑
                                        //todo delete moive
                                        if (Moive.deleteMoiveByname(moivename)){
                                            JOptionPane.showMessageDialog(deletedialog, "电影信息已删除。");
                                            deleteMoiveframe.dispose();
                                        }
                                        else{
                                            JOptionPane.showMessageDialog(deletedialog, "暂无该电影信息。");
                                            deleteMoiveframe.dispose();
                                        }
                                        deletedialog.dispose();
                                    }
                                });

                                cancelButton.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        // 取消删除，关闭确认删除窗口
                                        deletedialog.dispose();
                                    }
                                });

                                deletedialog.setVisible(true);
                            }
                        });

                        backButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                // 返回上一步，关闭当前窗口
                                deleteMoiveframe.dispose();
                                moivesManagementDialog.setVisible(true);
                            }
                        });

                        deleteMoiveframe.setVisible(true);
                        //todo 删除影片
                    }
                });

                findMoivesButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //todo 查找电影信息
                        // 在这里执行查询用户信息的逻辑
                        JFrame findMoiveframe = new JFrame("电影信息查询");
                        findMoiveframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        findMoiveframe.setSize(400, 150);
                        findMoiveframe.setLayout(new GridLayout(2, 1));

                        JLabel moiveNameLabel = new JLabel("请输入用户名:");
                        JTextField moiveNameField = new JTextField(20); // 增加文本框大小

                        JButton confirmButton = new JButton("确认");
                        JButton backButton = new JButton("返回上一步");

                        JPanel inputPanel = new JPanel(new GridBagLayout()); // 使用GridBagLayout布局
                        GridBagConstraints constraints = new GridBagConstraints();
                        constraints.insets = new Insets(5, 5, 5, 5); // 增加间距
                        inputPanel.add(moiveNameLabel, constraints);

                        constraints.gridx = 1;
                        inputPanel.add(moiveNameField, constraints);

                        JPanel buttonPanel = new JPanel();
                        buttonPanel.setLayout(new FlowLayout());
                        buttonPanel.add(confirmButton);
                        buttonPanel.add(backButton);

                        findMoiveframe.add(inputPanel);
                        findMoiveframe.add(buttonPanel);

                        findMoiveframe.setVisible(true);

                        confirmButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                String findMoiveName = moiveNameField.getText();
                                Moive moive = new Moive();
                                ArrayList<Moive> moivesArrayList = new ArrayList<>();

                                try {
                                    moivesArrayList = moive.readInformation();
                                } catch (SQLException ex) {
                                    throw new RuntimeException(ex);
                                } catch (ClassNotFoundException ex) {
                                    throw new RuntimeException(ex);
                                }

                                boolean tag = true;
                                for (int i = 0; i < moivesArrayList.size(); i++) {
                                    if (moivesArrayList.get(i).name.equals(findMoiveName)) {
                                        JOptionPane.showMessageDialog(findMoiveframe, moivesArrayList.get(i).toString());
                                        tag = false;
                                        findMoiveframe.dispose();
                                        moivesManagementDialog.setVisible(true);
                                    }

                                }
                                if (tag){
                                    JOptionPane.showMessageDialog(findMoiveframe, "暂无该电影信息!");
                                    findMoiveframe.dispose();
                                    moivesManagementDialog.setVisible(true);
                                }

                            }
                        });

                        backButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                // 返回上一步，关闭当前窗口
                                findMoiveframe.dispose();
                                moivesManagementDialog.setVisible(true);
                            }
                        });


                        //todo 查找电影信息
                    }
                });

                backButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        moivesManagementDialog.dispose();
                        managerSystemframe.setVisible(true);
                    }
                });
                moivesManagementDialog.setVisible(true);

            }
        });



        arrangeMoivesManagementButton.addActionListener(new ActionListener() {//排片管理
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(managerSystemframe, "退出登录");
                managerSystemframe.dispose();
            }
        });

        managerSystemframe.setVisible(true);

    }

    public static void main(String[] args) {
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                new CinemaApp();
//            }
//        });

        CinemaApp cinemaApp = new CinemaApp();
        //cinemaApp.cinemaApp();
        CinemaApp.managerSystem();
    }
}

