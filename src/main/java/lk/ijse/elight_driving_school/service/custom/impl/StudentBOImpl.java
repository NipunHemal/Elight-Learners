package lk.ijse.elight_driving_school.service.custom.impl;

import lk.ijse.elight_driving_school.entity.Student;
import lk.ijse.elight_driving_school.enums.DAOTypes;
import lk.ijse.elight_driving_school.service.custom.StudentBO;

import lk.ijse.elight_driving_school.service.exception.DuplicateException;
import lk.ijse.elight_driving_school.dao.DAOFactory;
import lk.ijse.elight_driving_school.dao.custom.StudentDAO;
import lk.ijse.elight_driving_school.dto.StudentDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class StudentBOImpl implements StudentBO {

    private final StudentDAO studentDAO = DAOFactory.getInstance().getDAO(DAOTypes.STUDENTS);
    private final EntityDTOConverter converter = new EntityDTOConverter();

    @Override
    public List<StudentDTO> getAllStudents() throws Exception {
        List<Student> studentsList = studentDAO.getAll();
        List<StudentDTO> studentsDTOList = new ArrayList<>();
        for (Student students : studentsList) {
            studentsDTOList.add(converter.getStudentsDTO(students));
        }
        return studentsDTOList;
    }

    @Override
    public String getLastStudentId() throws Exception {
        return studentDAO.getLastId();
    }

    @Override
    public boolean saveStudents(StudentDTO t) throws Exception {
        Optional<Student> students = studentDAO.findById(t.getStudentId());
        if (students.isPresent()) {
            throw new DuplicateException("Student already exists");
        }
        return studentDAO.save(converter.getStudentsEntity(t));

    }

    @Override
    public boolean updateStudents(StudentDTO t) throws Exception {
        Optional<Student> students = studentDAO.findById(t.getStudentId());
        if (students.isEmpty()) {
            throw new DuplicateException("Student Not Found");
        }
        return studentDAO.update(converter.getStudentsEntity(t));
    }

    @Override
    public boolean deleteStudents(String id) throws Exception {
        Optional<Student> students = studentDAO.findById(id);
        if (students.isEmpty()) {
            throw new DuplicateException("Student not found");
        }
        return studentDAO.delete(id);
    }

    @Override
    public List<String> getAllStudentIds() throws Exception {
        return studentDAO.getAllIds();
    }

    @Override
    public Optional<StudentDTO> findByStudentId(String id) throws Exception {
        Optional<Student> students = studentDAO.findById(id);
        if (students.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(converter.getStudentsDTO(students.get()));
    }

    @Override
    public String generateNewStudentId() {
        return studentDAO.generateNewId();
    }
}