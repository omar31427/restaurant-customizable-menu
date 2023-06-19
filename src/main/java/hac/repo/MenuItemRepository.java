package hac.repo;

import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
@Scope("singleton")
public interface MenuItemRepository extends JpaRepository<MenuItem,Long> {
    List<MenuItem> findByMenuItemPriceLessThan (double price);
    List<MenuItem> findMenuItemByMenu(Menu menu);
     List<MenuItem> findByIngredientsContaining (Ingredient ingredients);
}
