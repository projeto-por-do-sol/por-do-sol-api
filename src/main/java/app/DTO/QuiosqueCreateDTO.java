package app.DTO;


public record QuiosqueCreateDTO(
		
		String nome,
		String email,
		double latitude,
		double longitude,
		String empresa,
		String proprietario
		) {

}
