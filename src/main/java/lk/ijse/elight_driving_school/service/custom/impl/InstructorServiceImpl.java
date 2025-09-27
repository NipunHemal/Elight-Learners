package lk.ijse.elight_driving_school.service.custom.impl;

import lk.ijse.elight_driving_school.enums.DAOTypes;
import lk.ijse.elight_driving_school.mapper.InstructorMapper;
import lk.ijse.elight_driving_school.service.custom.InstructorService;
import lk.ijse.elight_driving_school.dao.DAOFactory;
import lk.ijse.elight_driving_school.dao.custom.InstructorDAO;
import lk.ijse.elight_driving_school.dto.InstructorDTO;
import lk.ijse.elight_driving_school.entity.Instructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class InstructorServiceImpl implements InstructorService {

    private final InstructorDAO instructorDAO = DAOFactory.getInstance().getDAO(DAOTypes.INSTRUCTORS);
//    private final EntityDTOConverter convertor = new EntityDTOConverter();


    @Override
    public List<InstructorDTO> getAllInstructors() throws Exception {
        List<Instructor> instructors = instructorDAO.getAll();
        List<InstructorDTO> instructorDTOs = new ArrayList<>();
        for (Instructor instructor : instructors) {
            instructorDTOs.add(InstructorMapper.toDTO(instructor));
        }
        return instructorDTOs;
    }

    @Override
    public boolean saveInstructors(InstructorDTO t) throws Exception {
        return instructorDAO.save(InstructorMapper.toEntity(t));
    }

    @Override
    public boolean updateInstructors(InstructorDTO t) throws Exception {
        Optional<Instructor> instructors = instructorDAO.findById(t.getInstructorId());
        if (instructors.isEmpty()) {
            throw new Exception("Instructor Not Found");
        }
        return instructorDAO.update(InstructorMapper.toEntity(t));
    }

    @Override
    public boolean deleteInstructors(String id) throws Exception {
        Optional<Instructor> instructors = instructorDAO.findById(id);
        if (instructors.isEmpty()) {
            throw new Exception("Instructor not Found");
        }
        return instructorDAO.delete(id);
    }

    @Override
    public List<String> getAllInstructorIds() throws Exception {
        return instructorDAO.getAllIds();
    }

    @Override
    public Optional<InstructorDTO> findByInstructorId(String id) throws Exception {
        Optional<Instructor> instructors = instructorDAO.findById(id);
        return instructors.map(InstructorMapper::toDTO);
    }
}
