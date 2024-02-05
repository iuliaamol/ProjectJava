package ui;

import domain.Cake;
import domain.Identifiable;
import domain.Order;
import repository.DuplicateEntityException;
import service.Service;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


public class UI {
    private final Service service;

    public UI(Service s){
        this.service=s;

    }

    public void menu(){
        System.out.println("-------------------");
        System.out.println("0: exit");
        System.out.println("1: add a new cake");
        System.out.println("2: add a new order");
        System.out.println("3: change an order");
        System.out.println("4: finish an order");
        System.out.println("5: see the orders");
        System.out.println("6: see the cakes");
        System.out.println("7: create reports");
        System.out.println("  ");

    }

    public void add_cake(String n, Integer i, int p) throws DuplicateEntityException {
        service.addCake(i, n, p);
    }
    public void update_cake(String n,Integer i,int p){
        service.updateCake(i,n,p);
    }
    public void remove_cake(Integer i) throws DuplicateEntityException {
        service.removeCake(i);
    }
    public void show_cakes(){
        Iterable<Cake> cakes=service.getAllCakes();
        for(Identifiable c:cakes)
            System.out.println(c);
    }


    public void add_order(Integer i, int cake_id, String a) throws DuplicateEntityException {

        service.addOrder(i,cake_id,a);
    }
    public void update_order(Integer i, int cake_id, String a){
        service.updateOrder(i,cake_id,a);
    }
    public void remove_order(Integer i) throws DuplicateEntityException {service.removeOrder(i);}
    public void show_orders(){
        Iterable<Order> orders=service.getAllOrders();
        for(Identifiable o:orders)
            System.out.println(o);
    }


    ///----------------------
    ///5 different reports





    public void run() throws DuplicateEntityException {
        while(true){
            menu();
            System.out.println("choose....");
            Scanner sc=new Scanner(System.in);
            int command=sc.nextInt();

            switch(command){
                case 0:
                    return;
                case 1: //add a new cake
                    System.out.println("enter the name of the new cake: ");
                    Scanner sc1=new Scanner(System.in);
                    String name=sc1.nextLine();
                    System.out.println("enter the id: ");
                    Scanner sc2=new Scanner(System.in);
                    int id=sc2.nextInt();
                    System.out.println("enter the price: ");
                    Scanner sc3=new Scanner(System.in);
                    int price=sc3.nextInt();
                    add_cake(name,id,price);
                    // show_cakes();
                    break;

                case 2: //add a new order
                    System.out.println("enter the id of the new order: ");
                    Scanner sc4=new Scanner(System.in);
                    int i=sc4.nextInt();
                    System.out.println("enter the id of the cake: ");
                    Scanner sc5=new Scanner(System.in);
                    int idca=sc5.nextInt();
                    System.out.println("enter the address : ");
                    Scanner sc6=new Scanner(System.in);
                    String ad=sc6.nextLine();
                    add_order(i,idca,ad);
                    //show_orders();
                    break;

                case 3: //change an order
                    System.out.println("enter the id of the order to change: ");
                    Scanner sc7=new Scanner(System.in);
                    int idc=sc7.nextInt();
                    System.out.println("enter the new cake id: ");
                    Scanner sc8=new Scanner(System.in);
                    int cake_id=sc8.nextInt();
                    System.out.println("enter the new adress: ");
                    Scanner sc9=new Scanner(System.in);
                    String addd=sc9.nextLine();
                    update_order(idc,cake_id,addd);
                    //show_orders();
                    break;

                case 4://finish an order
                    System.out.println("enter the id of the finished order: ");
                    Scanner sc10=new Scanner(System.in);
                    int idf=sc10.nextInt();
                    remove_order(idf);
                    //show_orders();
                    break;
                case 5:
                    show_orders();
                    break;
                case 6:
                    show_cakes();
                    break;
                case 7:
                    System.out.println("choose one report");
                    System.out.println("1-orders with a specific cake");
                    System.out.println("2-list of cakes ordered on a given address");
                    System.out.println("3-list of orders with most expensive Cake");
                    System.out.println("4-list of orders with cake's price greater than a threshold");
                    System.out.println("5-total number os sold cakes");
                    Scanner sc11=new Scanner(System.in);
                    int command2=sc.nextInt();

                    switch(command2){
                        case 0:
                            return;
                        case 1:
                            System.out.println("enter the specific cake id: ");
                            Scanner sc12=new Scanner(System.in);
                            int c_id=sc12.nextInt();
                            System.out.println(service.OrdersWithSpecificCake(c_id));
                            //System.out.println(orders);
                            break;
                        case 2:
                            System.out.println("enter the address: ");
                            Scanner sc13=new Scanner(System.in);
                            String addr=sc13.nextLine();
                            System.out.println(service.CakesOrderedToAddress(addr));
                            break;
                        case 3:
                            System.out.println(service.OrdersWithMostExpensiveCake());
                            break;
                        case 4:
                            System.out.println("enter the threshold: ");
                            Scanner sc14=new Scanner(System.in);
                            int thr=sc14.nextInt();
                            System.out.println(service.OrdersWithCakesPriceGreaterThan(thr));
                            break;
                        case 5:
                            System.out.println(service.getTotalNumberOfCakesSold());
                            break;

                    }



            }

        }
    }
}