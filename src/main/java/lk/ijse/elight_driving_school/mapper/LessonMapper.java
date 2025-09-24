package lk.ijse.elight_driving_school.mapper;

import lk.ijse.elight_driving_school.dto.LessonsDTO;
import lk.ijse.elight_driving_school.entity.Lesson;

public class LessonMapper {

    public static LessonsDTO toDTO(Lesson
                                           lesson) {
        if (lesson == null) return null;

        LessonsDTO dto = new LessonsDTO();
        dto.setLessonId(lesson.getLessonId());
        dto.setCourseId(lesson.getCourse() != null ? lesson.getCourse().getCourseId() : null);
        dto.setStudentId(lesson.getStudent() != null ? lesson.getStudent().getStudentId() : null);
        dto.setInstructorId(lesson.getInstructor() != null ? lesson.getInstructor().getInstructorId() : null);
        dto.setLessonDate(lesson.getLessonDate());
        dto.setStartTime(lesson.getStartTime());
        dto.setEndTime(lesson.getEndTime());
        dto.setStatus(lesson.getStatus());
        return dto;
    }

    public static Lesson toEntity(LessonsDTO dto) {
        if (dto == null) return null;

        Lesson lesson = new Lesson();
        lesson.setLessonId(dto.getLessonId());
        lesson.setLessonDate(dto.getLessonDate());
        lesson.setStartTime(dto.getStartTime());
        lesson.setEndTime(dto.getEndTime());
        lesson.setStatus(dto.getStatus());
        return lesson;
    }
}
