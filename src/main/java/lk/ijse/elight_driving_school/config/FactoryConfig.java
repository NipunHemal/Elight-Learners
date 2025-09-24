package lk.ijse.elight_driving_school.config;

import lk.ijse.elight_driving_school.entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Properties;

public class FactoryConfig {
    private static FactoryConfig factoryConfiguration;
    private SessionFactory sessionFactory;


    private FactoryConfig()  {
        try {
            Properties prop = new Properties();
            prop.load(
                    FactoryConfig.class.getClassLoader().getResourceAsStream("hibernate.properties")
            );

            Configuration configuration = new Configuration();
            configuration.addProperties(prop);

            configuration.addAnnotatedClass(Student.class);
            configuration.addAnnotatedClass(StudentCourseDetails.class);
            configuration.addAnnotatedClass(Course.class);
            configuration.addAnnotatedClass(Instructor.class);
            configuration.addAnnotatedClass(Lesson.class);
            configuration.addAnnotatedClass(Payment.class);
            configuration.addAnnotatedClass(User.class);

            sessionFactory = configuration.buildSessionFactory();

        }catch (Exception e){
            throw new RuntimeException("Error initializing Hibernate SessionFactory", e);
        }
    }

    public static synchronized FactoryConfig getInstance()  {
        return factoryConfiguration == null ?
                factoryConfiguration = new FactoryConfig()
                :
                factoryConfiguration;
    }

    public Session getSession(){
        Session session = sessionFactory.openSession();
        return session;
    }

    public Session getCurrentSession(){
        return sessionFactory.getCurrentSession();
    }
}