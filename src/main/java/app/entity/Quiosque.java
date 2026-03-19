package app.entity;

import java.time.LocalDate;
import java.util.List;

//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import app.util.StatusConta;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// @Criar-Administrador?

@SuppressWarnings("unused")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Quiosque {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome,  email;//, senhaHash;
	private double latitude, longitude, nota;
	@Transient
	private Long distancia;

	@ManyToOne
	@JoinColumn(name = "idEmpresa")
	private Empresa empresa;
	
	@OneToMany()
	private List<Usuario> administradores;
	
	public Quiosque(String nome, String email, double latitude, double longitude, Empresa empresa) {
		super();
		this.nome = nome;
		this.email = email;
		this.latitude = latitude;
		this.longitude = longitude;
		this.empresa = empresa;
	}

	@OneToMany(mappedBy = "quiosque")
	private List<Pedido> pedidos;

	@OneToMany
	private List<Usuario> entregadores;

	@OneToOne(mappedBy = "quiosque")
	private ContaBancaria contaBancaria;

	@OneToMany(mappedBy = "quiosque")
	private List<Item> itens;

	@Enumerated(EnumType.STRING)
	private StatusConta status;
}
