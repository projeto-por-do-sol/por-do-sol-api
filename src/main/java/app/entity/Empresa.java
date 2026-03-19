package app.entity;

import java.util.List;

import app.util.StatusConta;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Empresa {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String razaoSocial, nomeFantasia,cnpj, cep, uf, cidade;
	
	public Empresa(String razaoSocial, String nomeFantasia, String cnpj, String cep, String uf, String cidade,
			Usuario proprietario, StatusConta status) {
		super();
		this.razaoSocial = razaoSocial;
		this.nomeFantasia = nomeFantasia;
		this.cnpj = cnpj;
		this.cep = cep;
		this.uf = uf;
		this.cidade = cidade;
		this.proprietario = proprietario;
		this.status = status;
	}

	@OneToMany(mappedBy = "empresa")
	private List<Quiosque> quiosques;
	
	@OneToOne
	@JoinColumn(name = "idProprietario", nullable = false)
	private Usuario proprietario;
	
	@Enumerated(EnumType.STRING)
	private StatusConta status;
}
