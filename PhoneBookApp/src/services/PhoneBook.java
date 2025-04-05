package services;

import models.Contact;
import models.User;

import java.util.*;
import java.util.stream.Collectors;

public class PhoneBook {
    private final User currentUser;
    private List<Contact> contacts;
    private final Scanner scanner = new Scanner(System.in);

    public PhoneBook(User user) {
        this.currentUser = user;
        this.contacts = FileManager.loadContacts(user.getUsername());
    }

    public void start() {
        while (true) {
            System.out.println("\n1 - Контакты\n2 - Поиск\n3 - Фильтрация\n4 - Сортировка\n5 - Logger\n6 - Назад");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> contactMenu();
                case "2" -> searchMenu();
                case "3" -> filterMenu();
                case "4" -> sortMenu();
                case "5" -> showLogger();
                case "6" -> {
                    FileManager.saveContacts(currentUser.getUsername(), contacts);
                    return;
                }
                default -> System.out.println("Неверный ввод.");
            }
        }
    }

    private void contactMenu() {
        System.out.println("1 - Добавить\n2 - Удалить\n3 - Редактировать\n4 - Показать все");
        String c = scanner.nextLine();
        switch (c) {
            case "1" -> addContact();
            case "2" -> deleteContact();
            case "3" -> editContact();
            case "4" -> displayContacts();
            default -> System.out.println("Неверный выбор.");
        }
    }

    private void addContact() {
        System.out.print("Имя: ");
        String name = scanner.nextLine();
        System.out.print("Фамилия: ");
        String surname = scanner.nextLine();
        System.out.print("Телефон: ");
        String phone = scanner.nextLine();
        System.out.print("Возраст: ");
        int age = Integer.parseInt(scanner.nextLine());

        Contact contact = new Contact(name, surname, phone, age);
        contacts.add(contact);
        FileManager.appendLog(now() + " " + currentUser.getUsername() + " добавил контакт " + contact.getName() + " " + contact.getSurname());
        System.out.println("Контакт добавлен.");
    }

    private void deleteContact() {
        System.out.print("Введите ID или имя для удаления: ");
        String input = scanner.nextLine();
        Contact toRemove = contacts.stream()
                .filter(c -> c.getId().equals(input) || c.getName().equalsIgnoreCase(input))
                .findFirst().orElse(null);

        if (toRemove != null) {
            System.out.println("Подтвердите удаление (y/n): ");
            if (scanner.nextLine().equalsIgnoreCase("y")) {
                contacts.remove(toRemove);
                FileManager.appendLog(now() + " " + currentUser.getUsername() + " удалил контакт " + toRemove.getName() + " " + toRemove.getSurname());
                System.out.println("Контакт удален.");
            }
        } else {
            System.out.println("Контакт не найден.");
        }
    }

    private void editContact() {
        System.out.print("Введите ID для редактирования: ");
        String id = scanner.nextLine();
        Contact c = contacts.stream().filter(ct -> ct.getId().equals(id)).findFirst().orElse(null);

        if (c == null) {
            System.out.println("Контакт не найден.");
            return;
        }

        System.out.print("Новое имя (" + c.getName() + "): ");
        String name = scanner.nextLine();
        System.out.print("Новая фамилия (" + c.getSurname() + "): ");
        String surname = scanner.nextLine();
        System.out.print("Новый телефон (" + c.getPhone() + "): ");
        String phone = scanner.nextLine();
        System.out.print("Новый возраст (" + c.getAge() + "): ");
        String ageStr = scanner.nextLine();

        if (!name.isEmpty()) c.setName(name);
        if (!surname.isEmpty()) c.setSurname(surname);
        if (!phone.isEmpty()) c.setPhone(phone);
        if (!ageStr.isEmpty()) c.setAge(Integer.parseInt(ageStr));

        FileManager.appendLog(now() + " " + currentUser.getUsername() + " отредактировал контакт " + c.getName());
        System.out.println("Контакт обновлен.");
    }

    private void displayContacts() {
        for (Contact c : contacts) {
            System.out.println(c.getId() + " | " + c.getName() + " " + c.getSurname() + " | " + c.getPhone() + " | " + c.getAge());
        }
    }

    private void searchMenu() {
        System.out.println("1 - По имени\n2 - По фамилии\n3 - По номеру\n4 - По всем полям\n5 - Спец поиск (_ , %)");
        String option = scanner.nextLine();
        System.out.print("Введите поисковый запрос: ");
        String input = scanner.nextLine();

        List<Contact> results = switch (option) {
            case "1" -> contacts.stream().filter(c -> c.getName().toLowerCase().contains(input.toLowerCase())).toList();
            case "2" -> contacts.stream().filter(c -> c.getSurname().toLowerCase().contains(input.toLowerCase())).toList();
            case "3" -> contacts.stream().filter(c -> c.getPhone().contains(input)).toList();
            case "4" -> contacts.stream().filter(c ->
                    c.getName().contains(input) || c.getSurname().contains(input) || c.getPhone().contains(input)
            ).toList();
            case "5" -> wildcardSearch(input);
            default -> List.of();
        };

        results.forEach(c -> System.out.println(c.getId() + " | " + c.getName() + " " + c.getSurname() + " | " + c.getPhone() + " | " + c.getAge()));
    }

    private List<Contact> wildcardSearch(String pattern) {
        String regex = pattern.replace("%", ".*").replace("_", ".");
        return contacts.stream()
                .filter(c -> c.getName().matches("(?i).*" + regex + ".*") || c.getSurname().matches("(?i).*" + regex + ".*"))
                .toList();
    }

    private void filterMenu() {
        System.out.println("3 - возраст > N\n4 - возраст < N");
        String c = scanner.nextLine();
        System.out.print("Введите возраст: ");
        int n = Integer.parseInt(scanner.nextLine());

        List<Contact> filtered = switch (c) {
            case "3" -> contacts.stream().filter(ct -> ct.getAge() > n).toList();
            case "4" -> contacts.stream().filter(ct -> ct.getAge() < n).toList();
            default -> List.of();
        };

        filtered.forEach(ct -> System.out.println(ct.getName() + " " + ct.getSurname() + " | " + ct.getAge()));
    }

    private void sortMenu() {
        System.out.println("1 - По имени (A-Z)\n2 - По имени (Z-A)\n3 - По фамилии\n4 - По номеру");
        String opt = scanner.nextLine();

        Comparator<Contact> comparator = switch (opt) {
            case "1" -> Comparator.comparing(Contact::getName);
            case "2" -> Comparator.comparing(Contact::getName).reversed();
            case "3" -> Comparator.comparing(Contact::getSurname);
            case "4" -> Comparator.comparing(Contact::getPhone);
            default -> null;
        };

        if (comparator != null) {
            contacts = contacts.stream().sorted(comparator).collect(Collectors.toList());
            FileManager.appendLog(now() + " " + currentUser.getUsername() + " сделал сортировку");
            displayContacts();
        } else {
            System.out.println("Неверная опция.");
        }
    }

    private void showLogger() {
        try (Scanner sc = new Scanner(new java.io.File("data/logger.txt"))) {
            while (sc.hasNextLine()) System.out.println(sc.nextLine());
        } catch (Exception e) {
            System.out.println("Ошибка чтения лога.");
        }
    }

    private String now() {
        return java.time.LocalDateTime.now().toString().replace("T", " ");
    }
}