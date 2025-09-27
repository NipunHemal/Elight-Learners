package lk.ijse.elight_driving_school.mapper;

import lk.ijse.elight_driving_school.dao.DAOFactory;
import lk.ijse.elight_driving_school.dao.custom.CourseDAO;
import lk.ijse.elight_driving_school.dao.custom.StudentDAO;
import lk.ijse.elight_driving_school.dto.StudentCourseDetailsDTO;
import lk.ijse.elight_driving_school.entity.Course;
import lk.ijse.elight_driving_school.entity.Student;
import lk.ijse.elight_driving_school.entity.StudentCourseDetails;
import lk.ijse.elight_driving_school.enums.DAOTypes;

public class StudentCourseDetailsMapper {

    private static StudentDAO studentDAO = DAOFactory.getInstance().getDAO(DAOTypes.STUDENTS);
    private static CourseDAO courseDAO = DAOFactory.getInstance().getDAO(DAOTypes.COURSE);

    public static StudentCourseDetailsDTO toDTO(StudentCourseDetails scd) {
        if (scd == null) return null;

        StudentCourseDetailsDTO dto = new StudentCourseDetailsDTO();
        dto.setStudentCourseId(scd.getStudentCourseId().toString());
        dto.setStudentId(scd.getStudent() != null ? scd.getStudent().getStudentId().toString() : null);
        dto.setCourseId(scd.getCourse() != null ? scd.getCourse().getCourseId().toString() : null);
        dto.setEnrollmentDate(scd.getEnrollmentDate());
        dto.setStatus(scd.getStatus());
        dto.setGrade(scd.getGrade());

        return dto;
    }

    public static StudentCourseDetails toEntity(StudentCourseDetailsDTO dto) throws Exception {
        if (dto == null) return null;

        Student student = studentDAO.findById(dto.getStudentId()).orElse(null);
        Course course = courseDAO.findById(dto.getCourseId()).orElse(null);

        StudentCourseDetails scd = new StudentCourseDetails();
        scd.setStudentCourseId(dto.getStudentCourseId() == null ? null : Long.parseLong(dto.getStudentCourseId()));
        scd.setEnrollmentDate(dto.getEnrollmentDate());
        scd.setStatus(dto.getStatus());
        scd.setGrade(dto.getGrade());
        scd.setStudent(student);
        scd.setCourse(course);
        return scd;
    }
}
