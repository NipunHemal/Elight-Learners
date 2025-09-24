package lk.ijse.elight_driving_school.dao;


import lk.ijse.elight_driving_school.dao.custom.impl.*;
import lk.ijse.elight_driving_school.enums.DAOTypes;

public class DAOFactory {
    private static DAOFactory daoFactory;
    private DAOFactory() {}

    public static DAOFactory getInstance() {
        return daoFactory == null ? (daoFactory = new DAOFactory()) : daoFactory;
    }

    public <T extends SuperDAO> T getDAO(DAOTypes daoTypes) {
        return switch (daoTypes) {

            case COURSE ->  (T) new CourseDAOImpl();

            case INSTRUCTORS ->  (T) new InstructorDAOImpl();
//
            case LESSONS ->  (T) new LessonsDAOImpl();
//
            case PAYMENTS ->   (T) new PaymentsDAOImpl();
//
            case STUDENT_COURSE_DETAILS ->  (T) new StudentCourseDetailsDAOImpl();
//
            case STUDENTS -> (T) new StudentDAOImpl();
//
            case USER -> (T) new UserDAOImpl();
//
            case QUERY -> (T) new QueryDAOImpl();

        };
    }
}