package br.com.bfelix.OneToOne.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EnderecoDTO {
	
	private String rua;
    private String cidade;
    private String estado;
}
