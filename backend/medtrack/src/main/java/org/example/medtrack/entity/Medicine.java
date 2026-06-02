package org.example.medtrack.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @NotBlank(message = "Category cannot be empty")
    private String category;

    @NotNull(message = "Quantity is required")
    private Integer quantity;

    @NotNull(message = "Price is required")
    private Double price;

    @NotNull(message = "Expiry date is required")
    private LocalDate expiryDate;
}