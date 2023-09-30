
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {
    public  String md5(String input) {

        try {
            // 创建一个MD5哈希对象
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 将字符串转换为字节数组
            byte[] bytes = input.getBytes();
            // 更新哈希对象的输入
            md.update(bytes);
            // 计算哈希值
            byte[] hash = md.digest();
            // 将哈希值转换为十六进制字符串
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xFF & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}