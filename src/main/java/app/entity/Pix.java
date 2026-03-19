package app.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue("PIX")
public class Pix extends FormaPagamento { 
	
	private String chavePix;
	
	 @Override
    public boolean processarPagamento() {
        return true;
    }
}
