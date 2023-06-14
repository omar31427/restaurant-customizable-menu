package hac.Services;
import hac.repo.Ingredient;
import hac.repo.MenuItemRepository;
import hac.repo.MenuItem;
import hac.repo.MenuItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Transactional
@Service
public class MenuItemServices{
    @Qualifier("MenuItemRepository")
    private MenuItemRepository repository;
    public void addMenuItems(ArrayList<MenuItem> MenuItems) {
        repository.saveAll(MenuItems);
    }
    public void saveMenuItem(MenuItem MenuItem) {
        repository.save(MenuItem);
    }
    public void deleteMenuItem(long id) {
        repository.deleteById(id);
    }
    public void updateMenuItem(MenuItem MenuItem) {
        repository.save(MenuItem);
    }
    public Optional<MenuItem> getMenuItem(long id) {
        return repository.findById(id);
    }
    public List<MenuItem> getMenuItemByPrice(double price){
        return repository.findByPriceLessThan(price);
    }
    public List<MenuItem> getMenuItemByIngredients(Ingredient ingredients){
        return repository.findByIngredientsContaining(ingredients);
    }

}