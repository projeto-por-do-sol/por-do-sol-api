package app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import app.DTO.QuiosqueCreateDTO;
import app.DTO.QuiosqueDTO;
import app.entity.Empresa;
import app.entity.Item;
import app.entity.Quiosque;
import app.entity.Usuario;
import app.repository.EmpresaRepository;
import app.repository.ItemRepository;
import app.repository.QuiosqueRepository;

@Service
public class QuiosqueService {

	@Autowired
    private ItemRepository itemRepository;
	@Autowired
	private QuiosqueRepository quiosqueRepository;
	@Autowired
	private EmpresaRepository empresaRepository;


	public QuiosqueCreateDTO save(
			Usuario usuario,
			QuiosqueDTO data) {
		
		
		Empresa empresa = empresaRepository.findByProprietario(usuario)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,("Empresa não encontrada")));		
		Quiosque q = new Quiosque(data.nome(), data.email(), data.latitude(), data.longitude(), empresa);	
		this.quiosqueRepository.save(q);	
		QuiosqueCreateDTO responseData = new QuiosqueCreateDTO(q.getNome(),q.getEmail(),q.getLatitude(),q.getLongitude(),q.getEmpresa().getRazaoSocial(),q.getEmpresa().getProprietario().getNome());
		return responseData;
	}	

	public List<Quiosque> findByDistancia(
			double latUsuario,
			double lonUsuario,
			double raioM) {
		
		
		double R = 6371;
	    double latDiff = Math.toDegrees(raioM / R);
	    double lonDiff = Math.toDegrees(raioM / (R * Math.cos(Math.toRadians(latUsuario))));
	    double minLat = latUsuario - latDiff;
	    double maxLat = latUsuario + latDiff;
	    double minLon = lonUsuario - lonDiff;
	    double maxLon = lonUsuario + lonDiff;
	    List<Quiosque> lista = this.quiosqueRepository.findByDistancia(latUsuario, lonUsuario, raioM, minLat, maxLat, minLon, maxLon);
	    
	    for(Quiosque q : lista) {
	    	Long dist = calcularDistancia(latUsuario, lonUsuario, q.getLatitude(), q.getLongitude());	    	
	    	if(dist > raioM) 				//Invert
	    		lista.remove(q);
	    	q.setDistancia(dist);	    	//Invert
	    }
	    
	    
	    return lista;	    
    }
	
	public Quiosque findById(
			long id) {
		
		
		return this.quiosqueRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,("Quiosque não encontrado")));
	}
	
	public List<Item> findByQuiosque(
			long id){
		
		
		Quiosque q = this.quiosqueRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,("Quiosque não encontrado")));
		return this.itemRepository.findByQuiosque(q);
	}
	
	
	
	
	public List<Quiosque> findAll(){
		return this.quiosqueRepository.findAll();
	}
	

	
	public Long calcularDistancia(
	        double lat1, double lon1,
	        double lat2, double lon2) {

	    final int R = 6371000; // metros

	    double dLat = Math.toRadians(lat2 - lat1);
	    double dLon = Math.toRadians(lon2 - lon1);

	    double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
	            Math.cos(Math.toRadians(lat1)) *
	            Math.cos(Math.toRadians(lat2)) *
	            Math.sin(dLon/2) * Math.sin(dLon/2);

	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

	    return Math.round(R * c);
	}
}
