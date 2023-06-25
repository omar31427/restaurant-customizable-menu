package hac.repo;
import jakarta.persistence.*;


import jakarta.validation.constraints.*;
import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table("Menu")
public class Menu implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private long menu_id;
    @Getter
    @Setter
    @NotEmpty(message = "Name is mandatory")
    private String menuName = "";
    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
    @Getter
    @Setter
    private List<MenuItem> menuItems = new ArrayList<>();

    @Getter
    @Setter
    private String iconPath;
    public Menu() {
        this.menuItems = new ArrayList<>();
    }
    public Menu(String name)
    {
        this.menuName = name;
    }


    public void addItem(MenuItem newItem) {
     //   newItem.setMenu(this);
        menuItems.add(newItem);
    }

    public void removeItem(MenuItem item) {
       // item.setMenu(null);
        menuItems.remove(item);
    }

    public long getMenu_id(){
        return this.menu_id;
    }
    public void setMenu_id(long id){
        this.menu_id = id;
    }

}
