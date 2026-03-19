package app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.entity.Empresa;
import app.repository.EmpresaRepository;

@Service
public class EmpresaService {
	
	
	
	@Autowired
	EmpresaRepository empresaRepository;
	
	public Empresa save(Empresa empresa) {			
		
		return this.empresaRepository.save(empresa);
	}
	

}
