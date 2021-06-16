package emgc.randomlunch.enums;

public enum CountryCode {
    USA("1"), CANADA("1"), RUSSIA("7"), FRANCE("33"), SPAIN("34"), SOUTH_KOREA("82");

    private final String code;

    CountryCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
