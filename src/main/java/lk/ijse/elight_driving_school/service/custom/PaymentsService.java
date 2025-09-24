package lk.ijse.elight_driving_school.service.custom;

import lk.ijse.elight_driving_school.dto.PaymentsDTO;

import java.util.List;
import java.util.Optional;

public interface PaymentsService {
    List<PaymentsDTO> getAllPayments() throws Exception;

    String getLastPaymentId() throws Exception;

    boolean savePayments(PaymentsDTO t) throws Exception;

    boolean updatePayments(PaymentsDTO t) throws Exception;

    boolean deletePayments(String id) throws Exception;

    List<String> getAllPaymentIds() throws Exception;

    Optional<PaymentsDTO> findByPaymentId(String id) throws Exception;

    String generateNewPaymentId();
}
