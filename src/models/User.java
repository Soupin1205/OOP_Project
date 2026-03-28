package src.models;
public abstract class User {
    protected String name; 
    protected boolean member;  

    // Constructor
    public User(String name, boolean member) {
        this.name = name;
        this.member = member;
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Getter for membership status
    public boolean isMember() {
        return member;
    }

    // Abstract method - every user must implement how to show info
    public abstract void showInfo();
}