import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;
public class Moive {
    String name;//片名
     String director;//导演
     String star;//主演

    String synopsis;//剧情简介

    String duration;//时长(单位：min)

    ArrayList arrayListMoives = new ArrayList<Moive>();


    static Link_MySql linkMySql = new Link_MySql();
    public Moive(String name, String director, String star, String synopsis, String duration) {
        this.name = name;
        this.director = director;
        this.star = star;
        this.synopsis = synopsis;
        this.duration = duration;
    }
    public Moive(){
    }


    @Override
    public String toString() {
        return "电影名:"+this.name+"\t\t导演:"+this.director+"\t主演:"+this.star+"\t\t简介:"+this.synopsis+"\t\t时长"+this.duration+"min\n";
    }

    void addMoivesinform(){
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

    void printMoives(ArrayList arrayListMoives){
        if(arrayListMoives == null){
            System.out.println("无数据");
            return;
        }
        //System.out.println("电影名\t\t"+"导演\t\t"+"主演\t\t"+"简介\t\t"+"时长\n");
        for (int i = 0; i <arrayListMoives.size() ; i++) {
            System.out.println(arrayListMoives.get(i));
        }
    }


    boolean insertMoives(Moive moive) throws SQLException, ClassNotFoundException {
        Connection connection = linkMySql.linkMysql();

        if(connection != null){
            try{
                String insertQuery = "insert into moives (name,director,star,synopsis,duration)values(?,?,?,?,?)";
                PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
                preparedStatement.setString(1, moive.name);
                preparedStatement.setString(2, moive.director);
                preparedStatement.setString(3, moive.star);
                preparedStatement.setString(4, moive.synopsis);
                preparedStatement.setString(5, moive.duration);

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
    boolean  updatemoive() throws SQLException, ClassNotFoundException {
        Connection connection = linkMySql.linkMysql();

        if(connection != null){
            try{
                Moive moive = new Moive();
                System.out.println();
                //todo 完善updatemoives
                String updateSQL = "UPDATE moives SET duration = ? WHERE name = ?";
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
    public ArrayList<Moive> readInformation() throws SQLException, ClassNotFoundException {

        Connection connection = linkMySql.linkMysql();
        if(connection != null){
            String selectSQL = "SELECT * FROM moives";
            // 创建PreparedStatement对象，用于执行SQL查询
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);

            // 执行查询操作
            ResultSet resultSet = preparedStatement.executeQuery();

            // 遍历结果集并输出数据
            while (resultSet.next()) {
                Moive moive = new Moive();
                moive.name = resultSet.getString("name");
                moive.director = resultSet.getString("director");
                moive.star = resultSet.getString("star");
                moive.synopsis = resultSet.getString("synopsis");
                moive.duration = resultSet.getString("duration");
                arrayListMoives.add(moive);
            }

            // 关闭连接
            resultSet.close();
            preparedStatement.close();
            connection.close();

            return arrayListMoives;
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


    public static char [][] readScreeningseat() throws SQLException, ClassNotFoundException {
        Connection connection = linkMySql.linkMysql();
        char [][] screenInfo = new char[5][12];
        if(connection != null){
            String selectSQL = "SELECT * FROM screeningroom1";
            // 创建PreparedStatement对象，用于执行SQL查询
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);

            // 执行查询操作
            ResultSet resultSet = preparedStatement.executeQuery();

            // 遍历结果集并输出数据

                for (int i = 0; i < 5 &&resultSet.next(); i++) {
                    for (int j = 1; j <= 12; j++) {
                        if(resultSet.getInt("a"+j) == 0){
                            screenInfo[i][j-1]='空';
                        }
                        else {
                            screenInfo[i][j-1]='满';
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

    public boolean selectSeats() throws SQLException, ClassNotFoundException {

        char[][]  seatinfor  = new char[5][12];
        seatinfor = readScreeningseat();

        printScreenSeat(seatinfor);

        Scanner read = new Scanner(System.in);
        System.out.print("请输入行数:");
          int row = read.nextInt();//b
        System.out.print("请输入列数:");
          int col = read.nextInt();//a
        Connection connection = linkMySql.linkMysql();
        try {//判断选择座位是否规范
            if (!(row > 0 && row <= 5) && (col > 0 && col <= 12)) {
                throw new RuntimeException();
            }
        }
            catch(Exception e){
                System.out.println("输入不符合规范，座位不存在!!!请重新输入");
                System.out.print("请输入行数:");
                row = read.nextInt();//b
                System.out.print("请输入列数:");
                col = read.nextInt();//a
            }

        try{
            if(seatinfor[row-1][col-1] == '满'){
                throw new RuntimeException();
            }
        }
        catch (Exception e){
            System.out.println("座位已被其他人选择!!!请重新输入");
            System.out.print("请输入行数:");
            row = read.nextInt();//b
            System.out.print("请输入列数:");
            col = read.nextInt();//a
        }

        if(((row>0&&row<=5) && (col>0&&col<=12)) && (seatinfor[row-1][col-1]!= '满') ){
            if(connection != null){
                try{

                    String updateSQL = "UPDATE screeningroom1 SET a"+col+"= ? WHERE rol = ?";
                    //String updateSQL = "UPDATE moives SET duration = ? WHERE name = ?";

                    PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);//执行sql语句

                    // 设置要更新的值和条件
                    preparedStatement.setInt(1, 1);

                    preparedStatement.setString(2, "b"+row); // 根据ID更新数据

                    // 执行更新操作
                    int rowsUpdated = preparedStatement.executeUpdate();

                    if (rowsUpdated > 0) {
                        System.out.println("更新成功，影响行数: " + rowsUpdated);
                    } else {
                        System.out.println("未更新任何行");
                    }

                    printScreenSeat(readScreeningseat());
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

        return false;
    }

    void printScreenSeat(char [][] s) {

        System.out.print("\t\t\t\t\t放映厅\n\n");
        //System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++");
        //System.out.print("|   1|\t2 |\t3|\t4\t5\t6\t7\t8\t9\t10\t11\t12\n");
        System.out.print("   1\t2 \t3\t4\t5\t6\t7\t8\t9\t10\t11\t12\n");
        for (int i = 0; i < 5; i++) {
            //System.out.print("|");
            //System.out.print(i+1+" |");
            System.out.print(i + 1 + " ");
            for (int j = 0; j < 12; j++) {

                if (j == 11) {
                    System.out.print(s[i][j]);
                } else {
                    //System.out.print(s[i][j]+"|\t");
                    System.out.print(s[i][j] + "\t");
                }
            }
            System.out.println();
            //System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++");
        }
    }

    boolean isEmpty(){
        if(this.name.isEmpty()||this.director.isEmpty()||this.star.isEmpty()||this.synopsis.isEmpty()|| this.duration.isEmpty()){
            return true;
        }
        else
            return false;
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Moive moive = new Moive("1","2","3","4","5");
        //moive.printMoives(moive.readInformation());
        //moive.printScreenSeat(readScreeningseat());
        moive.selectSeats();
        }
}
