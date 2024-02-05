package repository;

import domain.Cake;
import domain.Identifiable;

import java.io.*;

public class CakeRepoTextFile extends FileRepo<Integer, Cake> {

    public CakeRepoTextFile(String filename) {
        super(filename);
        readFromFile();
    }

    @Override
    public void readFromFile() {
        //super.elements.clear();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(this.filename));
            String line = null;
            while ( (line = reader.readLine()) != null){
                String[] tokens = line.split("[,]");
                if ( tokens.length != 3 )
                    continue;
                else{
                    Integer ID = Integer.parseInt(tokens[0]);
                    String name = tokens[1].trim();
                    Integer price= Integer.parseInt(tokens[2].trim());
                    super.elements.put(ID,new Cake(ID, name, price));

                }
            }
            reader.close();
        }
        catch (FileNotFoundException f){
            throw new RuntimeException("File not found");
        } catch (IOException  e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void writeToFile() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(this.filename));
            Iterable<Cake> cakes = this.getAllItems();
            for (Cake c : cakes)
                writer.write(c.getId() + ", " + c.getName() + ", " + c.getPrice() + "\n");
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



}