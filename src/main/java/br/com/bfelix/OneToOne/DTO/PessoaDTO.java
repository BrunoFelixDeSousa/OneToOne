package br.com.bfelix.OneToOne.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PessoaDTO {

	private String nome;
	@JsonProperty("endereco")
	private EnderecoDTO endereco;
}
