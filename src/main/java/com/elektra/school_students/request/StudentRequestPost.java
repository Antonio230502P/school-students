package com.elektra.school_students.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class StudentRequestPost {
    @NotNull(message = "El campo name es obligatorio y no puede ser nulo")
    @NotBlank(message = "El nombre del alumno no puede ir en blanco")
    private String name;

    @NotNull(message = "El campo age es obligatorio y no puede ser nulo")
    @Min(value = 3, message = "Un alumno no puede tener menos de 3 años (Kinder)")
    @Max(value = 100, message = "Un alumno no puede tener más de 100 años (Solo se ha registrado un japones que se graduo a los 96 años)")
    private Integer age;

    @NotNull(message = "El campo grade es obligatorio y no puede ser nulo")
    @Min(value = 1, message = "El grado más bajo es 1")
    @Max(value = 20, message = "Suponiendo que sea una escuela que tenga todos los niveles educativos, el grado más alto sería 20 (3 de kinder + 6 de primaria + 3 de secundaria + 3 de preparatoria/bachillerato + 5 de grado)")
    private Integer grade;

    @NotNull(message = "El campo address es obligatorio y no puede ser nulo")
    @NotBlank(message = "La dirección no puede ir en blanco")
    private String address;
    
    @NotNull(message = "El campo foreignStudent es obligatorio y no puede ser nulo")
    @NotBlank(message = "El campo foreignStudent no puede ir en blanco")
    @Size(min = 1, max = 1, message = "foreignStudent debe ser un solo carácter ('Y' o 'N')")
    @Pattern(regexp = "^[YN]$", message = "El campo foreignStudent solo puede ser 'Y' o 'N'")
    private String foreignStudent;
}
