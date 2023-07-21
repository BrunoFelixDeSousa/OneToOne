package br.com.bfelix.OneToOne.controllers;

import br.com.bfelix.OneToOne.DTO.PessoaEnderecoDTO;
import br.com.bfelix.OneToOne.entities.Endereco;
import br.com.bfelix.OneToOne.entities.Pessoa;
import br.com.bfelix.OneToOne.services.PessoaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/pessoas")
public class PessoaController {

	private final PessoaService pessoaService;

	public PessoaController(PessoaService pessoaService) {
		this.pessoaService = pessoaService;
	}
	
	@PostMapping
	public ResponseEntity<Object> CriarPessoa(@RequestBody PessoaEnderecoDTO pessoaEnderecoDTO) {

		Pessoa pessoa = new Pessoa();
		pessoa.setNome(pessoaEnderecoDTO.getNome());
		
		Endereco endereco = new Endereco();
		endereco.setRua(pessoaEnderecoDTO.getEnderecoDTO().getRua());
        endereco.setCidade(pessoaEnderecoDTO.getEnderecoDTO().getCidade());
        endereco.setEstado(pessoaEnderecoDTO.getEnderecoDTO().getEstado());
        
        pessoaService.salvarPessoaComEndereco(pessoa, endereco);
        
        return ResponseEntity.status(HttpStatus.CREATED).body("Pessoa cadastrado com sucesso!");
	}

}
