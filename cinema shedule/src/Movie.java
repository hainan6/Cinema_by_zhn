import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;
public class Movie {
    String name;//片名
     String director;//导演
     String star;//主演
    String synopsis;//剧情简介
    String duration;//时长(单位：min)
     ArrayList arrayListMovies = new ArrayList<Movie>();
    static Link_MySql linkMySql = new Link_MySql();
    public Movie(String name, String director, String star, String synopsis, String duration) {
        this.name = name;
        this.director = director;
        this.star = star;
        this.synopsis = synopsis;
        this.duration = duration;
    }
    public Movie(){
    }

    @Override
    public String toString() {
        return "电影名:"+this.name+"\n导演:"+this.director+"\n主演:"+this.star+"\n简介:"+this.synopsis+"\n时长"+this.duration+"min\n";
    }

    boolean insertMovies(Movie movie) throws SQLException, ClassNotFoundException {
        Connection connection = linkMySql.linkMysql();

        if(connection != null){
            try{
                String insertQuery = "insert into movies (name,director,star,synopsis,duration)values(?,?,?,?,?)";
                PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
                preparedStatement.setString(1, movie.name);
                preparedStatement.setString(2, movie.director);
                preparedStatement.setString(3, movie.star);
                preparedStatement.setString(4, movie.synopsis);
                preparedStatement.setString(5, movie.duration);

                int rowsAffected = preparedStatement.executeUpdate();
                connection.close();//关闭连接
                return rowsAffected > 0;
            }
            catch (Exception e){
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }
    boolean updatemovie(Movie movie) throws SQLException, ClassNotFoundException {
//        Connection connection = linkMySql.linkMysql();
//        if(connection != null){
//            try{
//
//                //todo 完善updatemovies
//                String updateSQL = "UPDATE movies SET duration = ? WHERE name = ?";
//                PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);//执行sql语句
//
//                // 设置要更新的值和条件
//                preparedStatement.setInt(1, 50);
//
//                preparedStatement.setString(2, "1"); // 根据ID更新数据
//
//                // 执行更新操作
//                int rowsUpdated = preparedStatement.executeUpdate();
//
//                if (rowsUpdated > 0) {
//                    System.out.println("更新成功，影响行数: " + rowsUpdated);
//                } else {
//                    System.out.println("未更新任何行");
//                }
//
//                connection.close();//关闭连接
//
//            }
//            catch (Exception e){
//                e.printStackTrace();
//            }
//            return true;
//        }
//
        boolean tag1 = deleteMovieByname(movie.name);

        boolean tag2 = insertMovies(movie);

        if(tag1 == false){
            return false;
        }
        if (tag1 && tag2){
            return true;
        }else return false;
    }
    public  ArrayList<Movie> readInformation() throws SQLException, ClassNotFoundException {

        Connection connection = linkMySql.linkMysql();
        if(connection != null){
            String selectSQL = "SELECT * FROM MOVIES";
            // 创建PreparedStatement对象，用于执行SQL查询
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);

            // 执行查询操作
            ResultSet resultSet = preparedStatement.executeQuery();

            // 遍历结果集并输出数据
            while (resultSet.next()) {
                Movie movie = new Movie();
                movie.name = resultSet.getString("name");
                movie.director = resultSet.getString("director");
                movie.star = resultSet.getString("star");
                movie.synopsis = resultSet.getString("synopsis");
                movie.duration = resultSet.getString("duration");
                arrayListMovies.add(movie);
            }

            // 关闭连接
            resultSet.close();
            preparedStatement.close();
            connection.close();

            return arrayListMovies;
        }
        else{
            return null;
        }

    }

    public static String[][] readScreeningseat() throws SQLException, ClassNotFoundException {
        Connection connection = linkMySql.linkMysql();
        String [][] screenInfo = new String[7][13];
        if(connection != null){
            String selectSQL = "SELECT * FROM screeningroom1";
            // 创建PreparedStatement对象，用于执行SQL查询
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            // 执行查询操作
            ResultSet resultSet = preparedStatement.executeQuery();
            // 遍历结果集并输出数据
                for (int i = 0; i < 7 &&resultSet.next(); i++) {
                    for (int j = 1; j <= 12; j++) {
                        if(resultSet.getInt("a"+j) == 0){
                            screenInfo[i][j]="空";
                        }
                        else {
                            screenInfo[i][j]="满";
                        }
                    }
            }
            // 关闭连接
            resultSet.close();
            preparedStatement.close();
            connection.close();

            return screenInfo;
        }
        else{
            System.out.println("连接失败!!!");
            return null;
        }
    }

    boolean isEmpty(){
        if(this.name.isEmpty()||this.director.isEmpty()||this.star.isEmpty()||this.synopsis.isEmpty()|| this.duration.isEmpty()){
            return true;
        }
        else
            return false;
    }

    public static boolean deleteMovieByname(String movieName) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // 创建数据库连接
            connection = linkMySql.linkMysql();

            // SQL语句，根据用户名删除用户信息
            String deleteSQL = "DELETE FROM movies WHERE name = ?";
            preparedStatement = connection.prepareStatement(deleteSQL);

            // 设置参数
            preparedStatement.setString(1, movieName);

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


}
