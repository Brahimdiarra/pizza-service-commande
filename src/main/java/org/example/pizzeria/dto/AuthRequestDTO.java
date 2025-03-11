package org.example.dto;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import jakarta.constraints.Email;
import jakarta.constraints.NotBlank;
import lombok.Data;



public class AuthRequestDTO {
    @NotBlank(message="l'email est obligatoire")
    @Email(message="format email invalide")
    private String email;
    @NotBlank(message="le mot de passe  est obligatoire")
    private String password;
}