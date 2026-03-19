package app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.DTO.EmpresaDTO;
import app.entity.Empresa;
import app.entity.Usuario;
import app.service.EmpresaService;
import app.util.StatusConta;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/empresas")
public class EmpresaController {

	
	@Autowired
    private EmpresaService empresaService;
	
	@PreAuthorize("hasRole('PROPRIETARIO')")
    @PostMapping
    public ResponseEntity<Empresa> save(@AuthenticationPrincipal Usuario proprietario,
                                        @RequestBody @Valid EmpresaDTO data) {

        Empresa empresa = new Empresa(data.razaoSocial(), data.nomeFantasia(), data.cnpj(), data.cep(),
        							 data.uf(), data.cidade(), proprietario, StatusConta.Ativa);

        this.empresaService.save(empresa);
        return new ResponseEntity<Empresa> (empresa, HttpStatus.CREATED);
    }


}
