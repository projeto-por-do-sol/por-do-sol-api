package app.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
//import java.util.Collection;
//import java.util.List;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import app.util.StatusConta;
import app.util.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
//import jakarta.persistence.PrePersist;
//import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"email", "role"} ) )
public class Usuario implements UserDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(nullable = false, unique = true, updatable = false)
	private UUID publicId;
	
	private String nome, senhaHash, cpf, telefone, email;
	private UserRole role;	
	
	private LocalDate dataCadastro, dataNasc;
	private LocalDateTime ultimoLogin;

	@Enumerated(EnumType.STRING)
	private StatusConta status;
	
	@PrePersist
	protected void onCreate() {
		if(publicId == null)
			publicId = UUID.randomUUID();
		this.ultimoLogin = LocalDateTime.now();
	    this.dataCadastro = LocalDate.now();
	    this.status = StatusConta.Ativa;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if(this.role == UserRole.PROPRIETARIO) return List.of(new SimpleGrantedAuthority("ROLE_PROPRIETARIO"), new SimpleGrantedAuthority("ROLE_ADMIN"));
		if(this.role == UserRole.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
		if(this.role == UserRole.ENTREGADOR) return List.of(new SimpleGrantedAuthority("ROLE_ENTREGADOR"));
		return List.of(new SimpleGrantedAuthority("ROLE_USER"));
	}

	@Override
	public @Nullable String getPassword() {
		// TODO Auto-generated method stub
		return senhaHash;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return email;
	}

	public Usuario(String nome, String email, String senhaHash, String cpf, String telefone, UserRole role,
			LocalDate dataNasc) {
		super();
		this.nome = nome;
		this.email = email;
		this.senhaHash = senhaHash;
		this.cpf = cpf;
		this.telefone = telefone;		
		this.role = role;
		this.dataNasc = dataNasc;
	}

	
}
