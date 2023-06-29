package hac.repo;



import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.data.relational.core.mapping.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table("Ingredient")
public class Ingredient implements Serializable{
    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long ingredient_id;

    @ManyToMany(mappedBy = "ingredients")
    @Getter
    @Setter
    private List<MenuItem> menuItems;

    @Getter
    @Setter
    private String ingredientName;
    @Getter
    @Setter
    @NotNull
    private boolean vegan = false;
    @Getter
    @Setter
    @NotNull
    private boolean vegetarian = false;

    public Ingredient(){

    }

    public Ingredient(String name, boolean vegan, boolean vegetarian){
        this.ingredientName = name;
        this.vegan = vegan;
        this.vegetarian = vegetarian;
    }

    public void addMenuItem(MenuItem menuItem){
        if (menuItems == null) {
            menuItems = new ArrayList<>();
        }
        this.menuItems.add(menuItem);
    }
}
