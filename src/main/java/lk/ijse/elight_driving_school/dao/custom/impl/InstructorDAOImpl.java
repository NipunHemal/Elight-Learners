package lk.ijse.elight_driving_school.dao.custom.impl;

import lk.ijse.elight_driving_school.config.FactoryConfig;
import lk.ijse.elight_driving_school.dao.custom.InstructorDAO;
import lk.ijse.elight_driving_school.entity.Instructor;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class InstructorDAOImpl implements InstructorDAO {

    private final FactoryConfig factoryConfig = FactoryConfig.getInstance();


    @Override
    public boolean save(Instructor instructor) throws Exception {
        Session session = factoryConfig.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.persist(instructor);
            transaction.commit();
            return true;
        }catch (Exception e){
            transaction.rollback();
            return false;
        }finally {
            session.close();
        }
    }

    @Override
    public boolean update(Instructor instructor) throws Exception {
        Session session = factoryConfig.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.merge(instructor);
            transaction.commit();
            return true;
        }catch (Exception e){
            transaction.rollback();
            return false;
        }finally {
            session.close();
        }
    }

    @Override
    public boolean delete(String id) throws Exception {
        Session session = factoryConfig.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Instructor instructor = (Instructor) session.get(Instructor.class, id);
            if (instructor != null) {
                session.remove(instructor);
                transaction.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public List<Instructor> getAll() throws Exception {
        Session session = factoryConfig.getSession();
        try {
            Query<Instructor> query = session.createQuery("from Instructor ", Instructor.class);
            List<Instructor> instructorList = query.list();
            return instructorList;
        }finally {
            session.close();
        }
    }

    @Override
    public List<String> getAllIds() throws Exception {
        Session session = factoryConfig.getSession();
        try {
            Query<String> query = session.createQuery("SELECT i.instructorId FROM Instructor i", String.class);
            return query.list();
        } finally {
            session.close();
        }
    }

    @Override
    public Optional<Instructor> findById(String id) throws Exception {
        Session session = factoryConfig.getSession();
        try {
            Instructor instructor = session.get(Instructor.class, id);
            return Optional.ofNullable(instructor);
        } finally {
            session.close();
        }
    }

}
