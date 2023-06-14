package hac.repo;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.relational.core.mapping.Table;
import java.io.Serializable;
import java.util.List;

@Entity
@Table("MenuItem")
public class MenuItem implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long menuItemId;
    @Max(100)
    @PositiveOrZero
    private double price;
    @NotEmpty
    private String menuItemName;
    @ManyToMany
    private List<Ingredient> ingredients ;

    private String imagePath;

    public MenuItem(){

    }

    public MenuItem(String name, List<Ingredient> ingredients,String imagePath){
        this.menuItemName = name;
        this.ingredients = ingredients;
        this.imagePath = imagePath;
    }
    public long getId(){
        return this.menuItemId;
    }

    public void setId(long id ){
        this.menuItemId = id;
    }
    public String getName() {
        return menuItemName;
    }

    public void setName(String newName){
        this.menuItemName = newName;
    }

    public List<Ingredient> getIngredients(){
        return ingredients;
    }

    public void setIngredients(List<Ingredient> newIngredients){
        ingredients = newIngredients;
    }

    public String getImagePath(){
        return imagePath;
    }

    public void setImagePath(String imagePath){

    }
}
