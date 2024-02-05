package repository;

import domain.Cake;
import domain.Order;

import java.io.*;
import java.util.HashMap;
import java.util.Map;


public class OrderRepoBinaryFile extends FileRepo<Integer, Order> {

    public OrderRepoBinaryFile(String filename) {
        super(filename);
        readFromFile();
    }

    @Override
    public void readFromFile() {
        File file = new File(filename);
        if (!file.exists()) {
            Map<Integer,Order > emptyMap = new HashMap<>();
            //data = new HashMap<>();
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
                oos.writeObject(emptyMap);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            elements=(Map<Integer,Order>) ois.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void writeToFile() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename));
            oos.writeObject(this.elements);
            oos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}