package com.example.buensaboruno.domain.entities;

import com.example.buensaboruno.domain.enums.FormaPago;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
//@Audited

public class Factura extends Base{
    @Temporal(TemporalType.DATE)
    private LocalDate fechaFacturacion;
    private Integer mpPaymentId;
    private Integer mpMerchantOrderId;
    private String mpPreferenceId;
    private String mpPaymentType;
    private FormaPago formaPago;
    private Double totalVenta;

    @OneToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

}
