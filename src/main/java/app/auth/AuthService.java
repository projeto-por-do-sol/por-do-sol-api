package app.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import app.DTO.RegisterAdminResponseDTO;
import app.entity.Empresa;
import app.entity.Quiosque;
import app.entity.Usuario;
import app.repository.EmpresaRepository;
import app.repository.QuiosqueRepository;
import app.util.UserRole;



@Service
public class AuthService implements UserDetailsService{

	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private EmpresaRepository empresaRepository;
	@Autowired
	private QuiosqueRepository quiosqueRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return usuarioRepository.findByEmail(username);
	}
	
	public Usuario register(RegisterDTO data){

	    if(usuarioRepository.findByEmail(data.login()) != null)
	        throw new RuntimeException("Usuário já cadastrado");
	    if(!data.role().equals(UserRole.PROPRIETARIO) && !data.role().equals(UserRole.CLIENTE)) {
	    	throw new ResponseStatusException(
	                HttpStatus.UNAUTHORIZED, "Apenas proprietarios podem cadastrar funcionarios");
	    }
	    String encryptedPassword = passwordEncoder.encode(data.password());
	    Usuario user = new Usuario(data.nome(), data.login(), encryptedPassword,data.cpf(), data.telefone(),data.role(), data.dataNasc());
	    return usuarioRepository.save(user);
	}
	
	public RegisterAdminResponseDTO registerAdmin(Usuario usuario, RegisterAdminDTO data){
	    if(usuarioRepository.findByEmail(data.login()) != null)
	        throw new RuntimeException("Usuário já cadastrado");
	    if(data.role().equals(UserRole.PROPRIETARIO) || data.role().equals(UserRole.CLIENTE)) 
	    	throw new ResponseStatusException(
	                HttpStatus.UNAUTHORIZED, "Apenas funcionarios do quiosques podem ser cadastrados");
	    Empresa empresa = empresaRepository.findByProprietario(usuario)
	            .orElseThrow(() -> new ResponseStatusException(
	                HttpStatus.NOT_FOUND, "Empresa não encontrada"
	            ));

	        Quiosque quiosque = quiosqueRepository.findById(data.id())
	            .orElseThrow(() -> new ResponseStatusException(
	                HttpStatus.NOT_FOUND, "Quiosque não encontrado"
	            ));

	        if (!empresa.getQuiosques().contains(quiosque)) {
	            throw new ResponseStatusException(
	                HttpStatus.FORBIDDEN, "Quiosque não pertence à empresa do usuário"
	            );
	        }
	    
	    
	    String encryptedPassword = passwordEncoder.encode(data.password());
	    Usuario user = new Usuario(data.nome(), data.login(), encryptedPassword, data.cpf(), data.telefone(),data.role(), data.dataNasc());
	    if(user.getRole().equals(UserRole.ADMIN))
	    	quiosque.getAdministradores().add(user);
	    else
	    	quiosque.getEntregadores().add(user);
	    this.usuarioRepository.save(user);
	    this.quiosqueRepository.save(quiosque);    
	    RegisterAdminResponseDTO response = new RegisterAdminResponseDTO(quiosque.getNome(), data.nome(),data.login(), data.role(), data.telefone(), data.dataNasc());
	    return response;
	}

}
