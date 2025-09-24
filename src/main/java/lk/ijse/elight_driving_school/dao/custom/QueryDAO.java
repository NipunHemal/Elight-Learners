package lk.ijse.elight_driving_school.dao.custom;

import lk.ijse.elight_driving_school.dao.SuperDAO;

public interface QueryDAO extends SuperDAO {
    public int getStudentCountForLesson(String lessonId);
}
