package hac.repo;

import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
@Scope("singleton")
public interface MenuRepository extends JpaRepository<Menu,Long> {

}
