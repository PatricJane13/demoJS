package spring.dao;


import spring.model.Role;
import spring.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {
    void add(User user) throws SQLException;

    List<User> listUsers();

    User getUserById(Long id);

    void updateUser(User user);

    void deleteUser(User user);

    User findByUsername(String name);

    Role getUserRole(String role);

    boolean isExistsRoleByUser(Long id, String role);
}
