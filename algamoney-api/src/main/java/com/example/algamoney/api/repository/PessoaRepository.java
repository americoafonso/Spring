package com.example.algamoney.api.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.algamoney.api.model.Pessoa;
import com.example.algamoney.api.repository.projection.ResumoPessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

	public Page<Pessoa> findByNomeContaining(String nome, Pageable pageable);
	
	// Lista por ordem alfabetica
	public List<Pessoa> findByAtivoOrderByNomeAsc(Boolean ativo);
	
	// Lista por ordem alfabetica inversa
	public List<ResumoPessoa> findByAtivoOrderByNomeDesc(Boolean ativo);
	
}
