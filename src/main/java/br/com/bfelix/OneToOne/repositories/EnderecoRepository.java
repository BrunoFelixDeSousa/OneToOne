package br.com.bfelix.OneToOne.repositories;

import br.com.bfelix.OneToOne.entities.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.bfelix.OneToOne.entities.Endereco;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long>{

    Endereco findByPessoa(Pessoa pessoa);
}
