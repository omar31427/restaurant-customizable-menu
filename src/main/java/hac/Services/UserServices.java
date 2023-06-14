package hac.Services;
import hac.repo.User;
import hac.repo.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Transactional
@Service
public class UserServices {
    @Qualifier("UserRepository")
    private UserRepository repository;
    public void addUsers(ArrayList<User> users) {
        repository.saveAll(users);
    }
    public void saveUser(User user) {
        repository.save(user);
    }
    public void deleteUser(long id) {
        repository.deleteById(id);
    }
    public void updateUser(User user) {
        repository.save(user);
    }
    public Optional<User> getUser(long id) {
        return repository.findById(id);
    }

    public List<User> getUsers(){
        return repository.findAll();
    }

}
