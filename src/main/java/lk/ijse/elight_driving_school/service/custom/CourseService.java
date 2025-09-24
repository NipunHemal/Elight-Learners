package lk.ijse.elight_driving_school.service.custom;

import lk.ijse.elight_driving_school.service.SuperService;
import lk.ijse.elight_driving_school.dto.CourseDTO;

import java.util.List;
import java.util.Optional;

public interface CourseService extends SuperService {
    List<CourseDTO> getAllCourses() throws Exception;

    String getLastCourseId() throws Exception;

    boolean saveCourses(CourseDTO t) throws Exception;

    boolean updateCourses(CourseDTO t) throws Exception;

    boolean deleteCourses(String id) throws Exception;

    List<String> getAllCourseIds() throws Exception;

    Optional<CourseDTO> findByCourseId(String id) throws Exception;

    String generateNewCourseId();

}
