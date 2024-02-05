package domain;

import java.io.Serializable;
import java.util.Objects;

public  class Order implements Identifiable<Integer>, Serializable {
    private Integer id;
    private Cake cake;
    private String adress;

    public Order(Integer id,Cake cake,String adress){
        this.id=id;
        this.cake=cake;
        this.adress=adress;
    }


    @Override
    public void setId(Integer id) {
        this.id = id;
    }


    @Override
    public Integer getId() {
        return this.id;
    }

    public Cake getCake() {
        return cake;
    }

    public void setCake(Cake cake) {
        this.cake = cake;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) && Objects.equals(cake, order.cake) && Objects.equals(adress, order.adress);
    }


    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", cake=" + cake +
                ", adress='" + adress + '\'' +
                '}';
    }
}