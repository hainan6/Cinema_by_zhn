import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MoviesShedule {
    String moiveName;
    public int price;
    int hallNum;
    String showtime;
    static Link_MySql linkMySql = new Link_MySql();

    boolean setMoivesShedule(MoviesShedule moviesShedule) throws SQLException, ClassNotFoundException {
        try (Connection connection = linkMySql.linkMysql()) {
            // 构建插入SQL语句
            String insertSQL = "INSERT INTO movieSchedule (movieTitle, hallNumber, showtime, price) VALUES (?, ?, ?, ?)";
            // 创建PreparedStatement对象
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
            // 设置参数
            preparedStatement.setString(1, moviesShedule.moiveName);
            preparedStatement.setInt(2, moviesShedule.hallNum);
            preparedStatement.setString(3, moviesShedule.showtime);
            preparedStatement.setDouble(4, moviesShedule.price);

            // 执行插入操作
            int rowsInserted = preparedStatement.executeUpdate();

            return rowsInserted>0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public MoviesShedule(){}
    public MoviesShedule(String name,int price , int hallNum ){
        this.moiveName=name;
        this.price=price;
        this.hallNum=hallNum;
        Date currentDate = new Date();
        // 创建 SimpleDateFormat 对象以指定日期时间格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 使用 SimpleDateFormat 格式化日期时间
        String registrationTime = dateFormat.format(currentDate);

        this.showtime= registrationTime;
        System.out.println(this.showtime);
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        MoviesShedule moviesShedule = new MoviesShedule();

        moviesShedule.setMoivesShedule(new MoviesShedule("122",50,3));
    }

}
