package th.nguyenxuandat.BotNav_Recy.models.entity;

public class Transaction {
    private int image;
    private String name;

    public Transaction(int image, String name) {
        this.image = image;
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public String getName() {
        return name;
    }
}

