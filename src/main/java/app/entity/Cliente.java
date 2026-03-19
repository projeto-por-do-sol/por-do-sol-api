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
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.OneToMany;
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
//public class Cliente extends Usuario {
//	
//	public Cliente(String nome, String email, String senhaHash, String cpf, String telefone, UserRole role,
//			LocalDate dataNasc) {
//		super(nome, email, senhaHash, cpf, telefone, role, dataNasc);
//	}
//
//	private Integer codVerificacao;
//	
//	@OneToMany(mappedBy = "cliente")
//	private List<Pedido> pedidos;
//	
//	@OneToOne
//	@JoinColumn(name = "idCarteira")
//	private Carteira carteira;
//	
//	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//	    return List.of(new SimpleGrantedAuthority("ROLE_USER"));
//	}
//}