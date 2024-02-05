package gui;

import domain.Cake;
import domain.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import service.Service;

public class OrdersController {
    public Service service;
    @FXML
    private TextField searchTextFile;

    @FXML
    private ListView<Order> ordersListView;

    public OrdersController(Service service) {
        this.service=service;

    }
    void populateList() {
        Iterable<Order> orderIterable = service.getAllOrders();

        // Convert the iterable to an ObservableList
        ObservableList<Order> ordersList = FXCollections.observableArrayList();
        orderIterable.forEach(ordersList::add);

        // Set the items in the ListView
        ordersListView.setItems(ordersList);
    }

    public void initialize(){
        populateList();
    }


    @FXML
    private TextField idCakeOTextFIled;

    @FXML
    private TextField idOrderTextFiled;

    @FXML
    private TextField adressOrderTextFiled;

    @FXML
    private Button addOrderButton;

    @FXML
    private Button removeOrderButton;

    @FXML
    void addOrder(ActionEvent event) {
        Integer id =Integer.parseInt(idOrderTextFiled.getText());
        Integer idC =Integer.parseInt(idCakeOTextFIled.getText());
        String adr=adressOrderTextFiled.getText();
        service.addOrder(id,idC,adr);
        populateList();

    }

    @FXML
    void removeOrder(ActionEvent event) {
        Integer id =Integer.parseInt(idOrderTextFiled.getText());
        service.removeOrder(id);
        populateList();
    }
    @FXML
    void updateOrder(ActionEvent event) {
        Integer id =Integer.parseInt(idOrderTextFiled.getText());
        Integer idC =Integer.parseInt(idCakeOTextFIled.getText());
        String adr=adressOrderTextFiled.getText();
        service.updateOrder(id,idC,adr);
        populateList();


    }
    @FXML
    private TextField text1;

    @FXML
    private TextField text2;

    @FXML
    private TextField text3;

    //@FXML
//    void CakesOrderedToAddress(ActionEvent event) {
//        String adr=text2.getText();
//        Iterable<Cake> cakeIterable = service.CakesOrderedToAddress(adr);
//        // Convert the iterable to an ObservableList
//        ObservableList<Cake> cakeList = FXCollections.observableArrayList();
//        cakeIterable.forEach(cakeList::add);
//
//        // Set the items in the ListView
//        orderListView.setItems(cakeList);
//    }

    @FXML
    void OrdersWithCakesPriceGreaterThan(ActionEvent event) {
        Integer nr =Integer.parseInt(text3.getText());
        Iterable<Order> orderIterable = service.OrdersWithCakesPriceGreaterThan(nr);

        // Convert the iterable to an ObservableList
        ObservableList<Order> ordersList = FXCollections.observableArrayList();
        orderIterable.forEach(ordersList::add);

        // Set the items in the ListView
        ordersListView.setItems(ordersList);
    }

    @FXML
    void OrdersWithMostExpensiveCake(ActionEvent event) {
        service.OrdersWithMostExpensiveCake();
        Iterable<Order> orderIterable = service.OrdersWithMostExpensiveCake();

        // Convert the iterable to an ObservableList
        ObservableList<Order> ordersList = FXCollections.observableArrayList();
        orderIterable.forEach(ordersList::add);

        // Set the items in the ListView
        ordersListView.setItems(ordersList);

    }

    @FXML
    void OrdersWithSpecificCake(ActionEvent event) {
        Integer idcc =Integer.parseInt(text1.getText());
        Iterable<Order> orderIterable = service.OrdersWithSpecificCake(idcc);;

        // Convert the iterable to an ObservableList
        ObservableList<Order> ordersList = FXCollections.observableArrayList();
        orderIterable.forEach(ordersList::add);

        // Set the items in the ListView
        ordersListView.setItems(ordersList);


    }

    @FXML
    void TotalNumberOfCakesSold(ActionEvent event) {
        service.getTotalNumberOfCakesSold();

        populateList();

    }


}
