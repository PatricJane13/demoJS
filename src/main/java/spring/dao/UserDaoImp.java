package spring.dao;

import spring.model.Role;
import spring.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void add(User user) {
        entityManager.persist(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        Query query = entityManager.createQuery("From User");
        return query.getResultList();
    }

    @Override
    public User getUserById(Long id) {
        Query query = entityManager.createQuery("FROM User WHERE id = :id");
        query.setParameter("id", id);
        return (User) query.getSingleResult();
    }

    @Override
    public void updateUser(User user) {
        entityManager.merge(user);
    }

    @Override
    public void deleteUser(User user) {
        entityManager.remove(user);
    }

    @Override
    public User findByUsername(String email) {
        Query query = entityManager.createQuery("FROM User WHERE email=:email");
        query.setParameter("email", email);
        return (User) query.getSingleResult();
    }

    @Override
    public Role getUserRole(String role) {
        Query query = entityManager.createQuery("FROM Role WHERE role =:role");
        query.setParameter("role", role);
        return (Role)query.getSingleResult();
    }

    @Override
    public boolean isExistsRoleByUser(Long id, String role) {
        User user = getUserById(id);

        Query query = entityManager.createQuery("FROM Role WHERE role =:role");
        query.setParameter("role", role);
        return false;
    }
}
