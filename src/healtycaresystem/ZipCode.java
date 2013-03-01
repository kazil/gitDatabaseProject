package healtycaresystem;

public class ZipCode {

    private String zipCode;
    private String city;

    public ZipCode(String zipCode, String city) {
        this.zipCode = zipCode;
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getCity() {
        return city;
    }

    @Override
    public String toString() {
        return "Zip code: " + zipCode + "\nCity: " + city;
    }
}
