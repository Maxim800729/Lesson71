//Старт : 22.03.2025
//Финиш : 11.04.2025 (20 дней)
//
//
//        -----------------------------------
//        |                                  |
//        |                                  |
//        |        1   -   Sign in           |
//        |                                  |
//        |        2   -   Sign up           |
//        |                                  |
//        |        3   -   Exit              |
//        |                                  |
//        |                                  |
//        -----------------------------------
//
//
//
//        📄 Техническое задание
//Название проекта: Консольное приложение "Телефонная книга"
//Язык реализации: Java
//Формат взаимодействия: Консоль
//🎯 Цель проекта
//Разработать консольное Java-приложение для управления телефонной книгой.
//Пользователь должен иметь возможность создавать, редактировать, удалять и просматривать контакты,
//а также сохранять и загружать данные из файла.
//
//
//        🧩 Функциональные требования
//1. CRUD-операции для контактов:
//C (Create) – Добавление нового контакта
//R (Read) – Просмотр списка всех контактов
//U (Update) – Редактирование существующего контакта
//D (Delete) – Удаление контакта по ID или имени
//
//2. Структура контакта:
//Каждый контакт должен содержать:
//
//Уникальный ID (генерируется автоматически)
//Имя
//        Фамилия
//Номер телефона
//Возраст
//
//3. Хранение данных:
//Контакты должны сохраняться в файл (contacts.txt)
//Реализовать чтение данных из файла при запуске
//Реализовать запись данных в файл после изменений
//
//
//4. Поиск и фильтрация:
//Поиск по имени, фамилии, номеру телефона
//
//
//_ -> 1 символ
//% -> 0 или N количество символов
//
//Иван -> Иван Иванов
//Ива -> Ива
//Ива_ -> Иван Иванов , Ивар Иванов ,  Иваг Иванов
//__рид -> Фарид , Дарид , Гарид
//Ива% -> Иванфывап Иванов , Ивар Иванов ,  Иваг Иванов , Ива
//
//Поддержка частичного совпадения
//
//ВА - Нармина Махмудова , Фарид вараев
//
//
//
//5. Сортировка:
//Возможность отсортировать контакты:
//
//По имени (A-Z, Z-A)
//По фамилии
//По номеру телефона
//
//6. Удобный CLI-интерфейс:
//Меню с выбором опций
//
//1 - Контакты  (1 - Добавить 2 - Удалить 3 - Редактировать 4 - Отобразить)   1) Фарид Абдуллаев 29 +994519999902
//        2 - Поиск     (0 - Регистр OFF 1 - По имени 2 - По фамилии , 3 - По номеру , 4 - По всем параметрам , 5 - Спец поиск (_ , %))
//        3 - Фильтрация (1 - только мужчины 2 только женщины , 3 возраст больше n, 4 возраст меньше n )
//4 - Сортировка
//5 - Logger
//6 - Back
//
//        (1 -По ид  2 - по номеру 3 - по нумбер)
//Подтверждение удаления
//
//Обработка ошибок (например, некорректный номер телефона, несуществующий ID и т.д.)
//
//
//📦 Нефункциональные требования
//Читаемый и структурированный код (рекомендуется использовать классы: PhoneBook, Contact, FileManager)
//
//Соблюдение принципов ООП
//
//
//💡 Дополнительные (опциональные) фичи:
//Поддержка нескольких номеров у одного контакта
//
//Логирование действий (файл logger.txt)
//22.03.2025 15:02 Farid сделал вход
//22.03.2025 15:03 Farid удалил контакт Фарид Абдуллаев 29 +994519999902
//        22.03.2025 15:05 Farid добавил контакт Дима Абдуллаев 29 +994519999902
//        22.03.2025 15:05 Farid сделал сортировку контакт Дима Абдуллаев 29 +994519999902
//
//
//
//        1 - Sign In
//Enter username : Fayev
//Enter passowrd : Baku29
//
//
//2 - Sign Up
//Enter name           : Farid
//Enter surnema        : Abdullayev
//Enter username       : f-abdullayev  (UNIQUE)
//Enter passowrd       : Baku29
//Enter repead passowrd: Baku29
//
//Check password and repead
//
//username : Fayev
//
//
//
//3 - Print logger
//
//4 - Exit
//
//
//contacts/FayevContacts.txt
//contacts/f-abdullayevContacts.txt
//
//
//class User
//class Contact
//
//
//1) users.txt ( список всех юзеров )
//2) username.txt ( у каждого юзера свой файл контаков)
//3) logger.txt ( 1 общий для всех юзеров)
//
//

/// / FileManager (fileManger , filemanger,FILEMANGER)
/// / email_address
/// / fullName
import services.AuthManager;
import services.FileManager;
import services.PhoneBook;

import java.util.List;
import java.util.Scanner;

import models.User;
//import services.AuthManager;
//import services.FileManager;
//import services.PhoneBook;
//
//import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List<User> users = FileManager.loadUsers();
        Scanner scanner = new Scanner(System.in);
        User currentUser = null;

        while (true) {
            System.out.println("1 - Sign In\n2 - Sign Up\n3 - Exit");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    currentUser = AuthManager.signIn(users);
                    if (currentUser != null) {
                        new PhoneBook(currentUser).start();
                    }
                    break;
                case "2":
                    User newUser = AuthManager.signUp(users);
                    if (newUser != null) {
                        users.add(newUser);
                        currentUser = newUser;
                        new PhoneBook(currentUser).start();
                    }
                    break;
                case "3":
                    return;
            }
        }
    }
}

