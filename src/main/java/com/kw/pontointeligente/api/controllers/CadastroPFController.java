package com.kw.pontointeligente.api.controllers;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kw.pontointeligente.api.builders.CadastroPFDtoBuilder;
import com.kw.pontointeligente.api.builders.FuncionarioBuilder;
import com.kw.pontointeligente.api.dtos.CadastroPFDto;
import com.kw.pontointeligente.api.entities.Empresa;
import com.kw.pontointeligente.api.entities.Funcionario;
import com.kw.pontointeligente.api.response.Response;
import com.kw.pontointeligente.api.services.EmpresaService;
import com.kw.pontointeligente.api.services.FuncionarioService;

@RestController
@RequestMapping("/api/cadastrar-pf")
@CrossOrigin(origins = "*")
public class CadastroPFController {

	private static final Logger log = LoggerFactory.getLogger(CadastroPFController.class);

	@Autowired
	private FuncionarioService funcionarioService;

	@Autowired
	private EmpresaService empresaService;

	@PostMapping
	public ResponseEntity<Response<CadastroPFDto>> cadastrar(@Valid @RequestBody CadastroPFDto cadastroPFDto,
			BindingResult result) throws NoSuchAlgorithmException {
		log.info("Cadastrando PF: {}", cadastroPFDto.getNome());

		Response<CadastroPFDto> response = new Response<CadastroPFDto>();

		validarDadosExistentes(cadastroPFDto, result);
		Funcionario funcionario = this.converterDtoParaFuncionario(cadastroPFDto, result);

		if (result.hasErrors()) {
			log.error("Erro ao validar os dados de cadastro de PF: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		Optional<Empresa> empresa = this.empresaService.findByCnpj(cadastroPFDto.getCnpj());
		empresa.ifPresent(emp -> funcionario.setEmpresa(emp));
		this.funcionarioService.persist(funcionario);

		response.setData(this.converterCadastroPFDTo(funcionario));

		return ResponseEntity.ok(response);

	}

	private void validarDadosExistentes(CadastroPFDto cadastroPFDto, BindingResult result) {

		Optional<Empresa> empresa = this.empresaService.findByCnpj(cadastroPFDto.getCnpj());
		if(!empresa.isPresent())
		{
			result.addError(new ObjectError("empresa", "Empresa não cadastrada"));
		}

		this.funcionarioService.findByCpf(cadastroPFDto.getCpf())
				.ifPresent(emp -> result.addError(new ObjectError("funcionario", "CPF já existe")));

		this.funcionarioService.findByEmail(cadastroPFDto.getEmail())
				.ifPresent(emp -> result.addError(new ObjectError("funcionario", "Email já existe")));

	}

	private Funcionario converterDtoParaFuncionario(CadastroPFDto cadastroPFDto, BindingResult result) {

		return FuncionarioBuilder.newFuncionario(cadastroPFDto).buildOne();
	}

	private CadastroPFDto converterCadastroPFDTo(Funcionario funcionario) {
		return CadastroPFDtoBuilder.newPFDto(funcionario).buildOne();
	}

}
