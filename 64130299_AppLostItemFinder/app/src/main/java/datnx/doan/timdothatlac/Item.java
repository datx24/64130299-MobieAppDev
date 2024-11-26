package datnx.doan.timdothatlac;

public class Item {
    private String id;
    private String name;
    private String description;
    private String imageUrl;
    private double latitude;
    private double longitude;
    private String address;

    public Item(String name, String imageUrl) {
    }

    public String getId() {
        return id;
    }

    public Item(String name, String description, String imageUrl, double latitude, double longitude, String address) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}

