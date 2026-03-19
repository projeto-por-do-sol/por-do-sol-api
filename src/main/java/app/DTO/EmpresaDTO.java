package app.DTO;

import app.entity.Usuario;
import app.util.StatusConta;

public record EmpresaDTO(String razaoSocial, String nomeFantasia, String cnpj, String cep, String uf, String cidade,
		Usuario proprietario, StatusConta status) {

}
