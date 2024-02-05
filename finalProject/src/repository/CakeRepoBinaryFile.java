package repository;

import domain.Cake;

import java.io.*;
import java.util.Map;


public class CakeRepoBinaryFile extends FileRepo<Integer, Cake> {

    public CakeRepoBinaryFile(String filename) {
        super(filename);
        readFromFile();
    }

    @Override
    public void readFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            // Read the object from the stream
            Object obj = ois.readObject();

            if (obj instanceof Map) {
                // Check if the object is of type Map
                super.elements.putAll((Map<Integer, Cake>) obj);
            } else {
                throw new RuntimeException("Invalid data format in the file");
            }
        } catch (IOException | ClassNotFoundException e) {
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