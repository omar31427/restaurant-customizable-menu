package hac.Services;
import hac.repo.MenuItem;
import hac.repo.MenuRepository;
import hac.repo.Menu;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Transactional
@Service
public class MenuServices{
    @Qualifier("MenuRepository")
    private MenuRepository repository;
    public void addMenu(ArrayList<Menu> Menus) {
        repository.saveAll(Menus);
    }
    public List<MenuItem> getMenuItemsByMenu(Menu menu){
        return menu.getMenuItems();
    }
    public void saveMenu(Menu Menu) {
        repository.save(Menu);
    }
    public void deleteMenu(long id) {
        repository.deleteById(id);
    }
    public void updateMenu(Menu Menu) {
        repository.save(Menu);
    }
    public Menu createNewMenu() {return new Menu();}
    public Optional<Menu> getMenu(long id) {
        return repository.findById(id);
    }
}