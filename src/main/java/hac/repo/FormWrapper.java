package hac.repo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
@Getter
@Setter
@Component
public class FormWrapper {

    @Getter
    @Setter
    private Menu menu;
    @Getter
    @Setter
    private MenuItem menuItem;

    public FormWrapper(){
        ;
    }
    public FormWrapper(Menu newMenu,MenuItem newItem){
        this.menu = newMenu;
        this.menuItem = newItem;
    }
    public String getMenuName(){
        return menu.getMenuName();
    }
    public String getMenuItemName(){
        return menuItem.getMenuItemName();
    }
}
