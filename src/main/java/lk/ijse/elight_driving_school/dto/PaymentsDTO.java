package lk.ijse.elight_driving_school.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import java.sql.Time;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentsDTO {
    private String paymentId;
    private Date paymentDate;
    private double amount;
    private String paymentMethod;
    private String status;
    private String studentId;
}
