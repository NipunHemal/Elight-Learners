package lk.ijse.elight_driving_school.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentDTO {
    private String studentId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private Date dob;
    private Date registrationDate;

    @Builder.Default
    private List<CourseDTO> courses = new ArrayList<>();

    @Builder.Default
    private List<StudentCourseDetailsDTO> studentCourseDetails = new ArrayList<>();

    @Builder.Default
    private List<LessonsDTO> lessons = new ArrayList<>();

    @Builder.Default
    private List<PaymentsDTO> payments = new ArrayList<>();
}