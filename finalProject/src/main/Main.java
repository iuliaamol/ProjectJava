package main;

import domain.Cake;
import domain.Order;
import gui.CakesController;
import gui.OrdersController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import repository.*;
import service.Service;

import ui.UI;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;


public class Main extends Application {


    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {

        InterRepo<Cake, Integer> Cake_repo = null;
        InterRepo<Order, Integer> Order_repo = null;



        try (FileReader fr = new FileReader("src/settings.properties")) {
            Properties props = new Properties();
            props.load(fr);

            String repoType = props.getProperty("repository");
            String sourceName1 = props.getProperty("cakefile");
            String sourceName2 = props.getProperty("orderfile");
            switch (repoType) {
                case "textfile":
                    Cake_repo = new CakeRepoTextFile(sourceName1);
                    Order_repo = new OrderRepoTextFile(sourceName2);
                    Service service = new Service(Cake_repo, Order_repo);
                    CakesController cakesController=new CakesController(service);
                    OrdersController ordersController=new OrdersController(service);

                    FXMLLoader cakesLoader = new FXMLLoader(getClass().getResource("/gui/CakesGUI.fxml"));
                    cakesLoader.setController(cakesController);
                    Scene cakesScene = new Scene(cakesLoader.load());
                    stage.setScene(cakesScene);
                    stage.show();

                    FXMLLoader ordersLoader = new FXMLLoader(getClass().getResource("/gui/OrdersGUI.fxml"));
                    ordersLoader.setController(ordersController);
                    Scene ordersScene = new Scene(ordersLoader.load());
                    // use a different Stage for the Orders scene
                    Stage ordersStage = new Stage();
                    ordersStage.setScene(ordersScene);
                    ordersStage.show();

                    break;
                case "binaryfile":
                    Cake_repo = new CakeRepoBinaryFile(sourceName1);
                    Order_repo = new OrderRepoBinaryFile(sourceName2);
                    Service service2 = new Service(Cake_repo, Order_repo);
                    UI ui2 = new UI(service2);
                    ui2.run();
                    break;
                case "database":
                    Cake_repo = new CakeRepoDB(sourceName1);
                    Order_repo = new OrderRepoDB(sourceName2);
                    Service service3 = new Service(Cake_repo, Order_repo);
                    UI ui3 = new UI(service3);
                    ui3.run();
                    break;

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (DuplicateEntityException e) {
            System.out.println("already exists");

        }


    }
}
