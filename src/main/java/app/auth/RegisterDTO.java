package app.auth;

import java.time.LocalDate;

import app.util.UserRole;

//import app.util.UserRole;

public record RegisterDTO(String nome, String login, String password, String cpf, UserRole role, String telefone, LocalDate dataNasc) {

}
