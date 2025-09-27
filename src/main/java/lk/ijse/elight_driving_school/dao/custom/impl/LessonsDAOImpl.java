package lk.ijse.elight_driving_school.dao.custom.impl;

import lk.ijse.elight_driving_school.config.FactoryConfig;
import lk.ijse.elight_driving_school.dao.custom.LessonsDAO;
import lk.ijse.elight_driving_school.entity.Lesson;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class LessonsDAOImpl implements LessonsDAO {
    private final FactoryConfig factoryConfig = FactoryConfig.getInstance();


    @Override
    public boolean save(Lesson lessons) throws Exception {
        Session session = factoryConfig.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.persist(lessons);
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
    public boolean update(Lesson lessons) throws Exception {
        Session session = factoryConfig.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.merge(lessons);
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
            Lesson lessons = (Lesson) session.get(Lesson.class, id);
            if (lessons != null) {
                session.remove(lessons);
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
    public List<Lesson> getAll() throws Exception {
        Session session = factoryConfig.getSession();
        try {
            Query<Lesson> query = session.createQuery("from Lesson ",Lesson.class);
            List<Lesson> lessonsList = query.list();
            return lessonsList;
        }finally {
            session.close();
        }

    }

    @Override
    public List<String> getAllIds() throws Exception {
        Session session = factoryConfig.getSession();
        try {
            Query<String> query = session.createQuery("SELECT l.lessonId FROM Lesson l", String.class);
            return query.list();
        } finally {
            session.close();
        }
    }

    @Override
    public Optional<Lesson> findById(String id) throws Exception {
        Session session = factoryConfig.getSession();
        try {
            Lesson lessons = session.get(Lesson.class, id);
            return Optional.ofNullable(lessons);
        } finally {
            session.close();
        }
    }

}