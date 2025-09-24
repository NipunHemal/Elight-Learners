package lk.ijse.elight_driving_school.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InstructorDTO {
    private String instructorId;
    private String firstName;
    private String lastName;
    private String email;
    private String contact;
    private String specialization;
    private String availability;

    @Builder.Default
    private List<LessonsDTO> lessons = new  ArrayList<>();

    @Builder.Default
    private List<CourseDTO> courses = new ArrayList<>();
}
