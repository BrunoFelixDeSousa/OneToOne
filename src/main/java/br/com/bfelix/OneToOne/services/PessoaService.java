package br.com.bfelix.OneToOne.services;

import br.com.bfelix.OneToOne.DTO.EnderecoDTO;
import br.com.bfelix.OneToOne.DTO.PessoaDTO;
import org.springframework.stereotype.Service;

import br.com.bfelix.OneToOne.entities.Endereco;
import br.com.bfelix.OneToOne.entities.Pessoa;
import br.com.bfelix.OneToOne.repositories.EnderecoRepository;
import br.com.bfelix.OneToOne.repositories.PessoaRepository;

import java.util.ArrayList;
import java.util.List;

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

	public List<PessoaDTO> listarPessoasComEndereco() {
		List<Pessoa> pessoas = pessoaRepository.findAll();
		List<PessoaDTO> pessoasDTO = new ArrayList<>();

		for (Pessoa pessoa : pessoas) {
			Endereco endereco = enderecoRepository.findByPessoa(pessoa);
			PessoaDTO pessoaDTO = new PessoaDTO();
			pessoaDTO.setNome(pessoa.getNome());

			if (endereco != null) {
				EnderecoDTO enderecoDTO = new EnderecoDTO();
				enderecoDTO.setRua(endereco.getRua());
				enderecoDTO.setCidade(endereco.getCidade());
				enderecoDTO.setEstado(endereco.getEstado());

				pessoaDTO.setEndereco(enderecoDTO);
			}

			pessoasDTO.add(pessoaDTO);
		}

		return pessoasDTO;
	}
}
