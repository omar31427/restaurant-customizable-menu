package hac.repo;



import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.stereotype.Component;
import org.springframework.data.relational.core.mapping.Table;
import java.io.Serializable;
@Entity
@Table("Ingredient")
public class Ingredient implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long ingredientId;


    @NotEmpty
    private String ingredientName;
    @NotNull
    private boolean isVegan;
    @NotNull
    private boolean isVegetarian;

    public Ingredient(){

    }

    public Ingredient(String name, boolean vegan, boolean vegetarian){
        this.ingredientName = name;
        this.isVegan = vegan;
        this.isVegetarian = vegetarian;
    }
    public long getId(){
        return ingredientId;
    }

    public void setId(long id){
        this.ingredientId = id;
    }
    public String getName() {
        return ingredientName;
    }
    public void setName(String newName){
        this.ingredientName = newName;
    }

    public boolean getIsVegan(){
        return isVegan;
    }

    public void setIsVegan(boolean vegan){
        this.isVegan = vegan;
    }

    public boolean getIsVegetarian(){
        return this.isVegetarian;
    }

    public void setIsVegetarian(boolean vegetarian){
        this.isVegetarian = vegetarian;
    }
}
