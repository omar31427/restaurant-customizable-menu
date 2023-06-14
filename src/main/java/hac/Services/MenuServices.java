package hac.Services;
import hac.repo.MenuRepository;
import hac.repo.Menu;
import hac.repo.MenuRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void saveMenu(Menu Menu) {
        repository.save(Menu);
    }
    public void deleteMenu(long id) {
        repository.deleteById(id);
    }
    public void updateMenu(Menu Menu) {
        repository.save(Menu);
    }
    public Optional<Menu> getMenu(long id) {
        return repository.findById(id);
    }
}