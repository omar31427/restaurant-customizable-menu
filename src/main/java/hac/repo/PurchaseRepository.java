package hac.repo;

import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Scope("singleton")
@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
}
