import java.sql.SQLException;
import java.util.Scanner;
public class log_sign {
    User user = new User();
    Scanner read = new Scanner(System.in);
    UserAuthentication userAuthentication = new UserAuthentication();



    void gui() throws ClassNotFoundException, SQLException {
            System.out.println("1.登陆");
            System.out.println("2.注册");

            User user = new User();
            int flag = read.nextInt();

            if(flag==1){
                int i = 5;
                while (i>0 ){

                System.out.println("请输入账号:");
                user.username= read.next();
                System.out.println("请输入密码:");
                user.password=read.next();
                    if(userAuthentication.authenticateUser(user)!=false)
                        System.out.println("yes");
                    else {
                        System.out.println("这是第" + (6 - i) + "次尝试!" + "密码输入5次将被锁定30min!");
                        i--;
                    }
               }

            }
            else if(flag == 2){
                System.out.println("注册要求：\n用户名不小于5个字符\n密码长度大于8个字符，必须包含大小写字母、数字和标点符号\n");
                User user1 = new User().creatUser();
                if(userAuthentication.registerUser(user1))
                    //todo 账号
                    System.out.println("yes");
                else
                    System.out.println("no");
            }
        }

}
