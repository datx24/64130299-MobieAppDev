package datnx.doan.timdothatlac;


public class Item {
    private final int id;
    private final String name;
    private final String description;
    private final String imageUrl;
    private final String address;
    private final double latitude;
    private final double longitude;


    // Constructor
    public Item(int id, String name, String description, String imageUrl, String address, double latitude, double longitude, String timestamp) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
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
}


