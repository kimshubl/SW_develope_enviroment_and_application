public class User {
    private String name;
    private String residentRegistrationNumber;
    private String accountNumber;
    private String hashedPassword;

    public User(String name, String residentRegistrationNumber, String accountNumber, String hashedPassword) {
        this.name = name;
        this.residentRegistrationNumber = residentRegistrationNumber;
        this.accountNumber = accountNumber;
        this.hashedPassword = hashedPassword;
    }

    // Getter 및 Setter
    public String getName() { return name; }
    public String getResidentRegistrationNumber() { return residentRegistrationNumber; }
    public String getAccountNumber() { return accountNumber; }
    public String getHashedPassword() { return hashedPassword; }
}
