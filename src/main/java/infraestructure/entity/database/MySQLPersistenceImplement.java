package infraestructure.entity.database;

import domain.models.User;
import infraestructure.entity.IPersistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySQLPersistenceImplement implements IPersistence {

    private Connection connection;

    public MySQLPersistenceImplement() {
        this.connection = DatabaseConnection.connection();
    }

    @Override
    public void saveUser(User user) {
        String sql = "INSERT INTO users (username, password, email) VALUES (?,?,?)";

        try {
            PreparedStatement statement = this.connection.prepareStatement(sql);

            //pasamos los datos a la query
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());

            //modificamos registro
            statement.executeUpdate();

            //cerramos conexión
            statement.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User findById(String id) {

        String sql = "SELECT * FROM users WHERE id = ?";

        try {
            PreparedStatement statement = this.connection.prepareStatement(sql);

            statement.setString(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                User user = new User();

                user.setId(resultSet.getInt("id"));
                user.setUsername((resultSet.getString("username")));
                user.setPassword(resultSet.getString("password"));
                user.setEmail(resultSet.getString("email"));

                return user;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public void deleteUser(String id) {

        String sql = "DELETE FROM users WHERE id = ?";

        try {
            PreparedStatement statement = this.connection.prepareStatement(sql);

            statement.setString(1, id);
            statement.execute();

            statement.close();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void updateUser(User user) {

        String sql = "UPDATE users SET username = ?, password = ?, email = ? WHERE id = ?";

        try {
            PreparedStatement statement = this.connection.prepareStatement(sql);

            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.setInt(4, user.getId());

            int result = statement.executeUpdate();

            if (result > 0) {
                System.out.println("The user was modify");
            } else {
                System.out.println("Error modify user");
            }

            statement.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers(){
        String sql = "SELECT * FROM users";
        List <User> allUsers = new ArrayList<>();

        try {
            PreparedStatement statement = this.connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                User user = new User();

                user.setId(resultSet.getInt("id"));
                user.setUsername((resultSet.getString("username")));
                user.setPassword(resultSet.getString("password"));
                user.setEmail(resultSet.getString("email"));

                allUsers.add(user);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return allUsers;
    }

}
