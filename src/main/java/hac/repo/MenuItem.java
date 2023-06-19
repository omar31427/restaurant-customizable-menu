package hac.repo;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.relational.core.mapping.Table;
import java.io.Serializable;
import java.util.List;
@Getter
@Setter
@Entity
@Table("MenuItem")
public class MenuItem implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long menuItemId;
    @Max(100)
    @PositiveOrZero(message = "Price has to be more than or equal to 0.00")
    private double menuItemPrice;
    @NotEmpty
    private String menuItemName;
    @ManyToMany
    private List<Ingredient> ingredients ;
    @ManyToOne
    @JoinColumn(name = "menuId")
    private Menu menu;
    private String menuItemImagePath;

    public MenuItem(){

    }

    public MenuItem( String name, List<Ingredient> ingredients,String imagePath,double price){

        this.menuItemName = name;
        this.ingredients = ingredients;
        this.menuItemImagePath = imagePath;
        this.menuItemPrice = price;
    }
    public MenuItem( String name, String imagePath,double price){

        this.menuItemName = name;
        this.menuItemImagePath = imagePath;
        this.menuItemPrice = price;
    }

}
