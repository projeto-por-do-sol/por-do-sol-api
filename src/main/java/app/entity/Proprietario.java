//package app.entity;
//
//
//import java.time.LocalDate;
//import java.util.Collection;
//import java.util.List;
//
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//
//import app.util.UserRole;
//import jakarta.persistence.Entity;
//import jakarta.persistence.OneToOne;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@Entity
//public class Proprietario extends Usuario{	
//	@OneToOne(mappedBy = "proprietario")
//	private Empresa empresa;
//	
//	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//	    return List.of(new SimpleGrantedAuthority("ROLE_PROPRIETARIO"));
//	}
//
//	public Proprietario(String nome, String email, String senhaHash, String cpf, String telefone, UserRole role,
//			LocalDate dataNasc) {
//		super(nome, email, senhaHash, cpf, telefone, role, dataNasc);
//	}
//	
//}
