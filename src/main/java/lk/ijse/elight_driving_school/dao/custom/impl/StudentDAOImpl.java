package lk.ijse.elight_driving_school.dao.custom.impl;

import lk.ijse.elight_driving_school.config.FactoryConfig;
import lk.ijse.elight_driving_school.dao.custom.StudentDAO;
import lk.ijse.elight_driving_school.entity.Student;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class StudentDAOImpl implements StudentDAO {

    private final FactoryConfig factoryConfig = FactoryConfig.getInstance();

    @Override
    public boolean save(Student students) throws Exception {
        Session session = factoryConfig.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.persist(students);
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
    public boolean update(Student students) throws Exception {
        Session session = factoryConfig.getSession();
        Transaction transaction = session.beginTransaction();

        try{
            session.merge(students);
            transaction.commit();
            return true;
        }catch (Exception e){
            if (transaction != null) transaction.rollback();
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
            Student student = session.get(Student.class,id);
            if (student != null){
                session.remove(student);
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
    public List<Student> getAll() throws Exception {
        Session session = factoryConfig.getSession();
        try {
            Query<Student> query = session.createQuery("from Student",Student.class);
            List<Student> studentsList = query.list();
            System.out.println(studentsList.getFirst().getCourses());
            return studentsList;
        }finally {
            session.close();
        }
    }

    @Override
    public List<String> getAllIds() throws Exception {
        Session session = factoryConfig.getSession();
        try {
            Query<String> query = session.createQuery("SELECT s.studentId FROM Student s", String.class);
            return query.list();
        } finally {
            session.close();
        }
    }

    @Override
    public String getLastId() throws SQLException {
       Session session = factoryConfig.getSession();
       try {
           Query<String> query = session.createQuery("SELECT stu.id FROM Student stu ORDER BY stu.id DESC",
                   String.class).setMaxResults(1);
           List<String> studentList = query.list();
           if (studentList.isEmpty()) {
               return null;

           }
           return studentList.get(0);
       }finally {
           session.close();
       }
    }



    @Override
    public Optional<Student> findById(String id) throws SQLException {
        Session session = factoryConfig.getSession();
        try {
            Student student = session.get(Student.class, id);
            return Optional.ofNullable(student);
        } finally {
            session.close();
        }
    }

    @Override
    public String generateNewId() {
        String lastId = null;
        try {
            lastId = getLastId();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (lastId == null) {
            return "S-001";
        } else {
            int num = Integer.parseInt(lastId.split("-")[1]);
            num++;
            return String.format("S-%03d", num);
        }
    }
}
