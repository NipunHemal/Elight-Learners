package lk.ijse.elight_driving_school.mapper;

import lk.ijse.elight_driving_school.dto.PaymentsDTO;
import lk.ijse.elight_driving_school.entity.Payment;

public class PaymentMapper {

    public static PaymentsDTO toDTO(Payment payment) {
        if (payment == null) return null;

        PaymentsDTO dto = new PaymentsDTO();
        dto.setPaymentId(payment.getPaymentId());
        dto.setPaymentDate(payment.getPaymentDate());
        dto.setAmount(payment.getAmount());
        dto.setPaymentMethod(payment.getPaymentMethod());
        dto.setStatus(payment.getStatus());
        dto.setStudentId(payment.getStudent() != null ? payment.getStudent().getStudentId() : null);

        return dto;
    }

    public static Payment toEntity(PaymentsDTO dto) {
        if (dto == null) return null;

        Payment payment = new Payment();
        payment.setPaymentId(dto.getPaymentId());
        payment.setPaymentDate(dto.getPaymentDate());
        payment.setAmount(dto.getAmount());
        payment.setPaymentMethod(dto.getPaymentMethod());
        payment.setStatus(dto.getStatus());
        return payment;
    }
}
