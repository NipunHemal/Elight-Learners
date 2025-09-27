package lk.ijse.elight_driving_school.dao.custom.impl;

import lk.ijse.elight_driving_school.config.FactoryConfig;
import lk.ijse.elight_driving_school.dao.custom.UserDAO;
import lk.ijse.elight_driving_school.entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserDAOImpl implements UserDAO {

    private final FactoryConfig factoryConfig = FactoryConfig.getInstance();

    @Override
    public List<User> getAll() throws SQLException {
        Session session = factoryConfig.getSession();
        try {
            Query<User> query = session.createQuery("from User", User.class);
            List<User> list = query.getResultList();
            return list;
        }finally {
            session.close();
        }
    }

    @Override
    public boolean save(User user) throws SQLException {
        System.out.println(user);
        Session session = factoryConfig.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.persist(user);
            transaction.commit();
            return true;
        }catch (Exception e){
            transaction.rollback();
            e.printStackTrace();
            return false;
        }finally {
            session.close();
        }
    }

    @Override
    public boolean update(User user) throws SQLException {
        Session session = factoryConfig.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.merge(user);
            transaction.commit();
            return true;
        }catch (Exception e){
            transaction.rollback();
            e.printStackTrace();
            return false;
        }finally {
            session.close();
        }
    }

    @Override
    public boolean delete(String id) throws SQLException {
        Session session = factoryConfig.getSession();
        Transaction transaction = session.beginTransaction();
        try{
            User user = session.get(User.class,id);
            if (user != null) {
                session.remove(user);
                transaction.commit();
                return true;
            }
            return false;
        }catch (Exception e){
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        }finally {
            session.close();
        }
    }

    @Override
    public List<String> getAllIds() throws Exception {
        Session session = factoryConfig.getSession();
        try {
            Query<String> query = session.createQuery("SELECT u.userId FROM User u", String.class);
            return query.list();
        } finally {
            session.close();
        }
    }

    @Override
    public Optional<User> findById(String id) throws SQLException {
        Session session = factoryConfig.getSession();

        try {
            User user = session.get(User.class,id);
            return Optional.ofNullable(user);
        }finally {
            session.close();
        }
    }

    public User getUserByEmail(String email){
        Session session = factoryConfig.getSession();
        try {
            Query<User> query = session.createQuery("FROM User u WHERE u.email = :email", User.class);
            query.setParameter("email", email);
            if (query.getResultList() == null) return null;
            return query.uniqueResult();
        } finally {
            session.close();
        }
    }
}