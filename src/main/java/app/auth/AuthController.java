package app.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.DTO.RegisterAdminResponseDTO;
import app.config.TokenService;
import app.entity.Usuario;
import jakarta.validation.Valid;

@RestController
@RequestMapping("auth")
public class AuthController {

	@Autowired
	TokenService tokenService;
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	AuthService authService;
	

	@PostMapping("/login")
	public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO data) {
		var userNamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
		var auth = this.authenticationManager.authenticate(userNamePassword);
		
		var token = tokenService.generateToken((Usuario) auth.getPrincipal());
		return ResponseEntity.ok(new LoginResponseDTO(token));
	}
	
	@PostMapping("/register")
	public ResponseEntity<Usuario> register(@RequestBody @Valid RegisterDTO data){		
		Usuario user = authService.register(data);
		return ResponseEntity.status(HttpStatus.CREATED).body(user);
	}
	
	@PreAuthorize("hasRole('PROPRIETARIO')")
	@PostMapping("/admin/register")
	public ResponseEntity<RegisterAdminResponseDTO> registerAdmin(@AuthenticationPrincipal Usuario usuario, @RequestBody @Valid RegisterAdminDTO data){		
		RegisterAdminResponseDTO user = authService.registerAdmin(usuario, data);
		return ResponseEntity.status(HttpStatus.CREATED).body(user);
	}
	
	@GetMapping("/me")
	public ResponseEntity<Usuario> me(@AuthenticationPrincipal Usuario user){
		return ResponseEntity.ok(user);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	@PreAuthorize("hasRole('PROPRIETARIO')")
//	@PostMapping("/register/admin")
//	public ResponseEntity<String> registerAdmin(@RequestBody @Valid RegisterDTO data){
//		
//		String encryptedPassword = passwordEncoder.encode(data.password());
//		Administrador newAdmin = new Administrador(data.nome(), data.login(),encryptedPassword,data.cpf(), data.telefone(), UserRole.ADMIN, data.dataNasc());	
//		
//		return authService.registerAdmin(data);
//	}
//	@PreAuthorize("hasRole('PROPRIETARIO')")
//	@PostMapping("/register/entregador")
//	public ResponseEntity<String> registerEntregador(@RequestBody @Valid RegisterDTO data){
//		
//		String encryptedPassword = passwordEncoder.encode(data.password());
//		Entregador newEntregador = new Entregador(data.nome(), data.login(),encryptedPassword,data.cpf(), data.telefone(), UserRole.ENTREGADOR, data.dataNasc());		
//		
//		
//		return authService.registerEntregador(data);
//	}
//	
//	@PostMapping("/register/proprietario")
//	public ResponseEntity<String> registerProprietario(@RequestBody @Valid RegisterDTO data){
//		
//		String encryptedPassword = passwordEncoder.encode(data.password());
//		Proprietario newProprietario = new Proprietario(data.nome(), data.login(),encryptedPassword,data.cpf(), data.telefone(), UserRole.PROPRIETARIO, data.dataNasc());		
//		
//		
//		return authService.registerProprietaio(data);
//	}
	
}