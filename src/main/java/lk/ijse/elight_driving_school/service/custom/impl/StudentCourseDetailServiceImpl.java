package lk.ijse.elight_driving_school.service.custom.impl;

import lk.ijse.elight_driving_school.entity.Student;
import lk.ijse.elight_driving_school.enums.DAOTypes;
import lk.ijse.elight_driving_school.mapper.StudentCourseDetailsMapper;
import lk.ijse.elight_driving_school.service.custom.StudentCourseDetailService;
import lk.ijse.elight_driving_school.service.exception.DuplicateException;
import lk.ijse.elight_driving_school.dao.DAOFactory;
import lk.ijse.elight_driving_school.dao.custom.CourseDAO;
import lk.ijse.elight_driving_school.dao.custom.StudentCourseDetailDAO;
import lk.ijse.elight_driving_school.dao.custom.StudentDAO;
import lk.ijse.elight_driving_school.dto.StudentCourseDetailsDTO;
import lk.ijse.elight_driving_school.entity.Course;
import lk.ijse.elight_driving_school.entity.StudentCourseDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentCourseDetailServiceImpl implements StudentCourseDetailService {

    private final StudentCourseDetailDAO studentCourseDetailDAO = (StudentCourseDetailDAO) DAOFactory.getInstance().getDAO(DAOTypes.STUDENT_COURSE_DETAILS);
    private final StudentDAO studentDAO = (StudentDAO) DAOFactory.getInstance().getDAO(DAOTypes.STUDENTS);
    private final CourseDAO courseDAO = (CourseDAO) DAOFactory.getInstance().getDAO(DAOTypes.COURSE);

    @Override
    public List<StudentCourseDetailsDTO> getAllStudentCourseDetails() throws Exception {
        List<StudentCourseDetails> studentCourseDetails = studentCourseDetailDAO.getAll();
        List<StudentCourseDetailsDTO> studentCourseDetailsDTOs = new ArrayList<>();
        for (StudentCourseDetails studentCourseDetail : studentCourseDetails) {
            studentCourseDetailsDTOs.add(StudentCourseDetailsMapper.toDTO(studentCourseDetail));
        }
        return studentCourseDetailsDTOs;
    }

    @Override
    public String getLastStudentCourseDetailId() throws Exception {
        return studentCourseDetailDAO.getLastId();
    }

    @Override
    public boolean saveStudentCourseDetails(StudentCourseDetailsDTO t) throws Exception {
        Optional<Student> studentExists = studentDAO.findById(t.getStudentId());
        Optional<Course> courseExists = courseDAO.findById(t.getCourseId());
        Optional<StudentCourseDetails> studentCourseDetailsExists = studentCourseDetailDAO.findById(t.getStudentCourseId());

        if (studentCourseDetailsExists.isPresent()) {
            throw new DuplicateException("Student Course Details already exists");
        }
        if (studentExists.isPresent() &&  courseExists.isPresent()) {
            return studentCourseDetailDAO.save(StudentCourseDetailsMapper.toEntity(t));
        }
        throw new Exception("Student or Course not found");
    }

    @Override
    public boolean updateStudentCourseDetails(StudentCourseDetailsDTO t) throws Exception {
        Optional<StudentCourseDetails>  studentCourseDetailsExists = studentCourseDetailDAO.findById(t.getStudentCourseId());
        if (studentCourseDetailsExists.isEmpty()) {
            throw new Exception("Student Course not found");
        }
        return studentCourseDetailDAO.update(StudentCourseDetailsMapper.toEntity(t));
    }

    @Override
    public boolean deleteStudentCourseDetails(String id) throws Exception {
        Optional<StudentCourseDetails>  studentCourseDetailsExists = studentCourseDetailDAO.findById(id);
        if (studentCourseDetailsExists.isEmpty()) {
            throw new Exception("Student Course not found");
        }
        return studentCourseDetailDAO.delete(id);
    }

    @Override
    public List<String> getAllStudentCourseDetailIds() throws Exception {
        return studentCourseDetailDAO.getAllIds();
    }

    @Override
    public Optional<StudentCourseDetailsDTO> findByStudentCourseDetailId(String id) throws Exception {
        Optional<StudentCourseDetails> details = studentCourseDetailDAO.findById(id);
        return details.map(StudentCourseDetailsMapper::toDTO);
    }
}
