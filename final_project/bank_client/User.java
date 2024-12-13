package test3;

public class User {
    private String name; // 암호화된 이름
    private String residentRegistrationNumber; // 암호화된 주민등록번호
    private String accountNumber; // 암호화된 계좌번호
    private String hashedPassword; // 해시된 비밀번호
    private String salt; // 비밀번호 솔트
    

    public User(String name, String rrn, String account, String hashedPassword, String salt) {
        if (name.isEmpty() || rrn.isEmpty() || account.isEmpty() || hashedPassword.isEmpty() || salt.isEmpty()) {
            throw new IllegalArgumentException("모든 필드를 올바르게 입력해야 합니다.");
        }
        this.name = name;
        this.residentRegistrationNumber = rrn;
        this.accountNumber = account;
        this.hashedPassword = hashedPassword;
        this.salt = salt;
    }

    public String getName() {
        return name;
    }

    public String getResidentRegistrationNumber() {
        return residentRegistrationNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public String getSalt() {
        return salt;
    }

    public String toCSV() {
        return name + "," + residentRegistrationNumber + "," + accountNumber + "," + hashedPassword + "," + salt;
    }

    public static User fromCSV(String csvLine) {
        String[] data = csvLine.split(",");
        if (data.length == 5) {
            return new User(data[0], data[1], data[2], data[3], data[4]);
        }
        return null; // 잘못된 데이터 처리
    }
}

   