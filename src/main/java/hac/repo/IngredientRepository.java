package hac.repo;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
@Scope("singleton")
public interface IngredientRepository extends JpaRepository<Ingredient,Long>{
    List<Ingredient> findByIngredientName(String name);

    List<Ingredient> findByIsVegan(boolean vegan);
    List<Ingredient> findByIsVegetarian(boolean vegetarian);
}


