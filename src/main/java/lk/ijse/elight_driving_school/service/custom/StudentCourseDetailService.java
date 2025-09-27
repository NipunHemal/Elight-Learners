package lk.ijse.elight_driving_school.service.custom;

import lk.ijse.elight_driving_school.dto.StudentCourseDetailsDTO;

import java.util.List;
import java.util.Optional;

public interface StudentCourseDetailService {
    List<StudentCourseDetailsDTO> getAllStudentCourseDetails() throws Exception;

    boolean saveStudentCourseDetails(StudentCourseDetailsDTO t) throws Exception;

    boolean updateStudentCourseDetails(StudentCourseDetailsDTO t) throws Exception;

    boolean deleteStudentCourseDetails(String id) throws Exception;

    List<String> getAllStudentCourseDetailIds() throws Exception;
}
