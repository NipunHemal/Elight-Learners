package lk.ijse.elight_driving_school.service.custom.impl;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import lk.ijse.elight_driving_school.entity.Lesson;
import lk.ijse.elight_driving_school.enums.DAOTypes;
import lk.ijse.elight_driving_school.service.custom.LessonsBO;
import lk.ijse.elight_driving_school.service.exception.DuplicateException;
import lk.ijse.elight_driving_school.service.exception.NotFoundException;
import lk.ijse.elight_driving_school.dao.DAOFactory;
import lk.ijse.elight_driving_school.dao.custom.CourseDAO;
import lk.ijse.elight_driving_school.dao.custom.InstructorDAO;
import lk.ijse.elight_driving_school.dao.custom.LessonsDAO;
import lk.ijse.elight_driving_school.dao.custom.QueryDAO;
import lk.ijse.elight_driving_school.dto.LessonsDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LessonsBOImpl implements LessonsBO {

    private final LessonsDAO lessonsDAO = (LessonsDAO) DAOFactory.getInstance().getDAO(DAOTypes.LESSONS);
    private final CourseDAO courseDAO = (CourseDAO) DAOFactory.getInstance().getDAO(DAOTypes.COURSE);
    private final InstructorDAO instructorDAO =  (InstructorDAO) DAOFactory.getInstance().getDAO(DAOTypes.INSTRUCTORS);
    private final QueryDAO queryDAO = (QueryDAO) DAOFactory.getInstance().getDAO(DAOTypes.QUERY);
    private final EntityDTOConverter converter = new EntityDTOConverter();

    @Override
    public List<LessonsDTO> getAllLessons() throws Exception {
        List<Lesson> lessons = lessonsDAO.getAll();
        List<LessonsDTO> lessonsDTOS = new ArrayList<>();
        for (Lesson lesson : lessons) {
            lessonsDTOS.add(converter.getLessonsDTO(lesson));
        }
        return lessonsDTOS;
    }

    @Override
    public String getLastLessonId() throws Exception {
        return lessonsDAO.getLastId();
    }

    @Override
    public boolean saveLessons(LessonsDTO t) throws Exception {

        //check course exist
        boolean courseExists = courseDAO.findById(t.getCourseId()).isPresent();

        // check instructor exists
        boolean instructorExists = instructorDAO.findById(t.getCourseId()).isPresent();

        if (courseExists && instructorExists) {
            return lessonsDAO.save(converter.getLessonsEntity(t));
        }
        throw new Exception("Lessons not saved");
    }

    @Override
    public boolean updateLessons(LessonsDTO t) throws Exception {
        Optional<Lesson> lessons = lessonsDAO.findById(t.getLessonId());
        if (lessons.isEmpty()) {
            throw new DuplicateException("Lessons Not Found");
        }
        return lessonsDAO.update(converter.getLessonsEntity(t));
    }

    @Override
    public boolean deleteLessons(String id) throws Exception {
        Optional<Lesson> lesson = lessonsDAO.findById(id);
        if (lesson.isEmpty()) {
            throw new NotFoundException("Lesson not found");
        }

        int studentsEnrolled = queryDAO.getStudentCountForLesson(id);
        if (studentsEnrolled > 0) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Students are enrolled in this lesson. Are you sure you want to delete?",
                    ButtonType.YES,
                    ButtonType.NO);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);

            ButtonType result = alert.showAndWait().orElse(ButtonType.NO);
            if (result != ButtonType.YES) {
                return false; // User cancelled deletion
            }
        }

        return lessonsDAO.delete(id);
    }

    @Override
    public List<String> getAllLessonIds() throws Exception {
        return lessonsDAO.getAllIds();
    }

    @Override
    public Optional<LessonsDTO> findByLessonId(String id) throws Exception {
        Optional<Lesson> lessons = lessonsDAO.findById(id);
        if (lessons.isPresent()) {
            return Optional.of(converter.getLessonsDTO(lessons.get()));
        }
        return Optional.empty();
    }
}
