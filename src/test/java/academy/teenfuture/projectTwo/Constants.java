package academy.teenfuture.projectTwo;

public enum Constants {
    Email("victorsduv+1@gmail.com"),
    PW("1234Qwer"),
    Phone("22345678"),
    Invalid_email("0000"),
    Invalid_phone("0000");

    private final String value;

    private Constants(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
