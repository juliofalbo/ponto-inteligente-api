package com.kw.pontointeligente.api.services.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kw.pontointeligente.api.entities.Funcionario;
import com.kw.pontointeligente.api.repositories.FuncionarioRepository;
import com.kw.pontointeligente.api.services.FuncionarioService;

@Service
public class FuncionarioServiceImpl implements FuncionarioService {

	private static final Logger log = LoggerFactory.getLogger(FuncionarioServiceImpl.class);

	@Autowired
	private FuncionarioRepository funcionarioServiceImpl;

	@Override
	public Funcionario persist(Funcionario funcionario) {
		log.info("Persistindo um funcionário com o CPF {}", funcionario.getCpf());
		return this.funcionarioServiceImpl.save(funcionario);
	}

	@Override
	public Optional<Funcionario> findByCpf(String cpf) {
		log.info("Buscando um funcionário para o CPF {}", cpf);
		return Optional.ofNullable(this.funcionarioServiceImpl.findByCpf(cpf));
	}

	@Override
	public Optional<Funcionario> findByEmail(String email) {
		log.info("Buscando um funcionário para o EMAIL {}", email);
		return Optional.ofNullable(this.funcionarioServiceImpl.findByEmail(email));
	}

	@Override
	public Optional<Funcionario> findById(Long id) {
		log.info("Buscando um funcionário para o ID {}", id);
		return Optional.ofNullable(this.funcionarioServiceImpl.findOne(id));
	}

}
