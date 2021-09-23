package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ContactData {
    private final String fileName = "Contacts.txt";
    private ObservableList<Contact> contacts = FXCollections.observableArrayList();
    private final static ContactData myInstance = new ContactData();

    private ContactData() {
    }

    public void storeContacts() throws IOException {
        List<Contact> list1 = new ArrayList<>(this.contacts);
        Path path = Paths.get(fileName);
        BufferedWriter bw = Files.newBufferedWriter(path);
        try {
            Iterator<Contact> iterator = contacts.listIterator();
            while (iterator.hasNext()) {
                StringBuilder s = new StringBuilder();
                Contact contact = iterator.next();
                bw.write(String.format("%s\t%s\t%s\t%s", contact.getFirstName(), contact.getLastName(),
                        contact.getPhoneNumber(), contact.getNotes()));
                bw.newLine();

            }
        } finally {
            bw.close();
        }

    }

    public void loadContacts() throws IOException {
        Path path = Paths.get(fileName);
        BufferedReader br = Files.newBufferedReader(path);
        try {
            String line;
            while ((line = br.readLine()) != null) {
                String[] strings = line.split("\t");
                Contact contact = new Contact(strings[0], strings[1], strings[2], strings[3]);
                contacts.add(contact);
            }
            System.out.println(contacts.size());
        } finally {
            if (br != null)
                br.close();
        }
    }

    public static ContactData getMyInstance() {
        return myInstance;
    }

    public ObservableList<Contact> getContacts() {
        return FXCollections.observableList(this.contacts);
    }
}
