package hac.Services;

import hac.repo.Purchase;
import hac.repo.PurchaseRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Transactional
@Service
public class PurchaseServices {
    @Autowired
    PurchaseRepository repository;
    public List<Purchase> getAllPurchases (){
        return repository.findAll();
    }

    public void save(Purchase purchase) {
        repository.save(purchase);
    }
}
