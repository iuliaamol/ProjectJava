package repository;

import domain.Cake;
import domain.Order;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderRepoDB extends DBRepo<Integer,Order> {

    public OrderRepoDB(String tableName){
        super(tableName);
        getData();
    }

    public MemoryRepo<Cake,Integer> CakeRepo=new MemoryRepo<Cake,Integer>();
    @Override
    public void getData() {
        try {
            openConnection();
            String selectString = "SELECT * FROM " + tableName + ";";
            try (PreparedStatement ps = conn.prepareStatement(selectString)) {
                ResultSet resultSet = ps.executeQuery();
                while (resultSet.next()) {
                    int id = resultSet.getInt("Oid");

                    String cake = resultSet.getString("cake");
                    String[] tokens = cake.split("[,]");
                    String name=tokens[0].trim();//cake_name
                    // Extract only the numeric part of the id and price
                    String idString = tokens[1].trim().replaceAll("\\D", ""); // Remove non-numeric characters
                    String priceString = tokens[2].trim().replaceAll("\\D", ""); // Remove non-numeric characters
                    Integer IDc = Integer.parseInt(idString); // cake_id
                    Integer price = Integer.parseInt(priceString); // cake_price

                    String adress = resultSet.getString("address");

                    elements.put(id,new Order(id,new Cake(IDc,name,price),adress));

                }
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

    @Override
    public void addItem(Order elem) throws DuplicateEntityException {
        try
        {
            openConnection();
            String insertString = "INSERT INTO " + tableName + " VALUES (?, ?, ?);";
            try (PreparedStatement ps = conn.prepareStatement(insertString)) {
                ps.setInt(1, elem.getId());
                ps.setString(2, String.valueOf(elem.getCake()));
                ps.setString(3, elem.getAdress());
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
