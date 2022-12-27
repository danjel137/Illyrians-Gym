package data.dataFromOperationalDB;


import java.sql.*;
import java.util.Properties;

class GetAllFromGymTableTest {
    public static void main(String[] args) throws SQLException {
        String postgresDriver = "org.postgresql.Driver";
        String hostname = "jdbc:postgresql://" + System.getenv("hostAndDbName");


        String url = hostname;
        Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", "12345600");
        //props.setProperty("ssl", "true");
        Connection conn = DriverManager.getConnection(url, props);
        PreparedStatement stmt = conn.prepareStatement("select * from gym");
        ResultSet resultSet=stmt.executeQuery();
        while (resultSet.next()){
            System.out.println(resultSet.getString(1));
        }
//        String url = "jdbc:postgresql://localhost/test?user=fred&password=secret&ssl=true";
        //Connection conn = DriverManager.getConnection(url);


    }
}