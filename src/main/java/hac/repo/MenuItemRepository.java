package hac.repo;

import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Scope("singleton")
@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem,Long> {
    List<MenuItem> findByMenuItemPriceLessThan (double price);
    List<MenuItem> findMenuItemByMenuItemNameContainingIgnoreCase(String name);
    List<MenuItem> findMenuItemByIngredientsContainingIgnoreCase(Ingredient ingredient);
    List<MenuItem> findMenuItemByMenuItemNameContainingIgnoreCaseAndVeganAndVegetarian(String name, boolean vegan,boolean vegetarian);

    //List<MenuItem> findMenuItemByMenu(Menu menu);
     //List<MenuItem> findByIngredientsContaining (Ingredient ingredients);
}
