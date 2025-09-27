package lk.ijse.elight_driving_school.service.custom;

import lk.ijse.elight_driving_school.dto.StudentDTO;
import lk.ijse.elight_driving_school.service.SuperService;

import java.util.List;
import java.util.Optional;

public interface StudentService extends SuperService {
    List<StudentDTO> getAllStudents() throws Exception;

    boolean saveStudents(StudentDTO t) throws Exception;

    boolean updateStudents(StudentDTO t) throws Exception;

    boolean deleteStudents(String id) throws Exception;

    List<String> getAllStudentIds() throws Exception;

    Optional<StudentDTO> findByStudentId(String id) throws Exception;
}
