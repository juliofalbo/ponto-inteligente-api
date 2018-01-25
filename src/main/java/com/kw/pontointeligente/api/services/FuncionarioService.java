package com.kw.pontointeligente.api.services;

import java.util.Optional;

import com.kw.pontointeligente.api.entities.Funcionario;

public interface FuncionarioService {

	/**
	 * Salva um funcionário no banco de dados
	 * 
	 * @param funcionario
	 * @return Funcionario
	 */
	Funcionario persist(Funcionario funcionario);

	/**
	 * Retorna um Funcionario a partir de um CPF
	 * 
	 * @param cpf
	 * @return Optional<Funcionario>
	 */
	Optional<Funcionario> findByCpf(String cpf);

	/**
	 * Retorna um Funcionario a partir de um email
	 * 
	 * @param email
	 * @return Optional<Funcionario>
	 */
	Optional<Funcionario> findByEmail(String email);
	
	/**
	 * Retorna um Funcionario a partir de um id
	 * 
	 * @param id
	 * @return Optional<Funcionario>
	 */
	Optional<Funcionario> findById(Long id);

}
