package hac.Services;
import hac.repo.IngredientRepository;
import hac.repo.Ingredient;
import hac.repo.IngredientRepository;
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
public class IngredientServices{
    @Qualifier("IngredientRepository")
    private IngredientRepository repository;
    public void addIngredient(ArrayList<Ingredient> Ingredients) {
        repository.saveAll(Ingredients);
    }
    public void saveIngredient(Ingredient Ingredient) {
        repository.save(Ingredient);
    }
    public void deleteIngredient(long id) {
        repository.deleteById(id);
    }
    public void updateIngredient(Ingredient Ingredient) {
        repository.save(Ingredient);
    }
    public Optional<Ingredient> getIngredient(long id) {
        return repository.findById(id);
    }
    public List<Ingredient> getIngredientsByVegan(boolean vegan){
        return repository.findByIsVegan(vegan);
    }
    public List<Ingredient> getIngredientsByVegetarian(boolean vegetarian){
        return repository.findByIsVegetarian(vegetarian);
    }
    public List<Ingredient> getIngredientsByName(String name){
        return repository.findByIngredientName(name);
    }
}