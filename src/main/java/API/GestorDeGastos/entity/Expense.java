package API.GestorDeGastos.entity;


import API.GestorDeGastos.entity.enums.CategoryEnum;
import API.GestorDeGastos.entity.enums.PaymentMethodEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "expense")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Campo obligatorio!")
    @Size(min = 3, max = 200)
    @Column(nullable = false, length = 200)
    private String description;

    @NotNull(message = "Campo obligatorio!")
    @DecimalMin("0.01")
    @Digits(integer = 10, fraction = 2)
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    @NotNull(message = "Campo obligatorio!")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoryEnum category;

    @NotNull(message = "Campo obligatorio!")
    @PastOrPresent(message = "La fecha debe de ser actual o pasada.")
    @Column(nullable = false)
    private LocalDate date;

    @NotNull(message = "Campo obligatorio!")
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    private PaymentMethodEnum paymentMethod;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
