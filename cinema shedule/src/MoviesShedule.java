import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MoviesShedule {
    int id;
    String moiveName;
    String price;
    String hallNum;
    String showtime;
    static Link_MySql linkMySql = new Link_MySql();

    ArrayList<MoviesShedule> moviesSheduleArrayList = new ArrayList<>();

    public MoviesShedule(){}
    public MoviesShedule(String name,String price ,String hallNum ){
        this.moiveName=name;
        this.price=price;
        this.hallNum=hallNum;
        Date currentDate = new Date(1203,0,1,9,30);//1900开始算 month从0开始算
        // 创建 SimpleDateFormat 对象以指定日期时间格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 使用 SimpleDateFormat 格式化日期时间
        String registrationTime = dateFormat.format(currentDate);

        this.showtime= registrationTime;
        System.out.println(this.showtime);
    }
    boolean insertMovieShedule(MoviesShedule moviesShedule) throws SQLException, ClassNotFoundException {
        try (Connection connection = linkMySql.linkMysql()) {
            // 构建插入SQL语句
            String insertSQL = "INSERT INTO movieSchedule (movieTitle, hallNumber, showtime, price) VALUES (?, ?, ?, ?)";
            // 创建PreparedStatement对象
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
            // 设置参数
            preparedStatement.setString(1, moviesShedule.moiveName);
            preparedStatement.setString(2, moviesShedule.hallNum);
            preparedStatement.setString(3, moviesShedule.showtime);
            preparedStatement.setString(4, moviesShedule.price);

            // 执行插入操作
            int rowsInserted = preparedStatement.executeUpdate();

            return rowsInserted>0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    boolean deleteMovieScheduleByid(int id){
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // 创建数据库连接
            connection = linkMySql.linkMysql();
            String deleteSQL = "DELETE FROM movieschedule WHERE id = ?";
            preparedStatement = connection.prepareStatement(deleteSQL);
            preparedStatement.setString(1, moiveName);
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
    @Override
    public String toString() {
        return "ID:"+id+"电影名:" + moiveName +
                "放映厅号:" + hallNum +
                "放映时间:" + showtime +
                "票价:" + price ;
    }
    public ArrayList<MoviesShedule> readmoviesSheduleList() throws SQLException, ClassNotFoundException {
        Connection connection = linkMySql.linkMysql();
        if(connection != null){
            String selectSQL = "SELECT * FROM movieSchedule";
            // 创建PreparedStatement对象，用于执行SQL查询
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);

            // 执行查询操作
            ResultSet resultSet = preparedStatement.executeQuery();

            // 遍历结果集并输出数据
            while (resultSet.next()) {
                MoviesShedule moviesShedule = new MoviesShedule();
                moviesShedule.id = resultSet.getInt("id");
                moviesShedule.moiveName = resultSet.getString("movieTitle");
                moviesShedule.hallNum = resultSet.getString("hallNumber");
                moviesShedule.price = resultSet.getString("price");
                moviesShedule.showtime= resultSet.getString("showtime");
                moviesSheduleArrayList.add(moviesShedule);
            }
            // 关闭连接
            resultSet.close();
            preparedStatement.close();
            connection.close();

            return moviesSheduleArrayList;
        }
        else{
            return null;
        }
    }
    boolean updateMovieSchedule(MoviesShedule moviesShedule) throws SQLException, ClassNotFoundException {
        Connection connection = linkMySql.linkMysql();
        if(connection != null){
            try{
                String updateSQL = "UPDATE movieschedule SET movieTitle = ?,hallNumber=?,showtime=?,price=? WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);//执行sql语句
                // 设置要更新的值和条件
                preparedStatement.setString(1, moviesShedule.moiveName);
                preparedStatement.setString(2, moviesShedule.hallNum); // 根据ID更新数据
                preparedStatement.setString(3, moviesShedule.showtime);
                preparedStatement.setString(4, moviesShedule.price);
                preparedStatement.setInt(5, moviesShedule.id);
                // 执行更新操作
                int rowsUpdated = preparedStatement.executeUpdate();
                connection.close();//关闭连接
                if (rowsUpdated > 0) {
                    return true;
                } else {
                    return false;
                }
            }
            catch (Exception e){
                e.printStackTrace();
                return false;
            }

        }
        else
            return false;

    }
    boolean isEmpty() {
        boolean result;
        if (this.moiveName.isEmpty() || this.hallNum.isEmpty() || this.showtime.isEmpty() || this.price.isEmpty()) {
            result = true;
        } else {
            result = false;
        }
        return result;
    }
}
