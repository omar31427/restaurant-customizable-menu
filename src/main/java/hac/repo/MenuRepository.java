package hac.repo;

import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Scope("singleton")
@Repository
public interface MenuRepository extends JpaRepository<Menu,Long> {
    Optional<Menu> findMenuByMenuName(String name);
}
