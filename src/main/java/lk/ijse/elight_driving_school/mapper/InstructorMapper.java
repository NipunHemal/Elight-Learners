package lk.ijse.elight_driving_school.mapper;

import lk.ijse.elight_driving_school.dto.InstructorDTO;
import lk.ijse.elight_driving_school.dto.tm.InstructorTM;
import lk.ijse.elight_driving_school.entity.Instructor;


public class InstructorMapper {

    public static InstructorDTO toDTO(Instructor instructor) {
        if (instructor == null) return null;

        return InstructorDTO.builder()
                .instructorId(Long.toString(instructor.getInstructorId()))
                .firstName(instructor.getFirstName())
                .lastName(instructor.getLastName())
                .email(instructor.getEmail())
                .contact(instructor.getContact())
                .specialization(instructor.getSpecialization())
                .availability(instructor.getAvailability())
//                .courses(
//                        instructor.getCourses().stream()
//                                .map(CourseMapper::toDTO).toList()
//                )
//                .lessons(
//                        instructor.getLessons().stream()
//                                .map(LessonMapper::toDTO).toList()
//                )
                .build();
    }

    public static Instructor toEntity(InstructorDTO dto) {
        if (dto == null) return null;

        Instructor instructor = new Instructor();
        instructor.setInstructorId(dto.getInstructorId() == null ? null : Long.parseLong(dto.getInstructorId()));
        instructor.setFirstName(dto.getFirstName());
        instructor.setLastName(dto.getLastName());
        instructor.setEmail(dto.getEmail());
        instructor.setContact(dto.getContact());
        instructor.setSpecialization(dto.getSpecialization());
        instructor.setAvailability(dto.getAvailability());

        return instructor;
    }

    public static InstructorTM toTM(InstructorDTO dto) {
        if (dto == null) return null;

        InstructorTM instructor = new InstructorTM();
        instructor.setInstructorId(dto.getInstructorId());
        instructor.setFirstName(dto.getFirstName());
        instructor.setLastName(dto.getLastName());
        instructor.setEmail(dto.getEmail());
        instructor.setContact(dto.getContact());
        instructor.setSpecialization(dto.getSpecialization());
        instructor.setAvailability(dto.getAvailability());

        return instructor;
    }
}
