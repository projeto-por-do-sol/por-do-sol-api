package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.entity.Empresa;
import app.entity.Usuario;

import java.util.Optional;


public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
	
	Optional<Empresa> findByProprietario(Usuario proprietario);

}
