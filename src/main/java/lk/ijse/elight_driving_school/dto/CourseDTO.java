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
public class CourseDTO {
    private String courseId;
    private String courseName;
    private String duration;
    private double fee;
    private String description;
    private String instructorId;

    @Builder.Default
    private List<StudentCourseDetailsDTO> studentCourseDetails = new ArrayList<>();

    @Builder.Default
    private List<LessonsDTO> lessons = new ArrayList<>();
}
