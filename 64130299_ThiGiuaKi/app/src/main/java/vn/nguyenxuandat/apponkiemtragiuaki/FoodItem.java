package vn.nguyenxuandat.apponkiemtragiuaki;
import java.io.Serializable;
import java.util.List;

public class FoodItem implements Serializable {
    private String name;
    private String image;
    private String region;
    private String description;
    private List<String> ingredients;

    // Getter và Setter cho từng thuộc tính
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<String> getIngredients() { return ingredients; }
    public void setIngredients(List<String> ingredients) { this.ingredients = ingredients; }
}
