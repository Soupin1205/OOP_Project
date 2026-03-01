// Abstract class User
// Represents a generic user in the system (could be Customer, Admin, etc.)
public abstract class User {
    protected String name;       // user's name
    protected boolean member;    // membership status

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