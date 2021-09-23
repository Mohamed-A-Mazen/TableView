package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        TableView<Contact> myTable = new TableView<Contact>();
        BorderPane root = new BorderPane();
        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("Contacts");
        MenuItem add = new MenuItem("add");
        MenuItem edit = new MenuItem("edit");
        MenuItem exit = new MenuItem("exit");
        MenuItem delete = new MenuItem("delete");
        menu.getItems().addAll(add, edit, delete, exit);
        menuBar.getMenus().add(menu);
        TableColumn<Contact, String> firstCol = new TableColumn<>("first name");
        firstCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        TableColumn<Contact, String> secondCol = new TableColumn<>("last name");
        secondCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        TableColumn<Contact, String> third = new TableColumn<>("PhoneNum");
        third.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        TableColumn<Contact, String> fourth = new TableColumn<>("Notes");
        fourth.setCellValueFactory(new PropertyValueFactory<>("notes"));
        myTable.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                Contact contact = myTable.getSelectionModel().getSelectedItem();
                EventHandlers.deleteItem(contact, keyEvent);
            }
        });
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (myTable.getSelectionModel().getSelectedItem() == null) {
                    myTable.getSelectionModel().selectFirst();
                    Contact contact = myTable.getSelectionModel().getSelectedItem();
                    EventHandlers.deleteItem(contact);
                } else {
                    Contact contact = myTable.getSelectionModel().getSelectedItem();
                    EventHandlers.deleteItem(contact);
                }
            }
        });
        add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Contact contact = myTable.getSelectionModel().getSelectedItem();
                EventHandlers.dialog("add", contact);
            }
        });
        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Platform.exit();
            }
        });
        edit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Contact contact = myTable.getSelectionModel().getSelectedItem();
                EventHandlers.dialog("edit", contact);
            }
        });
        myTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Contact>() {
            @Override
            public void changed(ObservableValue<? extends Contact> observableValue, Contact contact, Contact t1) {
                myTable.setItems(ContactData.getMyInstance().getContacts());
            }
        });
        myTable.getColumns().addAll(firstCol, secondCol, third, fourth);
        myTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        myTable.setItems(ContactData.getMyInstance().getContacts());
        root.setCenter(myTable);
        root.setTop(menuBar);
        primaryStage.setTitle("Contacts Table");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        ContactData.getMyInstance().storeContacts();
    }

    @Override
    public void init() throws Exception {
        ContactData.getMyInstance().loadContacts();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
