package hac.repo;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
@Table("MenuItem")
public class MenuItem implements Serializable{
    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(insertable = false, updatable = false)
    private long item_id;
    @Serial
    private static final long serialVersionUID = 2637833444727181844L;
    @Getter
    @Setter
    @Max(200)
    @PositiveOrZero(message = "Price has to be more than or equal to 0.00")
    private double menuItemPrice;
    @Getter
    @Setter
    @NotEmpty
    private String menuItemName;
    @ManyToMany(fetch = FetchType.EAGER)
    @Getter
    @Setter
    private List<Ingredient> ingredients = new ArrayList<>();


   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "menu_id")
   @Getter
   @Setter
   private Menu menu;
    @Getter
    @Setter
    private String menuItemImagePath = "";
    @Getter
    @Setter
    private boolean vegan;
    @Getter
    @Setter
    private boolean vegetarian;
    public MenuItem(){

    }

    /*public MenuItem( String name, List<Ingredient> ingredients,String imagePath,double price){

        this.menuItemName = name;
        this.ingredients = ingredients;
        this.menuItemImagePath = imagePath;
        this.menuItemPrice = price;
    }*/
    public MenuItem( String name, String imagePath,double price){

        this.menuItemName = name;
        this.menuItemImagePath = imagePath;
        this.menuItemPrice = price;
    }
    public boolean hasIngredient(Ingredient ingredient){
        for(Ingredient ing: this.ingredients)
            if(Objects.equals(ing.getIngredientName(), ingredient.getIngredientName()))
                return true;

        return false;
    }
    public void addIngredient (Ingredient ingredient){
        if (ingredients == null) {
            ingredients = new ArrayList<>();
        }
        this.ingredients.add(ingredient);
    }
    public void setFilters(){
        if(this.ingredients.isEmpty())
        {
            this.vegan = false;
            this.vegetarian = false;
            return;
        }
        this.vegan = isVegan();
        this.vegetarian = isVegetarian();
    }
    public boolean isVegan(){
        for(Ingredient ingredient : this.ingredients)
            if(!ingredient.isVegan())
                return false;

        return true;
    }
    public boolean isVegetarian(){
        for(Ingredient ingredient : this.ingredients)
            if(!ingredient.isVegetarian())
                return false;

        return true;
    }

}
