package lk.ijse.elight_driving_school.mapper;

import lk.ijse.elight_driving_school.dto.CourseDTO;
import lk.ijse.elight_driving_school.dto.tm.CourseTM;
import lk.ijse.elight_driving_school.entity.Course;

public class CourseMapper {

    public static CourseDTO toDTO(Course course) {
        if (course == null) return null;

        return CourseDTO.builder()
                .courseId(course.getCourseId())
                .courseName(course.getCourseName())
                .duration(course.getDuration())
                .fee(course.getFee())
                .description(course.getDescription())
                .instructorId(
                        course.getInstructor() != null ? course.getInstructor().getInstructorId() : null
                )
                .studentCourseDetails(
                        course.getStudentCourseDetails().stream()
                                .map(StudentCourseDetailsMapper::toDTO).toList()
                )
                .lessons(
                        course.getLessons().stream()
                                .map(LessonMapper::toDTO).toList()
                )
                .build();
    }

    public static Course toEntity(CourseDTO dto) {
        if (dto == null) return null;

        Course course = new Course();
        course.setCourseId(dto.getCourseId());
        course.setCourseName(dto.getCourseName());
        course.setDuration(dto.getDuration());
        course.setFee(dto.getFee());
        course.setDescription(dto.getDescription());
        return course;
    }

    public static CourseTM toTM(CourseDTO dto) {
        if (dto == null) return null;

        CourseTM course = new CourseTM();
        course.setCourseId(dto.getCourseId());
        course.setCourseName(dto.getCourseName());
        course.setDuration(dto.getDuration());
        course.setFee(dto.getFee());
        course.setDescription(dto.getDescription());
        return course;
    }
}
