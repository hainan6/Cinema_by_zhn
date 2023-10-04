import java.util.Random;

public class RandomPassword {
    private static final String LOWERCASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String PUNCTUATION = "!@#$%^&*()_+-=[]{}|;:,.<>?";


    public static String generateRandomString() {//生成随机密码
        Random random = new Random();
        StringBuilder randomString = new StringBuilder();

        // 随机选择一个字符包含数字
        char randomDigit = DIGITS.charAt(random.nextInt(DIGITS.length()));
        randomString.append(randomDigit);

        // 随机选择一个小写字母
        char randomLowercaseLetter = LOWERCASE_LETTERS.charAt(random.nextInt(LOWERCASE_LETTERS.length()));
        randomString.append(randomLowercaseLetter);

        // 随机选择一个大写字母
        char randomUppercaseLetter = UPPERCASE_LETTERS.charAt(random.nextInt(UPPERCASE_LETTERS.length()));
        randomString.append(randomUppercaseLetter);

        // 随机选择一个标点符号
        char randomPunctuation = PUNCTUATION.charAt(random.nextInt(PUNCTUATION.length()));
        randomString.append(randomPunctuation);

        // 随机生成剩余的字符
        int remainingLength = 6; // 总长度为10，已经有4个字符了
        while (remainingLength > 0) {
            String characterSet = LOWERCASE_LETTERS + UPPERCASE_LETTERS + DIGITS + PUNCTUATION;
            char randomChar = characterSet.charAt(random.nextInt(characterSet.length()));
            randomString.append(randomChar);
            remainingLength--;
        }

        // 将随机字符打乱顺序，以确保不同类型字符的分布
        String shuffledString = shuffleString(randomString.toString());

        return shuffledString;
    }
    private static String shuffleString(String input) {//打乱顺序
        char[] characters = input.toCharArray();
        Random random = new Random();
        for (int i = characters.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            char temp = characters[index];
            characters[index] = characters[i];
            characters[i] = temp;
        }
        return new String(characters);
    }
}
