package app.DTO;

import java.time.LocalDate;
import app.util.UserRole;

public record RegisterAdminResponseDTO(String quiosque,String nome, String login,  UserRole role, String telefone, LocalDate dataNasc) {

}
