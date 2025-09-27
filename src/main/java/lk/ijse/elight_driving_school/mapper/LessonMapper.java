package lk.ijse.elight_driving_school.mapper;

import lk.ijse.elight_driving_school.dao.DAOFactory;
import lk.ijse.elight_driving_school.dao.custom.InstructorDAO;
import lk.ijse.elight_driving_school.dto.LessonsDTO;
import lk.ijse.elight_driving_school.dto.tm.LessonsTM;
import lk.ijse.elight_driving_school.entity.Instructor;
import lk.ijse.elight_driving_school.entity.Lesson;
import lk.ijse.elight_driving_school.enums.DAOTypes;

public class LessonMapper {

    private static InstructorDAO instructorDAO = DAOFactory.getInstance().getDAO(DAOTypes.INSTRUCTORS);

    public static LessonsDTO toDTO(Lesson
                                           lesson) {
        if (lesson == null) return null;

        LessonsDTO dto = new LessonsDTO();
        dto.setLessonId(lesson.getLessonId().toString());
        dto.setCourseId(lesson.getCourse() != null ? lesson.getCourse().getCourseId().toString() : null);
        dto.setStudentId(lesson.getStudent() != null ? lesson.getStudent().getStudentId().toString() : null);
        dto.setInstructorId(lesson.getInstructor() != null ? lesson.getInstructor().getInstructorId().toString() : null);
        dto.setLessonDate(lesson.getLessonDate());
        dto.setStartTime(lesson.getStartTime());
        dto.setEndTime(lesson.getEndTime());
        dto.setStatus(lesson.getStatus());
        return dto;
    }

    public static Lesson toEntity(LessonsDTO dto) throws Exception {
        if (dto == null) return null;
        Instructor instructor = instructorDAO.findById(dto.getInstructorId()).get();
        Lesson lesson = new Lesson();
        lesson.setLessonId(dto.getLessonId() == null ? null : Long.parseLong(dto.getLessonId()));
        lesson.setLessonDate(dto.getLessonDate());
        lesson.setStartTime(dto.getStartTime());
        lesson.setEndTime(dto.getEndTime());
        lesson.setStatus(dto.getStatus());
        lesson.setInstructor(instructor);
        return lesson;
    }

    public static LessonsTM toTM(LessonsDTO dto){
        if (dto == null) return null;

        LessonsTM lesson = new LessonsTM();
        lesson.setLessonId(dto.getLessonId());
        lesson.setLessonDate(dto.getLessonDate());
        lesson.setStartTime(dto.getStartTime().toString());
        lesson.setEndTime(dto.getEndTime().toString());
        lesson.setStatus(dto.getStatus());
        return lesson;
    }
}
