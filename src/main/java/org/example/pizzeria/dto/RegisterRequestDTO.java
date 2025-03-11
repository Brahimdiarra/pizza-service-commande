package org.example.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RegisterRequestDTO {

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Format d'email invalide")
    private String email;

    @NotBlank(message = "Le mot de passe est obligatoire")
    private String password;

    @NotBlank(message = "Le nom complet est obligatoire")
    private String fullName;

    private String address;

    @Pattern(regexp = "^[0-9]{10}$", message = "Le numéro de téléphone doit contenir 10 chiffres")
    private String phoneNumber;
}