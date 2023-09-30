import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Link_MySql {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/cinema";

    private static final String JDBC_USER = "root";//MySql 用户名

    private static final String JDBC_PASSWORD = "zhn20040209";//MySql 密码


    Connection linkMysql() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");//加载驱动
        Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
        return connection;
    }

}
