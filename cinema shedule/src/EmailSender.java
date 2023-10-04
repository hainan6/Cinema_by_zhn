import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

class EmailSender {
    final String emailUsername = "1828997471@qq.com"; // 发件人的邮箱
    final String emailPassword = "ayhtlmetfpoebeaj"; // 发件人的邮箱密码
    String subject; // 邮件主题


    public  boolean forgetPasswordEmail(User user) {

        subject=new String("亲爱的"+user.phoneNumber+"用户");
        String messageText; // 邮件内容
        StringBuilder forgottenpassword = new StringBuilder("这是您的新密码：");
        forgottenpassword.append(user.password);
        forgottenpassword.append("\n\n\n\n");
        forgottenpassword.append("感谢您使用云大影院！");
        messageText=forgottenpassword.toString();

        // 设置邮件属性
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.qq.com"); // 使用QQ邮箱作为SMTP服务器
        props.put("mail.smtp.port", "587"); // SMTP端口

        // 创建会话对象
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailUsername, emailPassword);
            }
        });

        try {
            // 创建邮件消息
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailUsername));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.email));
            message.setSubject(subject); // 邮件主题
            message.setText(messageText); // 邮件内容

            // 发送邮件
            Transport.send(message);

            return true;

        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static void main(String[] args) {
        EmailSender emailSender = new EmailSender();
        User user = new User();
        user.username="djfhg";
        user.email="2727332026@qq.com";
        user.password="12345";
        emailSender.forgetPasswordEmail(user);
    }
}
