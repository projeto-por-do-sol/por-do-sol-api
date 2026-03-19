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
//import app.util.StatusConta;
//import app.util.UserRole;
//import jakarta.persistence.Entity;
//import jakarta.persistence.EnumType;
//import jakarta.persistence.Enumerated;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.OneToMany;
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
//public class Entregador extends Usuario {
//	@ManyToOne
//	@JoinColumn(name = "idQuiosque")
//	private Quiosque quiosque;
//
//	@OneToMany(mappedBy = "entregador")
//	private List<Pedido> pedidos;
//	
//	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//	    return List.of(new SimpleGrantedAuthority("ROLE_ENTREGADOR"));
//	}
//
//	public Entregador(String nome, String email, String senhaHash, String cpf, String telefone, UserRole role,
//			LocalDate dataNasc) {
//		super(nome, email, senhaHash, cpf, telefone, role, dataNasc);
//	}
//	
//}
