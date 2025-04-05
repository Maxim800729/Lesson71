package models;

import java.util.UUID;

public class Contact {

    private String id;
    private String name;
    private String surname;
    private String phone;
    private int age;

    public Contact(String name, String surname, String phone, int age) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.age = age;
    }

    public Contact(String id, String name, String surname, String phone, int age) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.age = age;
    }

    public String getId() {return id;}
    public String getName() {return name;}
    public String getSurname() {return surname;}
    public String getPhone() {return phone;}
    public int getAge(){return age;}

    public void setName(String name){this.name = name;}
    public void setSurname(String surname){this.surname = surname;}
    public void setPhone(String phone){this.phone = phone;}
    public void setAge(int age){this.age = age;}

    @Override
    public String toString(){
        return name + " " + surname+ " | "+ phone + " | " + age;
    }

}
