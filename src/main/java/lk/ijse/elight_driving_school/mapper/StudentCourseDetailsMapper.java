package lk.ijse.elight_driving_school.mapper;

import lk.ijse.elight_driving_school.dto.StudentCourseDetailsDTO;
import lk.ijse.elight_driving_school.entity.StudentCourseDetails;

public class StudentCourseDetailsMapper {

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

    public static StudentCourseDetails toEntity(StudentCourseDetailsDTO dto) {
        if (dto == null) return null;

        StudentCourseDetails scd = new StudentCourseDetails();
        scd.setStudentCourseId(Long.parseLong(dto.getStudentCourseId()));
        scd.setEnrollmentDate(dto.getEnrollmentDate());
        scd.setStatus(dto.getStatus());
        scd.setGrade(dto.getGrade());
        return scd;
    }
}
