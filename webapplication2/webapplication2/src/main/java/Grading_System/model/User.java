package Grading_System.model;

public class User {
    private int userId;
    private String userName;
    private String password;
    private String role;

    public User() {
        this(-1, null, null, null); // Default values
    }
    public User(int userId, String username, String password, String role) {
        this.userId = userId;
        this.userName = username;
        this.password = password;
        this.role = role;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUsername(String username) {
        this.userName = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
