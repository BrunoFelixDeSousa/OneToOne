package br.com.bfelix.OneToOne.controllers;

import br.com.bfelix.OneToOne.DTO.PessoaDTO;
import br.com.bfelix.OneToOne.entities.Endereco;
import br.com.bfelix.OneToOne.entities.Pessoa;
import br.com.bfelix.OneToOne.services.PessoaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/pessoas")
public class PessoaController {

	private final PessoaService pessoaService;

	public PessoaController(PessoaService pessoaService) {
		this.pessoaService = pessoaService;
	}
	
	@PostMapping
	public ResponseEntity<Object> CriarPessoa(@RequestBody PessoaDTO pessoaDTO) {

		Pessoa pessoa = new Pessoa();
		pessoa.setNome(pessoaDTO.getNome());
		
		Endereco endereco = new Endereco();
		endereco.setRua(pessoaDTO.getEndereco().getRua());
        endereco.setCidade(pessoaDTO.getEndereco().getCidade());
        endereco.setEstado(pessoaDTO.getEndereco().getEstado());
        
        pessoaService.salvarPessoaComEndereco(pessoa, endereco);
        
        return ResponseEntity.status(HttpStatus.CREATED).body("Pessoa cadastrado com sucesso!");
	}

	@GetMapping
	public ResponseEntity<List<PessoaDTO>> listarPessoas() {
		List<PessoaDTO> pessoasComEndereco = pessoaService.listarPessoasComEndereco();

		if (pessoasComEndereco.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(pessoasComEndereco);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> buscarPessoa(@PathVariable(value = "id") Long id) {
		PessoaDTO pessoaDTO = pessoaService.buscarPessoaComEndereco(id);

		if (pessoaDTO != null) {
			return ResponseEntity.status(HttpStatus.OK).body(pessoaDTO);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pessoa Não encontrado!");
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deletarPessoa(@PathVariable(value = "id") Long id) {
		boolean deletado = pessoaService.deletarPessoaComEndereco(id);

		if (deletado) {
			return ResponseEntity.status(HttpStatus.OK).body("Pessoa e endereço deletados com sucesso.");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pessoa não encotrado.");
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<Object> atualizarPessoa(@PathVariable(value = "id") Long id, @RequestBody PessoaDTO pessoaDTO) {
		boolean atualizado = pessoaService.atualizarPessoaComContato(id, pessoaDTO);

		if (atualizado) {
			return ResponseEntity.status(HttpStatus.OK).body("Pessoa e endereço atualizado com sucesso.");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pessoa não encotrado.");
		}
	}
}
