package lk.ijse.elight_driving_school.mapper;

import lk.ijse.elight_driving_school.dao.DAOFactory;
import lk.ijse.elight_driving_school.dao.custom.StudentDAO;
import lk.ijse.elight_driving_school.dto.PaymentsDTO;
import lk.ijse.elight_driving_school.dto.tm.PaymentsTM;
import lk.ijse.elight_driving_school.entity.Payment;
import lk.ijse.elight_driving_school.entity.Student;
import lk.ijse.elight_driving_school.enums.DAOTypes;

public class PaymentMapper {
    private static StudentDAO studentDAO = DAOFactory.getInstance().getDAO(DAOTypes.STUDENTS);

    public static PaymentsDTO toDTO(Payment payment) {
        if (payment == null) return null;

        PaymentsDTO dto = new PaymentsDTO();
        dto.setPaymentId(payment.getPaymentId().toString());
        dto.setPaymentDate(payment.getPaymentDate());
        dto.setAmount(payment.getAmount());
        dto.setPaymentMethod(payment.getPaymentMethod());
        dto.setStatus(payment.getStatus());
        dto.setStudentId(payment.getStudent() != null ? payment.getStudent().getStudentId().toString() : null);

        return dto;
    }

    public static Payment toEntity(PaymentsDTO dto) throws Exception {
        if (dto == null) return null;
        Student student = studentDAO.findById(dto.getStudentId()).orElse(null);

        Payment payment = new Payment();
        payment.setPaymentId(dto.getPaymentId() == null ? null : Long.parseLong(dto.getPaymentId()));
        payment.setPaymentDate(dto.getPaymentDate());
        payment.setAmount(dto.getAmount());
        payment.setPaymentMethod(dto.getPaymentMethod());
        payment.setStatus(dto.getStatus());
        payment.setStudent(student);
        return payment;
    }

    public static PaymentsTM toTM(PaymentsDTO dto){
        if (dto == null) return null;

        PaymentsTM payment = new PaymentsTM();
        payment.setPaymentId(dto.getPaymentId());
        payment.setPaymentDate(dto.getPaymentDate());
        payment.setAmount(dto.getAmount());
        payment.setPaymentMethod(dto.getPaymentMethod());
        payment.setStudentId(dto.getStudentId());
        payment.setStatus(dto.getStatus());
        return payment;
    }
}
