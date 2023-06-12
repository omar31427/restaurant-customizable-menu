package hac.repo;
import jakarta.persistence.*;


import jakarta.validation.constraints.*;
import java.io.Serializable;

import java.util.List;
@Entity
public class Menu implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long Menu_id;

    @NotEmpty
    private String name;
    @OneToMany
    @JoinTable(
            name = "MenuItem",
            joinColumns = @JoinColumn(name = "Menu_id"),
            inverseJoinColumns = @JoinColumn(name = "MenuItem_id")
    )
    private List<Ingredient> sandwiches ;

    private String iconPath;

    public long getId(){
        return Menu_id;
    }

    public void setId(long id)
    {
        this.Menu_id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public List<Ingredient> getSandwiches(){
        return sandwiches;
    }

    public void setSandwiches(List<Ingredient> sandwiches){
        this.sandwiches = sandwiches;
    }

    public String getIconPath(){
        return iconPath;
    }

    public void setIconPath(String iconPath){
        this.iconPath = iconPath;
    }
}
