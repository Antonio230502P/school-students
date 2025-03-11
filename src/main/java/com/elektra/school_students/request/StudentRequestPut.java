package com.elektra.school_students.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentRequestPut {
    private String name;

    @Min(value = 3, message = "Un alumno no puede tener menos de 3 años (Kinder)")
    @Max(value = 100, message = "Un alumno no puede tener más de 100 años (Solo se ha registrado un japones que se graduo a los 96 años)")
    private Integer age;

    @Min(value = 1, message = "El grado más bajo es 1")
    @Max(value = 20, message = "Suponiendo que sea una escuela que tenga todos los niveles educativos, el grado más alto sería 20 (3 de kinder + 6 de primaria + 3 de secundaria + 3 de preparatoria/bachillerato + 5 de grado)")
    private Integer grade;

    private String address;
    
    @Size(min = 1, max = 1, message = "foreignStudent debe ser un solo carácter ('Y' o 'N')")
    @Pattern(regexp = "^[YN]$", message = "El campo foreignStudent solo puede ser 'Y' o 'N'")
    private String foreignStudent;
}
