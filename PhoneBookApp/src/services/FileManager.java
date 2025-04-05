package services;

import models.Contact;
import models.User;

import java.io.*;
import java.util.*;

public class FileManager {
    private static final String USERS_FILE = "data/users.txt";
    private static final String CONTACTS_FOLDER = "data/contacts/";

    public static List<User> loadUsers() {
        List<User> users = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 3)
                    users.add(new User(parts[0], parts[1], parts[2]));
            }
        } catch (IOException e) {
            System.out.println("Ошибка при загрузке пользователей.");
        }
        return users;
    }

    public static List<Contact> loadContacts(String username) {
        List<Contact> contacts = new ArrayList<>();
        File file = new File(CONTACTS_FOLDER + username + "Contacts.txt");
        if (!file.exists()) return contacts;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 5)
                    contacts.add(new Contact(parts[0], parts[1], parts[2], parts[3], Integer.parseInt(parts[4])));
            }
        } catch (IOException e) {
            System.out.println("Ошибка при загрузке контактов.");
        }
        return contacts;
    }

    public static void saveContacts(String username, List<Contact> contacts) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CONTACTS_FOLDER + username + "Contacts.txt"))) {
            for (Contact c : contacts) {
                bw.write(String.join(";", c.getId(), c.getName(), c.getSurname(), c.getPhone(), String.valueOf(c.getAge())));
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении контактов.");
        }
    }

    public static void appendLog(String log) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("data/logger.txt", true))) {
            bw.write(log);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Ошибка при логировании.");
        }
    }
}




