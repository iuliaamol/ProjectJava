package service;

import domain.Cake;
import domain.Order;
import repository.InterRepo;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


public class Service {
    private final InterRepo<Cake,Integer> cakeRepo;
    private final InterRepo<Order,Integer> orderRepo;

    public Service(InterRepo<Cake,Integer> cakeRepo, InterRepo<Order,Integer> orderRepo) {
        //cakeRepo = cakeRepo;
        this.cakeRepo = (InterRepo<Cake,Integer>) cakeRepo;
        this.orderRepo = (InterRepo<Order,Integer>) orderRepo;
    }

    public Iterable<Cake> getAllCakes() {
        return cakeRepo.getAllItems();
    }

    public Iterable<Order> getAllOrders() {
        return  orderRepo.getAllItems();
    }

    // Operations on cakes
    public void addCake(Integer id, String name, int price) {
        try {
            cakeRepo.addItem(new Cake(id, name, price));
        } catch (Exception e) {
            // Handle the exception
            System.err.println("Error cake: " + e.getMessage());
        }
    }

    public Cake getCakeById(Integer id) {
        return cakeRepo.findItem(id);
    }

    public void updateCake(Integer id, String name, int price) {
        try {
            Cake cake = cakeRepo.findItem(id);
            if (cake != null) {
                cake.setName(name);
                cake.setPrice(price);
            }
        }catch(Exception e){
            // Handle the exception
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void removeCake(Integer id)  {
        try {
            cakeRepo.removeItem(id);
        }catch(Exception e){
            // Handle the exception
            System.err.println("Error: " + e.getMessage());
        }
    }

    // Operations on orders
    public void addOrder(Integer id, int cakeId, String address)  {
        try {
            if (orderRepo.findItem(id) == null) {
                Cake cake = getCakeById(cakeId);
                if (cake != null) {
                    orderRepo.addItem(new Order(id, cake, address));
                }
            }
        }catch (Exception e) {
            System.err.println("Error:" + e.getMessage());
        }
    }

    public Order getOrderById(Integer id) {
        return orderRepo.findItem(id);
    }

    public void updateOrder(Integer id, int cakeId, String address) {
        try {
            Order order = orderRepo.findItem(id);
            if (order != null) {
                Cake cake = getCakeById(cakeId);
                if (cake != null) {
                    order.setCake(cake);
                    order.setAdress(address);
                }
            }
        }catch (Exception e) {
            System.err.println("Error:" + e.getMessage());
        }
    }

    public void removeOrder(Integer id)  {
        try{
            orderRepo.removeItem(id);
        }catch (Exception e) {
            System.err.println("Error:" + e.getMessage());
        }
    }


    //orders with a specific cake id
    public List<Order> OrdersWithSpecificCake(int cid) {
        Iterable<Order> allOrdersIterable = this.getAllOrders();

        // Convert the Iterable to a List
        List<Order> allOrders = StreamSupport.stream(allOrdersIterable.spliterator(), false)
                .toList();
        //System.out.println(allOrders);
        return allOrders.stream()
                .filter(order -> order.getCake().getId()==cid)
                .collect(Collectors.toList());
    }


    //list of cakes ordered on a given address
    public List<Cake> CakesOrderedToAddress(String givenAddress) {
        Iterable<Order> allOrdersIterable = this.getAllOrders();

        // Convert the Iterable to a List
        List<Order> allOrders = StreamSupport.stream(allOrdersIterable.spliterator(), false)
                .toList();


        return allOrders.stream()
                .filter(order -> order.getAdress().equals(givenAddress))
                .map(Order::getCake)
                .collect(Collectors.toList());
    }

    //list of orders with cake's price greater than a threshold
    public List<Order> OrdersWithCakesPriceGreaterThan(int threshold) {
        Iterable<Order> allOrdersIterable = this.getAllOrders();
        // Convert the Iterable to a List
        List<Order> allOrders = StreamSupport.stream(allOrdersIterable.spliterator(), false)
                .toList();
        return allOrders.stream()
                .filter(order -> order.getCake().getPrice() > threshold)
                .collect(Collectors.toList());
    }

    //list of orders with most expensive Cake
    public List<Order> OrdersWithMostExpensiveCake() {
        Iterable<Order> allOrdersIterable = this.getAllOrders();

        // Convert the Iterable to a List
        List<Order> allOrders = StreamSupport.stream(allOrdersIterable.spliterator(), false)
                .toList();
        int maxPrice = allOrders.stream()
                .mapToInt(order -> order.getCake().getPrice())
                .max()
                .orElse(0);
        return allOrders.stream()
                .filter(order -> order.getCake().getPrice() == maxPrice)
                .collect(Collectors.toList());
    }

    //total number of sold cakes
    public int getTotalNumberOfCakesSold() {

        return (int) StreamSupport.stream(this.getAllOrders().spliterator(), false)
                .mapToLong(order -> order.getCake().getId())
                .distinct()
                .count();
    }



}