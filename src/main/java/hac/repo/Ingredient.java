package hac.repo;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import org.springframework.stereotype.Component;

import java.io.Serializable;
@Entity
public class Ingredient implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long ingredient_id;


    @NotEmpty
    private String name;
    @NotNull
    private boolean isVegan;
    @NotNull
    private boolean isVegetarian;

    public Ingredient(){

    }

    public Ingredient(String name, boolean vegan, boolean vegetarian){
        this.name = name;
        this.isVegan = vegan;
        this.isVegetarian = vegetarian;
    }
    public long getId(){
        return ingredient_id;
    }

    public void setId(long id){
        this.ingredient_id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String newName){
        this.name = newName;
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
