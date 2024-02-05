package repository;

import domain.Cake;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CakeRepoDB extends DBRepo<Integer,Cake> {
    public CakeRepoDB(String tableName) {
        super(tableName);
        getData();
    }

    @Override
    public void getData() {
        try {
            openConnection();
            String selectString = "SELECT * FROM " + tableName + ";";
            try (PreparedStatement ps = conn.prepareStatement(selectString)) {
                ResultSet resultSet = ps.executeQuery();
                while (resultSet.next()) {
                    int id = resultSet.getInt("Cid");
                    String name = resultSet.getString("name");
                    int price = resultSet.getInt("price");
                    Cake cake = new Cake(id, name, price);
                    super.elements.put(id,cake);
                }
            }
        } catch (SQLException e) {
            throw  new RuntimeException(e);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }



    @Override
    public void addItem(Cake elem) throws DuplicateEntityException
    {
        try
        {
            openConnection();
            String insertString = "INSERT INTO " + tableName + " VALUES (?, ?, ?);";
            try (PreparedStatement ps = conn.prepareStatement(insertString)) {
                ps.setInt(1, elem.getId());
                ps.setString(2, elem.getName());
                ps.setInt(3, elem.getPrice());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}