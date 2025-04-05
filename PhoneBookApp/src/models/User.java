package models;

public class User {
    private String fullName;
    private String username;
    private String password;

    public User(String fullName, String userName, String password) {
        this.fullName = fullName;
        this.username = username;
        this.password = password;
    }
    public String getFullName(){return fullName;}
    public String getUsername(){return username;}
    public String getPassword(){return password;}


}
