package lk.ijse.elight_driving_school.service.custom;

import lk.ijse.elight_driving_school.service.SuperService;
import lk.ijse.elight_driving_school.service.exception.DuplicateException;
import lk.ijse.elight_driving_school.service.exception.InUseException;
import lk.ijse.elight_driving_school.dto.InstructorDTO;
import lk.ijse.elight_driving_school.dto.StudentDTO;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface InstructorBO extends SuperService {
    List<InstructorDTO> getAllInstructors() throws Exception;

    String getLastInstructorId() throws Exception;

    boolean saveInstructors(InstructorDTO t) throws Exception;

    boolean updateInstructors(InstructorDTO t) throws Exception;

    boolean deleteInstructors(String id) throws Exception;

    List<String> getAllInstructorIds() throws Exception;

    Optional<InstructorDTO> findByInstructorId(String id) throws Exception;

    String generateNewInstructorsId();


}
