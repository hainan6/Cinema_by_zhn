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

    void addMoviesinform(){
        Scanner read = new Scanner(System.in);

        System.out.println("电影姓名:");
        this.name=read.next();
        System.out.println("电影导演:");
        this.director=read.next();
        System.out.println("电影主演:(逗号隔开)");
        this.star = read.next();
        System.out.println("电影简介:");
        this.synopsis = read.next();
        System.out.println("电影时长:");
        this.duration=read.next();
    }

    void printMovies(ArrayList arrayListMovies){
        if(arrayListMovies == null){
            System.out.println("无数据");
            return;
        }
        //System.out.println("电影名\t\t"+"导演\t\t"+"主演\t\t"+"简介\t\t"+"时长\n");
        for (int i = 0; i <arrayListMovies.size() ; i++) {
            System.out.println(arrayListMovies.get(i));
        }
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

String sb(){
    Scanner scanner = new Scanner(System.in);
    System.out.println("请输入电影名字:");
    String name = scanner.next();
    System.out.println("请输入导演:");
    String director = scanner.next();
    System.out.println("请输入主演");
    String star = scanner.next();
    System.out.println("请输入简介");
    String synopisis = scanner.next();
    System.out.println("请输入时长(min):");
    int duration = scanner.nextInt();

    return "("+ this.name+","+this.director+","+this.star+","+this.synopsis+","+this.duration+")";
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

//    public boolean selectSeats() throws SQLException, ClassNotFoundException {
//
//        String [][]seatinfor  = new String[5][12];
//        seatinfor = readScreeningseat();
//
//        //printScreenSeat(seatinfor);
//
//        Scanner read = new Scanner(System.in);
//        System.out.print("请输入行数:");
//          int row = read.nextInt();//b
//        System.out.print("请输入列数:");
//          int col = read.nextInt();//a
//        Connection connection = linkMySql.linkMysql();
//        try {//判断选择座位是否规范
//            if (!(row > 0 && row <= 5) && (col > 0 && col <= 12)) {
//                throw new RuntimeException();
//            }
//        }
//            catch(Exception e){
//                System.out.println("输入不符合规范，座位不存在!!!请重新输入");
//                System.out.print("请输入行数:");
//                row = read.nextInt();//b
//                System.out.print("请输入列数:");
//                col = read.nextInt();//a
//            }
//
//        try{
//            if(seatinfor[row-1][col-1] == '满'){
//                throw new RuntimeException();
//            }
//        }
//        catch (Exception e){
//            System.out.println("座位已被其他人选择!!!请重新输入");
//            System.out.print("请输入行数:");
//            row = read.nextInt();//b
//            System.out.print("请输入列数:");
//            col = read.nextInt();//a
//        }
//
//        if(((row>0&&row<=5) && (col>0&&col<=12)) && (seatinfor[row-1][col-1]!= '满') ){
//            if(connection != null){
//                try{
//
//                    String updateSQL = "UPDATE screeningroom1 SET a"+col+"= ? WHERE rol = ?";
//                    //String updateSQL = "UPDATE movies SET duration = ? WHERE name = ?";
//
//                    PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);//执行sql语句
//
//                    // 设置要更新的值和条件
//                    preparedStatement.setInt(1, 1);
//
//                    preparedStatement.setString(2, "b"+row); // 根据ID更新数据
//
//                    // 执行更新操作
//                    int rowsUpdated = preparedStatement.executeUpdate();
//
//                    if (rowsUpdated > 0) {
//                        System.out.println("更新成功，影响行数: " + rowsUpdated);
//                    } else {
//                        System.out.println("未更新任何行");
//                    }
//
//                    printScreenSeat(readScreeningseat());
//                    connection.close();//关闭连接
//
//                }
//                catch (Exception e){
//                    e.printStackTrace();
//                }
//                return true;
//            }
//            else
//                return false;
//        }
//
//        return false;
//    }

//    void printScreenSeat(char [][] s) {
//
//        System.out.print("\t\t\t\t\t放映厅\n\n");
//        //System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++");
//        //System.out.print("|   1|\t2 |\t3|\t4\t5\t6\t7\t8\t9\t10\t11\t12\n");
//        System.out.print("   1\t2 \t3\t4\t5\t6\t7\t8\t9\t10\t11\t12\n");
//        for (int i = 0; i < 5; i++) {
//            //System.out.print("|");
//            //System.out.print(i+1+" |");
//            System.out.print(i + 1 + " ");
//            for (int j = 0; j < 12; j++) {
//
//                if (j == 11) {
//                    System.out.print(s[i][j]);
//                } else {
//                    //System.out.print(s[i][j]+"|\t");
//                    System.out.print(s[i][j] + "\t");
//                }
//            }
//            System.out.println();
//            //System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++");
//        }
//    }

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

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        String[][] s = readScreeningseat();
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 13; j++) {
                System.out.print(s[i][j]+" ");
            }
            System.out.println();
        }
    }

}
