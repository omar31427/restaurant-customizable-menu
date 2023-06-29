package hac.Services;
import hac.repo.*;
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
    @Autowired
    private MenuItemRepository repository;
    public void addMenuItems(ArrayList<MenuItem> menuItems) {
        repository.saveAll(menuItems);
    }

    public List<MenuItem> getAllItems(){
        return repository.findAll();
    }
    public void addMenuItem(MenuItem menuItem) {
        repository.save(menuItem);
    }
    public void saveMenuItem(MenuItem MenuItem) {
        repository.save(MenuItem);
    }
    public void deleteMenuItem(long id) {
        repository.deleteById(id);
    }
    public List<MenuItem> getItemsContaining (String name){
        return repository.findMenuItemByMenuItemNameContainingIgnoreCase(name);
    }
    public List<MenuItem> getItemsContainingIngredient(Ingredient ingredient){
        return repository.findMenuItemByIngredientsContainingIgnoreCase(ingredient);
    }
    public void updateMenuItem(MenuItem menuItem) {
        repository.save(menuItem);
    }
    public Optional<MenuItem> getMenuItem(long id) {
        return repository.findById(id);
    }
    //public List<MenuItem> getMenuItemByMenuItemPrice (double price){
        //return repository.findByMenuItemPriceLessThan(price);
   // }
    //public List<MenuItem> getMenuItemByIngredients(Ingredient ingredients){
      //  return repository.findByIngredientsContaining(ingredients);
    //}

}