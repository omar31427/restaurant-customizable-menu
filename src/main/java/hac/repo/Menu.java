package hac.repo;
import jakarta.persistence.*;


import jakarta.validation.constraints.*;
import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;
import java.util.List;


@Entity
@Table("Menu")
public class Menu implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private long menuId;
    @Getter
    @Setter
    @NotEmpty(message = "Name is mandatory")
    private String menuName;
    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL)
    @Getter
    @Setter
    private List<MenuItem> menuItems;

    @Getter
    @Setter
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
