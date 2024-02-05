package repository;

import domain.Cake;
import domain.Order;

import java.io.*;

public class OrderRepoTextFile extends FileRepo<Integer, Order> {

    public OrderRepoTextFile(String filename) {
        super(filename);
        readFromFile();
    }

    @Override
    protected void readFromFile() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(this.filename));
            String line = null;
            while ( (line = reader.readLine()) != null){
                String[] tokens = line.split("[,]");
                if ( tokens.length != 5 )
                    continue;
                else{
                    Integer ID = Integer.parseInt(tokens[0]);
                    //cake
                    Integer IDc = Integer.parseInt(tokens[1].trim());
                    String name=tokens[2].trim();
                    Integer price =Integer.parseInt(tokens[3].trim());
                    String adress = tokens[4].trim();
                    super.elements.put(ID,new Order(ID,new Cake(IDc,name,price),adress));
                }
            }
            reader.close();
        }
        catch (FileNotFoundException f){
            throw new RuntimeException("File not found");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void writeToFile() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(this.filename));
            Iterable<Order> orders = this.getAllItems();
            for (Order o : orders)
                writer.write(o.getId() + ", " +o.getCake().getId()+", "+ o.getCake().getName() + ", " +o.getCake().getPrice() + ", " + o.getAdress() + "\n");
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



}