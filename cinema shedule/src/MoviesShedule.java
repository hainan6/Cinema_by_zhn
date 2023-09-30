import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MoviesShedule {
    String moiveName;
    public int price;
    int hallNum;
    String showtime;
    static Link_MySql linkMySql = new Link_MySql();

    ArrayList<MoviesShedule> moviesSheduleArrayList = new ArrayList<>();

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
        Date currentDate = new Date(1203,0,1,9,30);//1900开始算 month从0开始算
        // 创建 SimpleDateFormat 对象以指定日期时间格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 使用 SimpleDateFormat 格式化日期时间
        String registrationTime = dateFormat.format(currentDate);

        this.showtime= registrationTime;
        System.out.println(this.showtime);
    }

    @Override
    public String toString() {
        return "电影名:" + moiveName  +
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
                moviesShedule.moiveName = resultSet.getString("movieTitle");
                moviesShedule.hallNum = resultSet.getInt("hallNumber");
                moviesShedule.price = resultSet.getInt("price");
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

    boolean  updatemoiveschedule() throws SQLException, ClassNotFoundException {
        Connection connection = linkMySql.linkMysql();

        if(connection != null){
            try{
                Movie movie = new Movie();
                System.out.println();
                //todo 完善updatemoives
                String updateSQL = "UPDATE movieschedule SET showtime = ? WHERE movieTitle = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);//执行sql语句

                // 设置要更新的值和条件
                preparedStatement.setInt(1, 50);

                preparedStatement.setString(2, "1"); // 根据ID更新数据

                // 执行更新操作
                int rowsUpdated = preparedStatement.executeUpdate();

                if (rowsUpdated > 0) {
                    System.out.println("更新成功，影响行数: " + rowsUpdated);
                } else {
                    System.out.println("未更新任何行");
                }

                connection.close();//关闭连接

            }
            catch (Exception e){
                e.printStackTrace();
            }
            return true;
        }
        else
            return false;

    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        MoviesShedule moviesShedule = new MoviesShedule("dfsf",51,0);
        moviesShedule.setMoivesShedule(moviesShedule);
//        ArrayList<MoviesShedule> moviesShedules = moviesShedule.readmoviesSheduleList();
//        System.out.println(moviesShedules.size());
//        for (int i = 0; i < moviesShedules.size(); i++) {
//            System.out.println(moviesShedules.get(i).toString());
//        }
    }

}
