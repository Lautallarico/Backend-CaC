package infraestructure.entity;

import domain.models.User;

import java.util.List;

public interface IPersistence {

    void saveUser(User user);

    User findById(String id);

    void deleteUser(String id);

    void updateUser(User user);

    List<User> getAllUsers();
}
