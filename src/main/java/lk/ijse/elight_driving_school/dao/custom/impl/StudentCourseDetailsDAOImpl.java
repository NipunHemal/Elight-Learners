package lk.ijse.elight_driving_school.dao.custom.impl;

import lk.ijse.elight_driving_school.config.FactoryConfig;
import lk.ijse.elight_driving_school.dao.custom.StudentCourseDetailDAO;
import lk.ijse.elight_driving_school.entity.StudentCourseDetails;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class StudentCourseDetailsDAOImpl implements StudentCourseDetailDAO {

    private final FactoryConfig factoryConfig = FactoryConfig.getInstance();

    @Override
    public boolean save(StudentCourseDetails studentCourseDetails) throws Exception {
        Session session = factoryConfig.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.persist(studentCourseDetails);
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
    public boolean update(StudentCourseDetails studentCourseDetails) throws Exception {
        Session session = factoryConfig.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.merge(studentCourseDetails);
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
        Session session  = factoryConfig.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            StudentCourseDetails studentCourseDetails = (StudentCourseDetails) session.get(StudentCourseDetails.class, id);
            if (studentCourseDetails != null) {
                session.remove(studentCourseDetails);
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
    public List<StudentCourseDetails> getAll() throws Exception {
        Session session = factoryConfig.getSession();
        try {
            Query<StudentCourseDetails> query = session.createQuery("from StudentCourseDetails where course.id=:courseId");
            List<StudentCourseDetails> studentCourseDetailsList = query.list();
            return studentCourseDetailsList;
        }finally {
            session.close();
        }
    }

    @Override
    public List<String> getAllIds() throws Exception {
        Session session = factoryConfig.getSession();
        try {
            Query<String> query = session.createQuery("SELECT scd.studentCourseId FROM StudentCourseDetails scd", String.class);
            return   query.list();
        }finally {
            session.close();
        }
    }

    @Override
    public Optional<StudentCourseDetails> findById(String id) throws Exception {
        Session session = factoryConfig.getSession();
        try {
            StudentCourseDetails studentCourseDetails = session.get(StudentCourseDetails.class, id);
            return Optional.ofNullable(studentCourseDetails);
        } finally {
            session.close();
        }
    }
}
