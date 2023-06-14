package hac.repo;
import jakarta.persistence.*;


import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.springframework.data.relational.core.mapping.Table;
import java.util.List;
@Entity
@Table("Menu")
public class Menu implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long menuId;

    @NotEmpty
    private String menuName;
    @OneToMany
    private List<MenuItem> menuItems ;

    private String iconPath;

    public long getId(){
        return menuId;
    }

    public void setId(long id)
    {
        this.menuId = id;
    }
    public String getName() {
        return menuName;
    }

    public void setName(String name){
        this.menuName = name;
    }

    public List<MenuItem> getMenuItems(){
        return menuItems;
    }

    public void setMenuItems(List<MenuItem> sandwiches){
        this.menuItems = sandwiches;
    }

    public String getIconPath(){
        return iconPath;
    }

    public void setIconPath(String iconPath){
        this.iconPath = iconPath;
    }
}
