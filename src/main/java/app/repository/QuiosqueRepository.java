package app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import app.entity.Empresa;
import app.entity.Quiosque;
import app.entity.Usuario;

public interface QuiosqueRepository extends JpaRepository<Quiosque, Long> {

	@Query(value = "SELECT * "			
			+ " FROM quiosque"
			+ " WHERE latitude BETWEEN :minLat AND :maxLat"
			+ "  AND longitude BETWEEN :minLon AND :maxLon", nativeQuery = true)
	List<Quiosque> findByDistancia(double latUsuario,double lonUsuario, double raioM, double minLat, double maxLat, double minLon, double maxLon);
	
	Optional<Quiosque> findByAdministradores(Usuario administrador);
	
	Optional<Quiosque> findByEmpresa(Empresa empresa);
	
	Optional<Quiosque> findByAdministradoresContaining(Usuario administrador);
}
