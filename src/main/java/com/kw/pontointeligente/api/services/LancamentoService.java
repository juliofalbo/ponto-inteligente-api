package com.kw.pontointeligente.api.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.kw.pontointeligente.api.entities.Lancamento;

public interface LancamentoService {

	/**
	 * Retorna uma lista paginada de lançamentos de um determinado funcionário.
	 * 
	 * @param funcionarioId
	 * @param pageRequest
	 * @return Page<Lancamento>
	 */
	Page<Lancamento> findByFuncionarioId(Long funcionarioId, PageRequest pageRequest);

	/**
	 * Retorna um lançamento por ID.
	 * 
	 * @param id
	 * @return Optional<Lancamento>
	 */
	Optional<Lancamento> findById(Long id);

	/**
	 * Persiste um lançamento na base de dados.
	 * 
	 * @param lancamento
	 * @return Lancamento
	 */
	Lancamento persist(Lancamento lancamento);

	/**
	 * Remove um lançamento da base de dados.
	 * 
	 * @param id
	 */
	void delete(Long id);

}