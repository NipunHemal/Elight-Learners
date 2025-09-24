package lk.ijse.elight_driving_school.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "courses")
public class Course {
    @Id
    private String courseId;

    @Column(nullable = false)
    private String courseName;

    private String duration;

    private double fee;

    private String description;

    // Many courses can be taught by one instructor
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id")
    @ToString.Exclude
    private Instructor instructor;

    // Many-to-Many with Students via a join table
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<StudentCourseDetails> studentCourseDetails = new ArrayList<>();

    // One-to-Many: Course ↔ Lessons
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Lesson> lessons = new ArrayList<>();
}
