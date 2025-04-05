package services;

import models.User;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;
import java.util.Scanner;

public class AuthManager {
    private static final Scanner scanner = new Scanner(System.in);

    public static User signIn(List<User> users) {
        System.out.print("Enter username: ");
        String user = scanner.nextLine();
        System.out.print("Enter password: ");
        String pass = scanner.nextLine();

        for (User u : users) {
            if (u.getUsername().equals(user) && u.getPassword().equals(pass)) {
                FileManager.appendLog(getTime() + " " + user + " вошёл в систему");
                return u;
            }
        }
        System.out.println("Неверные данные.");
        return null;
    }

    public static User signUp(List<User> users) {
        System.out.print("Full name: ");
        String fullName = scanner.nextLine();
        System.out.print("Username (уникальное): ");
        String username = scanner.nextLine();

        for (User u : users) {
            if (u.getUsername().equals(username)) {
                System.out.println("Username занят.");
                return null;
            }
        }

        System.out.print("Password: ");
        String pass = scanner.nextLine();
        System.out.print("Repeat Password: ");
        String pass2 = scanner.nextLine();

        if (!pass.equals(pass2)) {
            System.out.println("Пароли не совпадают.");
            return null;
        }

        User newUser = new User(fullName, username, pass);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("data/users.txt", true))) {
            bw.write(String.join(";", fullName, username, pass));
            bw.newLine();
        } catch (Exception e) {
            System.out.println("Ошибка при регистрации.");
        }

        FileManager.appendLog(getTime() + " " + username + " зарегистрировался");
        return newUser;
    }

    private static String getTime() {
        return java.time.LocalDateTime.now().toString().replace("T", " ");
    }
}

