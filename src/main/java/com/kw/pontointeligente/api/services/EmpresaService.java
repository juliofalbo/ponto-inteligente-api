package com.kw.pontointeligente.api.services;

import java.util.Optional;

import com.kw.pontointeligente.api.entities.Empresa;

public interface EmpresaService {

	/**
	 * Retorna uma Empresa a partir de um CNPJ
	 * @param cnpj
	 * @return Optional<Empresa>
	 */
	Optional<Empresa> findByCnpj(String cnpj);

	/**
	 * Salva uma empresa no banco de dados
	 * @param empresa
	 * @return Empresa
	 */
	Empresa persist(Empresa empresa);

}
