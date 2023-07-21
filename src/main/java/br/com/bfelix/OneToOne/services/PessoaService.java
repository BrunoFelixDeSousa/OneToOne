package br.com.bfelix.OneToOne.services;

import org.springframework.stereotype.Service;

import br.com.bfelix.OneToOne.entities.Endereco;
import br.com.bfelix.OneToOne.entities.Pessoa;
import br.com.bfelix.OneToOne.repositories.EnderecoRepository;
import br.com.bfelix.OneToOne.repositories.PessoaRepository;

@Service
public class PessoaService {
	
	private final PessoaRepository pessoaRepository;
	private final EnderecoRepository enderecoRepository;
	
	public PessoaService(PessoaRepository pessoaRepository, EnderecoRepository enderecoRepository) {
		this.pessoaRepository = pessoaRepository;
		this.enderecoRepository = enderecoRepository;
	}

	public void salvarPessoaComEndereco(Pessoa pessoa, Endereco endereco) {

		endereco.setPessoa(pessoa);
		pessoa.setEndereco(endereco);

		pessoaRepository.save(pessoa);
		enderecoRepository.save(endereco);
	}
	
	

}
