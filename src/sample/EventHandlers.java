package sample;

import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class EventHandlers {


    public static void dialog(String source, Contact contact) {
        Stage stage = new Stage();
        stage.setWidth(500);
        stage.setHeight(250);
        stage.initModality(Modality.APPLICATION_MODAL);
        Dialog<ButtonType> dialog = new Dialog();
        dialog.setTitle("dialogue");
        Label label = new Label("enter info to add a new item");
        label.setPrefWidth(50);
        label.setFont(Font.font("Times New Roman bold", 20));
        dialog.setHeaderText(label.getText());
        TextField firstName = new TextField();
        firstName.setPromptText("First Name");
        TextField lastName = new TextField();
        lastName.setPromptText("Last Name");
        TextField phoneNumber = new TextField();
        phoneNumber.setPromptText("phone number");
        TextField notes = new TextField();
        notes.setPromptText("notes");
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.getChildren().addAll(firstName, lastName, phoneNumber, notes);
        dialog.setGraphic(vBox);
        ButtonType ok = new ButtonType("ok", ButtonType.OK.getButtonData());
        ButtonType cancel = new ButtonType("cancel", ButtonType.CANCEL.getButtonData());
        dialog.getDialogPane().getButtonTypes().addAll(ok, cancel);
        dialog.setResizable(true);
        if (source.equals("edit")) {
            firstName.setText(contact.getFirstName());
            lastName.setText(contact.getLastName());
            phoneNumber.setText(contact.getPhoneNumber());
            notes.setText(contact.getNotes());
        }
        Optional<ButtonType> result = dialog.showAndWait();
        String s = firstName.getText() + lastName.getText() +
                phoneNumber.getText() + notes.getText().trim();
        if (result.isPresent() && result.get().getButtonData().equals(ButtonType.OK.getButtonData()) && !s.equals("")) {
            Contact contactToBeAdded = new Contact(firstName.getText(), lastName.getText(), phoneNumber.getText(), notes.getText());
            if (source.equals("edit")) {
                int index = ContactData.getMyInstance().getContacts().indexOf(contact);
                ContactData.getMyInstance().getContacts().remove(contact);
                ContactData.getMyInstance().getContacts().add(index, contactToBeAdded);
            } else {
                ContactData.getMyInstance().getContacts().add(contactToBeAdded);
            }

        }
        stage.close();
    }

    public static void deleteItem(Contact contact) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("confirm the deletion");
        alert.setContentText("the following contact " + contact.getFirstName() + " will be removed");
        alert.setResizable(true);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            ContactData.getMyInstance().getContacts().remove(contact);
        }
    }

    public static void deleteItem(Contact contact, KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.DELETE) || keyEvent.getCode().equals(KeyCode.BACK_SPACE)) {
            deleteItem(contact);
        }
    }
}


