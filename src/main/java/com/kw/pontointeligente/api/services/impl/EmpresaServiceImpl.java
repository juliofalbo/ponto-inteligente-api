package com.kw.pontointeligente.api.services.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kw.pontointeligente.api.entities.Empresa;
import com.kw.pontointeligente.api.repositories.EmpresaRepository;
import com.kw.pontointeligente.api.services.EmpresaService;

@Service
public class EmpresaServiceImpl implements EmpresaService {

	private static final Logger log = LoggerFactory.getLogger(EmpresaServiceImpl.class);
	
	@Autowired
	private EmpresaRepository empresaRepository;
	
	@Override
	public Optional<Empresa> findByCnpj(String cnpj) {
		log.info("Buscando uma empresa para o CNPJ {}", cnpj);
		return Optional.ofNullable(this.empresaRepository.findByCnpj(cnpj));
	}

	@Override
	public Empresa persist(Empresa empresa) {
		log.info("Persistindo a empresa com o CNPJ {}", empresa.getCnpj());
		return this.empresaRepository.save(empresa);
	}

}
