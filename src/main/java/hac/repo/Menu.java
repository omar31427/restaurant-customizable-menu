package hac.repo;
import jakarta.persistence.*;


import jakarta.validation.constraints.*;
import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;
import java.util.List;

@Getter
@Setter
@Entity
@Table("Menu")
public class Menu implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long menuId;

    @NotEmpty(message = "Name is mandatory")
    private String menuName;
    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL)
    private List<MenuItem> menuItems;

    private String iconPath;
    public Menu() {

    }
    public Menu(String name)
    {
        this.menuName = name;
    }


    public void addItem(MenuItem newItem){
        this.menuItems.add(newItem);
    }

}
