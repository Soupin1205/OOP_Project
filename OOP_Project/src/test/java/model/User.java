package model;

public abstract class User {
    protected String userId;
    protected String name;
    protected String password;
    protected boolean active;

    public User(String userId, String name, String password) {
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.active = true;
    }

    public String getUserId() { return userId; }
    public String getName() { return name; }
    public boolean isActive() { return active; }
    
    public boolean checkPassword(String pwd) {
        return password != null && password.equals(pwd);
    }

    public void setActive(boolean active) { this.active = active; }
    public void setName(String name) { this.name = name; }
    public void setPassword(String password) { this.password = password; }
    
    public abstract void showInfo();
}