package lk.ijse.elight_driving_school.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "instructors")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Instructor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long instructorId;

    private String firstName;
    private String lastName;
    private String email;
    private String contact;
    private String specialization;
    private String availability;

    // One instructor can teach many courses
    @OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Course> courses = new ArrayList<>();

    // One instructor can have many lessons
    @OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Lesson> lessons = new ArrayList<>();
}
