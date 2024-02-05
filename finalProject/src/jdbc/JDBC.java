package jdbc;

import domain.Cake;
import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBC {
    private static final String JDBC_URL =
            "jdbc:sqlite:Shop";

    private Connection conn = null;

    private void openConnection() {
        try {
            // with DriverManager
//            if (conn == null || conn.isClosed())
//                conn = DriverManager.getConnection(JDBC_URL);

            // with DataSource
            SQLiteDataSource ds = new SQLiteDataSource();
            ds.setUrl(JDBC_URL);
            if (conn == null || conn.isClosed())
                conn = ds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void closeConnection() {
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void createSchema() {
        try {
            try (final Statement stmt = conn.createStatement()) {
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Cakes(" +
                        "Cid INTEGER PRIMARY KEY ," +
                        "name VARCHAR(200), " +
                        "price int);");
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Orders(Oid INTEGER PRIMARY KEY, cake varchar(200), address varchar(200));");
            }
        } catch (SQLException e) {
            System.err.println("[ERROR] createSchema : " + e.getMessage());
        }
    }

    void initTables() {
        final String[] Cakes = new String[]{
                "1|chocolateCake|200",
                "2|vanillaCake|220",
                "3|caramelCake|210",
        };
        final String[] Orders = new String[]{
                "1|1|chocolateCake|200|Stejarului",
                "2|2|vanillaCake|220|Eroilor",
                "3|3|caramelCake|210|Primaverii",
        };

        try {
            try (PreparedStatement statement = conn.prepareStatement("INSERT INTO Cakes(Cid, name, price) VALUES (?, ?,?)")) {
                for (String s : Cakes) {
                    String[] tokens = s.split("[|]");
                    statement.setInt(1, Integer.parseInt(tokens[0]));
                    statement.setString(2, tokens[1]);
                    statement.setInt(3, Integer.parseInt(tokens[2]));
                    statement.executeUpdate();
                }
            }

            try (PreparedStatement statement = conn.prepareStatement("INSERT INTO Orders(Oid,cake, address) VALUES (?, ?,?)")) {
                for (String order : Orders) {
                    String[] tokens = order.split("[|]");
                    statement.setInt(1, Integer.parseInt(tokens[0]));//id
                    Cake cake = new Cake(Integer.parseInt(tokens[1]), tokens[2], Integer.parseInt(tokens[3]));
                    // Serialize the Cake object to a string
                    String stringCake = cake.toString();

                    statement.setString(2, stringCake);
                    statement.setString(3, tokens[4]); //adress
                    statement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        JDBC db_example = new JDBC();
        db_example.openConnection();
        db_example.createSchema();
        db_example.initTables();
        db_example.closeConnection();
    }

}