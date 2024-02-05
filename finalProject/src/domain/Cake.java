package domain;
import java.io.Serializable;
import java.util.Objects;

public class Cake implements Identifiable<Integer> , Serializable {
    private Integer id;
    private String name;

    private int price;




    public Cake(Cake another){
        this.name=another.name;
        this.id=another.id;
        this.price=another.price;
    }

    public Cake(Integer id, String name, int price) {
        this.name=name;
        this.id=id;
        this.price=price;
    }


    public String getName() {
        return this.name;
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }


    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cake cake = (Cake) o;
        return price == cake.price && Objects.equals(id, cake.id) && Objects.equals(name, cake.name);
    }



    @Override
    public String toString() {
        return "Cake{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", price=" + price +
                '}';
    }
}