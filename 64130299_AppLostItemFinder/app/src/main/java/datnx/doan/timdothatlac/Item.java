package datnx.doan.timdothatlac;


public class Item {
    private int id;
    private String name;
    private String description;
    private String imageUrl;
    private String address;
    private double latitude;
    private double longitude;
    private String timestamp;

    // Constructor
    public Item(int id, String name, String description, String imageUrl, String address, double latitude, double longitude, String timestamp) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timestamp = timestamp;
    }

    // Getters and setters (optional)
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getAddress() {
        return address;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getTimestamp() {
        return timestamp;
    }
}


