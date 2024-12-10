package th.nguyenxuandat.BotNav_Recy.models.entity;

public class Notification {
    private String title;
    private int imageResId;

    public Notification(String title, int imageResId) {
        this.title = title;
        this.imageResId = imageResId;
    }

    public String getTitle() {
        return title;
    }

    public int getImageResId() {
        return imageResId;
    }
}

