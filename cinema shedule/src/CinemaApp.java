import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
        logAndSignframe.setBounds(600, 400, 350, 200);
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

                                              if (username.isEmpty() || password.isEmpty()) {
                                                  JOptionPane.showMessageDialog(logAndSignframe, "请输入账号和密码！");
                                              } else {
                                                  switch (selectedUserType) {
                                                      case "用户" -> {
                                                          // 用户登录逻辑
                                                          boolean flag = false;
                                                          UserAuthentication userAuthentication = new UserAuthentication();
                                                          User user = new User();
                                                          user.username = username;
                                                          user.password = password;

                                                          User userdemo = new User();

                                                          try {
                                                              //
                                                              if (!userdemo.increaseLoginAttempts(user)) {
                                                                  JOptionPane.showMessageDialog(logAndSignframe, "暂无该用户信息！");
                                                                  logAndSignframe.dispose();
                                                                  cinemaApp();
                                                              } else {
                                                                  if (userdemo.isLocked(user)) {
                                                                      JOptionPane.showMessageDialog(logAndSignframe, "该账户已被锁定，请联系管理员");
                                                                  } else {
                                                                      flag = userAuthentication.authenticateUser(user);
                                                                      if (flag) {
                                                                          JOptionPane.showMessageDialog(logAndSignframe, "登陆成功！");
                                                                          userdemo.initLoginAttempts(user);//初始化登录次数
                                                                          userSystem();
                                                                          logAndSignframe.dispose();
                                                                      } else {
                                                                          JOptionPane.showMessageDialog(logAndSignframe, "密码错误，登陆失败");
                                                                      }
                                                                  }

                                                              }

                                                          } catch (SQLException ex) {
                                                              throw new RuntimeException(ex);
                                                          } catch (ClassNotFoundException ex) {
                                                              throw new RuntimeException(ex);
                                                          }
                                                      }
                                                      case "管理员" -> {
                                                          boolean flag = false;
                                                          UserAuthentication userAuthentication = new UserAuthentication();
                                                          Admin admin = new Admin();
                                                          admin.username = username;
                                                          admin.password = password;
                                                          try {
                                                              flag = userAuthentication.authenticateUser(admin);
                                                              if (flag) {
                                                                  JOptionPane.showMessageDialog(logAndSignframe, "登陆成功");
                                                                  adminSystem();
                                                                  logAndSignframe.dispose();
                                                              } else {
                                                                  showLoginFailPopup();
                                                              }
                                                          } catch (SQLException ex) {
                                                              throw new RuntimeException(ex);
                                                          } catch (ClassNotFoundException ex) {
                                                              throw new RuntimeException(ex);
                                                          }
                                                      }
                                                      case "经理" -> {
                                                          boolean flag = false;
                                                          UserAuthentication userAuthentication = new UserAuthentication();
                                                          Manager manager = new Manager();
                                                          manager.username = username;
                                                          manager.password = password;
                                                          try {
                                                              flag = userAuthentication.authenticateUser(manager);
                                                              if (flag == true) {
                                                                  JOptionPane.showMessageDialog(logAndSignframe, "登陆成功！");
                                                                  managerSystem();
                                                                  logAndSignframe.dispose();
                                                              } else {
                                                                  showLoginFailPopup();
                                                              }
                                                          } catch (SQLException ex) {
                                                              throw new RuntimeException(ex);
                                                          } catch (ClassNotFoundException ex) {
                                                              throw new RuntimeException(ex);
                                                          }
                                                      }
                                                      case "前台" -> {
                                                          boolean flag = false;
                                                          UserAuthentication userAuthentication = new UserAuthentication();
                                                          Reception reception = new Reception();
                                                          reception.username = username;
                                                          reception.password = password;
                                                          try {
                                                              flag = userAuthentication.authenticateUser(reception);
                                                              if (flag) {
                                                                  JOptionPane.showMessageDialog(logAndSignframe, "登陆成功！");
                                                                  receptionSystem();
                                                                  logAndSignframe.dispose();
                                                              } else {
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
                                          }
                                      }
        );


        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = passwordField.getText();

                String selectedUserType = (String) userTypeComboBox.getSelectedItem();
                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(logAndSignframe, "请输入账号和密码！");
                } else {
                    if (selectedUserType.equals("用户")) {
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
                                // 在这里执行输入处理逻辑，可以使用phone和email的值
                                try {
                                    if (userAuthentication.registerUser(user)) {
                                        JOptionPane.showMessageDialog(addInfoUserframe, "注册成功！");
                                        addInfoUserframe.dispose();
                                    } else {
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
                    } else {
                        //showRegisterNoPower();
                        JOptionPane.showMessageDialog(logAndSignframe, "您无此权限，请联系系统管理员!");
                    }
                }
            }
        });

        logAndSignframe.setVisible(true);
    }


    private void showLoginSuccessPopup() {//登陆成功
        logAndSignframe.setVisible(false);
        JFrame successFrame = new JFrame("登录成功");
        successFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 关闭弹出窗口时不影响主窗口
        successFrame.setBounds(600, 400, 300, 100);
        JLabel successLabel = new JLabel("登录成功！");
        successLabel.setHorizontalAlignment(SwingConstants.CENTER);
        successFrame.add(successLabel);
        successFrame.setVisible(true);
    }

    private void showLoginFailPopup() {//登陆失败
        JFrame successFrame = new JFrame("");
        successFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 关闭弹出窗口时不影响主窗口
        successFrame.setBounds(600, 400, 300, 100);
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

        dialog.setBounds(400, 400, 200, 100);
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
        frame.setBounds(620, 420, 300, 100);
        // 设置窗口可见
        frame.setVisible(true);
        // 创建一个按钮，用于关闭弹窗

    }

    private void openSystem(User user) {
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
                                admin.username = "admin";
                                admin.password = prePassword;
                                // 验证原密码是否正确
                                try {
                                    if (userAuthentication.authenticateUser(admin)) {//此处用登陆验证原密码正确
                                        //确认新密码符合要求
                                        if (UserAuthentication.isValidPassword(newPassword)) {
                                            // 修改密码
                                            if (admin.updateAdminPassword(newPassword)) {
                                                JOptionPane.showMessageDialog(passwordChangeDialog, "密码修改成功！");
                                                passwordChangeDialog.dispose();
                                            } else
                                                JOptionPane.showMessageDialog(passwordChangeDialog, "连接故障，请重试！");
                                        } else {
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
                        //TODO 当输入的原密码 和 新密码为空时
                        confirmButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                //todo 重置用户名密码
                                String username = usernameField.getText();
                                String newPassword = newPasswordField.getText();

                                UserAuthentication userAuthentication = new UserAuthentication();
                                User user = new User();
                                Admin admin = new Admin();
                                user.username = username;
                                user.password = newPassword;
                                // 验证原密码是否正确
                                try {
                                    //确认新密码符合要求
                                    if (UserAuthentication.isValidPassword(newPassword)) {
                                        // 修改密码
                                        if (admin.updateUserPassword(user)) {
                                            JOptionPane.showMessageDialog(userPasswordChangeDialog, "密码重置成功！");
                                            userPasswordChangeDialog.dispose();
                                            passwordManagementFrame.setVisible(true);
                                        } else {
                                            JOptionPane.showMessageDialog(userPasswordChangeDialog, "系统内无该名用户！");
                                            usernameField.setText("");
                                            newPasswordField.setText("");
                                        }

                                    } else {
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
                            userList = admin.readUserInformation();
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

                                        if (Admin.deleteUserByUsername(username)) {
                                            JOptionPane.showMessageDialog(deletedialog, "用户信息已删除。");
                                        } else {
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
                                if (tag) {
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

    private static void managerSystem() {
        JFrame managerSystemframe = new JFrame("经理系统");
        managerSystemframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        managerSystemframe.setSize(400, 200);
        managerSystemframe.setLayout(new GridLayout(3, 1));

        JButton moviesManagementButton = new JButton("影片管理");
        JButton arrangeMoviesManagementButton = new JButton("排片管理");
        JButton logoutButton = new JButton("退出登录");

        managerSystemframe.add(moviesManagementButton);
        managerSystemframe.add(arrangeMoviesManagementButton);
        managerSystemframe.add(logoutButton);

        moviesManagementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                managerSystemframe.setVisible(false);
                JDialog moviesManagementDialog = new JDialog(managerSystemframe, "影片管理");
                moviesManagementDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                moviesManagementDialog.setSize(300, 300);
                moviesManagementDialog.setLayout(new GridLayout(6, 1));

                JButton listMoviesButton = new JButton("列出所有影片信息");
                JButton addMoviesButton = new JButton("添加影片信息");
                JButton updateMoviesButton = new JButton("修改影片信息");
                JButton deleteMoviesButton = new JButton("删除影片信息");
                JButton findMoviesButton = new JButton("查询影片信息");
                JButton backButton = new JButton("返回上一步");


                moviesManagementDialog.add(listMoviesButton);
                moviesManagementDialog.add(addMoviesButton);
                moviesManagementDialog.add(updateMoviesButton);
                moviesManagementDialog.add(deleteMoviesButton);
                moviesManagementDialog.add(findMoviesButton);
                moviesManagementDialog.add(backButton);

                listMoviesButton.addActionListener(new ActionListener() {//查看电影列表
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Movie movie = new Movie();
                        ArrayList<Movie> moviesList = new ArrayList<>();

                        JTextArea moviesListArea = new JTextArea(30, 50);
                        moviesListArea.setEditable(false); // 禁止编辑文本区域
                        moviesListArea.setLineWrap(true);
                        moviesListArea.setText("");

                        try {
                            moviesList = movie.readInformation();
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        } catch (ClassNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }
                        JDialog moviesListDialog = new JDialog(managerSystemframe, "电影列表");
                        moviesListDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        moviesListDialog.setLayout(new BorderLayout());
                        moviesListDialog.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosing(WindowEvent e) {
                                moviesListArea.setText("");
                                moviesListDialog.dispose();
                            }
                        });
                        moviesListArea.setText("");
                        // 将用户列表信息添加到文本区域
                        StringBuilder userListText = new StringBuilder();
                        for (Movie movie1 : moviesList) {
                            userListText.append("电影名: ").append(movie1.name).append("\n");
                            userListText.append("导演: ").append(movie1.director).append("\n");
                            userListText.append("主演: ").append(movie1.star).append("\n");
                            userListText.append("简介: ").append(movie1.synopsis).append("\n");
                            userListText.append("时长：").append(movie1.duration).append("min\n");
                            userListText.append("_________________________\n");
                        }
                        moviesListArea.setText(userListText.toString());
                        moviesListArea.paintImmediately(moviesListArea.getBounds());
                        JScrollPane scrollPane = new JScrollPane(moviesListArea);
                        moviesListDialog.add(scrollPane, BorderLayout.CENTER);

                        moviesListDialog.pack();
                        moviesListDialog.setVisible(true);
                    }
                });

                addMoviesButton.addActionListener(new ActionListener() {//添加电影信息
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        moviesManagementDialog.setVisible(false);
                        JTextField movieNameField, directorField, actorsField, durationField;
                        JTextArea descriptionArea;
                        JDialog movieInfoinputDialog = new JDialog(managerSystemframe, "电影信息输入");
                        movieInfoinputDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                        JDialog movieslistDialog = CinemaApp.moviesList(movieInfoinputDialog);

                        movieInfoinputDialog.setSize(400, 300);
                        movieInfoinputDialog.setLocationRelativeTo(null);

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

                        movieInfoinputDialog.setVisible(true);

                        okButton.addActionListener(new ActionListener() {//确定添加电影信息
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                // 在这里获取输入的电影信息并进行处理
                                Movie movie = new Movie();

                                movie.name = movieNameField.getText();
                                movie.director = directorField.getText();
                                movie.star = actorsField.getText();
                                movie.synopsis = descriptionArea.getText();
                                movie.duration = durationField.getText();

                                try {
                                    if (movie.isEmpty()) {
                                        JOptionPane.showMessageDialog(movieInfoinputDialog, "请完整输入电影信息!");
                                    } else {
                                        if (movie.insertMovies(movie)) {
                                            JOptionPane.showMessageDialog(movieInfoinputDialog, "添加影片成功!");
                                            movieInfoinputDialog.dispose();
                                            movieslistDialog.dispose();
                                            moviesManagementDialog.setVisible(true);
                                        } else {
                                            JOptionPane.showMessageDialog(movieInfoinputDialog, "添加影片失败!");
                                            movieInfoinputDialog.dispose();
                                            movieslistDialog.dispose();
                                            moviesManagementDialog.setVisible(true);
                                        }
                                    }

                                } catch (SQLException ex) {
                                    throw new RuntimeException(ex);
                                } catch (ClassNotFoundException ex) {
                                    throw new RuntimeException(ex);
                                }

                            }
                        });

                        cancelButton.addActionListener(new ActionListener() {//返回上一步
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                // 返回上一步，关闭弹窗
                                movieInfoinputDialog.dispose();
                                movieslistDialog.dispose();
                                moviesManagementDialog.setVisible(true);
                            }
                        });

                        movieInfoinputDialog.getContentPane().add(panel);
                    }
                });

                updateMoviesButton.addActionListener(new ActionListener() {// 修改电影信息
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        managerSystemframe.setVisible(false);
                        JTextField movieNameField, directorField, actorsField, durationField;
                        JTextArea descriptionArea;
                        JDialog movieInfoUpdatedialog = new JDialog(managerSystemframe, "电影信息更新");
                        movieInfoUpdatedialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        movieInfoUpdatedialog.setSize(400, 300);
                        movieInfoUpdatedialog.setLocationRelativeTo(null);

                        JDialog movieslistframe = CinemaApp.moviesList(movieInfoUpdatedialog);

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

                        movieInfoUpdatedialog.setVisible(true);


                        okButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                // 在这里获取输入的电影信息并进行处理
                                Movie movie = new Movie();

                                movie.name = movieNameField.getText();
                                movie.director = directorField.getText();
                                movie.star = actorsField.getText();
                                movie.synopsis = descriptionArea.getText();
                                movie.duration = durationField.getText();

                                try {
                                    if (movie.isEmpty()) {
                                        JOptionPane.showMessageDialog(movieInfoUpdatedialog, "请完整输入电影信息!");
                                    } else {
                                        if (movie.updatemovie(movie)) {
                                            JOptionPane.showMessageDialog(movieInfoUpdatedialog, "修改影片信息成功!");
                                            movieInfoUpdatedialog.dispose();
                                            movieslistframe.dispose();
                                            moviesManagementDialog.setVisible(true);
                                        } else {
                                            JOptionPane.showMessageDialog(movieInfoUpdatedialog, "暂无该影片信息!");
                                            movieInfoUpdatedialog.dispose();
                                            movieslistframe.dispose();
                                            moviesManagementDialog.setVisible(true);
                                        }
                                    }

                                } catch (SQLException ex) {
                                    throw new RuntimeException(ex);
                                } catch (ClassNotFoundException ex) {
                                    throw new RuntimeException(ex);
                                }

                            }
                        });

                        cancelButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                // 返回上一步，关闭弹窗
                                movieInfoUpdatedialog.dispose();
                                movieslistframe.dispose();
                                moviesManagementDialog.setVisible(true);
                            }
                        });

                        movieInfoUpdatedialog.getContentPane().add(panel);
                    }

                });

                deleteMoviesButton.addActionListener(new ActionListener() {//   删除影片
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JFrame deleteMovieframe = new JFrame("电影信息删除");
                        deleteMovieframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        deleteMovieframe.setSize(400, 150);
                        deleteMovieframe.setLayout(new GridLayout(2, 1));

                        JLabel movieNameLabel = new JLabel("请输入电影名:");
                        JTextField movieNameField = new JTextField(20); // 增加文本框大小
                        JButton confirmButton = new JButton("确认");
                        JButton backButton = new JButton("返回上一步");

                        JPanel inputPanel = new JPanel(new GridBagLayout()); // 使用GridBagLayout布局
                        GridBagConstraints constraints = new GridBagConstraints();
                        constraints.insets = new Insets(5, 5, 5, 5); // 增加间距
                        inputPanel.add(movieNameLabel, constraints);

                        constraints.gridx = 1;
                        inputPanel.add(movieNameField, constraints);

                        JPanel buttonPanel = new JPanel();
                        buttonPanel.setLayout(new FlowLayout());
                        buttonPanel.add(confirmButton);
                        buttonPanel.add(backButton);

                        deleteMovieframe.add(inputPanel);
                        deleteMovieframe.add(buttonPanel);

                        confirmButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                String movieName = movieNameField.getText();
                                // 弹出确认删除窗口
                                JDialog deletedialog = new JDialog(deleteMovieframe, "确认删除信息");
                                deletedialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                                deletedialog.setSize(300, 150);
                                deletedialog.setLayout(new GridLayout(2, 1));

                                JLabel confirmationLabel = new JLabel("确认删除电影 " + movieName + " 的信息吗？");
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

                                        if (Movie.deleteMovieByname(movieName)) {
                                            JOptionPane.showMessageDialog(deletedialog, "电影信息已删除。");
                                            deleteMovieframe.dispose();
                                        } else {
                                            JOptionPane.showMessageDialog(deletedialog, "暂无该电影信息。");
                                            deleteMovieframe.dispose();
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
                                deleteMovieframe.dispose();
                                moviesManagementDialog.setVisible(true);
                            }
                        });

                        deleteMovieframe.setVisible(true);
                    }
                });

                findMoviesButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //todo 查找电影信息
                        // 在这里执行查询用户信息的逻辑
                        JFrame findMovieframe = new JFrame("电影信息查询");
                        findMovieframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        findMovieframe.setSize(400, 150);
                        findMovieframe.setLayout(new GridLayout(2, 1));

                        JLabel movieNameLabel = new JLabel("请输入电影名:");
                        JTextField movieNameField = new JTextField(20); // 增加文本框大小

                        JButton confirmButton = new JButton("确认");
                        JButton backButton = new JButton("返回上一步");

                        JPanel inputPanel = new JPanel(new GridBagLayout()); // 使用GridBagLayout布局
                        GridBagConstraints constraints = new GridBagConstraints();
                        constraints.insets = new Insets(5, 5, 5, 5); // 增加间距
                        inputPanel.add(movieNameLabel, constraints);

                        constraints.gridx = 1;
                        inputPanel.add(movieNameField, constraints);

                        JPanel buttonPanel = new JPanel();
                        buttonPanel.setLayout(new FlowLayout());
                        buttonPanel.add(confirmButton);
                        buttonPanel.add(backButton);

                        findMovieframe.add(inputPanel);
                        findMovieframe.add(buttonPanel);

                        findMovieframe.setVisible(true);

                        confirmButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                String findMovieName = movieNameField.getText();
                                Movie movie = new Movie();
                                ArrayList<Movie> moviesArrayList = new ArrayList<>();

                                try {
                                    moviesArrayList = movie.readInformation();
                                } catch (SQLException ex) {
                                    throw new RuntimeException(ex);
                                } catch (ClassNotFoundException ex) {
                                    throw new RuntimeException(ex);
                                }

                                boolean tag = true;
                                for (int i = 0; i < moviesArrayList.size(); i++) {
                                    if (moviesArrayList.get(i).name.equals(findMovieName)) {
                                        JOptionPane.showMessageDialog(findMovieframe, moviesArrayList.get(i).toString());
                                        tag = false;
                                        findMovieframe.dispose();
                                        moviesManagementDialog.setVisible(true);
                                    }

                                }
                                if (tag) {
                                    JOptionPane.showMessageDialog(findMovieframe, "暂无该电影信息!");
                                    findMovieframe.dispose();
                                    moviesManagementDialog.setVisible(true);
                                }

                            }
                        });

                        backButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                // 返回上一步，关闭当前窗口
                                findMovieframe.dispose();
                                moviesManagementDialog.setVisible(true);
                            }
                        });

                    }
                });

                backButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        moviesManagementDialog.dispose();
                        managerSystemframe.setVisible(true);
                    }
                });
                moviesManagementDialog.setVisible(true);

            }
        });


        arrangeMoviesManagementButton.addActionListener(new ActionListener() {//排片管理
            @Override
            public void actionPerformed(ActionEvent e) {
                managerSystemframe.setVisible(false);
                JDialog movieScheduleManagementDialog = new JDialog(managerSystemframe, "排片管理");
                movieScheduleManagementDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                movieScheduleManagementDialog.setSize(300, 300);
                movieScheduleManagementDialog.setLayout(new GridLayout(5, 1));

                JButton addScheduleButton = new JButton("增加场次");
                JButton updateScheduleButton = new JButton("修改场次");
                JButton deleteScheduleButton = new JButton("删除场次");
                JButton listScheduleButton = new JButton("列出所有场次信息");
                JButton backButton = new JButton("返回上一步");

                movieScheduleManagementDialog.add(addScheduleButton);
                movieScheduleManagementDialog.add(updateScheduleButton);
                movieScheduleManagementDialog.add(deleteScheduleButton);
                movieScheduleManagementDialog.add(listScheduleButton);
                movieScheduleManagementDialog.add(backButton);

                addScheduleButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        movieScheduleManagementDialog.setVisible(false);
                        Calendar calendar = Calendar.getInstance();
                        Date currentDate = calendar.getTime();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("MM月dd日");
                        String[] date = new String[7];
                        date[0] = dateFormat.format(currentDate);
                        for (int i = 1; i < 7; i++) {
                            // 增加一天
                            calendar.add(Calendar.DAY_OF_MONTH, 1);
                            Date nextDate = calendar.getTime();
                            date[i] = dateFormat.format(nextDate);
                        }
                        JDialog arrangeMovieScheduleJdialog = new JDialog(movieScheduleManagementDialog, "电影场次安排");
                        arrangeMovieScheduleJdialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                        arrangeMovieScheduleJdialog.setSize(400, 350);
                        arrangeMovieScheduleJdialog.getContentPane().setBackground(Color.LIGHT_GRAY);

                        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));

                        JLabel movieNameLabel = new JLabel("电影名称:");
                        JTextField movieNameField = new JTextField();
                        JLabel hallLabel = new JLabel("放映厅:");
                        JComboBox<String> hallComboBox = new JComboBox<>(new String[]{"1号厅", "2号厅", "3号厅"});
                        JLabel dayLabel = new JLabel("选择日期:");
                        JComboBox<String> dayComboBox = new JComboBox<>(new String[]{date[0], date[1], date[2], date[3], date[4], date[5], date[6]});
                        JLabel timeLabel = new JLabel("选择时间:");
                        JComboBox<String> timeComboBox = new JComboBox<>(new String[]{"8:30", "11:30", "14:00", "19:00", "22:00"});
                        JLabel priceLabel = new JLabel("价格:");
                        JTextField priceField = new JTextField();

                        JPanel buttonPanel = new JPanel();
                        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

                        JButton confirmButton = new JButton("确认");
                        JButton cancelButton = new JButton("取消");

                        confirmButton.setPreferredSize(new Dimension(100, 30));
                        cancelButton.setPreferredSize(new Dimension(100, 30));

                        buttonPanel.add(confirmButton);
                        buttonPanel.add(cancelButton);

                        panel.add(movieNameLabel);
                        panel.add(movieNameField);
                        panel.add(hallLabel);
                        panel.add(hallComboBox);
                        panel.add(dayLabel);
                        panel.add(dayComboBox);
                        panel.add(timeLabel);
                        panel.add(timeComboBox);
                        panel.add(priceLabel);
                        panel.add(priceField);

                        arrangeMovieScheduleJdialog.add(panel, BorderLayout.CENTER);
                        arrangeMovieScheduleJdialog.add(buttonPanel, BorderLayout.SOUTH);

                        JDialog moviesListdemo = moviesList(arrangeMovieScheduleJdialog);//影片列表
                        JDialog moviesScheduleListdemo = moviesScheduleList(arrangeMovieScheduleJdialog);//排片列表

                        confirmButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                MoviesShedule moviesShedule = new MoviesShedule();
                                moviesShedule.moiveName = movieNameField.getText();
                                moviesShedule.hallNum = (String) hallComboBox.getSelectedItem();
                                moviesShedule.showtime = dayComboBox.getSelectedItem() + (String) timeComboBox.getSelectedItem();
                                moviesShedule.price = priceField.getText();
                                try {
                                    if (moviesShedule.isEmpty()) {
                                        JOptionPane.showMessageDialog(arrangeMovieScheduleJdialog, "请完整输入电影信息!");
                                    } else {
                                        if (moviesShedule.insertMovieShedule(moviesShedule)) {
                                            JOptionPane.showMessageDialog(arrangeMovieScheduleJdialog, "添加影片成功!");
                                            arrangeMovieScheduleJdialog.dispose();
                                            moviesListdemo.dispose();
                                            moviesScheduleListdemo.dispose();
                                            movieScheduleManagementDialog.setVisible(true);
                                        } else {
                                            JOptionPane.showMessageDialog(arrangeMovieScheduleJdialog, "添加影片失败!");
                                            arrangeMovieScheduleJdialog.dispose();
                                            moviesListdemo.dispose();
                                            moviesScheduleListdemo.dispose();
                                            movieScheduleManagementDialog.setVisible(true);
                                        }
                                    }

                                } catch (SQLException ex) {
                                    throw new RuntimeException(ex);
                                } catch (ClassNotFoundException ex) {
                                    throw new RuntimeException(ex);
                                }
                            }
                        });

                        cancelButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                arrangeMovieScheduleJdialog.dispose();
                                moviesListdemo.dispose();
                                moviesScheduleListdemo.dispose();
                                movieScheduleManagementDialog.setVisible(true);
                            }
                        });

                        arrangeMovieScheduleJdialog.setVisible(true);
                    }
                });

                updateScheduleButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //todo 怎么更新呀
                        movieScheduleManagementDialog.setVisible(false);
                        JDialog deleteMovieframe = new JDialog(movieScheduleManagementDialog, "电影排片信息删除");
                        deleteMovieframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        deleteMovieframe.setSize(400, 150);
                        deleteMovieframe.setLayout(new GridLayout(2, 1));

                        moviesScheduleList(deleteMovieframe);

                        JLabel movie_NameLabel = new JLabel("请输入ID:");
                        JTextField movie_NameField = new JTextField(20); // 增加文本框大小
                        JButton confirm_Button = new JButton("确认");
                        JButton back_Button = new JButton("返回上一步");

                        JPanel inputPanel = new JPanel(new GridBagLayout()); // 使用GridBagLayout布局
                        GridBagConstraints constraints = new GridBagConstraints();
                        constraints.insets = new Insets(5, 5, 5, 5); // 增加间距
                        inputPanel.add(movie_NameLabel, constraints);

                        constraints.gridx = 1;
                        inputPanel.add(movie_NameField, constraints);

                        JPanel buttonPanel = new JPanel();
                        buttonPanel.setLayout(new FlowLayout());
                        buttonPanel.add(confirm_Button);
                        buttonPanel.add(back_Button);

                        deleteMovieframe.add(inputPanel);
                        deleteMovieframe.add(buttonPanel);

                        deleteMovieframe.setVisible(true);
                        confirm_Button.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                //todo 修改影片信息即根据ID修改

                                Calendar calendar = Calendar.getInstance();
                                Date currentDate = calendar.getTime();
                                SimpleDateFormat dateFormat = new SimpleDateFormat("MM月dd日");
                                String[] date = new String[7];
                                date[0] = dateFormat.format(currentDate);
                                for (int i = 1; i < 7; i++) {
                                    // 增加一天
                                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                                    Date nextDate = calendar.getTime();
                                    date[i] = dateFormat.format(nextDate);
                                }

                                JDialog updateMovieScheduleJdialog = new JDialog(movieScheduleManagementDialog, "电影场次修改");
                                updateMovieScheduleJdialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                                updateMovieScheduleJdialog.setSize(400, 350);
                                updateMovieScheduleJdialog.getContentPane().setBackground(Color.LIGHT_GRAY);

                                JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));

                                JLabel movieNameLabel = new JLabel("电影名称:");
                                JTextField movieNameField = new JTextField();
                                JLabel hallLabel = new JLabel("放映厅:");
                                JComboBox<String> hallComboBox = new JComboBox<>(new String[]{"1号厅", "2号厅", "3号厅"});
                                JLabel dayLabel = new JLabel("选择日期:");
                                JComboBox<String> dayComboBox = new JComboBox<>(new String[]{date[0], date[1], date[2], date[3], date[4], date[5], date[6]});
                                JLabel timeLabel = new JLabel("选择时间:");
                                JComboBox<String> timeComboBox = new JComboBox<>(new String[]{"8:30", "11:30", "14:00", "19:00", "22:00"});
                                JLabel priceLabel = new JLabel("价格:");
                                JTextField priceField = new JTextField();

                                JPanel button_Panel = new JPanel();
                                button_Panel.setLayout(new FlowLayout(FlowLayout.CENTER));

                                JButton confirmButton = new JButton("确认");
                                JButton cancelButton = new JButton("取消");

                                confirmButton.setPreferredSize(new Dimension(100, 30));
                                cancelButton.setPreferredSize(new Dimension(100, 30));

                                button_Panel.add(confirmButton);
                                button_Panel.add(cancelButton);

                                panel.add(movieNameLabel);
                                panel.add(movieNameField);
                                panel.add(hallLabel);
                                panel.add(hallComboBox);
                                panel.add(dayLabel);
                                panel.add(dayComboBox);
                                panel.add(timeLabel);
                                panel.add(timeComboBox);
                                panel.add(priceLabel);
                                panel.add(priceField);

                                updateMovieScheduleJdialog.add(panel, BorderLayout.CENTER);
                                updateMovieScheduleJdialog.add(button_Panel, BorderLayout.SOUTH);

                                JDialog moviesListdemo = moviesList(updateMovieScheduleJdialog);//影片列表
                                JDialog moviesScheduleListdemo = moviesScheduleList(updateMovieScheduleJdialog);//排片列表

                                confirmButton.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        MoviesShedule moviesShedule = new MoviesShedule();

                                        moviesShedule.moiveName = movieNameField.getText();
                                        moviesShedule.hallNum = (String) hallComboBox.getSelectedItem();
                                        moviesShedule.showtime = dayComboBox.getSelectedItem() + (String) timeComboBox.getSelectedItem();
                                        moviesShedule.price = priceField.getText();

                                        try {
                                            if (moviesShedule.isEmpty()) {
                                                JOptionPane.showMessageDialog(updateMovieScheduleJdialog, "请完整输入排片信息!");
                                            } else {
                                                if (moviesShedule.updateMovieSchedule(moviesShedule)) {
                                                    JOptionPane.showMessageDialog(updateMovieScheduleJdialog, "修改成功!");
                                                    updateMovieScheduleJdialog.dispose();
                                                    moviesListdemo.dispose();
                                                    moviesScheduleListdemo.dispose();
                                                    movieScheduleManagementDialog.setVisible(true);
                                                } else {
                                                    JOptionPane.showMessageDialog(updateMovieScheduleJdialog, "暂无该排片信息!");
                                                    updateMovieScheduleJdialog.dispose();
                                                    moviesListdemo.dispose();
                                                    moviesScheduleListdemo.dispose();
                                                    movieScheduleManagementDialog.setVisible(true);
                                                }
                                            }

                                        } catch (SQLException ex) {
                                            throw new RuntimeException(ex);
                                        } catch (ClassNotFoundException ex) {
                                            throw new RuntimeException(ex);
                                        }
                                    }
                                });

                                cancelButton.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        updateMovieScheduleJdialog.dispose();
                                        moviesListdemo.dispose();
                                        moviesScheduleListdemo.dispose();
                                        movieScheduleManagementDialog.setVisible(true);
                                    }
                                });

                                updateMovieScheduleJdialog.setVisible(true);
                            }
                        });

                    }
                });

                deleteScheduleButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //todo 删除排片
                        // 删除排片 有特定的ID 根据ID删除
                        JFrame deleteMovieScheduleframe = new JFrame("电影排片信息删除");
                        deleteMovieScheduleframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        deleteMovieScheduleframe.setSize(400, 150);
                        deleteMovieScheduleframe.setLayout(new GridLayout(2, 1));

                        JLabel movieNameLabel = new JLabel("请输入电影名:");
                        JTextField movieNameField = new JTextField(20); // 增加文本框大小
                        JButton confirmButton = new JButton("确认");
                        JButton backButton = new JButton("返回上一步");

                        JPanel inputPanel = new JPanel(new GridBagLayout()); // 使用GridBagLayout布局
                        GridBagConstraints constraints = new GridBagConstraints();
                        constraints.insets = new Insets(5, 5, 5, 5); // 增加间距
                        inputPanel.add(movieNameLabel, constraints);

                        constraints.gridx = 1;
                        inputPanel.add(movieNameField, constraints);

                        JPanel buttonPanel = new JPanel();
                        buttonPanel.setLayout(new FlowLayout());
                        buttonPanel.add(confirmButton);
                        buttonPanel.add(backButton);

                        deleteMovieScheduleframe.add(inputPanel);
                        deleteMovieScheduleframe.add(buttonPanel);

                        confirmButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                int id = Integer.parseInt(movieNameField.getText());
                                // 弹出确认删除窗口
                                JDialog deletedialog = new JDialog(deleteMovieScheduleframe, "确认删除信息");
                                deletedialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                                deletedialog.setSize(300, 150);
                                deletedialog.setLayout(new GridLayout(2, 1));

                                JLabel confirmationLabel = new JLabel("确认删除电影排片信息吗？");
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
                                        MoviesShedule moviesShedule = new MoviesShedule();
                                        if (moviesShedule.deleteMovieScheduleByid(id)) {
                                            JOptionPane.showMessageDialog(deletedialog, "电影排片信息已删除。");
                                            deleteMovieScheduleframe.dispose();
                                        } else {
                                            JOptionPane.showMessageDialog(deletedialog, "暂无该电影排片信息。");
                                            deleteMovieScheduleframe.dispose();
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
                                deleteMovieScheduleframe.dispose();
                                movieScheduleManagementDialog.setVisible(true);
                            }
                        });

                        deleteMovieScheduleframe.setVisible(true);
                    }
                });

                listScheduleButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        MoviesShedule moviesScheduleDemo = new MoviesShedule();
                        ArrayList<MoviesShedule> moviesSchedulesList = new ArrayList<>();

                        JTextArea moviesListArea = new JTextArea(30, 50);
                        moviesListArea.setEditable(false); // 禁止编辑文本区域
                        moviesListArea.setLineWrap(true);
                        moviesListArea.setText("");

                        try {
                            moviesSchedulesList = moviesScheduleDemo.readmoviesSheduleList();
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        } catch (ClassNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }

                        JDialog moviesScheduleListDialog = new JDialog(managerSystemframe, "排片列表");
                        moviesScheduleListDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        moviesScheduleListDialog.setLayout(new BorderLayout());
                        moviesScheduleListDialog.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosing(WindowEvent e) {
                                moviesListArea.setText("");
                                moviesScheduleListDialog.dispose();
                            }
                        });
                        moviesListArea.setText("");
                        // 将用户列表信息添加到文本区域
                        StringBuilder userListText = new StringBuilder();
                        for (MoviesShedule moviesShedule : moviesSchedulesList) {
                            userListText.append("ID: ").append(moviesShedule.id).append("\n");
                            userListText.append("电影名: ").append(moviesShedule.moiveName).append("\n");
                            userListText.append("厅号: ").append(moviesShedule.hallNum).append("\n");
                            userListText.append("时间: ").append(moviesShedule.showtime).append("\n");
                            userListText.append("价格: ").append(moviesShedule.price).append("\n");
                            userListText.append("_________________________\n");
                        }
                        moviesListArea.setText(userListText.toString());
                        moviesListArea.paintImmediately(moviesListArea.getBounds());
                        JScrollPane scrollPane = new JScrollPane(moviesListArea);
                        moviesScheduleListDialog.add(scrollPane, BorderLayout.CENTER);

                        moviesScheduleListDialog.pack();
                        moviesScheduleListDialog.setVisible(true);
                    }
                });
                backButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        movieScheduleManagementDialog.dispose();
                        managerSystemframe.setVisible(true);
                    }
                });

                movieScheduleManagementDialog.setVisible(true);
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

    private static void receptionSystem() {
        JFrame receptionSystemframe = new JFrame("前台中心");
        receptionSystemframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        receptionSystemframe.setSize(400, 200);
        receptionSystemframe.setLayout(new GridLayout(3, 1));

        JButton moviesListButton = new JButton("上映影片清单");
        JButton moviesScheduleListButton = new JButton("影片场次清单");
        JButton sellTicketButton = new JButton("售票功能");
        JButton logoutButton = new JButton("退出登录");

        receptionSystemframe.add(moviesListButton);
        receptionSystemframe.add(moviesScheduleListButton);
        receptionSystemframe.add(sellTicketButton);
        receptionSystemframe.add(logoutButton);

        moviesListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                moviesList(receptionSystemframe);
            }
        });
        moviesScheduleListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moviesScheduleList(receptionSystemframe);
            }
        });

        sellTicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moviesScheduleList(receptionSystemframe);

            }
        });
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(receptionSystemframe, "退出登录");
                receptionSystemframe.dispose();
            }
        });
        receptionSystemframe.setVisible(true);
    }

    private static void userSystem() {
        JFrame userSystemframe = new JFrame("用户中心");
        userSystemframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        userSystemframe.setSize(400, 200);
        userSystemframe.setLayout(new GridLayout(3, 1));

        JButton passwordManagementButton = new JButton("密码管理");
        JButton buyMoviesManagementButton = new JButton("购票管理");
        JButton logoutButton = new JButton("退出登录");

        userSystemframe.add(passwordManagementButton);
        userSystemframe.add(buyMoviesManagementButton);
        userSystemframe.add(logoutButton);

        passwordManagementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userSystemframe.setVisible(false);
                JDialog passwordChangeDialog = new JDialog(userSystemframe, "修改密码");
                passwordChangeDialog.setVisible(true);

                passwordChangeDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                passwordChangeDialog.setSize(300, 150);
                passwordChangeDialog.setLayout(new GridLayout(4, 2));

                JLabel usernameLabel = new JLabel("账号:");
                JTextField usernameText = new JTextField();
                JLabel currentPasswordLabel = new JLabel("原密码:");
                JTextField currentPasswordField = new JPasswordField();
                JLabel newPasswordLabel = new JLabel("新密码:");
                JTextField newPasswordField = new JPasswordField();
                JButton confirmButton = new JButton("确定");
                JButton backButton = new JButton("返回");

                passwordChangeDialog.add(usernameLabel);
                passwordChangeDialog.add(usernameText);
                passwordChangeDialog.add(currentPasswordLabel);
                passwordChangeDialog.add(currentPasswordField);
                passwordChangeDialog.add(newPasswordLabel);
                passwordChangeDialog.add(newPasswordField);
                passwordChangeDialog.add(confirmButton);
                passwordChangeDialog.add(backButton);

                confirmButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String username = usernameText.getText();
                        String prePassword = currentPasswordField.getText();
                        String newPassword = newPasswordField.getText();

                        UserAuthentication userAuthentication = new UserAuthentication();
                        User user = new User();
                        user.username = username;
                        user.password = prePassword;
                        // 验证原密码是否正确
                        try {
                            if (userAuthentication.authenticateUser(user)) {//此处用登陆验证原密码正确
                                //确认新密码符合要求
                                if (UserAuthentication.isValidPassword(newPassword)) {
                                    // 修改密码
                                    if (user.updatePassword(user)) {
                                        JOptionPane.showMessageDialog(passwordChangeDialog, "密码修改成功！");
                                        passwordChangeDialog.dispose();
                                    } else JOptionPane.showMessageDialog(passwordChangeDialog, "连接故障，请重试！");
                                } else {
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
                        userSystemframe.setVisible(true);
                    }
                });
            }
        });

        buyMoviesManagementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //todo 展示场次ID 然后选择
                JDialog buyMoviesJDialog = new JDialog(userSystemframe, "购票");
                buyMoviesJDialog.setSize(400, 150);
                buyMoviesJDialog.setLayout(new GridLayout(2, 1));

                buyMoviesJDialog.setLocationRelativeTo(null);//居中

                userSystemframe.setVisible(false);

                moviesScheduleList(buyMoviesJDialog);

                JLabel movieNameLabel = new JLabel("请输入排片ID:");
                JTextField movieNameField = new JTextField(20); // 增加文本框大小
                JButton confirmButton = new JButton("确认");
                JButton backButton = new JButton("返回上一步");

                JPanel inputPanel = new JPanel(new GridBagLayout()); // 使用GridBagLayout布局
                GridBagConstraints constraints = new GridBagConstraints();
                constraints.insets = new Insets(5, 5, 5, 5); // 增加间距
                inputPanel.add(movieNameLabel, constraints);

                constraints.gridx = 1;
                inputPanel.add(movieNameField, constraints);

                JPanel buttonPanel = new JPanel();
                buttonPanel.setLayout(new FlowLayout());
                buttonPanel.add(confirmButton);
                buttonPanel.add(backButton);

                buyMoviesJDialog.add(inputPanel);
                buyMoviesJDialog.add(buttonPanel);

                confirmButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        buyMoviesJDialog.setVisible(false);
                        try {
                            //todo 根据所选id分配座次表
                            selectSeats(Movie.readScreeningseat(),userSystemframe,buyMoviesJDialog);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        } catch (ClassNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }
                        buyMoviesJDialog.dispose();
                       // userSystemframe.setVisible(true);
                    }
                });

                backButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        buyMoviesJDialog.dispose();
                        userSystemframe.setVisible(true);
                    }
                });

                buyMoviesJDialog.setVisible(true);


            }
        });
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(userSystemframe, "退出登录");
                userSystemframe.dispose();
            }
        });
        userSystemframe.setVisible(true);
    }


    public static JDialog moviesList(JDialog jDialog) {//电影清单
        Movie movie = new Movie();
        ArrayList<Movie> moviesList = new ArrayList<>();

        JTextArea moviesListArea = new JTextArea(30, 50);
        moviesListArea.setEditable(false); // 禁止编辑文本区域
        moviesListArea.setLineWrap(true);
        moviesListArea.setText("");

        try {
            moviesList = movie.readInformation();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
        JDialog moviesListDialog = new JDialog(jDialog, "电影列表");
        moviesListDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        moviesListDialog.setLayout(new BorderLayout());
        moviesListDialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                moviesListArea.setText("");
                moviesListDialog.dispose();
            }
        });
        moviesListArea.setText("");
        // 将用户列表信息添加到文本区域
        StringBuilder userListText = new StringBuilder();
        for (Movie movie1 : moviesList) {
            userListText.append("电影名: ").append(movie1.name).append("\n");
            userListText.append("导演: ").append(movie1.director).append("\n");
            userListText.append("主演: ").append(movie1.star).append("\n");
            userListText.append("简介: ").append(movie1.synopsis).append("\n");
            userListText.append("时长：").append(movie1.duration).append("min\n");
            userListText.append("_________________________\n");
        }
        moviesListArea.setText(userListText.toString());
        moviesListArea.paintImmediately(moviesListArea.getBounds());
        JScrollPane scrollPane = new JScrollPane(moviesListArea);
        moviesListDialog.add(scrollPane, BorderLayout.CENTER);

        moviesListDialog.pack();
        moviesListDialog.setVisible(true);
        return moviesListDialog;
    }

    public static JDialog moviesList(JFrame jFrame) {//电影清单
        Movie movie = new Movie();
        ArrayList<Movie> moviesList = new ArrayList<>();

        JTextArea moviesListArea = new JTextArea(30, 50);
        moviesListArea.setEditable(false); // 禁止编辑文本区域
        moviesListArea.setLineWrap(true);
        moviesListArea.setText("");

        try {
            moviesList = movie.readInformation();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
        JDialog moviesListDialog = new JDialog(jFrame, "电影列表");
        moviesListDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        moviesListDialog.setLayout(new BorderLayout());
        moviesListDialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                moviesListArea.setText("");
                moviesListDialog.dispose();
            }
        });
        moviesListArea.setText("");
        // 将用户列表信息添加到文本区域
        StringBuilder userListText = new StringBuilder();
        for (Movie movie1 : moviesList) {
            userListText.append("电影名: ").append(movie1.name).append("\n");
            userListText.append("导演: ").append(movie1.director).append("\n");
            userListText.append("主演: ").append(movie1.star).append("\n");
            userListText.append("简介: ").append(movie1.synopsis).append("\n");
            userListText.append("时长：").append(movie1.duration).append("min\n");
            userListText.append("_________________________\n");
        }
        moviesListArea.setText(userListText.toString());
        moviesListArea.paintImmediately(moviesListArea.getBounds());
        JScrollPane scrollPane = new JScrollPane(moviesListArea);
        moviesListDialog.add(scrollPane, BorderLayout.CENTER);

        moviesListDialog.pack();
        moviesListDialog.setVisible(true);
        return moviesListDialog;
    }

    public static JDialog moviesScheduleList(JDialog jDialog) {
        MoviesShedule moviesScheduleDemo = new MoviesShedule();
        ArrayList<MoviesShedule> moviesSchedulesList = new ArrayList<>();

        JTextArea moviesListArea = new JTextArea(30, 50);
        moviesListArea.setEditable(false); // 禁止编辑文本区域
        moviesListArea.setLineWrap(true);
        moviesListArea.setText("");

        try {
            moviesSchedulesList = moviesScheduleDemo.readmoviesSheduleList();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }

        JDialog moviesScheduleListDialog = new JDialog(jDialog, "排片列表");
        moviesScheduleListDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        moviesScheduleListDialog.setLayout(new BorderLayout());
        moviesScheduleListDialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                moviesListArea.setText("");
                moviesScheduleListDialog.dispose();
            }
        });
        moviesListArea.setText("");
        // 将用户列表信息添加到文本区域
        StringBuilder userListText = new StringBuilder();
        for (MoviesShedule moviesShedule : moviesSchedulesList) {
            userListText.append("ID: ").append(moviesShedule.id).append("\n");
            userListText.append("电影名: ").append(moviesShedule.moiveName).append("\n");
            userListText.append("厅号: ").append(moviesShedule.hallNum).append("\n");
            userListText.append("时间: ").append(moviesShedule.showtime).append("\n");
            userListText.append("价格: ").append(moviesShedule.price).append("\n");
            userListText.append("_________________________\n");
        }
        moviesListArea.setText(userListText.toString());
        moviesListArea.paintImmediately(moviesListArea.getBounds());
        JScrollPane scrollPane = new JScrollPane(moviesListArea);
        moviesScheduleListDialog.add(scrollPane, BorderLayout.CENTER);

        moviesScheduleListDialog.pack();
        moviesScheduleListDialog.setVisible(true);

        return moviesScheduleListDialog;
    }

    public static JDialog moviesScheduleList(JFrame jFrame) {
        MoviesShedule moviesScheduleDemo = new MoviesShedule();
        ArrayList<MoviesShedule> moviesSchedulesList = new ArrayList<>();

        JTextArea moviesListArea = new JTextArea(30, 50);
        moviesListArea.setEditable(false); // 禁止编辑文本区域
        moviesListArea.setLineWrap(true);
        moviesListArea.setText("");

        try {
            moviesSchedulesList = moviesScheduleDemo.readmoviesSheduleList();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }

        JDialog moviesScheduleListDialog = new JDialog(jFrame, "排片列表");
        moviesScheduleListDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        moviesScheduleListDialog.setLayout(new BorderLayout());
        moviesScheduleListDialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                moviesListArea.setText("");
                moviesScheduleListDialog.dispose();
            }
        });
        moviesListArea.setText("");
        // 将用户列表信息添加到文本区域
        StringBuilder userListText = new StringBuilder();
        for (MoviesShedule moviesShedule : moviesSchedulesList) {
            userListText.append("ID: ").append(moviesShedule.id).append("\n");
            userListText.append("电影名: ").append(moviesShedule.moiveName).append("\n");
            userListText.append("厅号: ").append(moviesShedule.hallNum).append("\n");
            userListText.append("时间: ").append(moviesShedule.showtime).append("\n");
            userListText.append("价格: ").append(moviesShedule.price).append("\n");
            userListText.append("_________________________\n");
        }
        moviesListArea.setText(userListText.toString());
        moviesListArea.paintImmediately(moviesListArea.getBounds());
        JScrollPane scrollPane = new JScrollPane(moviesListArea);
        moviesScheduleListDialog.add(scrollPane, BorderLayout.CENTER);

        moviesScheduleListDialog.pack();
        moviesScheduleListDialog.setVisible(true);

        return moviesScheduleListDialog;
    }

    //todo 选座位
    public static void selectSeats(String[][] seatsInfo,JFrame userFrame,JDialog buyDialog) {
        // 创建窗口
        JFrame selectFrame = new JFrame("购票选座");
        selectFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel titleLabel = new JLabel("购票选座", JLabel.CENTER);
        JComboBox<Integer> rowCountComboBox;
        JComboBox<Integer> columnCountComboBox;
        JButton confirmButton;
        JButton returnButton;
        JPanel tablePanel;
        JTable table;

        selectFrame.add(titleLabel, BorderLayout.NORTH);

        // 二维数组数据
        Object[][] data = new Object[7][13];
        // 填充数据，这里只是示例，您可以根据需要填充实际数据
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 13; j++) {
                if (j == 0) {
                    data[i][j] = "行 " + (i + 1);
                } else
                    data[i][j] = seatsInfo[i][j];
            }
        }

        // 列名
        String[] columnNames = new String[13];
        for (int i = 0; i < 13; i++) {
            if (i == 0) {
                columnNames[i] = " ";
            } else
                columnNames[i] = "列 " + i;
        }

        // 创建表格模型
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // 设置单元格不可编辑
            }
        };

        table = new JTable(model);

        // 设置表头不可拖动
        table.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(table);

        selectFrame.add(scrollPane, BorderLayout.CENTER);

        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel controlPanel = new JPanel(new FlowLayout());
        tablePanel = new JPanel(new GridLayout(7, 12));

        // 创建行数和列数的ComboBox
        rowCountComboBox = new JComboBox<>();
        for (int i = 1; i <= 7; i++) {
            rowCountComboBox.addItem(i);
        }

        columnCountComboBox = new JComboBox<>();
        for (int i = 1; i <= 12; i++) {
            columnCountComboBox.addItem(i);
        }

        confirmButton = new JButton("确定");
        returnButton = new JButton("返回");
        controlPanel.add(new JLabel("行数: "));
        controlPanel.add(rowCountComboBox);
        controlPanel.add(new JLabel("列数: "));
        controlPanel.add(columnCountComboBox);
        controlPanel.add(confirmButton);
        controlPanel.add(returnButton);
        mainPanel.add(controlPanel, BorderLayout.NORTH);
        mainPanel.add(tablePanel, BorderLayout.CENTER);
        selectFrame.add(mainPanel, BorderLayout.SOUTH);

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int rowCount = (int) rowCountComboBox.getSelectedItem();
                int columnCount = (int) columnCountComboBox.getSelectedItem();
                if (seatsInfo[rowCount - 1][columnCount].equals("满")) {
                    JOptionPane.showMessageDialog(selectFrame, "该座位已被其他用户选择!");
                }
                //todo 完善选座位
                else if (seatsInfo[rowCount - 1][columnCount].equals("空")) {
                    JOptionPane.showMessageDialog(selectFrame, "该座位已被锁定，请及时支付!");
                    paymentFrame(selectFrame,userFrame);
                }
            }
        });

        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectFrame.dispose();
                buyDialog.setVisible(true);
            }
        });

        selectFrame.setSize(800, 240);
        selectFrame.setVisible(true);
    }

    //todo 支付
    public static void paymentFrame(JFrame jFrame,JFrame userjFrame) {
        // 创建一个 JFrame
        JFrame paymentFrame = new JFrame("支付窗口");
        JLabel label = new JLabel();
        label.setHorizontalAlignment(SwingConstants.CENTER);

        JButton button = new JButton("支付完成");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //todo 支付完成
                JOptionPane.showMessageDialog(paymentFrame, "支付已完成！");
                paymentFrame.dispose();
                jFrame.dispose();
                userjFrame.setVisible(true);
            }
        });
        ImageIcon imageIcon = new ImageIcon("zhifu.jpg");
        label.setIcon(imageIcon);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(button);
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.add(label, BorderLayout.CENTER);
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(imagePanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        paymentFrame.getContentPane().add(mainPanel);
        paymentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        paymentFrame.setSize(600, 600);
        paymentFrame.setVisible(true);
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                new CinemaApp();
//            }
//        });


        CinemaApp.userSystem();
        //CinemaApp.selectSeats(Movie.readScreeningseat());
    }
}

