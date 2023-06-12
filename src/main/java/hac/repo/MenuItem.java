package hac.repo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.io.Serializable;
import java.util.List;

@Entity
public class MenuItem implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long menuItem_id;


    @NotEmpty
    private String name;
    @ManyToMany
    @JoinTable(
            name = "Ingredient",
            joinColumns = @JoinColumn(name = "sandwich_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    private List<Ingredient> ingredients ;

    private String imagePath;

    public MenuItem(){

    }

    public MenuItem(String name, List<Ingredient> ingredients,String imagePath){
        this.name = name;
        this.ingredients = ingredients;
        this.imagePath = imagePath;
    }
    public long getId(){
        return this.menuItem_id;
    }

    public void setId(long id ){
        this.menuItem_id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String newName){
        this.name = newName;
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
