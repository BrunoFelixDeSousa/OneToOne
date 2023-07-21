package br.com.bfelix.OneToOne.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PessoaEnderecoDTO {

	private String nome;
	private EnderecoDTO enderecoDTO;
}
