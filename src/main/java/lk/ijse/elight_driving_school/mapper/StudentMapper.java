package lk.ijse.elight_driving_school.mapper;

import lk.ijse.elight_driving_school.dto.StudentDTO;
import lk.ijse.elight_driving_school.dto.tm.StudentTM;
import lk.ijse.elight_driving_school.entity.Student;

public class StudentMapper {

    public static StudentDTO toDTO(Student student) {
        if (student == null) return null;

        return StudentDTO.builder()
                .studentId(student.getStudentId().toString())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .email(student.getEmail())
                .phone(student.getPhone())
                .address(student.getAddress())
                .dob(student.getDob())
                .registrationDate(student.getRegistrationDate())
                .studentCourseDetails(
                        student.getStudentCourseDetails().stream()
                                .map(StudentCourseDetailsMapper::toDTO).toList()
                )
                .lessons(
                        student.getLessons().stream()
                                .map(LessonMapper::toDTO).toList()
                )
                .payments(
                        student.getPayments().stream()
                                .map(PaymentMapper::toDTO).toList()
                )
                .build();
    }

    public static Student toEntity(StudentDTO dto) {
        if (dto == null) return null;

        Student student = new Student();
        student.setStudentId(Long.parseLong(dto.getStudentId()));
        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());
        student.setEmail(dto.getEmail());
        student.setPhone(dto.getPhone());
        student.setAddress(dto.getAddress());
        student.setDob(dto.getDob());
        student.setRegistrationDate(dto.getRegistrationDate());

        return student;
    }

    public static StudentTM toTM(StudentDTO dto) {
        if (dto == null) return null;

        StudentTM student = new StudentTM();
        student.setStudentId(dto.getStudentId());
        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());
        student.setEmail(dto.getEmail());
        student.setPhone(dto.getPhone());
        student.setAddress(dto.getAddress());
        student.setDob(dto.getDob());
        student.setRegistrationDate(dto.getRegistrationDate());

        return student;
    }
}
