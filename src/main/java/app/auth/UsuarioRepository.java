package app.auth;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import app.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
	boolean existsByEmailAndRole(String email, String role);
	
	UserDetails findByEmail(String email);
	UserDetails findByPublicId(UUID id);
}
