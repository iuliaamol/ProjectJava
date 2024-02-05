package gui;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import org.w3c.dom.events.MouseEvent;
import service.Service;
import domain.Cake;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;


public class CakesController {
    private Service service;

    public CakesController(Service service) {
        this.service = service;
    }

    @FXML
    private TextField searchTextFile;

    @FXML
    private ListView<Cake> cakesListView;

    void populateList() {
        Iterable<Cake> cakeIterable = service.getAllCakes();

        // Convert the iterable to an ObservableList
        ObservableList<Cake> cakesList = FXCollections.observableArrayList();
        cakeIterable.forEach(cakesList::add);

        // Set the items in the ListView
        cakesListView.setItems(cakesList);
    }
    public void initialize(){
        populateList();
        //addCakeButton.setOnMouseClicked(event -> addCakeButton());
    }


    @FXML
    private TextField idCakeTextField;

    @FXML
    private TextField nameCakeTextField;

    @FXML
    private TextField priceCakeTextField;

    @FXML
    private Button addCakeButton;

    @FXML
    private Button removeCakeButton;


    @FXML
    void addCakeButton(ActionEvent event) {
        Integer id =Integer.parseInt(idCakeTextField.getText());
        String name=nameCakeTextField.getText();
        Integer price =Integer.parseInt(priceCakeTextField.getText());
        service.addCake(id,name,price);
        populateList();

    }

    @FXML
    void removeCakeButton(ActionEvent event) {
        Integer id =Integer.parseInt(idCakeTextField.getText());
        service.removeCake(id);
        populateList();

    }

}
