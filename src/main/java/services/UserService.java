package services;

import domain.models.User;
import infraestructure.entity.IPersistence;
import infraestructure.entity.database.MySQLPersistenceImplement;

import java.util.List;

public class UserService implements IPersistence {

    private IPersistence persistance = new MySQLPersistenceImplement();

    @Override
    public void saveUser(User user) {
        persistance.saveUser(user);
    }

    @Override
    public User findById(String id) {
        return persistance.findById(id);
    }

    @Override
    public void deleteUser(String id) {
        persistance.deleteUser(id);
    }

    @Override
    public void updateUser(User user) {
        persistance.updateUser(user);
    }


    public List<User> getAllUsers(){
        return persistance.getAllUsers();
    }

}
