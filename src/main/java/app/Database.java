package app;

import java.sql.*;

public class Database {
    private static Database instance;

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    private Connection conn;
    private Statement st;

    private Database() {
        try  {
            conn = DriverManager.getConnection("jdbc:mysql://db:3306/app_db?autoReconnect=true&useSSL=false", "root", "");
            st = conn.createStatement();
            st.executeUpdate("create table if not exists next_number (number int NOT NULL AUTO_INCREMENT, PRIMARY KEY(number));");
            System.out.println("Connection to DB established");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Cannot connect to DB");
            System.exit(-2);
        }
    }

    public int getNextNumber() {
        int res = 0;

        try {
            st.executeUpdate("insert into next_number values();");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            ResultSet rs = st.executeQuery("select max(number) from next_number;");
            while (rs.next()) {
                res = rs.getInt(1);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

    public void close() throws SQLException {
        st.close();
        conn.close();
    }
}
