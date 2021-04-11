package com.example.algamoney.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.algamoney.api.model.Pessoa;
import com.example.algamoney.api.repository.PessoaRepository;
import com.example.algamoney.api.repository.projection.ResumoPessoa;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	public Pessoa salvar(Pessoa pessoa) {
		return pessoaRepository.save(pessoa);
	}

	public Pessoa atualizar(Long codigo, Pessoa pessoa) {
		Pessoa pessoaSalva = buscarPessoaPeloCodigo(codigo);
		BeanUtils.copyProperties(pessoa, pessoaSalva, "codigo");
		return pessoaRepository.save(pessoaSalva);
	}
	
	/**
	 * Listagem das Pessoas paginadas.
	 * @param pageable
	 * 
	 */
	public Page<Pessoa> listar(Pageable pageable) {
		return pessoaRepository.findAll(pageable);		
	}
	
	public List<Pessoa> listAtivos(Boolean ativo) {
		return pessoaRepository.findByAtivoOrderByNomeAsc(ativo);
	}
	
	public List<ResumoPessoa> buscarAtivos(Boolean ativo) {
		return  pessoaRepository.findByAtivoOrderByNomeDesc(ativo);
	}
	
	public Page<Pessoa> pesquisar(String nome, Pageable pageable) {
		return pessoaRepository.findByNomeContaining(nome, pageable);		
	}
	
	
	/**
	 * Metodo para atualizar a propriedade ativo do tipo Boolean da entidade Pessoa
	 * @param codigo
	 * @param ativo
	 */
	public void atualizarPropriedadeAtivo(Long codigo, Boolean ativo) {
		Pessoa pessoaSalva = buscarPessoaPeloCodigo(codigo);
		pessoaSalva.setAtivo(ativo);
		pessoaRepository.save(pessoaSalva);
	}

	/**
	 * Metodo para validar se o Id/codigo da pessoa existe
	 * @param codigo
	 * @return
	 */
	public Pessoa buscarPessoaPeloCodigo(Long codigo) {
		Pessoa pessoaSalva = pessoaRepository.findById(codigo)
				.orElseThrow(() -> new EmptyResultDataAccessException(1));// => se pessoa for null retorna a excessao.
		return pessoaSalva;
	}

}
