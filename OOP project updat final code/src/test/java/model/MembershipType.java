package model;

public enum MembershipType {
    NONE(0.0, "Regular", 0),
    SILVER(0.10, "Silver", 100),
    GOLD(0.20, "Gold", 300),
    PLATINUM(0.30, "Platinum", 500);
    
    private final double rate;
    private final String displayName;
    private final double requiredBalance;
    
    MembershipType(double rate, String displayName, double requiredBalance) {
        this.rate = rate;
        this.displayName = displayName;
        this.requiredBalance = requiredBalance;
    }
    
    public double getRate() { return rate; }
    public String getDisplayName() { return displayName; }
    public double getRequiredBalance() { return requiredBalance; }
    
    public static MembershipType getByBalance(double balance) {
        if (balance >= PLATINUM.requiredBalance) return PLATINUM;
        if (balance >= GOLD.requiredBalance) return GOLD;
        if (balance >= SILVER.requiredBalance) return SILVER;
        return NONE;
    }
    
    @Override
    public String toString() {
        return displayName + " (" + (int)(rate * 100) + "% off)";
    }
}