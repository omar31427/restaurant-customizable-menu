package hac.repo;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Scope("singleton")
@Repository
public interface IngredientRepository extends JpaRepository<Ingredient,Long>{
    List<Ingredient> findByIngredientName(String name);

    List<Ingredient> findIngredientByVegan(boolean vegan);
    List<Ingredient> findIngredientByVegetarian(boolean vegetarian);
}


