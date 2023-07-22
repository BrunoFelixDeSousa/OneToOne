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
import java.util.Optional;

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

	public PessoaDTO buscarPessoaComEndereco(Long pessoaId) {
		Optional<Pessoa> pessoaOptional = pessoaRepository.findById(pessoaId);

		if (pessoaOptional.isPresent()) {
			Pessoa pessoa = pessoaOptional.get();
			Endereco endereco = pessoa.getEndereco();

			PessoaDTO pessoaDTO = new PessoaDTO();
			pessoaDTO.setNome(pessoa.getNome());

			if (endereco != null) {
				EnderecoDTO enderecoDTO = new EnderecoDTO(endereco.getRua(), endereco.getCidade(), endereco.getEstado());

				pessoaDTO.setEndereco(enderecoDTO);
			}

			return pessoaDTO;

		} else {
			return null;
		}


	}

	public boolean deletarPessoaComEndereco(Long pessoaId) {
		Optional<Pessoa> pessoaOptional = pessoaRepository.findById(pessoaId);

		if (pessoaOptional.isPresent()) {
			Pessoa pessoa = pessoaOptional.get();

			if (pessoa.getEndereco() != null) {
				enderecoRepository.delete(pessoa.getEndereco());
			}

			pessoaRepository.delete(pessoa);
			return true;
		} else {
			return false;
		}
	}

	public boolean atualizarPessoaComContato(Long pessoaId, PessoaDTO pessoaDTO) {
		// Verificar se a pessoa existe no banco de dados
		Optional<Pessoa> pessoaOptional = pessoaRepository.findById(pessoaId);

		if (pessoaOptional.isPresent()) {
			Pessoa pessoa = pessoaOptional.get();

			// Atualizar os dados da pessoa com base no objeto PessoaDTO
			pessoa.setNome(pessoaDTO.getNome());

			// Verificar se o endereço está presente no objeto PessoaDTO
			if (pessoaDTO.getEndereco() != null) {
				// Verificar se a pessoa já possui um endereço associado
				if (pessoa.getEndereco() != null) {
					// Se já tiver um endereço associado, atualizar os dados do endereço
					Endereco endereco = pessoa.getEndereco();
					endereco.setRua(pessoaDTO.getEndereco().getRua());
					endereco.setCidade(pessoaDTO.getEndereco().getCidade());
					endereco.setEstado(pessoaDTO.getEndereco().getEstado());
				} else {
					// Se não tiver um endereço associado, criar um novo endereço
					Endereco novoEndereco = new Endereco();
					novoEndereco.setRua(pessoaDTO.getEndereco().getRua());
					novoEndereco.setCidade(pessoaDTO.getEndereco().getCidade());
					novoEndereco.setEstado(pessoaDTO.getEndereco().getEstado());

					// Associar o novo endereço à pessoa
					pessoa.setEndereco(novoEndereco);
				}
			} else {
				// Se o objeto PessoaDTO não tiver informações de endereço,
				// verificamos se a pessoa possui um endereço associado e o removemos.
				pessoa.setEndereco(null);
			}

			// Salvar as alterações no banco de dados
			pessoaRepository.save(pessoa);
			return true; // Indicar que a atualização foi bem-sucedida
		}
		return false; // Indicar que a pessoa com o ID especificado não foi encontrada
	}
}
