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
import java.io.Serializable;
import java.util.List;


@Entity
@Table("MenuItem")
public class MenuItem implements Serializable{
    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(insertable = false, updatable = false)
    private long item_id;
    @Getter
    @Setter
    @Max(100)
    @PositiveOrZero(message = "Price has to be more than or equal to 0.00")
    private double menuItemPrice;
    @Getter
    @Setter
    @NotEmpty
    private String menuItemName;
   /* @ManyToMany
    @Getter
    @Setter
    private List<Ingredient> ingredients ;
    */

   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "menu_id")
   @Getter
   @Setter
   private Menu menu;
    @Getter
    @Setter
    private String menuItemImagePath = "";

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

}
